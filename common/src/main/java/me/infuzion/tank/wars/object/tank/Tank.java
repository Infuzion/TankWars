package me.infuzion.tank.wars.object.tank;


import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.object.GameObject;
import me.infuzion.tank.wars.object.Tickable;
import me.infuzion.tank.wars.object.perk.PerkLaser;
import me.infuzion.tank.wars.object.projectile.Projectile;
import me.infuzion.tank.wars.object.projectile.TankProjectile;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.provider.LocalInfoProvider;
import me.infuzion.tank.wars.sprite.SpritePlayer;
import me.infuzion.tank.wars.sprite.SpriteSheetLoader;
import me.infuzion.tank.wars.util.GraphicsObject;
import me.infuzion.tank.wars.util.Position;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

public class Tank implements Drawable, Tickable, GameObject {

    private static final int turnRate = 6;
    //    private static final int shotResetTime = 0;
    private static final int shotResetTime = 30;
    private static final Random random = new Random();
    private final String name;
    private final UUID uuid;
    private transient InfoProvider provider;
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
    private Position pos_last = new Position(-1, -1);

    public Tank(String name, int x, int y, int rot, InfoProvider provider) {
        this(UUID.randomUUID(), new Position(x, y), rot, getRandomColor(), getRandomColor(), true, name, provider);
        this.provider = provider;
    }

    public Tank(ByteBuffer byteBuffer, InfoProvider provider) {
        //UUID uuid = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        //
        //Position position = new Position(byteBuffer.getDouble(), byteBuffer.getDouble());
        //int rot = byteBuffer.getInt();
        //
        //Color primaryColor = intToColor(byteBuffer.getInt());
        //
        //Color secondaryColor = intToColor(byteBuffer.getInt());
        //
        //boolean alive = byteBuffer.get() == 1;
        //
        //int nameLength = byteBuffer.getInt();
        //
        //byte[] nameChars = new byte[nameLength];
        //byteBuffer.get(nameChars);
        //String name = new String(nameChars);
        //noinspection ConstantConditions
        this(byteBuffer.rewind() != null ?                                             // Hack to keep it in 1 statement
                        new UUID(byteBuffer.getLong(), byteBuffer.getLong()) : null,   // UUID
                new Position(byteBuffer.getDouble(), byteBuffer.getDouble()),          // Position
                byteBuffer.getShort(),                                                 // Rotation
                intToColor(byteBuffer.getInt()),                                       // Primary Color
                intToColor(byteBuffer.getInt()),                                       // Secondary Color
                byteBuffer.get() == 1,                                           // Is Alive?
                deserializeName(byteBuffer),                                           // Name
                provider);                                                             // Info Provider
    }

    public Tank(UUID uuid, Position position, int rot, Color primaryColor, Color secondaryColor, boolean alive, String name, InfoProvider provider) {
        this.uuid = uuid;
        this.name = name;
        this.position = position;
        this.rot = rot;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.alive = alive;

        this.rotRadians = Math.toRadians(rot - 90);
        provider.register(this);
        provider.registerPersistent(this);
        provider.getNetworkManager().register(Tank::new, this::serialize, this.getClass().getName().hashCode(), true);
    }

    public static void main(String[] a) {
        LocalInfoProvider provider = new LocalInfoProvider();
        Tank ab = new Tank("123", 0, 1, 123, provider);
        ByteBuffer byteBuffer = ab.serialize();
        Tank c = new Tank(byteBuffer, provider);
        System.out.println(ab.alive);
        System.out.println(c.alive);
    }

    private static Color getRandomColor() {
        return Color.getHSBColor(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    private static String deserializeName(ByteBuffer byteBuffer) {
        int nameLength = byteBuffer.getInt();

        byte[] nameChars = new byte[nameLength];
        byteBuffer.get(nameChars);
        return new String(nameChars);
    }

    private static int colorToInt(Color color) {
        return color.getRGB();
    }

    private static Color intToColor(int color) {
        return new Color(color);
    }

    private ByteBuffer serialize() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(47 + name.length());

        byteBuffer.putLong(uuid.getMostSignificantBits()).putLong(uuid.getLeastSignificantBits());  // 16
        byteBuffer.putDouble(this.position.getX()).putDouble(this.position.getY());                 // 16
        byteBuffer.putShort((short) (rot % 360));                                                   // 2
        byteBuffer.putInt(colorToInt(primaryColor)).putInt(colorToInt(secondaryColor));             // 8
        byteBuffer.put((byte) (alive ? 1 : 0));                                                     // 1
        byteBuffer.putInt(name.length());                                                           // 4
        byteBuffer.put(name.getBytes());                                                            // name.length
        // 47 + name.length
        return byteBuffer;
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
        provider.register(projectile);
    }

    public void shoot(Projectile projectile) {

    }

    public int getRot() {
        return rot;
    }

    public void setRot(int rot) {
        if (rot > 360 || rot < -360) {
            rot %= 360;
        }
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
        PerkLaser p = new PerkLaser();
        p.apply(this);
        provider.register(p);
    }

    public void respawn(Position pos, int rot) {
        this.alive = true;
        setPosition(pos);
        setRot(rot);
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
        if (pos_last.equals(position)) {
            return false;
        }
        if (!alive) {
            if (explosionPlayer != null) {
                explosionPlayer.draw(g, position.getX(), position.getY());
            }
            System.out.println("DEAD");
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
        this.provider = provider;
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
