package me.infuzion.tank.wars.input;

public class Action {

    private final ActionType actionType;
    private final int player;

    public Action(ActionType actionType, int player) {
        this.actionType = actionType;
        this.player = player;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        return (o == this) ||
                (o instanceof Action &&
                        ((Action) o).actionType == this.actionType &&
                        ((Action) o).player == this.player);
    }

    @Override
    public int hashCode() {
        return actionType.hashCode() + player * 24;
    }
}
