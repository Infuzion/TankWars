package me.infuzion.tank.wars.object;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.UUID;

public class TankProjectile implements GameObject, Projectile {
    private final int speed = 5;
    private final UUID uuid;
    private Position position;
    private Velocity velocity;


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
    public void draw(Graphics2D g) {
        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrame(position.getX(), position.getY(), 15, 15);
        g.setColor(Color.blue);
        g.draw(circle);
        position.setX(position.getX() + velocity.getxVelocity());
        position.setY(position.getY() + velocity.getyVelocity());
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public Velocity getVelocity() {
        return velocity;
    }
}
