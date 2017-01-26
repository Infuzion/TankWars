package me.infuzion.tank.wars.sound;

import org.json.simple.JSONObject;

public class SoundDescriptor {

    private final String name;
    private final String fileName;
    private final boolean loop;

    public SoundDescriptor(JSONObject jsonObject) {
        this.name = (String) jsonObject.get("name");
        this.fileName = (String) jsonObject.get("file");
        this.loop = (boolean) jsonObject.getOrDefault("loop", false);
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isLoop() {
        return loop;
    }
}
