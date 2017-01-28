package me.infuzion.tank.wars.object.tank;


import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.Random;
import java.util.UUID;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.perk.PerkLaser;
import me.infuzion.tank.wars.object.projectile.TankProjectile;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.sprite.SpritePlayer;
import me.infuzion.tank.wars.sprite.SpriteSheetLoader;
import me.infuzion.tank.wars.util.GraphicsObject;
import me.infuzion.tank.wars.util.Position;

public class Tank implements Drawable, Tickable, GameObject {

    private static final int turnRate = 6;
    //    private static final int shotResetTime = 0;
    private static final int shotResetTime = 30;
    private static final Random random = new Random();
    private final String name;
    private final UUID uuid;
    private transient final InfoProvider provider;
    private Position shootLocation;
    private int speed = 25;
    private Position position;
    private int rot;
    private double rotRadians;
    private SpritePlayer explosionPlayer;
    private Color primaryColor = Color
        .getHSBColor(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    private Color secondaryColor = Color.DARK_GRAY;
    private Position center;
    private Shape bounds = new Rectangle();
    private long lastShot = -1;
    private UUID owner;
    private boolean alive;
    private int rot_last = -1;
    private Position pos_last = new Position(-1, -1);

    public Tank(String name, int x, int y, int rot, InfoProvider provider) {
        this.provider = provider;
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.rot = rot;
        this.position = new Position(x, y);
        this.rotRadians = Math.toRadians(rot - 90);
        this.alive = true;
        provider.addGameObject(this);
        provider.registerPersistent(this);
    }

    public Position getCenter() {
        return center;
    }

    public void moveForward() {
        if (!alive) {
            return;
        }
        position.setX(position.getX() - speed * Math.cos(rotRadians));
        position.setY(position.getY() - speed * Math.sin(rotRadians));
    }

    public void shoot() {
        if (shootLocation == null || !alive || lastShot + shotResetTime >= provider.getTick()) {
            return;
        }
        lastShot = provider.getTick();
        TankProjectile projectile = new TankProjectile(center, rot - 180);
        provider.addGameObject(projectile);
    }

    public int getRot() {
        return rot;
    }

    public void setRot(int rot) {
        this.rotRadians = Math.toRadians(rot - 90);
        this.rot = rot;
    }

    public void turnLeft() {
        if (!alive) {
            return;
        }
        setRot(rot - turnRate);
    }

    public void turnRight() {
        if (!alive) {
            return;
        }
        setRot(rot + turnRate);
    }

    public String getName() {
        return name;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void moveBackward() {
        if (!alive) {
            return;
        }
        position.setX(position.getX() + speed * Math.cos(rotRadians));
        position.setY(position.getY() + speed * Math.sin(rotRadians));
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean isDestroyable() {
        return alive;
    }

    @Override
    public void destroy(InfoProvider provider) {
        this.alive = false;
        SpriteSheetLoader loader = SpriteSheetLoader.getInstance();
        this.explosionPlayer = new SpritePlayer(loader.getSprite("explosion"));
    }

    public void respawn(Position pos, int rot) {
        this.alive = true;
        setPosition(pos);
        setRot(rot);
        PerkLaser p = new PerkLaser();
        p.apply(this);
        provider.registerAll(p);

    }

    public Shape getBounds() {
        if (!alive) {
            return null;
        }
        return bounds;
    }

    public void setBounds(Shape bounds) {
        this.bounds = bounds;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void draw(GraphicsObject g, int x, int y, int rot, boolean drawName) {
        draw(g, x, y, rot, drawName, false);
    }

    private void draw(GraphicsObject g, int x, int y, int rot, boolean drawName,
        boolean updateBounds) {
        Rectangle tankBody = new Rectangle(x, y, 50, 100);

        double centX = tankBody.getCenterX();
        double centY = tankBody.getCenterY();

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rot), centX, centY);
        Shape tankBodyToDraw = transform.createTransformedShape(tankBody);

        Ellipse2D circle = new Double();
        circle.setFrame(tankBody);
        circle.setFrame((centX + ((circle.getX() - circle.getCenterX())) / 2) - 12,
            (centY + (circle.getY() - circle.getCenterY()) / 2), 50, 50);
        Shape circleToDraw = transform.createTransformedShape(circle);

        Rectangle barrel = new Rectangle();
        barrel.setFrame(circle.getBounds());
        barrel.setFrame((centX - ((barrel.getX() - barrel.getCenterX())) / 2) - 18,
            (centY - (barrel.getY() - barrel.getCenterY()) / 2) - 12, 10, 75);

        Point p = new Point((int) (centX - ((barrel.getX() - barrel.getCenterX())) / 2) - 4,
            (int) (centY - (barrel.getY() - barrel.getCenterY()) / 2) + 75);
        Shape barrelToDraw = transform.createTransformedShape(barrel);

        g.setPaint(this.getPrimaryColor());
        g.draw(tankBodyToDraw);
        g.fill(tankBodyToDraw);

        g.setPaint(this.getSecondaryColor());

        g.draw(barrelToDraw);
        g.fill(barrelToDraw);

        g.draw(circleToDraw);
        g.fill(circleToDraw);
        transform.transform(p, p);
        g.drawOval((int) p.getX(), (int) p.getY(), 5, 5);
        if (shootLocation == null) {
            shootLocation = new Position(p.getX(), p.getY());
        } else {
            shootLocation.setX(p.getX());
            shootLocation.setY(p.getY());
        }
        if (updateBounds) {
            this.setBarrelPosition(new Position(p.getX(), p.getY()));
            this.setBounds(transform.createTransformedShape(tankBody.getBounds()));
        }
        if (drawName) {
            g.drawString(name, (int) centX, (int) bounds.getBounds().getMaxY() + 50);
        }
    }

    @Override
    public boolean draw(GraphicsObject g) {
        if (rot_last == rot) {
            if (pos_last.equals(position)) {
                return false;
            }
        }
        if (!this.isAlive()) {
            if (explosionPlayer != null) {
                explosionPlayer.draw(g, position.getX(), position.getY());
            }
            return true;
        }
        double x = position.getX();
        double y = position.getY();
        int rot = this.getRot();

        draw(g, (int) x, (int) y, rot, true, true);
        return true;
    }

    private void setBarrelPosition(Position center) {
        this.center = center;
    }

    @Override
    public void tick(InfoProvider provider) {
        if (explosionPlayer != null) {
            explosionPlayer.tick();
        }
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public boolean isAlive() {
        return alive;
    }
}
