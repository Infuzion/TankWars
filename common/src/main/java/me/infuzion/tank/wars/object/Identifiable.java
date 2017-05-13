package me.infuzion.tank.wars.object;

import java.util.UUID;

public interface Identifiable {

    UUID uuid = UUID.randomUUID();

    default UUID getUuid() {
        return uuid;
    }

}
