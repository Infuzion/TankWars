package me.infuzion.tank.wars.sprite;

import org.json.simple.JSONObject;

public class SpriteDescriptor {

    private final String name;
    private final int amount;
    private final int columns;
    private final int rows;
    private final int speed;
    private final double scaleFactor;

    private final String fileName;

    public SpriteDescriptor(JSONObject jsonObject) {
        this.name = (String) jsonObject.get("name");

        JSONObject spriteObject = (JSONObject) jsonObject.get("sprite");
        this.fileName = (String) jsonObject.get("file");
        this.amount = ((Long) spriteObject.get("amount")).intValue();
        this.columns = ((Long) spriteObject.get("columns")).intValue();
        this.rows = ((Long) spriteObject.get("rows")).intValue();
        this.speed = ((Long) spriteObject.getOrDefault("speed", 1)).intValue();

        JSONObject scale = (JSONObject) jsonObject.get("scale");
        if (scale == null) {
            scaleFactor = 1;
        } else {
            scaleFactor = (Double.parseDouble(String.valueOf(scale.getOrDefault("factor", 1))));
        }
        jsonObject.clear();
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public String getFileName() {
        return fileName;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getSpeed() {
        return speed;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }
}
