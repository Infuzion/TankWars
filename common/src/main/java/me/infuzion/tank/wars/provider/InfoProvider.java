package me.infuzion.tank.wars.provider;

import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Identifiable;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.provider.network.NetworkManager;

import java.util.List;
import java.util.UUID;

public interface InfoProvider {
    List<GameObject> getGameObjects();

    void registerPersistent(Identifiable identifiable);

    void run();

    List<Drawable> getDrawableObjects();

    List<Tickable> getTickableObjects();

    void register(Identifiable identifiable);

    void quit();

    boolean getQuit();

    List<Tank> getTanks();

    List<Tank> getControllableTanks();

    int getFPS();

    void setFPS(int fps);

    int getTPS();

    void setTPS(int tps);

    long getTick();

    void setTick(long tick);

    boolean isRemote();

    void removeGameObject(GameObject object);

    NetworkManager getNetworkManager();

    List<Tank> ownedBy(UUID uuid);

    List<Tank> getOwned();
}
