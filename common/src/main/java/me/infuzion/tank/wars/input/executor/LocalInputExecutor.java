package me.infuzion.tank.wars.input.executor;

import me.infuzion.tank.wars.input.Action;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocalInputExecutor implements Tickable, InputExecutor {

    private Set<Action> pressed = new HashSet<>(); // stores currently pressed actions
    private InfoProvider provider;

    public LocalInputExecutor(InfoProvider provider) {
        provider.register(this);
        provider.registerPersistent(this);
    }

    @Override
    public void onActionDown(Action action) {
        pressed.add(action);
    }

    @Override
    public void onActionRelease(Action action) {
        pressed.remove(action);
    }

    @Override
    public void executeAction(Tank tank, Action action, InputType type) {
        switch (action.getActionType()) {
            case FIRE:
                tank.shoot();
                break;
            case TURN_LEFT:
                tank.turnLeft();
                break;
            case TURN_RIGHT:
                tank.turnRight();
                break;
            case MOVE_FORWARD:
                tank.moveForward();
                break;
            case MOVE_BACKWARD:
                tank.moveBackward();
                break;
            case QUIT:
                provider.quit();
                break;
        }
    }

    @Override
    public void tick(InfoProvider provider) {
        this.provider = provider;
        List<Tank> tanks = provider.getControllableTanks();
        int size = tanks.size();
        for (Action e : pressed) {
            if (e == null) {
                continue;
            }

            if (e.getPlayer() >= size) {
                continue;
            }
            Tank tank = tanks.get(e.getPlayer());
            executeAction(tank, e, InputType.PRESSED);
        }
    }
}
