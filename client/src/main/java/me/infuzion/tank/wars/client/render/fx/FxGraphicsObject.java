package me.infuzion.tank.wars.client.render.fx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import me.infuzion.tank.wars.util.GraphicsObject;

public class FxGraphicsObject implements GraphicsObject {

    private final GraphicsContext context;

    public FxGraphicsObject(GraphicsContext context) {
        this.context = context;

    }

    private static double[][] getPoints(PathIterator path) {
        List<double[]> pointList = new ArrayList<>();
        double[] coords = new double[6];
        int numSubPaths = 0;
        for (PathIterator pi = path;
            !pi.isDone();
            pi.next()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO:
                    pointList.add(Arrays.copyOf(coords, 2));
                    ++numSubPaths;
                    break;
                case PathIterator.SEG_LINETO:
                    pointList.add(Arrays.copyOf(coords, 2));
                    break;
                case PathIterator.SEG_CLOSE:
                    if (numSubPaths > 1) {
                        throw new IllegalArgumentException("Path contains multiple subpaths");
                    }
                    return pointList.toArray(new double[pointList.size()][]);
                default:
                    throw new IllegalArgumentException("Path contains curves");
            }
        }
        throw new IllegalArgumentException("Unclosed path");
    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
        context.strokeOval(x, y, width, height);
    }

    @Override
    public void drawString(String str, int x, int y) {
        context.fillText(str, x, y);
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        context.fillRect(x, y, width, height);
    }

    @Override
    public void setColor(Color color) {
        setPaint(color);
    }

    @Override
    public void setColor(javafx.scene.paint.Color color) {
        context.setStroke(color);
        context.setFill(color);
    }

    @Override
    public void setFont(Font font) {
        context.setFont(javafx.scene.text.Font.font(font.getFamily(), font.getSize()));
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        context.rect(x, y, width, height);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        context.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public void fill(Shape shape) {
        if (shape instanceof Rectangle2D) {
            context.fillRect(((Rectangle2D) shape).getX(), ((Rectangle2D) shape).getY(),
                ((Rectangle2D) shape).getWidth(), ((Rectangle2D) shape).getHeight());
        } else {
            shapeToPath(shape);
            context.fill();
        }
    }

    private void shapeToPath(Shape shape) {
        double[] coords = new double[6];

        this.context.beginPath();
        PathIterator iterator = shape.getPathIterator(null);
        while (!iterator.isDone()) {
            int segType = iterator.currentSegment(coords);
            switch (segType) {
                case PathIterator.SEG_MOVETO:
                    this.context.moveTo(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    this.context.lineTo(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    this.context.quadraticCurveTo(coords[0], coords[1], coords[2],
                        coords[3]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    this.context.bezierCurveTo(coords[0], coords[1], coords[2],
                        coords[3], coords[4], coords[5]);
                    break;
                case PathIterator.SEG_CLOSE:
                    this.context.closePath();
                    break;
                default:
                    throw new RuntimeException("Unrecognised segment type "
                        + segType);
            }
            iterator.next();
        }
    }

    @Override
    public void fill(javafx.scene.shape.Shape shape) {

    }

    @Override
    public void setPaint(Color color) {
        context.setFill(toFXColor(color));
        context.setStroke(toFXColor(color));
    }

    @Override
    public void draw(Shape shape) {
        shapeToPath(shape);
        context.stroke();
    }

    @Override
    public void drawImage(BufferedImage image, int x, int y) {
        if (image != null) {
            Image img = SwingFXUtils.toFXImage(image, null);
            context.drawImage(img, x, y);
        }
    }

    private javafx.scene.paint.Color toFXColor(Color c) {
        return javafx.scene.paint.Color
            .rgb(c.getRed(), c.getGreen(), c.getBlue());
    }

    @Override
    public Font getFont() {
        return new Font(context.getFont().getName(), Font.PLAIN, (int) context.getFont().getSize());
    }

    @Override
    public void setFont(javafx.scene.text.Font font) {
        context.setFont(font);
    }
}
