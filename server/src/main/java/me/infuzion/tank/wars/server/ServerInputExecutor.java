package me.infuzion.tank.wars.server;

import me.infuzion.tank.wars.input.Action;
import me.infuzion.tank.wars.input.executor.InputType;
import me.infuzion.tank.wars.input.executor.LocalInputExecutor;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class ServerInputExecutor extends LocalInputExecutor {

    private final List<Player> sockets;
    private final InfoProvider provider;

    public ServerInputExecutor(InfoProvider provider, List<Player> sockets) {
        super(provider);
        this.sockets = sockets;
        this.provider = provider;
    }

    @Override
    public void executeAction(Tank tank, Action action, InputType type) {
        super.executeAction(tank, action, type);
//        Game.Position.Builder positionBuilder = Game.Position.newBuilder();
//        positionBuilder.setXPos(tank.getPosition().getX());
//        positionBuilder.setYPos(tank.getPosition().getY());
//
//        Game.GameObject.Builder gameObjectBuilder = Game.GameObject.newBuilder();
//        gameObjectBuilder.setUuid(tank.getUuid().toString());
//        gameObjectBuilder.setPos(positionBuilder);
//        gameObjectBuilder.setClass_(tank.getClass().getCanonicalName());


        byte[] bytes = provider.getNetworkManager().serialize();
        byte[] size = ByteBuffer.allocate(2).putShort((short) bytes.length).array();

        for (Player e : sockets) {
            try {
                OutputStream os = e.socket.getOutputStream();
                os.write(size);
                os.write(bytes);
                System.out.print(Arrays.toString(bytes));
                os.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
