package me.infuzion.tank.wars.client.render.swing;

import me.infuzion.tank.wars.client.key.swing.SwingKeyListener;
import me.infuzion.tank.wars.input.executor.LocalInputExecutor;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.Settings;

import javax.swing.*;

public class SwingRenderer implements me.infuzion.tank.wars.client.render.Renderer {
    private final InfoProvider provider;
    private final SwingKeyListener keyListener;
    private JFrame frame;
    private TankWarsCanvas canvas;


    public SwingRenderer(InfoProvider provider, LocalInputExecutor inputExecutor) {
        this.provider = provider;
        this.keyListener = new SwingKeyListener(inputExecutor);
        init();
    }

    private void init() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new CloseListener(provider));
        frame.setSize(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        frame.setTitle("Tank Wars");

        canvas = new TankWarsCanvas(provider);
        canvas.addKeyListener(keyListener);
        canvas.setFocusable(true);
        canvas.setSize(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        canvas.setDoubleBuffered(true);
        frame.add(canvas);
        frame.setResizable(false);

        canvas.setVisible(true);
        frame.setVisible(true);
    }


    @Override
    public void draw() {
        canvas.repaint();
    }

    @Override
    public void stopRenderer() {
        frame.setVisible(false);
        frame.dispose();
    }

}
