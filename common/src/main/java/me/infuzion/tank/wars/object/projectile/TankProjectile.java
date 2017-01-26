package me.infuzion.tank.wars.object.projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.UUID;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.Position;
import me.infuzion.tank.wars.util.Velocity;

public class TankProjectile implements Projectile {

    private final static int speed = 25;
    private final UUID uuid;
    private Position position;
    private Velocity velocity;
    private int lifetime = 0;
    private int rot;
    private Shape bounds = new Ellipse2D.Double();

    public TankProjectile(Position position, int rotation) {
        if (position == null) {
            this.position = new Position(0, 0);
        } else {
            this.position = position.copy();
        }
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

    private void setBounds(Shape bounds) {
        this.bounds = bounds;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean draw(Graphics2D g) {
        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrame(position.getX(), position.getY(), 15, 15);
        setBounds(circle);
        g.setColor(Color.blue);
        g.draw(circle);
        g.setColor(Color.GREEN);
        g.fill(circle);
        return true;
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
            destroy(provider);
            return;
        }

        GameObject toDestroy = null;
        Area collision = new Area(getBounds());
        for (GameObject o : provider.getGameObjects()) {
            if (!o.hasCollision() || o instanceof Projectile) {
                continue;
            }

            if (o.getBounds() == null) {
                return;
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
                destroy(provider);
                toDestroy.destroy(provider);
            } else {
                double avgX =
                    toDestroy.getBounds().getBounds().getMaxX() - toDestroy.getBounds().getBounds()
                        .getMinX();
                double avgY =
                    toDestroy.getBounds().getBounds().getMaxY() - toDestroy.getBounds().getBounds()
                        .getMinY();
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
