package me.infuzion.tank.wars.object.misc;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.GraphicsObject;
import me.infuzion.tank.wars.util.Settings;

public class Scoreboard implements Drawable, Tickable {

    private final Map<String, Integer> score = new ConcurrentHashMap<>();
    private InfoProvider provider;

    public Scoreboard(InfoProvider provider) {
        this.provider = provider;
        provider.registerAll(this);
        provider.registerPersistent(this);
    }

    public void addScore(Tank tank) {
        System.out.println("Score added to: " + tank.getName());
        score.put(tank.getName(), score.getOrDefault(tank.getName(), 0) + 1);
        System.out.println("current score : " + score.get(tank.getName()));
    }

    @Override
    public boolean draw(GraphicsObject g) {
        int x = (Settings.SCREEN_WIDTH / 2) - (provider.getTanks().size() * 100);
        g.fillRect(x - 40, Settings.SCREEN_HEIGHT - 190,
            provider.getTanks().size() * 130, 130);

        for (Tank e : provider.getTanks()) {
            int score = this.score.getOrDefault(e.getName(), 0);

            e.draw(g, x, Settings.SCREEN_HEIGHT - 180, 180, false);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 25));
            g.setColor(Color.RED);
            g.drawString(String.valueOf(score), x + 15, Settings.SCREEN_HEIGHT - 110);
            x += 120;
        }
        return true;
    }

    @Override
    public void tick(InfoProvider provider) {
        provider.getTanks()
            .forEach((e) -> score.put(e.getName(), score.getOrDefault(e.getName(), 0)));
    }
}
