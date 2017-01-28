package me.infuzion.tank.wars.object.misc;


import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.util.UUID;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.GraphicsObject;
import me.infuzion.tank.wars.util.Position;

public class Wall implements Drawable, GameObject {

    private static final Random random = new Random();
    private static final Color color = Color.DARK_GRAY;
    private final double width;
    private final double height;
    private UUID uuid = UUID.randomUUID();
    private Position position;
    private Shape bounds;
    private int rotation = 0;

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

    private void setBounds(Shape bounds) {
        this.bounds = bounds;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean draw(GraphicsObject g) {
        Rectangle rectangle = new Rectangle((int) position.getX(), (int) position.getY(),
            (int) width, (int) height);
        AffineTransform transform = new AffineTransform();
        g.setColor(color);
        transform.rotate(Math.toRadians(rotation), rectangle.getCenterX(), rectangle.getCenterY());
        Shape toDraw = transform.createTransformedShape(rectangle);
        g.fill(toDraw);
        setBounds(toDraw);
        return true;
    }
}
