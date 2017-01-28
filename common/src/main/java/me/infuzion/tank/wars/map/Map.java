package me.infuzion.tank.wars.map;

import java.util.List;
import me.infuzion.tank.wars.object.GameObject;

public class Map {

    private List<GameObject> objects;

    public Map(List<GameObject> objects) {
        this.objects = objects;
    }

    public List<GameObject> getObjects() {
        return objects;
    }
}
