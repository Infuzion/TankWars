package me.infuzion.tank.wars.object;

import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.Position;
import me.infuzion.tank.wars.util.Velocity;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.UUID;

public class TankProjectile implements Projectile {
    private final static int speed = 40;
    private final UUID uuid;
    private Position position;
    private Velocity velocity;
    private int lifetime = 0;
    private int rot;
    private Shape bounds = new Ellipse2D.Double();


    public TankProjectile(Position position, int rotation) {
        this.position = position.copy();
        this.rot = rotation;
        this.uuid = UUID.randomUUID();
        calculateVelocity();
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isDestroyable() {
        return true;
    }

    @Override
    public Shape getBounds() {
        return bounds;
    }

    @Override
    public void setBounds(Shape bounds) {
        this.bounds = bounds;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void draw(Graphics2D g) {
        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrame(position.getX(), position.getY(), 15, 15);
        setBounds(circle);
        g.setColor(Color.blue);
        g.draw(circle);
    }

    private void move() {
        position.setX(position.getX() + velocity.getxVelocity());
        position.setY(position.getY() + velocity.getyVelocity());
    }

    @Override
    public void tick(InfoProvider provider) {
        lifetime++;
        if (lifetime < 3) {
            move();
            return;
        }
        if (lifetime > 750) {
            provider.removeGameObject(this);
            return;
        }

        GameObject toDestroy = null;
        Area collision = new Area(getBounds());
        for (GameObject o : provider.getGameObjects()) {
            if (o instanceof Projectile) {
                continue;
            }

            Area collision2 = new Area(o.getBounds());
            collision2.intersect(collision);
            if (!collision2.isEmpty()) {
                position.setX(position.getX() - velocity.getxVelocity());
                position.setY(position.getY() - velocity.getyVelocity());
                toDestroy = o;
                break;
            }
        }
        if (toDestroy != null) {
            if (toDestroy.isDestroyable()) {
                provider.removeGameObject(this);
                provider.removeGameObject(toDestroy);
            } else {
                double avgX = toDestroy.getBounds().getBounds().getMaxX() - toDestroy.getBounds().getBounds().getMinX();
                double avgY = toDestroy.getBounds().getBounds().getMaxY() - toDestroy.getBounds().getBounds().getMinY();
                if (avgX < avgY) {
                    rot = 360 - rot;
                } else {
                    rot = 180 - rot;
                }
                calculateVelocity();
            }
        }
        move();
    }

    private void calculateVelocity() {
        double rotRadians = Math.toRadians(rot - 90);
        double velX = speed * Math.cos(rotRadians);
        double velY = speed * Math.sin(rotRadians);
        velocity = new Velocity(velX, velY);
    }

    @Override
    public Velocity getVelocity() {
        return velocity;
    }
}
