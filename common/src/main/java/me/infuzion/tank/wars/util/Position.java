package me.infuzion.tank.wars.util;

import java.io.Serializable;

public class Position implements Serializable {
    private static final long serialVersionUID = 1L;
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position copy() {
        return new Position(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        if (((Position) obj).getX() == this.x) {
            if (((Position) obj).getY() == this.y) {
                return true;
            }
        }
        return false;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
