package me.infuzion.tank.wars.server;

import me.infuzion.tank.wars.object.Tank;

import java.net.Socket;
import java.util.UUID;

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
