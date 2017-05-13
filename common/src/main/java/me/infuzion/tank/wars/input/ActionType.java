package me.infuzion.tank.wars.input;

import me.infuzion.tank.wars.protobuf.Game;

public enum ActionType {
    FIRE,
    TURN_LEFT,
    TURN_RIGHT,
    MOVE_FORWARD,
    MOVE_BACKWARD,
    QUIT;

    public static ActionType fromProtobufAction(Game.Action.Type action) {
        switch (action) {
            case SHOOT:
                return FIRE;
            case TURN_LEFT:
                return TURN_LEFT;
            case TURN_RIGHT:
                return TURN_RIGHT;
            case MOVE_FORWARDS:
                return MOVE_FORWARD;
            case MOVE_BACKWARDS:
                return MOVE_BACKWARD;
            default:
                return null;
        }
    }

    public Game.Action.Type toProtobufAction() {
        switch (this) {
            case FIRE:
                return Game.Action.Type.SHOOT;
            case TURN_LEFT:
                return Game.Action.Type.TURN_LEFT;
            case TURN_RIGHT:
                return Game.Action.Type.TURN_RIGHT;
            case MOVE_FORWARD:
                return Game.Action.Type.MOVE_FORWARDS;
            case MOVE_BACKWARD:
                return Game.Action.Type.MOVE_BACKWARDS;
            default:
                return null;
        }
    }
}
