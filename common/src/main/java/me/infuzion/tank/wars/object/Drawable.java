package me.infuzion.tank.wars.object;

import me.infuzion.tank.wars.util.GraphicsObject;

public interface Drawable extends Identifiable {

    boolean draw(GraphicsObject g);
}
