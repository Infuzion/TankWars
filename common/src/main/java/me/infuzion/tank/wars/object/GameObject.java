package me.infuzion.tank.wars.object;


import java.awt.Shape;
import java.io.Serializable;
import java.util.UUID;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.Position;

public interface GameObject extends Serializable {

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
