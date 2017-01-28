package me.infuzion.tank.wars.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.provider.LocalInfoProvider;
import me.infuzion.tank.wars.provider.remote.GameState;
import me.infuzion.tank.wars.util.Settings;

public class Server implements Runnable {
    private List<Player> players = new CopyOnWriteArrayList<>();
    private long tick = 0;
    private ServerSocket serverSocket;

    public void start() throws IOException {
        serverSocket = new ServerSocket(7572);
        new Thread(this).start();

        InfoProvider provider = new LocalInfoProvider();


        //Game loop
        new Thread(() -> {
            long start = System.currentTimeMillis();
            int tps = 0;
            while (true) {

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
            }
        }).start();

        while (true) {
            List<Player> toRemove = new ArrayList<>();
            for (Player e : players) {
                try {

                    GameState toSend = new GameState(provider.getTanks(), provider.getGameObjects(),
                            provider.getDrawableObjects(), provider.getTickableObjects(),
                            provider.getTPS(), provider.getTick());
                    ObjectOutputStream objOut = new ObjectOutputStream(e.socket.getOutputStream());
                    objOut.writeObject(toSend);
                    objOut.flush();
                } catch (IOException a) {
                    a.printStackTrace();
                    toRemove.add(e);
                }
            }
            for (Player e : toRemove) {
                players.remove(e);
            }
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
                        new LocalInfoProvider()), s));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
