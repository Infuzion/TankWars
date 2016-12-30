package me.infuzion.tank.wars.client.object;

import me.infuzion.tank.wars.client.provider.InfoProvider;

public interface Tickable extends GameObject{
    void tick(InfoProvider provider);
}
