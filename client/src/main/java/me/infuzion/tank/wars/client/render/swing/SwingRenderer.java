package me.infuzion.tank.wars.client.render.swing;

import me.infuzion.tank.wars.client.util.Settings;
import me.infuzion.tank.wars.provider.InfoProvider;

import javax.swing.*;

public class SwingRenderer implements me.infuzion.tank.wars.client.render.Renderer {
    private final InfoProvider provider;
    private final SwingKeyListener keyListener;
    private TankWarsCanvas canvas;


    public SwingRenderer(InfoProvider provider, SwingKeyListener keyListener) {
        this.provider = provider;
        this.keyListener = keyListener;
        init();
    }

    public void init() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        frame.setTitle("TankWars");

        canvas = new TankWarsCanvas();

//        canvas.getInputMap(WIFW).put(KeyStroke.getKeyStroke("pressed UP"), "move up p1");
//        canvas.getInputMap(WIFW).put(KeyStroke.getKeyStroke("pressed DOWN"), "move down p1");
//        canvas.getInputMap(WIFW).put(KeyStroke.getKeyStroke("pressed LEFT"), "turn left p1");
//        canvas.getInputMap(WIFW).put(KeyStroke.getKeyStroke("pressed RIGHT"), "turn right p1");
//        canvas.getInputMap(WIFW).put(KeyStroke.getKeyStroke("pressed SPACE"), "shoot p1");
//        KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true);
//
//        canvas.getInputMap(WIFW).put(KeyStroke.getKeyStroke("pressed W"), "move up p2");
//        canvas.getInputMap(WIFW).put(KeyStroke.getKeyStroke("pressed S"), "move down p2");
//        canvas.getInputMap(WIFW).put(KeyStroke.getKeyStroke("pressed A"), "turn left p2");
//        canvas.getInputMap(WIFW).put(KeyStroke.getKeyStroke("pressed D"), "turn right p2");
//        canvas.getInputMap(WIFW).put(KeyStroke.getKeyStroke("pressed Q"), "shoot p2");
//
//        canvas.getActionMap().put("move up p1", new PlayerAction(ActionType.FORWARD, 1, provider));
//        canvas.getActionMap().put("move down p1", new PlayerAction(ActionType.BACKWARD, 1, provider));
//        canvas.getActionMap().put("turn left p1", new PlayerAction(ActionType.LEFT, 1, provider));
//        canvas.getActionMap().put("turn right p1", new PlayerAction(ActionType.RIGHT, 1, provider));
//        canvas.getActionMap().put("shoot p1", new PlayerAction(ActionType.FIRE, 1, provider));
//
//        canvas.getActionMap().put("move up p2", new PlayerAction(ActionType.FORWARD, 2, provider));
//        canvas.getActionMap().put("move down p2", new PlayerAction(ActionType.BACKWARD, 2, provider));
//        canvas.getActionMap().put("turn left p2", new PlayerAction(ActionType.LEFT, 2, provider));
//        canvas.getActionMap().put("turn right p2", new PlayerAction(ActionType.RIGHT, 2, provider));
//        canvas.getActionMap().put("shoot p2", new PlayerAction(ActionType.FIRE, 2, provider));
//
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

    }

}
