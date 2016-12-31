package me.infuzion.tank.wars.object;


import me.infuzion.tank.wars.util.Position;

import java.awt.*;
import java.util.UUID;

public interface GameObject {
    Position getPosition();

    boolean isDestroyable();

    Shape getBounds();

    void setBounds(Shape bounds);

    UUID getUuid();
}
