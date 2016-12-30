package me.infuzion.tank.wars.provider;

import me.infuzion.tank.wars.Tank;
import me.infuzion.tank.wars.object.GameObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface InfoProvider {
    List<GameObject> getGameObjects();

    void addGameObject(GameObject toAdd);

    List<Tank> getTanks();

    void updateTanks(List<Tank> tanks);

    void setFPS(int fps);

    int getFPS();

    default void updateTank(Tank tank) {
        List<Tank> tanks = getTanks();
        List<Tank> toAdd = new ArrayList<>();
        System.out.println(tank.getName());
        System.out.println(tank.getRot());
        Iterator<Tank> i = tanks.iterator();
        while (i.hasNext()) {
            Tank e = i.next();
            if (e.getUuid().equals(tank.getUuid())) {
                i.remove();
                toAdd.add(tank);
            }
        }
        if (toAdd.size() == 0) {
            toAdd.add(tank);
        }
        System.out.println(toAdd.get(0).getRot());
        tanks.addAll(toAdd);
        updateTanks(tanks);
    }
}
