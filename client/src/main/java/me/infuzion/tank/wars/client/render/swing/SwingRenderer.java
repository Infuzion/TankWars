package me.infuzion.tank.wars.client.render.swing;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.Settings;

public class SwingRenderer implements me.infuzion.tank.wars.client.render.Renderer {
    private final InfoProvider provider;
    private final SwingKeyListener keyListener;
    private JFrame frame;
    private TankWarsCanvas canvas;


    public SwingRenderer(InfoProvider provider, SwingKeyListener keyListener) {
        this.provider = provider;
        this.keyListener = keyListener;
        init();
    }

    private void init() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new CloseListener(provider));
        frame.setSize(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        frame.setTitle("Tank Wars");

        canvas = new TankWarsCanvas();
        canvas.updateTanks(provider);
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
    public void stop() {
        frame.setVisible(false);
        frame.dispose();
    }

}
