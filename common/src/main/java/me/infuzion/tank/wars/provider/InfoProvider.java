package me.infuzion.tank.wars.provider;

import java.util.List;
import java.util.UUID;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Identifiable;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.tank.Tank;

public interface InfoProvider {
    List<GameObject> getGameObjects();

    void registerPersistent(Identifiable identifiable);

    List<Drawable> getDrawableObjects();

    List<Tickable> getTickableObjects();

    void addGameObject(GameObject toAdd);

    void registerDrawable(Drawable toAdd);

    void registerTickable(Tickable tickable);

    void registerAll(Identifiable identifiable);

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
