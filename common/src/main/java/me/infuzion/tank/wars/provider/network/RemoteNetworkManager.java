package me.infuzion.tank.wars.provider.network;

import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.provider.InfoProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.Consumer;

public class RemoteNetworkManager extends NetworkManager {
    private final Socket socket;
    private final InfoProvider provider;
    private final Consumer<List<GameObject>> updateObjects;
    private final boolean client;
    private volatile boolean shouldStop = false;

    public RemoteNetworkManager(Socket socket, InfoProvider provider, Consumer<List<GameObject>> updateObjects, boolean client) {
        super(provider);
        this.socket = socket;
        this.provider = provider;
        this.updateObjects = updateObjects;
        this.client = client;
    }

    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            if (client) {
                Thread inputThread = new Thread(() -> {
                    while (!shouldStop) {
                        try {
                            if (inputStream.available() > 2) {
                                byte[] input = new byte[2];
                                if (inputStream.read(input) != 2) {
                                    return;
                                }

                                short size = ByteBuffer.wrap(input).getShort();
                                byte[] data = new byte[size];

                                if (inputStream.read(data) != size) {
                                    return;
                                }

                                ByteBuffer dataBuffer = ByteBuffer.wrap(data);
                                updateObjects.accept(deserialize(dataBuffer));

                            }
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                        }
                    }
                });
                inputThread.setName("Network Input Thread");
                inputThread.start();
            } else {
                Thread outputThread = new Thread(() -> {
                    while (!shouldStop) {
                        try {
                            byte[] bytes = serialize();
                            outputStream.write(ByteBuffer.allocate(2).putShort((short) bytes.length).array());
                            outputStream.write(bytes);
                            Thread.sleep(1000 / 20);
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                outputThread.setName("Network Output Thread");
                outputThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        shouldStop = true;
    }

}
