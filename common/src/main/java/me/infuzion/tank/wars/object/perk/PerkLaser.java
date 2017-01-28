package me.infuzion.tank.wars.object.perk;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.projectile.Projectile;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.GraphicsObject;

public class PerkLaser implements Perk {

    private final static int MAX_BOUNCES = 5;
    private List<Integer[]> points = new CopyOnWriteArrayList<>();
    private Tank tank;

    @Override
    public void apply(Tank tank) {
        this.tank = tank;
    }

    @Override
    public boolean draw(GraphicsObject graphics) {
        if (!tank.isAlive()) {
            return false;
        }
        final int[] x_last = {(int) tank.getCenter().getX()};
        final int[] y_last = {(int) tank.getCenter().getY()};
        points.forEach((Integer[] a) -> {
            graphics.setColor(Color.RED);
            graphics.drawLine(x_last[0], y_last[0], a[0], a[1]);
            x_last[0] = a[0];
            y_last[0] = a[1];
        });
        return true;
    }

    @Override
    public void tick(InfoProvider provider) {
        if (tank == null || !tank.isAlive()) {
            return;
        }
        int currentBounce = 0;
        double speed = 0.5;
        double curX = tank.getCenter().getX();
        double curY = tank.getCenter().getY();
        int rot = tank.getRot() - 180;
        double rotRadians = Math.toRadians(rot - 90);
        double velX = speed * Math.cos(rotRadians);
        double velY = speed * Math.sin(rotRadians);
        int counter = 0;
        List<Integer[]> points_temp = new ArrayList<>();
        while (currentBounce <= MAX_BOUNCES) {
            if (counter > 150) {
                return;
            }
            counter++;
            for (GameObject o : provider.getGameObjects()) {
                if (o == tank || o instanceof Projectile || !o.hasCollision()
                    || o.getBounds() == null) {
                    continue;
                }

                curX += velX;
                curY += velY;
                if (o.getBounds().contains(curX, curY)) {
                    currentBounce++;
                    points_temp.add(new Integer[]{(int) curX, (int) curY});
                    double avgX =
                        o.getBounds().getBounds().getMaxX() - o.getBounds().getBounds()
                            .getMinX();
                    double avgY =
                        o.getBounds().getBounds().getMaxY() - o.getBounds().getBounds()
                            .getMinY();
                    if (avgX < avgY) {
                        rot = 360 - rot;
                    } else {
                        rot = 180 - rot;
                    }
                    rotRadians = Math.toRadians(rot - 90);
                    velX = speed * Math.cos(rotRadians);
                    velY = speed * Math.sin(rotRadians);
                    curX = (curX - Math.signum(velX) * 2);
                    curY = (curY - Math.signum(velY) * 2);
                    points.clear();
                    points.addAll(points_temp);
                    if (o instanceof Tank) {
                        return;
                    }
                }
            }
        }
    }
}
