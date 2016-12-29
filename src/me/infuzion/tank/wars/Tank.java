package me.infuzion.tank.wars;

import me.infuzion.tank.wars.object.Position;

import java.awt.*;

public class Tank {
    public static final int turnRate = 25;
    private final String name;
    private Position position;
    private int rot;
    private Color primaryColor = Color.GRAY;
    private Color secondaryColor = Color.DARK_GRAY;

    public Tank(String name, int x, int y, int rot) {
        this.name = name;
        this.rot = rot;
        this.position = new Position(x, y);

    }

    public double getX() {
        return position.getX();
    }

    public void setX(int x) {
        position.setX(x);
    }

    public double getY() {
        return position.getY();
    }

    public void setY(int y) {
        position.setY(y);
    }

    public int getRot() {
        return rot;
    }

    public void setRot(int rot) {
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
}
