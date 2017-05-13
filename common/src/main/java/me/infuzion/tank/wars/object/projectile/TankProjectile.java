package me.infuzion.tank.wars.object.projectile;

import javafx.scene.paint.Color;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.GraphicsObject;
import me.infuzion.tank.wars.util.Position;
import me.infuzion.tank.wars.util.Velocity;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.UUID;

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
            position.setX(this.position.getX() + 7.5);
            position.setY(this.position.getY() + 7.5);
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
    public boolean draw(GraphicsObject g) {
        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrame(position.getX(), position.getY(), 15, 15);
        setBounds(circle);
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
        if (provider.isRemote()) {
            return;
        }
        lifetime++;
        if (lifetime > 750) {
            destroy(provider);
            return;
        }

        GameObject collidedWith = null;
        Area collision = new Area(getBounds());
        for (GameObject o : provider.getGameObjects()) {
            if (!o.hasCollision() || o instanceof Projectile) {
                continue;
            }
            if (o.getBounds() == null) {
                continue;
            }
            Area collision2 = new Area(o.getBounds());
            collision2.intersect(collision);
            if (!collision2.isEmpty()) {
                position.setX(position.getX() - velocity.getxVelocity());
                position.setY(position.getY() - velocity.getyVelocity());
                collidedWith = o;
                break;
            }
        }
        if (collidedWith != null) {
            if (collidedWith.isDestroyable()) {
                onCollision(collidedWith, provider);
            } else {
                double avgX =
                    collidedWith.getBounds().getBounds().getMaxX() - collidedWith.getBounds()
                        .getBounds()
                        .getMinX();
                double avgY =
                    collidedWith.getBounds().getBounds().getMaxY() - collidedWith.getBounds()
                        .getBounds()
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

    protected void onCollision(GameObject collidedWith, InfoProvider provider) {
        destroy(provider);
        collidedWith.destroy(provider);
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

    @Override
    public void fire(Position position, int rotation) {

    }
}
