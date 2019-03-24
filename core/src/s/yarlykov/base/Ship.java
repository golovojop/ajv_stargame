package s.yarlykov.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.math.Rect;
import s.yarlykov.pool.BulletPool;
import s.yarlykov.sprite.Bullet;

public abstract class Ship extends Sprite {
    protected Vector2 velCurrent = new Vector2();           // Текущая скорость корабля
    protected Vector2 velShip = new Vector2();              // Скорость корабля
    protected Vector2 velBullet = new Vector2();           // Скорость пули
    protected Rect worldBounds;

    protected TextureRegion regionBullet;
    protected BulletPool bulletPool;
    protected Sound shootSound;
    protected Sound explosionSound;

    protected float reloadInterval;
    protected float reloadTimer;

    protected float bulletHeight;
    protected int health;
    protected int halfHealth;
    protected int armor;
    protected int damage;

    public Ship() {
    }

    public Ship(TextureRegion region) {
        super(region);
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(velCurrent, delta);
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, regionBullet, pos, velBullet, bulletHeight, worldBounds, damage);
        shootSound.play();
    }


    public void shoot(Vector2 position) {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, regionBullet, position, velBullet, bulletHeight, worldBounds, damage);
        shootSound.play();
    }

    public void hit(int damage) {
        health -= damage;

        // Сменить цвет, если повреждем
        if(health < halfHealth) {
            frame = 1;
        }

        if(health <= 0) {
            health = 0;
            destroy();
            explosionSound.play();
        }
    }

    public int getHealth() {
        return health;
    }

    abstract protected void moveRight();
    abstract protected void moveLeft();
    abstract protected void stop();
}
