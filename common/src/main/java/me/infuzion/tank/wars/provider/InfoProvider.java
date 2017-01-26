package me.infuzion.tank.wars.provider;

import java.util.List;
import java.util.UUID;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Tank;
import me.infuzion.tank.wars.object.Tickable;

public interface InfoProvider {
    List<GameObject> getGameObjects();

    List<Drawable> getDrawableObjects();

    List<Tickable> getTickableObjects();

    void addGameObject(GameObject toAdd);

    void quit();

    boolean getQuit();

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

    List<Tank> ownedBy(UUID uuid);

    List<Tank> getOwned();
}
