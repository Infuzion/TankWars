package me.infuzion.tank.wars.client.render.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.Settings;

public class TankWarsCanvas extends JPanel {

    private List<Tank> tanks = new ArrayList<>();
    private InfoProvider provider;

    public void updateTanks(InfoProvider provider) {
        this.tanks = provider.getTanks();
        this.provider = provider;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setBackground(Color.WHITE);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        for (Tank tank : tanks) {
            drawTank(tank, graphics2D);
        }

        graphics2D.setColor(Color.BLACK);
        for (Drawable object : provider.getDrawableObjects()) {
            Graphics g = graphics2D.create();
            object.draw((Graphics2D) g);
            g.dispose();
        }

        graphics2D.setColor(Color.black);
        graphics2D
            .drawString("FPS: " + provider.getFPS() + " limited to " + Settings.frameLimit, 0, 10);
        graphics2D
            .drawString("TPS: " + provider.getTPS() + " limited to " + Settings.tickLimit, 0, 20);

    }

    public void drawTank(Tank tank, Graphics2D graphics2D) {

    }
}
