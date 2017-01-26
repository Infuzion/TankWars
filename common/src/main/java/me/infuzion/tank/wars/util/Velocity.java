package me.infuzion.tank.wars.util;

import java.io.Serializable;

public class Velocity implements Serializable {
    private static final long serialVersionUID = 1L;
    private double xVelocity;
    private double yVelocity;

    public Velocity(double xVelocity, double yVelocity) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }
}
