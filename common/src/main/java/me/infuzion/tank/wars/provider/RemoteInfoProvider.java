package me.infuzion.tank.wars.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.Action;
import javax.swing.JOptionPane;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Identifiable;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.provider.remote.GameState;

public class RemoteInfoProvider implements InfoProvider {
    private Socket socket;
    private GameState lastResponse;
    private int fps;
    private boolean inUse = false;
    private boolean toUpdate = false;
    private Action update = null;
    private UUID uuid;

    public RemoteInfoProvider() throws IOException {
        uuid = UUID.randomUUID();
        int port = Integer.parseInt(JOptionPane.showInputDialog("Enter port"));
        String ip = JOptionPane.showInputDialog("Enter ip");
        socket = new Socket(ip, port);

        new Thread(() -> {
            try {
                ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
                while (true) {
                    updateResponse((GameState) objIn.readObject());
                    Thread.sleep(25);
                    if (toUpdate) {
                        objOut.writeObject(update);
                        toUpdate = false;
                    }
                }
            } catch (IOException | InterruptedException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
        while (lastResponse == null) {
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<GameObject> getGameObjects() {
        return getResponse().objects;
    }

    @Override
    public void registerPersistent(Identifiable identifiable) {

    }

    @Override
    public List<Drawable> getDrawableObjects() {
        return getResponse().drawables;
    }

    @Override
    public List<Tickable> getTickableObjects() {
        return getResponse().tickables;
    }

    @Override
    public void addGameObject(GameObject toAdd) {

    }

    @Override
    public void registerDrawable(Drawable toAdd) {

    }

    @Override
    public void registerTickable(Tickable tickable) {

    }

    @Override
    public void registerAll(Identifiable identifiable) {

    }

    @Override
    public void quit() {

    }

    @Override
    public boolean getQuit() {
        return false;
    }

    @Override
    public List<Tank> getTanks() {
        return getResponse().tanks;
    }

    @Override
    public void updateTanks(List<Tank> tanks) {

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
        return getResponse().tps;
    }

    @Override
    public void setTPS(int tps) {
        throw new UnsupportedOperationException("Cannot set tps on remote server");
    }

    @Override
    public long getTick() {
        return getResponse().tick;
    }

    @Override
    public void setTick(long tick) {
        throw new UnsupportedOperationException("Cannot set tick on remote server");
    }

    @Override
    public boolean isRemote() {
        return true;
    }

    @Override
    public void removeGameObject(GameObject object) {
        throw new UnsupportedOperationException("Cannot remove game object on remote server");
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
        List<Tank> toRet = new CopyOnWriteArrayList<>();
        for (Tank a : getTanks()) {
            if (a.getUuid().equals(uuid)) {
                toRet.add(a);
            }
        }
        return toRet;
    }

    private synchronized GameState getResponse() {
        while (inUse) {
        }
        inUse = true;
        GameState toRet = lastResponse;
        inUse = false;
        return toRet;
    }

    private synchronized void updateResponse(GameState state) {
        while (inUse) {
        }
        inUse = true;
        this.lastResponse = state;
        inUse = false;
    }

    private synchronized void sendUpdate(Action action) {
        this.update = action;
        toUpdate = true;
    }
}
