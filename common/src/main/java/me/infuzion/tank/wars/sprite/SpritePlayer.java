package me.infuzion.tank.wars.sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

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

    public void draw(Graphics2D graphics2D, double x, double y) {
        if (finished) {
            return;
        }
        index++;
        if (index % speed == 0) {
            current = iterator.next();
        }
        graphics2D.drawImage(current, (int) x, (int) y, null);
        if (!iterator.hasNext()) {
            finished = true;
        }
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
