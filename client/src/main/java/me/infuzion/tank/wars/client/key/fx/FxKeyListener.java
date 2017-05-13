package me.infuzion.tank.wars.client.key.fx;

import com.sun.istack.internal.NotNull;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import me.infuzion.tank.wars.input.Action;
import me.infuzion.tank.wars.input.ActionType;
import me.infuzion.tank.wars.input.executor.InputExecutor;

public class FxKeyListener {

    private final InputExecutor executor;

    public FxKeyListener(@NotNull InputExecutor executor) {
        this.executor = executor;
    }

    public void onKeyDown(KeyEvent event) {
        executor.onActionDown(getActionType(event));
    }

    private Action getActionType(KeyEvent e) {
        if (e.getCode() == (KeyCode.UP)) {
            return new Action(ActionType.MOVE_FORWARD, 0);
        }
        if (e.getCode() == (KeyCode.DOWN)) {
            return new Action(ActionType.MOVE_BACKWARD, 0);
        }
        if (e.getCode() == (KeyCode.LEFT)) {
            return new Action(ActionType.TURN_LEFT, 0);
        }
        if (e.getCode() == (KeyCode.RIGHT)) {
            return new Action(ActionType.TURN_RIGHT, 0);
        }
        if (e.getCode() == (KeyCode.SPACE)) {
            return new Action(ActionType.FIRE, 0);
        }

        if (e.getCode() == (KeyCode.W)) {
            return new Action(ActionType.MOVE_FORWARD, 1);
        }
        if (e.getCode() == (KeyCode.S)) {
            return new Action(ActionType.MOVE_BACKWARD, 1);
        }
        if (e.getCode() == (KeyCode.A)) {
            return new Action(ActionType.TURN_LEFT, 1);
        }
        if (e.getCode() == (KeyCode.D)) {
            return new Action(ActionType.TURN_RIGHT, 1);
        }
        if (e.getCode() == (KeyCode.Q)) {
            return new Action(ActionType.FIRE, 1);
        }
        if (e.getCode() == (KeyCode.ESCAPE)) {
            return new Action(ActionType.QUIT, 0);
        }
        return null;
    }

    public void onKeyRelease(KeyEvent event) {
        executor.onActionRelease(getActionType(event));
    }
}
