package me.infuzion.tank.wars.client.object;

import me.infuzion.tank.wars.client.util.Velocity;

public interface Projectile extends Drawable, Tickable{
    Velocity getVelocity();
}
