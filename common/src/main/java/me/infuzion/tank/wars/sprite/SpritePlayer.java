package me.infuzion.tank.wars.sprite;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import me.infuzion.tank.wars.util.GraphicsObject;

public class SpritePlayer {

    private final Sprite sprite;
    private final int speed;
    private int index;
    private Iterator<BufferedImage> iterator;
    private BufferedImage current;
    private boolean finished;

    public SpritePlayer(Sprite sprite) {
        this.speed = sprite.getDescriptor().getSpeed();
        this.index = 0;
        this.sprite = sprite;
        this.iterator = sprite.iterator();
    }

    public void tick() {
        if (finished) {
            return;
        }
        index++;
        if (index % speed == 0) {
            current = iterator.next();
        }
        if (!iterator.hasNext()) {
            finished = true;
        }
    }

    public void draw(GraphicsObject graphics, double x, double y) {
        if (finished) {
            return;
        }

        graphics.drawImage(current, (int) x, (int) y);

    }

    public void reset() {
        this.iterator = sprite.iterator();
        index = 0;
        finished = false;
    }

    public boolean isFinished() {
        return finished;
    }
}
