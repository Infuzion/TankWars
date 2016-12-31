package me.infuzion.tank.wars.provider;

import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Tank;
import me.infuzion.tank.wars.object.Tickable;

import java.util.List;

public interface InfoProvider {
    List<GameObject> getGameObjects();

    List<Drawable> getDrawableObjects();

    List<Tickable> getTickableObjects();

    void addGameObject(GameObject toAdd);

    List<Tank> getTanks();

    void updateTanks(List<Tank> tanks);

    int getFPS();

    void setFPS(int fps);

    int getTPS();

    void setTPS(int tps);

    long getTick();

    void setTick(long tick);

    boolean isRemote();

    void removeGameObject(GameObject object);
}
