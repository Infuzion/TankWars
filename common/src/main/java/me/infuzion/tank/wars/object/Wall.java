package me.infuzion.tank.wars.object;


import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.Position;

import java.awt.*;
import java.util.UUID;

public class Wall implements Drawable {
    private final double width;
    private final double height;
    private UUID uuid = UUID.randomUUID();
    private Position position;
    private Shape bounds;

    public Wall(double x, double y, double width, double height, InfoProvider provider) {
        this.width = width;
        this.height = height;
        this.position = new Position(x, y);
        provider.addGameObject(this);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isDestroyable() {
        return false;
    }

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

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    @Override
    public void draw(Graphics2D g) {
        Rectangle rectangle = new Rectangle((int) position.getX(), (int) position.getY(), (int) width, (int) height);
        g.draw(rectangle);
        setBounds(rectangle);
    }
}
