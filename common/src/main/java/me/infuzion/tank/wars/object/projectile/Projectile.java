package me.infuzion.tank.wars.object.projectile;


import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.util.Velocity;

public interface Projectile extends Drawable, Tickable, GameObject {

    Velocity getVelocity();
}
