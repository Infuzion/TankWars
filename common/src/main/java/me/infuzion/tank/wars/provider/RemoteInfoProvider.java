package me.infuzion.tank.wars.provider;

import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Tank;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.provider.remote.GameState;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class RemoteInfoProvider implements InfoProvider {
    private Socket socket;
    private GameState lastResponse;
    private int fps;
    private boolean inUse = false;

    public RemoteInfoProvider() throws IOException {
        int port = Integer.parseInt(JOptionPane.showInputDialog("Enter port"));
        String ip = JOptionPane.showInputDialog("Enter ip");
        socket = new Socket(ip, port);

        new Thread(() -> {
            try {
                while (true) {
                    ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
                    updateResponse((GameState) objIn.readObject());
                    Thread.sleep(25);
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
}
