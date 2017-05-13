package me.infuzion.tank.wars.object.perk;

import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.projectile.Projectile;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.GraphicsObject;

public class PerkLaser implements Perk {

    private final static int MAX_BOUNCES = 5;
    private Tank tank;

    private int[] x = new int[MAX_BOUNCES * 2];
    private int[] y = new int[MAX_BOUNCES * 2];

    @Override
    public void apply(Tank tank) {
        this.tank = tank;
    }

    @Override
    public boolean draw(GraphicsObject graphics) {
        if (!tank.isAlive()) {
            return false;
        }
        int x_last = (int) tank.getCenter().getX();
        int y_last = (int) tank.getCenter().getY();
//        graphics.setColor(Color.RED);
//        for (int i = 0; i < x.length; i++) {
//            graphics.drawLine(x_last, y_last, x[i], y[0]);
//            x_last = x[i];
//            y_last = y[i];
//        }
        return true;
    }

    @Override
    public void tick(InfoProvider provider) {
        if (tank == null || !tank.isAlive()) {
            return;
        }

        int currentBounce = 0;
        int pointCounter = 0;

        double speed = 0.5;

        int rot = tank.getRot() - 180;
        double rotRadians = Math.toRadians(rot - 90);

        double velX = speed * Math.cos(rotRadians);
        double velY = speed * Math.sin(rotRadians);

        double curX = tank.getCenter().getX();
        double curY = tank.getCenter().getY();

        int counter = 0;
        int[] x_temp = new int[x.length];
        int[] y_temp = new int[y.length];

        outsideLoop:
        while (currentBounce <= MAX_BOUNCES) {
            if (counter > 250) {
                return;
            }
            counter++;
            for (GameObject o : provider.getGameObjects()) {
                if (o instanceof Projectile || !o.hasCollision()
                        || o.getBounds() == null) {
                    continue;
                }

                curX += velX;
                curY += velY;
                if (o.getBounds().contains(curX, curY)) {
                    currentBounce++;
                    x_temp[pointCounter] = (int) curX;
                    y_temp[pointCounter] = (int) curY;
                    pointCounter++;
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
                    curX += velX;
                    curY += velY;
                    if (o instanceof Tank) {
                        break outsideLoop;
                    }
                }
            }
        }

        x = x_temp;
        y = y_temp;
    }
}

