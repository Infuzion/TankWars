package me.infuzion.tank.wars.render.swing;

import me.infuzion.tank.wars.TankWars;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.Tank;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.util.Position;
import me.infuzion.tank.wars.provider.InfoProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import static java.awt.geom.PathIterator.SEG_CLOSE;

public class TankWarsCanvas extends JPanel {
    private List<Tank> tanks = new ArrayList<>();
    private InfoProvider provider;

    public void updateTanks(InfoProvider provider) {
        this.tanks = provider.getTanks();
        this.provider = provider;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setBackground(Color.WHITE);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.black);
        graphics2D.drawString("FPS: " + provider.getFPS() + " limited to " + TankWars.frameLimit, 0, 10);

        for (Tank tank : tanks) {
            drawTank(tank, graphics2D);
        }

        for (Drawable object : provider.getDrawableObjects()) {
            object.draw(graphics2D);
        }

    }

    public void drawTank(Tank tank, Graphics2D graphics2D) {
        double x = tank.getX();
        double y = tank.getY();
        int rot = tank.getRot();

        Rectangle tankBody = new Rectangle((int) x, (int) y, 50, 100);

        double centX = tankBody.getCenterX();
        double centY = tankBody.getCenterY();

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rot), centX, centY);
        Shape tankBodyToDraw = transform.createTransformedShape(tankBody);

        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrame(tankBody);
        circle.setFrame((centX + ((circle.getX() - circle.getCenterX())) / 2) - 12,
                (centY + (circle.getY() - circle.getCenterY()) / 2), 50, 50);
        Shape circleToDraw = transform.createTransformedShape(circle);

        Rectangle barrel = new Rectangle();
        barrel.setFrame(circle.getBounds());
        barrel.setFrame((centX - ((barrel.getX() - barrel.getCenterX())) / 2) - 18,
                (centY - (barrel.getY() - barrel.getCenterY()) / 2) - 12, 10, 75);

        Shape barrelToDraw = transform.createTransformedShape(barrel);
        Shape bBound = transform.createTransformedShape(barrel.getBounds());

        Rectangle barrelBounds = transform.createTransformedShape(barrel.getBounds()).getBounds();

        Area area = new Area(barrelBounds);
        area.intersect(new Area(bBound));
        PathIterator iterator = area.getPathIterator(null);
        float[][] points = new float[4][2];
        float[] floats = new float[6];
        int i = 0;
        while (!iterator.isDone()) {
            int type = iterator.currentSegment(floats);
            if (type != SEG_CLOSE) {
                points[i][0]
                        = floats[0];
                points[i][1]
                        = floats[1];
            }
            iterator.next();
        }

        graphics2D.setPaint(tank.getPrimaryColor());
        graphics2D.draw(tankBodyToDraw);
        graphics2D.fill(tankBodyToDraw);

        graphics2D.setPaint(tank.getSecondaryColor());

        graphics2D.draw(barrelToDraw);
        graphics2D.fill(barrelToDraw);

        graphics2D.draw(circleToDraw);
        graphics2D.fill(circleToDraw);
        tank.setBarrelPosition(new Position(points[0][0], points[0][1]));

        tank.setBounds(transform.createTransformedShape(tankBody.getBounds()));
    }
}
