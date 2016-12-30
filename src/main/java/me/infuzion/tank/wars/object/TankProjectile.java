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
    private Shape bounds = new Ellipse2D.Double();


    public TankProjectile(Position position, int rotation) {
        this.position = position.copy();
        double rotRadians = Math.toRadians(rotation - 90);
        double velX = speed * Math.cos(rotRadians);
        double velY = speed * Math.sin(rotRadians);
        velocity = new Velocity(velX, velY);
        this.uuid = UUID.randomUUID();
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
        GameObject toDestroy = null;
        Area collision = new Area(getBounds());
        if (lifetime < 5) {
            move();
            return;
        }
        for (GameObject o : provider.getGameObjects()) {
            if (o instanceof Projectile) {
                continue;
            }
            System.out.println("Testing for intersection with " + o.getClass().getSimpleName());
            Area collision2 = new Area(o.getBounds());
            collision2.intersect(collision);
            if (!collision2.isEmpty()) {
                System.out.println("Found intersection with " + o.getClass().getCanonicalName());
                toDestroy = o;
                break;
            }
        }
        if (toDestroy != null) {
            if (toDestroy.isDestroyable()) {
                provider.removeGameObject(this);
                provider.removeGameObject(toDestroy);
            } else {
                this.velocity.setxVelocity(-velocity.getxVelocity());
                this.velocity.setyVelocity(-velocity.getyVelocity());

            }
        }
        move();
    }

    @Override
    public Velocity getVelocity() {
        return velocity;
    }
}
