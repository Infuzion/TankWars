package me.infuzion.tank.wars;

import me.infuzion.tank.wars.provider.LocalInfoProvider;
import me.infuzion.tank.wars.render.Renderer;
import me.infuzion.tank.wars.render.swing.SwingRenderer;

public class TankWars {
    private static double frameTime = 1000 / 60;

    public void start() {

        Renderer renderer = new SwingRenderer(new LocalInfoProvider());
        Tank p1 = new Tank("Player 1", 0, 0, 0);

        while (true) {
            long time = System.currentTimeMillis();
            renderer.draw();
            while (System.currentTimeMillis() < time + frameTime) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
