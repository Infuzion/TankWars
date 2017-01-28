package me.infuzion.tank.wars.object.perk;

import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.tank.Tank;

public interface Perk extends Tickable, Drawable {
    void apply(Tank tank);
}
