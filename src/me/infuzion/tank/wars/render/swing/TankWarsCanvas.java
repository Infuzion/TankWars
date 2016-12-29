package me.infuzion.tank.wars.render.swing;

import me.infuzion.tank.wars.Tank;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class TankWarsCanvas extends Canvas {
    List<Tank> tanks = new ArrayList<>();

    public void updateTanks(List<Tank> tanks) {
        this.tanks = tanks;
    }

    @Override
    public void paint(Graphics graphics) {
        setBackground(Color.WHITE);
        Graphics2D graphics2D = (Graphics2D) graphics;

        for (Tank tank : tanks) {
            System.out.println("Rendering: " + tank.getName());
            drawTank(tank, graphics2D);
        }
    }

    public void drawTank(Tank tank, Graphics2D graphics2D) {
        double x = tank.getX();
        double y = tank.getY();
        int rot = tank.getRot();

        Rectangle tankBody = new Rectangle((int) x, (int) y, 50, 100);
        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrame(x, y, 50, 50);
        graphics2D.setPaint(tank.getPrimaryColor());
        graphics2D.fill(tankBody);
        graphics2D.setPaint(tank.getSecondaryColor());
        graphics2D.fill(circle);
    }
}
