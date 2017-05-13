package me.infuzion.tank.wars.input.executor;

import me.infuzion.tank.wars.input.Action;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.protobuf.Game;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.provider.RemoteInfoProvider;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RemoteInputExecutor implements InputExecutor, Tickable {

    private Stack<Action> pressed = new Stack<>(); // stores currently pressed actions
    private Stack<Action> released = new Stack<>(); // stores released actions
    private List<Action> current = new ArrayList<>();
    private Socket socket;
    private int currentUpdateTick = 0;

    public RemoteInputExecutor(RemoteInfoProvider provider) {
        provider.register(this);
        provider.registerPersistent(this);
        socket = provider.getSocket();
    }

    @Override
    public void onActionDown(Action action) {
        if (!pressed.contains(action)) {
            pressed.add(action);
        }
    }

    @Override
    public void onActionRelease(Action action) {
        if (!released.contains(action)) {
            released.add(action);
        }
    }

    @Override
    public void executeAction(Tank tank, Action action, InputType type) {

    }

    @Override
    public void tick(InfoProvider provider) {
//        currentUpdateTick++;
//        if(!(currentUpdateTick % 5 == 0)) {
//            return;
//        }
//        currentUpdateTick = 0;
        List<Tank> tanks = provider.getControllableTanks();
        int size = tanks.size();
        while (!pressed.empty()) {
            Action e = pressed.pop();
            if (e == null) {
                break;
            }

            if (current.contains(e)) {
                continue;
            }

            if (e.getPlayer() > size) {
                continue;
            }
//            Tank tank = tanks.serialize(e.getPlayer());
            current.add(e);
            Game.Action.Builder b = Game.Action.newBuilder();
            //provider.getControllableTanks().get(e.getPlayer()).getUuid().toString()
            b.setUuid("123");
            b.setAction(Game.Action.ActionType.PRESS);
            b.setType(e.getActionType().toProtobufAction());

            try {
                b.build().writeDelimitedTo(socket.getOutputStream());
                socket.getOutputStream().flush();
                System.out.println("Writing key press! @" + System.currentTimeMillis());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        while (!released.empty()) {
            Action e = released.pop();
            if (e == null) {
                break;
            }

            if (e.getPlayer() > size) {
                continue;
            }
//            Tank tank = tanks.serialize(e.getPlayer());

            current.remove(e);
            Game.Action.Builder b = Game.Action.newBuilder();
//            b.setUuid(tank.getUuid().toString());
            b.setUuid("12-aASD-@32-A");
            b.setAction(Game.Action.ActionType.RELEASE);
            b.setType(e.getActionType().toProtobufAction());

            try {
                b.build().writeDelimitedTo(socket.getOutputStream());
                socket.getOutputStream().flush();
                System.out.println("Writing key release!");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
