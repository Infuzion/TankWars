package me.infuzion.tank.wars.object;

import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.Position;

import java.awt.*;
import java.util.UUID;

public class Wall implements Drawable {
    private UUID uuid = UUID.randomUUID();
    private Position position;
    private final InfoProvider provider;
    private Shape bounds;

    public Wall(double x, double y, InfoProvider provider) {
        this.position = new Position(x, y);
        this.provider = provider;
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
        Rectangle rectangle = new Rectangle((int) position.getX(), (int) position.getY(), 50, 50);
        g.draw(rectangle);
        setBounds(rectangle);
    }
}
