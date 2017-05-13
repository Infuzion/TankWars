package me.infuzion.tank.wars.client.key.swing;

import com.sun.istack.internal.NotNull;
import me.infuzion.tank.wars.input.Action;
import me.infuzion.tank.wars.input.ActionType;
import me.infuzion.tank.wars.input.executor.LocalInputExecutor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwingKeyListener implements KeyListener {

    private final LocalInputExecutor executor;

    public SwingKeyListener(@NotNull LocalInputExecutor executor) {
        this.executor = executor;
    }


    private Action getActionType(KeyEvent e) {
        if (e.getKeyCode() == (KeyEvent.VK_UP)) {
            return new Action(ActionType.MOVE_FORWARD, 0);
        }
        if (e.getKeyCode() == (KeyEvent.VK_DOWN)) {
            return new Action(ActionType.MOVE_BACKWARD, 0);
        }
        if (e.getKeyCode() == (KeyEvent.VK_LEFT)) {
            return new Action(ActionType.TURN_LEFT, 0);
        }
        if (e.getKeyCode() == (KeyEvent.VK_RIGHT)) {
            return new Action(ActionType.TURN_RIGHT, 0);
        }
        if (e.getKeyCode() == (KeyEvent.VK_SPACE)) {
            return new Action(ActionType.FIRE, 0);
        }

        if (e.getKeyCode() == (KeyEvent.VK_W)) {
            return new Action(ActionType.MOVE_FORWARD, 1);
        }
        if (e.getKeyCode() == (KeyEvent.VK_S)) {
            return new Action(ActionType.MOVE_BACKWARD, 1);
        }
        if (e.getKeyCode() == (KeyEvent.VK_A)) {
            return new Action(ActionType.TURN_LEFT, 1);
        }
        if (e.getKeyCode() == (KeyEvent.VK_D)) {
            return new Action(ActionType.TURN_RIGHT, 1);
        }
        if (e.getKeyCode() == (KeyEvent.VK_Q)) {
            return new Action(ActionType.FIRE, 1);
        }
        if (e.getKeyCode() == (KeyEvent.VK_ESCAPE)) {
            return new Action(ActionType.QUIT, 0);
        }
        return null;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        executor.onActionDown(getActionType(e));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        executor.onActionRelease(getActionType(e));
    }
}
