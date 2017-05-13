package me.infuzion.tank.wars.client;

import me.infuzion.tank.wars.client.render.Renderer;
import me.infuzion.tank.wars.client.render.fx.FxRenderer;
import me.infuzion.tank.wars.input.executor.InputExecutor;
import me.infuzion.tank.wars.input.executor.RemoteInputExecutor;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.provider.RemoteInfoProvider;
import me.infuzion.tank.wars.util.Settings;

import java.io.IOException;

public class TankWars {

    private static long tick = 0;

    public void start() throws IOException {

//        InfoProvider provider = new LocalInfoProvider();
//        InputExecutor executor = new LocalInputExecutor(provider);
        RemoteInfoProvider provider = new RemoteInfoProvider();
        provider.run();
        InputExecutor executor = new RemoteInputExecutor(provider);

        Renderer renderer = new FxRenderer(provider, executor);
//        Renderer renderer = new SwingRenderer(provider, (LocalInputExecutor) executor);
        long startTime = System.currentTimeMillis();
        //Render loop
        Thread renderLoop = new Thread(() -> {
            int fps = 0;
            long start = System.currentTimeMillis();
            while (true) {
                if (provider.getQuit()) {
                    renderer.stopRenderer();
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
//                if (fps % 2 == 0) {
//                    for (Tickable object : provider.getTickableObjects()) {
//                        object.tick(provider);
//                    }
//                }
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
            while (true) {
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
        });

        renderLoop.setName("Render loop");
        gameLoop.setName("Game loop");

        renderLoop.start();
        gameLoop.start();

        long endTime = System.currentTimeMillis();
        System.out.println("loaded in " + (endTime - startTime) + "ms");
    }
}
