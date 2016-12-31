package me.infuzion.tank.wars.object;


import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.Position;

import java.awt.*;
import java.util.UUID;

public class Tank implements Drawable, Tickable {
    private static final int shotResetTime = 30;
    public static final int turnRate = 5;
    private final String name;
    private final int speed = 25;
    private final UUID uuid;
    private transient final InfoProvider provider;
    private Position position;
    private int rot;
    private double rotRadians;
    private Color primaryColor = Color.GRAY;
    private Color secondaryColor = Color.DARK_GRAY;
    private Position center;
    private Shape bounds = new Rectangle();
    private long lastShot = -1;
    private UUID owner;

    public Tank(String name, int x, int y, int rot, InfoProvider provider) {
        this.provider = provider;
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.rot = rot;
        this.position = new Position(x, y);
        this.rotRadians = Math.toRadians(rot - 90);
        provider.addGameObject(this);
    }

    public void moveForward() {
        position.setX(position.getX() - speed * Math.cos(rotRadians));
        position.setY(position.getY() - speed * Math.sin(rotRadians));
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public void shoot() {
        if (lastShot + shotResetTime >= provider.getTick()) {
            return;
        }
        lastShot = provider.getTick();
        TankProjectile projectile = new TankProjectile(center, rot - 180);
        provider.addGameObject(projectile);
    }

    public int getRot() {
        return rot;
    }

    public void setRot(int rot) {
        this.rotRadians = Math.toRadians(rot - 90);
        this.rot = rot;
    }

    public void turnLeft() {
        setRot(rot - turnRate);
    }

    public void turnRight() {
        setRot(rot + turnRate);
    }

    public String getName() {
        return name;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void moveBackward() {
        position.setX(position.getX() + speed * Math.cos(rotRadians));
        position.setY(position.getY() + speed * Math.sin(rotRadians));
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isDestroyable() {
        return true;
    }

    public Shape getBounds() {
        return bounds;
    }

    public void setBounds(Shape bounds) {
        this.bounds = bounds;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void draw(Graphics2D g) {
    }

    public void setBarrelPosition(Position center) {
        this.center = center;
    }

    @Override
    public void tick(InfoProvider provider) {

    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
