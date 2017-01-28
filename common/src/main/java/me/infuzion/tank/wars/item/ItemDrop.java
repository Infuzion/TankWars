package me.infuzion.tank.wars.item;

import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.perk.Perk;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;

public interface ItemDrop extends GameObject {

    Perk getPerk();

    void onPickup(Tank tank, InfoProvider provider);

}
