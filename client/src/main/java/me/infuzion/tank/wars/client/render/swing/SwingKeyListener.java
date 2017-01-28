package me.infuzion.tank.wars.client.render.swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;

public class SwingKeyListener implements KeyListener, Tickable {
    private final InfoProvider infoProvider;

    private Set<Integer> pressed = new HashSet<>(); // stores currently pressed keycodes

    public SwingKeyListener(InfoProvider infoProvider) {
        this.infoProvider = infoProvider;
        infoProvider.registerTickable(this);
        infoProvider.registerPersistent(this);
    }

    public void tick(InfoProvider provider) {
        if (pressed.contains(KeyEvent.VK_ESCAPE)) {
            infoProvider.quit();
        }

        if (!(infoProvider.getTanks().size() > 0)) {
            return;
        }

        Tank tank = infoProvider.getTanks().get(0);
        if (pressed.contains(KeyEvent.VK_UP)) {
            tank.moveForward();
        }
        if (pressed.contains(KeyEvent.VK_DOWN)) {
            tank.moveBackward();
        }
        if (pressed.contains(KeyEvent.VK_LEFT)) {
            tank.turnLeft();
        }
        if (pressed.contains(KeyEvent.VK_RIGHT)) {
            tank.turnRight();
        }
        if (pressed.contains(KeyEvent.VK_SPACE)) {
            tank.shoot();
        }

        if (!(infoProvider.getTanks().size() > 1)) {
            return;
        }
        tank = infoProvider.getTanks().get(1);
        if (pressed.contains(KeyEvent.VK_W)) {
            tank.moveForward();
        }
        if (pressed.contains(KeyEvent.VK_S)) {
            tank.moveBackward();
        }
        if (pressed.contains(KeyEvent.VK_A)) {
            tank.turnLeft();
        }
        if (pressed.contains(KeyEvent.VK_D)) {
            tank.turnRight();
        }
        if (pressed.contains(KeyEvent.VK_Q)) {
            tank.shoot();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());

    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }

    @Override
    public UUID getUuid() {
        return null;
    }
}
