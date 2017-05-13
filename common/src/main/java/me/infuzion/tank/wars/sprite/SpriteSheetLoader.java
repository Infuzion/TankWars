package me.infuzion.tank.wars.sprite;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SpriteSheetLoader {

    private static final String SPRITE_PATH = "/sprite/";
    private static SpriteSheetLoader instance;
    private Map<String, Sprite> spriteMap = new HashMap<>();

    private SpriteSheetLoader() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject spriteList = (JSONObject) parser.parse(
            new InputStreamReader(getClass().getResourceAsStream(SPRITE_PATH + "sprites.json")));

        JSONArray sprites = (JSONArray) spriteList.get("sprites");

        sprites.forEach((Object e) -> {
            try {
                SpriteDescriptor descriptor = new SpriteDescriptor((JSONObject) parser
                    .parse(new InputStreamReader(getClass().getResourceAsStream(SPRITE_PATH + e))));
                spriteMap.put(descriptor.getName(), new Sprite(descriptor,
                    getClass().getResourceAsStream(SPRITE_PATH + descriptor.getFileName())));

            } catch (IOException | ParseException e1) {
                e1.printStackTrace();
            }
        });
        for (Object e : sprites) {
            String obj = (String) e;
            System.out.println(obj);
        }
        instance = this;
    }

    public static SpriteSheetLoader getInstance() {
        if (instance != null) {
            return instance;
        }
        try {
            return new SpriteSheetLoader();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        new SpriteSheetLoader();
    }

    public Sprite getSprite(String name) {
        return spriteMap.get(name);
    }
}
