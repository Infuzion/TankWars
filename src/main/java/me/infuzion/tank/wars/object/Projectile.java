package me.infuzion.tank.wars.object;

import me.infuzion.tank.wars.util.Velocity;

public interface Projectile extends Drawable, Tickable{
    Velocity getVelocity();
}
