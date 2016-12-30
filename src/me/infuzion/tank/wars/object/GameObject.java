package me.infuzion.tank.wars.object;

import java.awt.*;
import java.util.UUID;

public interface GameObject {
    Position getPosition();

    void draw(Graphics2D g);

    UUID getUuid();
}
