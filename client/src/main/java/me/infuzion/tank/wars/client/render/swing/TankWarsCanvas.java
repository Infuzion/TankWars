package me.infuzion.tank.wars.client.render.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.GraphicsObject;
import me.infuzion.tank.wars.util.Settings;

public class TankWarsCanvas extends JPanel {

    private static final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    private InfoProvider provider;

    public void updateTanks(InfoProvider provider) {
        this.provider = provider;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setBackground(Color.WHITE);
        ((Graphics2D) graphics).setRenderingHints(hints);
        GraphicsObject g = new SwingGraphicsObject((Graphics2D) graphics);

        graphics.setColor(Color.BLACK);
        for (Drawable object : provider.getDrawableObjects()) {
            object.draw(g);
        }

        graphics.setFont(graphics.getFont().deriveFont(13f));
        graphics.setColor(Color.black);
        graphics
            .drawString("FPS: " + provider.getFPS() + " limited to " + Settings.frameLimit, 0, 20);
        graphics
            .drawString("TPS: " + provider.getTPS() + " limited to " + Settings.tickLimit, 0, 40);
        graphics.drawString("Swing Renderer", 0, 60);

    }
}
