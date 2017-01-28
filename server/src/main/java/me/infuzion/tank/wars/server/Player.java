package me.infuzion.tank.wars.server;

import java.net.Socket;
import java.util.UUID;
import me.infuzion.tank.wars.object.tank.Tank;

public class Player {
    public final String name;
    public final UUID uuid;
    public final Tank tank;
    public final Socket socket;

    public Player(String name, UUID uuid, Tank tank, Socket socket) {
        this.name = name;
        this.uuid = uuid;
        this.tank = tank;
        this.socket = socket;
    }
}
