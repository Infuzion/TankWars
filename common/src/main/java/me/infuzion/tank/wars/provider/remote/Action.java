package me.infuzion.tank.wars.provider.remote;

import java.io.Serializable;
import java.util.UUID;

public class Action implements Serializable {
    private static final long serialVersionUID = 1L;
    public final UUID owner;
    public final UUID tankID;
    public final ActionType action;

    public Action(UUID owner, UUID tankID, ActionType action) {
        this.owner = owner;
        this.tankID = tankID;
        this.action = action;
    }
}
