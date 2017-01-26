package me.infuzion.tank.wars.sprite;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;

public class Sprite implements Iterable<BufferedImage> {

    private final List<BufferedImage> images;
    private final SoundDescriptor descriptor;

    public Sprite(SoundDescriptor descriptor, InputStream image) throws IOException {
        this.descriptor = descriptor;
        images = new ArrayList<>();
        BufferedImage bufferedImage = ImageIO.read(image);
        int rows = descriptor.getRows();
        int cols = descriptor.getColumns();
        int amount = descriptor.getAmount();

        int chunkWidth = bufferedImage.getWidth() / cols;
        int chunkHeight = bufferedImage.getHeight() / rows;
        int count = 0;
        double scaleFactor = descriptor.getScaleFactor();
        BufferedImage imageChunks[] = new BufferedImage[amount];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (count >= amount) {
                    break;
                }
                imageChunks[count] = new BufferedImage((int) (chunkWidth * scaleFactor),
                    (int) (chunkHeight * scaleFactor),
                    bufferedImage.getType());
                Graphics2D gr = imageChunks[count].createGraphics();
                gr.drawImage(bufferedImage, 0, 0, (int) (chunkWidth * scaleFactor),
                    (int) (chunkHeight * scaleFactor), chunkWidth * y,
                    chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight,
                    null);
                gr.dispose();
                count++;
            }
        }

        for (BufferedImage img : imageChunks) {
            if (img == null) {
                continue;
            }
            images.add(img);
        }
    }

    public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight,
        double fWidth, double fHeight) {
        BufferedImage dbi = null;
        if (sbi != null) {
            dbi = new BufferedImage(dWidth, dHeight, imageType);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
            g.drawRenderedImage(sbi, at);
        }
        return dbi;
    }

    @Override
    public Iterator<BufferedImage> iterator() {
        return images.iterator();
    }

    public SoundDescriptor getDescriptor() {
        return descriptor;
    }
}
