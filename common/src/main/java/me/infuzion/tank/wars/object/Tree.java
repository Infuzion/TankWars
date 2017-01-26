package me.infuzion.tank.wars.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.UUID;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.sprite.SpritePlayer;
import me.infuzion.tank.wars.sprite.SpriteSheetLoader;
import me.infuzion.tank.wars.util.Position;

public class Tree implements Drawable {

    private final UUID uuid;
    private SpritePlayer animation;
    private Position position;

    public Tree(InfoProvider provider, int x, int y) {
        this(provider, new Position(x, y));
    }

    public Tree(InfoProvider provider, Position position) {
        provider.addGameObject(this);
        uuid = UUID.randomUUID();
        this.position = position;
        SpriteSheetLoader loader = SpriteSheetLoader.getInstance();
        this.animation = new SpritePlayer(loader.getSprite("tree"));
    }

    @Override
    public boolean draw(Graphics2D g) {
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
}
