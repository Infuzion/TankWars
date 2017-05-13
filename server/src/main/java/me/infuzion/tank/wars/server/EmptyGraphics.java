package me.infuzion.tank.wars.server;

import me.infuzion.tank.wars.util.GraphicsObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EmptyGraphics implements GraphicsObject {

    @Override
    public void drawOval(int x, int y, int width, int height) {

    }

    @Override
    public void drawString(String str, int x, int y) {

    }

    @Override
    public void fillRect(int x, int y, int width, int height) {

    }

    @Override
    public void setColor(Color color) {

    }

    @Override
    public void setColor(javafx.scene.paint.Color color) {

    }

    @Override
    public void setFont(Font font) {

    }

    @Override
    public void drawRect(int x, int y, int width, int height) {

    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {

    }

    @Override
    public void fill(Shape shape) {

    }

    @Override
    public void fill(javafx.scene.shape.Shape shape) {

    }

    @Override
    public void setPaint(Color color) {

    }

    @Override
    public void draw(Shape shape) {

    }

    @Override
    public void drawImage(BufferedImage image, int x, int y) {

    }

    @Override
    public Font getFont() {
        return new Font("", Font.PLAIN, 16);
    }

    @Override
    public void setFont(javafx.scene.text.Font font) {

    }
}
