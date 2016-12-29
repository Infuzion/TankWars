package me.infuzion.tank.wars.render.swing;

import me.infuzion.tank.wars.Tank;
import me.infuzion.tank.wars.provider.LocalInfoProvider;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwingKeyListener implements KeyListener {

    private final LocalInfoProvider infoProvider;

    public SwingKeyListener(LocalInfoProvider infoProvider){
        this.infoProvider = infoProvider;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Tank p1 = infoProvider.getTanks().get(0);
        if(e.getKeyCode() == KeyEvent.VK_UP){
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
