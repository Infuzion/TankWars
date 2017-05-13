package me.infuzion.tank.wars.client.key;

import me.infuzion.tank.wars.input.ActionType;

import java.util.HashMap;
import java.util.Map;

public class KeyMap {

    private final Map<ActionType, String> keycodes;

    public KeyMap(Map<ActionType, String> keycodes) {
        this.keycodes = keycodes;
    }

    public KeyMap() {
        keycodes = new HashMap<>();
    }
}
