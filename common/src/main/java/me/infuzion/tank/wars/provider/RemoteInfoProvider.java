package me.infuzion.tank.wars.provider;

import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.provider.network.NetworkManager;
import me.infuzion.tank.wars.provider.network.RemoteNetworkManager;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class RemoteInfoProvider extends LocalInfoProvider {

    private Socket socket;
    private RemoteNetworkManager networkManager;

    public RemoteInfoProvider() throws IOException {
        super();
        int port = Integer.parseInt(JOptionPane.showInputDialog("Enter port"));
        String ip = JOptionPane.showInputDialog("Enter ip");
        socket = new Socket(ip, port);
    }

    @Override
    public NetworkManager getNetworkManager() {
        if (networkManager == null) {
            networkManager = new RemoteNetworkManager(socket, this, (e) -> {
                gameObjectHolder.clear();
                for (GameObject object : e) {
                    gameObjectHolder.addObject(object);
                }
            }, true);
            networkManager.run();
        }
        return networkManager;
    }

    @Override
    public void run() {
    }

    public Socket getSocket() {
        return socket;
    }


    @Override
    public boolean isRemote() {
        return true;
    }
}