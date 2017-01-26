package me.infuzion.tank.wars.client.render.swing;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import me.infuzion.tank.wars.provider.InfoProvider;

public class CloseListener implements WindowListener {

    private InfoProvider provider;

    public CloseListener(InfoProvider provider) {
        this.provider = provider;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        provider.quit();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
