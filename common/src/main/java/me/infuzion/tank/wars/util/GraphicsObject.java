package me.infuzion.tank.wars.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.image.BufferedImage;

public interface GraphicsObject {

    void drawOval(int x, int y, int width, int height);

    void drawString(String str, int x, int y);

    void fillRect(int x, int y, int width, int height);

    void setColor(Color color);

    void setColor(javafx.scene.paint.Color color);

    void setFont(Font font);

    void drawRect(int x, int y, int width, int height);

    void drawLine(int x1, int y1, int x2, int y2);

    void fill(Shape shape);

    void fill(javafx.scene.shape.Shape shape);

    void setPaint(Color color);

    void draw(Shape shape);

    void drawImage(BufferedImage image, int x, int y);

    Font getFont();

    void setFont(javafx.scene.text.Font font);
}
