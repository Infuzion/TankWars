package me.infuzion.tank.wars.object;

import me.infuzion.tank.wars.provider.InfoProvider;

public interface Tickable extends GameObject{
    void tick(InfoProvider provider);
}
