package me.infuzion.tank.wars.client.action;

import me.infuzion.tank.wars.object.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PlayerAction extends AbstractAction {
    private final ActionType type;
    private final int player;
    private final InfoProvider provider;

    public PlayerAction(ActionType type, int player, InfoProvider provider) {
        this.type = type;
        this.player = player;
        this.provider = provider;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Tank tank = provider.getTanks().get(player - 1);
        switch (type) {
            case FORWARD:
                tank.moveForward();
                break;
            case BACKWARD:
                tank.moveBackward();
                break;
            case LEFT:
                tank.turnLeft();
                break;
            case RIGHT:
                tank.turnRight();
                break;
            case FIRE:
                tank.shoot();
                break;
        }
    }
}
