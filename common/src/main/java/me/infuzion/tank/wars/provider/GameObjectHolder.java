package me.infuzion.tank.wars.provider;

import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Identifiable;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.tank.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class GameObjectHolder {
    private final List<Identifiable> objects = new ArrayList<>();
    private final List<UUID> persistentObjects = new ArrayList<>();
    //caches
    private List<GameObject> gameObjects = new ArrayList<>();
    private List<Tank> tanks = new ArrayList<>();
    private List<Drawable> drawables = new ArrayList<>();
    private List<Tickable> tickables = new ArrayList<>();

    private boolean needsUpdate = false;
    private Lock updating = new ReentrantLock();

    public List<Identifiable> getObjects() {
        return new ArrayList<>(objects);
    }

    public List<UUID> getPersistentObjects() {
        synchronized (persistentObjects) {
            return new ArrayList<>(persistentObjects);
        }
    }

    public void clear() {
        synchronized (objects) {
            objects.clear();
        }
        synchronized (persistentObjects) {
            persistentObjects.clear();
        }
        needsUpdate = true;
    }

    public List<GameObject> getGameObjects() {
        update();
        return gameObjects;
    }

    public void clearNonPersistent() {
        objects.removeIf(object -> !persistentObjects.contains(object.getUuid()));
        needsUpdate = true;
    }

    public boolean isPersistent(Identifiable identifiable) {
        synchronized (persistentObjects) {
            return persistentObjects.contains(identifiable.getUuid());
        }
    }

    public List<Tank> getTanks() {
        update();
        return new ArrayList<>(tanks);
    }

    private void updateLists() {
        while (true) {
            if (updating.tryLock()) {
                tanks = objects.stream().filter((e) -> e instanceof Tank).map((e) -> (Tank) e).collect(Collectors.toList());
                drawables = objects.stream().filter((e) -> e instanceof Drawable).map((e) -> (Drawable) e).collect(Collectors.toList());
                tickables = objects.stream().filter((e) -> e instanceof Tickable).map((e) -> (Tickable) e).collect(Collectors.toList());
                gameObjects = objects.stream().filter((e) -> e instanceof GameObject).map((e) -> (GameObject) e).collect(Collectors.toList());
                updating.unlock();
                return;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeObject(Identifiable object) {
        objects.remove(object);
        needsUpdate = true;
    }

    public void addObject(Identifiable object) {
        synchronized (objects) {
            objects.add(object);
        }
        needsUpdate = true;
    }

    public void addPersistent(Identifiable identifiable) {
        synchronized (persistentObjects) {
            persistentObjects.add(identifiable.getUuid());
        }
    }

    private void update() {
        if (needsUpdate) {
            updateLists();
            needsUpdate = false;
        }
    }

    public List<Drawable> getDrawables() {
        update();
        return new ArrayList<>(drawables);
    }

    public List<Tickable> getTickables() {
        update();
        return new ArrayList<>(tickables);
    }
}
