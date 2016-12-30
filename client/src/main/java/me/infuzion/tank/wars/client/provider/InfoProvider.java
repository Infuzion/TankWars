package me.infuzion.tank.wars.client.provider;

import me.infuzion.tank.wars.client.object.Drawable;
import me.infuzion.tank.wars.client.object.GameObject;
import me.infuzion.tank.wars.client.object.Tank;
import me.infuzion.tank.wars.client.object.Tickable;

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

    boolean isRemote();

    void removeGameObject(GameObject object);
}
