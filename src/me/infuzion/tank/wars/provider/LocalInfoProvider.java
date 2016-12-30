package me.infuzion.tank.wars.provider;

import me.infuzion.tank.wars.Tank;
import me.infuzion.tank.wars.object.GameObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocalInfoProvider implements InfoProvider {
    private static List<Tank> tanks;

    static {
        Tank one = new Tank("Player 1", 640, 0, 0);
        Tank two = new Tank("Player 2", 0, 240, 18);
        tanks = Arrays.asList(one, two);
    }

    private List<GameObject> objects = new ArrayList<>();
    private int fps;

    @Override
    public List<GameObject> getGameObjects() {
        return objects;
    }

    @Override
    public void addGameObject(GameObject toAdd) {
        objects.add(toAdd);
    }

    @Override
    public List<Tank> getTanks() {
        return LocalInfoProvider.tanks;
    }

    @Override
    public void updateTanks(List<Tank> tanks) {
        LocalInfoProvider.tanks = tanks;
    }

    @Override
    public void setFPS(int fps) {
        this.fps = fps;
    }

    @Override
    public int getFPS() {
        return fps;
    }


}
