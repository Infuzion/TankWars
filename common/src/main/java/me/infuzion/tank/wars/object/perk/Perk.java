package me.infuzion.tank.wars.object.perk;

import java.awt.Graphics2D;
import me.infuzion.tank.wars.object.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;

public interface Perk {

    void apply(Tank tank);

    void draw(Graphics2D graphics);

    void tick(InfoProvider provider);
}
