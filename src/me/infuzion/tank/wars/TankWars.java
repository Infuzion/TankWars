package me.infuzion.tank.wars;

import me.infuzion.tank.wars.object.Position;
import me.infuzion.tank.wars.object.TankProjectile;
import me.infuzion.tank.wars.object.Velocity;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.provider.LocalInfoProvider;
import me.infuzion.tank.wars.render.Renderer;
import me.infuzion.tank.wars.render.swing.SwingRenderer;

public class TankWars {
    private static final double frameTime = 1000 / 60;

    public void start() {
        InfoProvider provider = new LocalInfoProvider();
        Renderer renderer = new SwingRenderer(provider);

        long start = System.currentTimeMillis();
        int fps = 0;
        TankProjectile p = new TankProjectile(new Position(640d, 480d), -20);
        provider.addGameObject(p);
        while (true) {
            System.out.println(p.getPosition().getX() + " " + p.getPosition().getY());
            long time = System.currentTimeMillis();
            fps++;
            if (start + 1000 <= time) {
                provider.addGameObject(p);
                provider.setFPS(fps);
                start = time;
                fps = 0;
            }
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
