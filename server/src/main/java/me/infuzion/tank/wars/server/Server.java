package me.infuzion.tank.wars.server;

import me.infuzion.tank.wars.input.Action;
import me.infuzion.tank.wars.input.ActionType;
import me.infuzion.tank.wars.input.executor.InputExecutor;
import me.infuzion.tank.wars.input.executor.InputType;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.protobuf.Game;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.provider.LocalInfoProvider;
import me.infuzion.tank.wars.util.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server implements Runnable {

    private List<Player> players = new CopyOnWriteArrayList<>();
    private long tick = 0;
    private ServerSocket serverSocket;
    private InfoProvider provider;

    public void start() throws IOException {
        serverSocket = new ServerSocket(7572);
        System.out.println("Starting server at " +
                serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
        new Thread(this).start();

        provider = new LocalInfoProvider();
        provider.run();

        //Game loop
        new Thread(() -> {
            long start = System.currentTimeMillis();
            int tps = 0;
            while (true) {
                try {
                    long time = System.currentTimeMillis();
                    tps++;
                    tick++;
                    provider.setTick(tick);
                    if (start + 1000 <= time) {
                        provider.setTPS(tps);
                        start = time;
                        tps = 0;
                    }

                    for (Drawable obj : provider.getDrawableObjects()) {
                        obj.draw(new EmptyGraphics());
                    }

                    for (Tickable object : provider.getTickableObjects()) {
                        object.tick(provider);
                    }

                    while (System.currentTimeMillis() < time + Settings.tickTime) {
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Tank("1", 250, 250, 2, provider);
        new Tank("1", 250, 250, 2, provider);

        InputExecutor executor = new ServerInputExecutor(provider, players);
        while (true) {
            List<Player> toRemove = new ArrayList<>();
            for (Player e : players) {
                try {
                    if (e.socket.getInputStream().available() <= 1) {
                        continue;
                    }
                    System.out.println("Waiting for " + e.socket.toString());
                    Game.Action a = Game.Action.parseDelimitedFrom(e.socket.getInputStream());
                    ActionType type = ActionType.fromProtobufAction(a.getType());
                    InputType inputType = InputType.fromProtobufAction(a.getAction());
                    executor.executeAction(provider.getTanks().get(e.tankID), new Action(type, e.tankID), inputType);
                    System.out.println("Received @ " + System.currentTimeMillis());
                } catch (IOException a) {
                    a.printStackTrace();
                    toRemove.add(e);
                }
            }
            players.removeAll(toRemove);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void run() {
        try {
            while (true) {
                Socket s = serverSocket.accept();
                players.add(new Player("", UUID.randomUUID(), new Tank("", 250, 320, 90,
                        provider), s));
                System.out.println("Added player!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
