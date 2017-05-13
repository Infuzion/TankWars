package me.infuzion.tank.wars.object.misc;

import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.sprite.SpritePlayer;
import me.infuzion.tank.wars.sprite.SpriteSheetLoader;
import me.infuzion.tank.wars.util.GraphicsObject;
import me.infuzion.tank.wars.util.Position;

import java.awt.*;
import java.util.UUID;

public class Tree implements Drawable, GameObject, Tickable {

    private final UUID uuid;
    private SpritePlayer animation;
    private Position position;

    public Tree(InfoProvider provider, int x, int y) {
        this(provider, new Position(x, y));
    }

    public Tree(InfoProvider provider, Position position) {
        provider.register(this);
        uuid = UUID.randomUUID();
        this.position = position;
        SpriteSheetLoader loader = SpriteSheetLoader.getInstance();
        this.animation = new SpritePlayer(loader.getSprite("tree"));
    }

    @Override
    public boolean draw(GraphicsObject g) {
        if (animation.isFinished()) {
            animation.reset();
        }
        animation.draw(g, position.getX(), position.getY());
        return true;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isDestroyable() {
        return false;
    }

    @Override
    public Shape getBounds() {
        return new Rectangle();
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void tick(InfoProvider provider) {
        animation.tick();
    }
}
