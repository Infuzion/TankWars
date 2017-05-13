package me.infuzion.tank.wars.object;


import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.Position;

import java.awt.*;
import java.util.UUID;

public interface GameObject extends Identifiable {

    Position getPosition();

    boolean isDestroyable();

    default boolean hasCollision() {
        return true;
    }

    default void destroy(InfoProvider provider) {
        provider.removeGameObject(this);
    }

    Shape getBounds();

    UUID getUuid();
}
