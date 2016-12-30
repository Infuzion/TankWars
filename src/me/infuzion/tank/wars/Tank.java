package me.infuzion.tank.wars;

import me.infuzion.tank.wars.object.Position;
import me.infuzion.tank.wars.object.TankProjectile;
import me.infuzion.tank.wars.provider.InfoProvider;

import java.awt.*;
import java.util.UUID;

public class Tank {
    public static final int turnRate = 5;
    private final String name;
    private final int speed = 25;
    private final UUID uuid;
    private Position position;
    private int rot;
    private double rotRadians;
    private Color primaryColor = Color.GRAY;
    private Color secondaryColor = Color.DARK_GRAY;

    public Tank(String name, int x, int y, int rot) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.rot = rot;
        this.position = new Position(x, y);
        this.rotRadians = Math.toRadians(rot - 90);

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

    public void shoot(InfoProvider provider){
        TankProjectile projectile = new TankProjectile(position, rot);
        provider.addGameObject(projectile);
    }

    public int getRot() {
        return rot;
    }

    public void setRot(int rot) {
        this.rotRadians = Math.toRadians(rot - 90);
        this.rot = rot;
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

    public UUID getUuid() {
        return uuid;
    }
}
