package me.infuzion.tank.wars.client.render.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import me.infuzion.tank.wars.util.GraphicsObject;

public class SwingGraphicsObject implements GraphicsObject {

    private final Graphics2D g;

    SwingGraphicsObject(Graphics2D g) {
        this.g = g;
    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
        g.drawOval(x, y, width, height);
    }

    @Override
    public void drawString(String str, int x, int y) {
        g.drawString(str, x, y);
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        g.fillRect(x, y, width, height);
    }

    @Override
    public void setColor(Color color) {
        g.setColor(color);
    }

    @Override
    public void setColor(javafx.scene.paint.Color color) {
        g.setColor(
            new Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue(),
                (float) color.getOpacity()));
    }

    @Override
    public void setFont(Font font) {
        g.setFont(font);
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        g.drawRect(x, y, width, height);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void fill(Shape shape) {
        g.fill(shape);
    }

    @Override
    public void fill(javafx.scene.shape.Shape shape) {
        //TODO
    }

    @Override
    public void setPaint(Color color) {
        g.setPaint(color);
    }

    @Override
    public void draw(Shape shape) {
        g.draw(shape);
    }

    @Override
    public void drawImage(BufferedImage image, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    @Override
    public Font getFont() {
        return g.getFont();
    }

    @Override
    public void setFont(javafx.scene.text.Font font) {
        // TODO
    }
}
