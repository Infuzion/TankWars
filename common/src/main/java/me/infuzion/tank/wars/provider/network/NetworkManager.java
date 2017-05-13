package me.infuzion.tank.wars.provider.network;

import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.provider.InfoProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public abstract class NetworkManager {
    protected final InfoProvider provider;
    private Map<Integer, RemoteNetworkManager.NetworkedObject> networkedObjectMap = new HashMap<>();

    NetworkManager(InfoProvider provider) {
        this.provider = provider;
    }

    public void register(BiFunction<ByteBuffer, InfoProvider, GameObject> deserialize, Supplier<ByteBuffer> serialize,
                         int identifier, boolean selfRegisters) {
        networkedObjectMap.put(identifier, new RemoteNetworkManager.NetworkedObject(identifier, deserialize, serialize, selfRegisters));

    }

    public byte[] serialize() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (Map.Entry<Integer, RemoteNetworkManager.NetworkedObject> networkedObjectEntry : networkedObjectMap.entrySet()) {
            RemoteNetworkManager.NetworkedObject object = networkedObjectEntry.getValue();
            int id = networkedObjectEntry.getKey();
            ByteBuffer byteBuffer = object.serializer.get();
            try {
                outputStream.write(ByteBuffer.allocate(4).putInt(id).array());
                outputStream.write(byteBuffer.array());
                outputStream.write(new byte[]{-1, -1, -1, -1});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream.toByteArray();

    }

    public List<GameObject> deserialize(ByteBuffer buffer) {
        buffer.rewind();

        List<GameObject> toReturn = new ArrayList<>();

        while (buffer.remaining() > 4) {
            int i = buffer.getInt();
            int startIndex = buffer.position();
            int negOnes = 0;
            while (negOnes != 4) {
                if (buffer.get() == -1) {
                    negOnes++;
                }
            }
            int endIndex = buffer.position() - 4;
            byte[] bytes = new byte[endIndex - startIndex];
            buffer.position(startIndex);
            ByteBuffer toDeserialize = buffer.get(bytes, 0, endIndex - startIndex);
            RemoteNetworkManager.NetworkedObject obj = networkedObjectMap.get(i);
            if (obj != null && !obj.selfReserved) {
                toReturn.add(obj.deserializer.apply(toDeserialize, provider));
            }
            buffer.position(endIndex + 4);
        }
        return toReturn;
    }

    protected static class NetworkedObject {
        int id;
        BiFunction<ByteBuffer, InfoProvider, GameObject> deserializer;
        Supplier<ByteBuffer> serializer;
        boolean selfReserved;

        NetworkedObject(int id, BiFunction<ByteBuffer, InfoProvider, GameObject> deserializer,
                        Supplier<ByteBuffer> serializer, boolean selfReserved) {
            this.id = id;
            this.deserializer = deserializer;
            this.serializer = serializer;
            this.selfReserved = selfReserved;
        }
    }
}
