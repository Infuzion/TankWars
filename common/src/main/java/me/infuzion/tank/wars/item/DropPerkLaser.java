package me.infuzion.tank.wars.item;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.UUID;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.perk.Perk;
import me.infuzion.tank.wars.object.perk.PerkLaser;
import me.infuzion.tank.wars.object.tank.Tank;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.sprite.SpritePlayer;
import me.infuzion.tank.wars.sprite.SpriteSheetLoader;
import me.infuzion.tank.wars.util.GraphicsObject;
import me.infuzion.tank.wars.util.Position;

public class DropPerkLaser implements Drawable, ItemDrop, Tickable {

    private SpritePlayer player;
    private UUID uuid;
    private Perk perk;
    private Position position;

    public DropPerkLaser(InfoProvider provider, int x, int y) {
        perk = new PerkLaser();
        uuid = UUID.randomUUID();
        position = new Position(x, y);
        SpriteSheetLoader loader = SpriteSheetLoader.getInstance();
        player = new SpritePlayer(loader.getSprite("perk_laser"));
        provider.addGameObject(this);
    }

    @Override
    public boolean draw(GraphicsObject g) {
        if (player.isFinished()) {
            player.reset();
        }
        player.draw(g, position.getX(), position.getY());
        return true;
    }

    @Override
    public Position getPosition() {
        return null;
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
    public Perk getPerk() {
        return perk;
    }

    @Override
    public void onPickup(Tank tank, InfoProvider provider) {
        provider.removeGameObject(this);
    }

    @Override
    public void tick(InfoProvider provider) {
        player.tick();
    }
}
