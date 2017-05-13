package me.infuzion.tank.wars.input.executor;

import me.infuzion.tank.wars.protobuf.Game;

public enum InputType {
    PRESSED,
    RELEASED;

    public static InputType fromProtobufAction(Game.Action.ActionType action) {
        switch (action) {
            case RELEASE:
                return RELEASED;
            case PRESS:
                return PRESSED;
        }
        return null;
    }
}
