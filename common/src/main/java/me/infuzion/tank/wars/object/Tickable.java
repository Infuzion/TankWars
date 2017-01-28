package me.infuzion.tank.wars.object;


import me.infuzion.tank.wars.provider.InfoProvider;

public interface Tickable extends Identifiable {

    void tick(InfoProvider provider);
}
