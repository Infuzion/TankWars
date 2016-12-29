package me.infuzion.tank.wars.render.swing;

import me.infuzion.tank.wars.provider.InfoProvider;

import javax.swing.*;

public class SwingRenderer implements me.infuzion.tank.wars.render.Renderer {
    private final InfoProvider provider;
    private TankWarsCanvas canvas;


    public SwingRenderer(InfoProvider provider) {
        this.provider = provider;
        init();
    }

    public void init() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setTitle("TankWars");

        canvas = new TankWarsCanvas();
        canvas.updateTanks(provider.getTanks());
        canvas.setSize(640, 480);
        canvas.set
        frame.add(canvas);
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
