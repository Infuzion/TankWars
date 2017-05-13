package me.infuzion.tank.wars.input.executor;

import me.infuzion.tank.wars.input.Action;
import me.infuzion.tank.wars.object.tank.Tank;

public interface InputExecutor {

    void onActionDown(Action action);

    void onActionRelease(Action action);

    void executeAction(Tank tank, Action action, InputType type);
}
