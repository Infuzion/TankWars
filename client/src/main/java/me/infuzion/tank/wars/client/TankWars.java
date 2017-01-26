package me.infuzion.tank.wars.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.infuzion.tank.wars.client.render.Renderer;
import me.infuzion.tank.wars.client.render.swing.SwingKeyListener;
import me.infuzion.tank.wars.client.render.swing.SwingRenderer;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.provider.LocalInfoProvider;
import me.infuzion.tank.wars.util.Position;
import me.infuzion.tank.wars.util.Settings;

public class TankWars {

    private static long tick = 0;

    public void start() throws IOException {
        InfoProvider provider = new LocalInfoProvider();
        SwingKeyListener keyListener = new SwingKeyListener(provider);
        Renderer renderer = new SwingRenderer(provider, keyListener);

        long startTime = System.currentTimeMillis();
        //Render loop
        Thread renderLoop = new Thread(() -> {
            int fps = 0;
            long start = System.currentTimeMillis();
            while (true) {
                if (provider.getQuit()) {
                    renderer.stop();
                    return;
                }
                long time = System.currentTimeMillis();
                fps++;
                if (start + 1000 <= time) {
                    provider.setFPS(fps);
                    start = time;
                    fps = 0;
                }
                renderer.draw();
                while (System.currentTimeMillis() < time + Settings.frameTime) {
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Game loop
        Thread gameLoop = new Thread(() -> {
            long start = System.currentTimeMillis();
            int tps = 0;
            while (!provider.isRemote()) {
                if (provider.getQuit()) {
                    return;
                }
                long time = System.currentTimeMillis();
                tps++;
                tick++;
                provider.setTick(tick);
                if (start + 1000 <= time) {
                    provider.setTPS(tps);
                    start = time;
                    tps = 0;
                }
                keyListener.tick();

                List<GameObject> toRemove = new ArrayList<>();

                for (Tickable object : provider.getTickableObjects()) {
                    object.tick(provider);
                    Position position = object.getPosition();
                    if (position != null && (position.getX() > Settings.SCREEN_WIDTH + 100
                        || position.getX() < -100 ||
                        position.getY() > Settings.SCREEN_HEIGHT + 100 || position.getY() < -100)) {
                        toRemove.add(object);
                    }
                }
                for (GameObject o : toRemove) {
                    o.destroy(provider);
                }
                while (System.currentTimeMillis() < time + Settings.tickTime) {
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        renderLoop.setName("Render loop");
        gameLoop.setName("Game loop");

        renderLoop.start();
        gameLoop.start();

        long endTime = System.currentTimeMillis();
        System.out.println("loaded in " + (endTime - startTime) + "ms");
    }
}
