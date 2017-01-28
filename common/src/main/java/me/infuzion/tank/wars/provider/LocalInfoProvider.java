package me.infuzion.tank.wars.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import me.infuzion.tank.wars.item.DropPerkLaser;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Identifiable;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.misc.Scoreboard;
import me.infuzion.tank.wars.object.misc.Wall;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.util.Position;
import me.infuzion.tank.wars.util.Settings;

public class LocalInfoProvider implements InfoProvider {

    private static List<Tank> tanks = new CopyOnWriteArrayList<>();
    private List<GameObject> objects = new CopyOnWriteArrayList<>();
    private List<Drawable> drawables = new CopyOnWriteArrayList<>();
    private List<Tickable> tickables = new CopyOnWriteArrayList<>();
    private List<Identifiable> persistentObjects = new CopyOnWriteArrayList<>();
    private Scoreboard scoreboard;
    private int fps;
    private int tps;
    private boolean toQuit = false;
    private long tick;

    public LocalInfoProvider() {
        scoreboard = new Scoreboard(this);
        Thread t = new Thread(() -> {
            int i = 0;
            List<Tank> aliveTanks = new ArrayList<>();
            //noinspection InfiniteLoopStatement
            while (true) {
                aliveTanks.clear();
                int amountAlive = 0;
                for (Tank e : tanks) {
                    if (e.isAlive()) {
                        aliveTanks.add(e);
                        amountAlive++;
                    }
                }
                if (amountAlive <= 1) {
                    i++;
                    if (i > 25) {
                        if (aliveTanks.size() == 1) {
                            Tank last = aliveTanks.get(0);
                            if (last != null) {
                                scoreboard.addScore(last);
                            }
                        }
                        nextLevel();
                        i = 0;
                    }
                }
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Level advance thread");
        t.setDaemon(true);
        t.start();
        nextLevel();
    }

    private void nextLevel() {
        clearNonPersistent(objects);
        clearNonPersistent(drawables);
        clearNonPersistent(tickables);
        randomizeLevel();
    }

    private void clearNonPersistent(List list) {
        List<Object> toRet = null;
        for (Object e : list) {
            if (persistentObjects.contains(e)) {
                continue;
            }
            if (toRet == null) {
                toRet = new ArrayList<>();
            }
            toRet.add(e);
        }
        if (toRet != null) {
            list.removeAll(toRet);
        }
    }

    private void randomizeLevel() {
        Random r = new Random();
        int p1Y = r.nextInt(Settings.SCREEN_HEIGHT);
        int p1X = r.nextInt(Settings.SCREEN_WIDTH);
        int rot = r.nextInt(360);

        int p2Y = r.nextInt(Settings.SCREEN_HEIGHT);
        int p2X = r.nextInt(Settings.SCREEN_WIDTH);

        if (tanks.size() == 0) {
            new Tank("Player 1", p1X, p1Y, rot, this);
            new Tank("Player 2", p2X, p2Y, 360 - rot, this);
        } else {
            for (Tank e : tanks) {
                e.respawn(new Position(r.nextInt(Settings.SCREEN_WIDTH),
                    r.nextInt(Settings.SCREEN_HEIGHT)), rot);
                rot = 360 - rot;
            }
        }

        int walls = r.nextInt(16);
        for (int i = 0; i < walls; i++) {
            int wallX = r.nextInt(Settings.SCREEN_WIDTH);
            int wallY = r.nextInt(Settings.SCREEN_HEIGHT);
            boolean direction = r.nextBoolean();
//            boolean tree = r.nextBoolean();
//            if (tree) {
//                new Tree(this, wallX, wallY);
//            } else
            if (direction) {
                new Wall(wallX, wallY, r.nextInt(Settings.SCREEN_WIDTH), 25, this);
            } else {
                new Wall(wallX, wallY, 25, r.nextInt(Settings.SCREEN_HEIGHT), this);
            }
        }

        new Wall(-25, -25, Settings.SCREEN_WIDTH, 25, this);
        new Wall(-25, Settings.SCREEN_HEIGHT, Settings.SCREEN_WIDTH, 25, this);
        new Wall(-25, 0, 25, Settings.SCREEN_HEIGHT, this);
        new DropPerkLaser(this, 250, 250);
        new Wall(Settings.SCREEN_WIDTH, 0, 25, Settings.SCREEN_HEIGHT, this);
    }

    @Override
    public List<GameObject> getGameObjects() {
        return objects;
    }

    @Override
    public void registerPersistent(Identifiable identifiable) {
        persistentObjects.add(identifiable);
    }

    @Override
    public List<Drawable> getDrawableObjects() {
        return drawables;
    }

    @Override
    public List<Tickable> getTickableObjects() {
        return tickables;
    }

    @Override
    public void registerDrawable(Drawable toAdd) {
        drawables.add(toAdd);
    }

    @Override
    public void registerTickable(Tickable tickable) {
        tickables.add(tickable);
    }

    @Override
    public void registerAll(Identifiable toAdd) {
        if (toAdd instanceof Drawable) {
            drawables.add((Drawable) toAdd);
        }
        if (toAdd instanceof Tickable) {
            tickables.add((Tickable) toAdd);
        }
        if (toAdd instanceof Tank) {
            tanks.add((Tank) toAdd);
        }
    }

    @Override
    public void addGameObject(GameObject toAdd) {
        objects.add(toAdd);
        registerAll(toAdd);
    }

    @Override
    public void quit() {
        toQuit = true;
    }

    @Override
    public boolean getQuit() {
        return toQuit;
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
    public int getFPS() {
        return fps;
    }

    @Override
    public void setFPS(int fps) {
        this.fps = fps;
    }

    @Override
    public int getTPS() {
        return tps;
    }

    @Override
    public void setTPS(int tps) {
        this.tps = tps;
    }

    @Override
    public long getTick() {
        return tick;
    }

    @Override
    public void setTick(long tick) {
        this.tick = tick;
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    @Override
    public synchronized void removeGameObject(GameObject object) {
        objects.removeIf(e -> e.getUuid() == object.getUuid());
        tickables.removeIf(e -> e.getUuid() == object.getUuid());
        drawables.removeIf(e -> e.getUuid() == object.getUuid());
        tanks.removeIf(e -> e.getUuid() == object.getUuid());
    }

    @Override
    public List<Tank> ownedBy(UUID uuid) {
        List<Tank> toRet = new CopyOnWriteArrayList<>();
        for (Tank a : getTanks()) {
            if (a.getUuid().equals(uuid)) {
                toRet.add(a);
            }
        }
        return toRet;
    }

    @Override
    public List<Tank> getOwned() {
        return tanks;
    }


}
