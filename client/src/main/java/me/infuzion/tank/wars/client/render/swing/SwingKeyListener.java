package me.infuzion.tank.wars.client.render.swing;

import me.infuzion.tank.wars.object.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class SwingKeyListener implements KeyListener {

    private final InfoProvider infoProvider;

    private Set<Integer> pressed = new HashSet<>(); // stores currently pressed keycodes

    public SwingKeyListener(InfoProvider infoProvider) {
        this.infoProvider = infoProvider;
    }

    public void tick(){
        if(!(infoProvider.getTanks().size() > 0)){
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
            tank.setRot(tank.getRot() - Tank.turnRate);
        }
        if (pressed.contains(KeyEvent.VK_RIGHT)) {
            tank.setRot(tank.getRot() + Tank.turnRate);
        }
        if (pressed.contains(KeyEvent.VK_SPACE)) {
            tank.shoot();
        }

        if(!(infoProvider.getTanks().size() > 1)){
            return;
        }
        tank = infoProvider.getTanks().get(1);
        if (pressed.contains(KeyEvent.VK_W)) {
            tank.moveForward();
        }
        if (pressed.contains(KeyEvent.VK_A)) {
            tank.moveBackward();
        }
        if (pressed.contains(KeyEvent.VK_S)) {
            tank.setRot(tank.getRot() - Tank.turnRate);
        }
        if (pressed.contains(KeyEvent.VK_D)) {
            tank.setRot(tank.getRot() + Tank.turnRate);
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
}
