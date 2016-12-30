package me.infuzion.tank.wars.client.provider;

import me.infuzion.tank.wars.client.object.Drawable;
import me.infuzion.tank.wars.client.object.GameObject;
import me.infuzion.tank.wars.client.object.Tank;
import me.infuzion.tank.wars.client.object.Tickable;

import java.util.List;

public class RemoteInfoProvider implements InfoProvider {

    @Override
    public List<GameObject> getGameObjects() {
        return null;
    }

    @Override
    public List<Drawable> getDrawableObjects() {
        return null;
    }

    @Override
    public List<Tickable> getTickableObjects() {
        return null;
    }

    @Override
    public void addGameObject(GameObject toAdd) {

    }

    @Override
    public List<Tank> getTanks() {
        return null;
    }

    @Override
    public void updateTanks(List<Tank> tanks) {

    }

    @Override
    public int getFPS() {
        return 0;
    }

    @Override
    public void setFPS(int fps) {

    }

    @Override
    public int getTPS() {
        return 0;
    }

    @Override
    public void setTPS(int tps) {

    }

    @Override
    public boolean isRemote() {
        return false;
    }

    @Override
    public void removeGameObject(GameObject object) {

    }
}
