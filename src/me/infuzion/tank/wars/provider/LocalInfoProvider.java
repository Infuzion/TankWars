package me.infuzion.tank.wars.provider;

import me.infuzion.tank.wars.Tank;

import java.util.Arrays;
import java.util.List;

public class LocalInfoProvider implements InfoProvider {
    private List<Tank> tanks = Arrays.asList(new Tank("Player 1", 240, 0, 90),
            new Tank("Player 2", 0, 0, 180));

    @Override
    public List<Tank> getTanks() {
        return tanks;
    }
}
