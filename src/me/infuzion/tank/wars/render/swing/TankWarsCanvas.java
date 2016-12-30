package me.infuzion.tank.wars.render.swing;

import me.infuzion.tank.wars.Tank;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.provider.InfoProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

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
        graphics2D.drawString("FPS: "+ provider.getFPS(), 0, 10);
        List<Tank> toUpdate = new ArrayList<>();
        for (Tank tank : tanks) {
            drawTank(tank, graphics2D);
            toUpdate.add(tank);
        }
        for (GameObject object : provider.getGameObjects()){
            object.draw(graphics2D);
        }
        provider.updateTanks(toUpdate);
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
        transform.rotate(Math.toRadians(rot), centX, centY);
        barrel.setFrame((centX + ((barrel.getX() - barrel.getCenterX())) / 2),
                (centY + (barrel.getY() - barrel.getCenterY()) / 2), 10, 75);
        Shape barrelToDraw = transform.createTransformedShape(barrel);

        graphics2D.setPaint(tank.getPrimaryColor());
        graphics2D.draw(tankBodyToDraw);
        graphics2D.fill(tankBodyToDraw);

        graphics2D.setPaint(tank.getSecondaryColor());

        graphics2D.draw(barrelToDraw);
        graphics2D.fill(barrelToDraw);

        graphics2D.draw(circleToDraw);
        graphics2D.fill(circleToDraw);

    }
}
