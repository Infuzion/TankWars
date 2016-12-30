package me.infuzion.tank.wars.render.swing;

import me.infuzion.tank.wars.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwingKeyListener implements KeyListener {

    private final InfoProvider infoProvider;

    public SwingKeyListener(InfoProvider infoProvider) {
        this.infoProvider = infoProvider;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Tank p1 = infoProvider.getTanks().get(0);
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            p1.moveForward();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            p1.moveBackward();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            p1.setRot(p1.getRot() - Tank.turnRate);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            p1.setRot(p1.getRot() + Tank.turnRate);
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            p1.shoot(infoProvider);
        }
        infoProvider.updateTank(p1);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
