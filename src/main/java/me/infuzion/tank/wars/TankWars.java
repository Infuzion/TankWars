package me.infuzion.tank.wars;

import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.TankProjectile;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.provider.LocalInfoProvider;
import me.infuzion.tank.wars.render.Renderer;
import me.infuzion.tank.wars.render.swing.SwingKeyListener;
import me.infuzion.tank.wars.render.swing.SwingRenderer;
import me.infuzion.tank.wars.util.Position;
import me.infuzion.tank.wars.util.Settings;

import java.util.ArrayList;
import java.util.List;

public class TankWars {
    public static long tick = 0;
    public static final long frameLimit = 60;
    public static final double frameTime = 1000 / frameLimit;

    public static final long tickLimit = 20;
    public static final double tickTime = 1000 / tickLimit;

    public void start() {
        InfoProvider provider = new LocalInfoProvider();
        SwingKeyListener keyListener = new SwingKeyListener(provider);
        Renderer renderer = new SwingRenderer(provider, keyListener);

        TankProjectile p = new TankProjectile(new Position(640d, 480d), -20);
        provider.addGameObject(p);

        //Render loop
        new Thread(() -> {
            int fps = 0;
            long start = System.currentTimeMillis();
            while (true) {
                long time = System.currentTimeMillis();
                fps++;
                if (start + 1000 <= time) {
                    provider.setFPS(fps);
                    start = time;
                    fps = 0;
                }
                renderer.draw();
                while (System.currentTimeMillis() < time + frameTime) {
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //Game loop
        new Thread(() -> {
            while (true) {
                long time = System.currentTimeMillis();
                tick++;
                keyListener.tick();

                List<GameObject> toRemove = new ArrayList<>();

                for (Tickable object : provider.getTickableObjects()) {
                    object.tick(provider);
                    Position position = object.getPosition();
                    if (position.getX() > Settings.SCREEN_WIDTH + 100 || position.getX() < -100 ||
                            position.getY() > Settings.SCREEN_HEIGHT + 100 || position.getY() < -100) {
                        toRemove.add(object);
                    }
                }
                for (GameObject o : toRemove) {
                    provider.removeGameObject(o);
                }
                while (System.currentTimeMillis() < time + tickTime) {
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
