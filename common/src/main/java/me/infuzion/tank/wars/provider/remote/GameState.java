package me.infuzion.tank.wars.provider.remote;

import java.io.Serializable;
import java.util.List;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.tank.Tank;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    public final List<Tank> tanks;
    public final List<GameObject> objects;
    public final List<Drawable> drawables;
    public final List<Tickable> tickables;
    public final int tps;
    public final long tick;

    public GameState(List<Tank> tanks, List<GameObject> objects, List<Drawable> drawables, List<Tickable> tickables,
                     int tps, long tick) {
        this.tanks = tanks;
        this.objects = objects;
        this.drawables = drawables;
        this.tickables = tickables;
        this.tps = tps;
        this.tick = tick;
    }


}
