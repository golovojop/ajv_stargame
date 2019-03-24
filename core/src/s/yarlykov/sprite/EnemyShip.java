package s.yarlykov.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Ship;
import s.yarlykov.base.Sprite;
import s.yarlykov.math.Rect;
import s.yarlykov.pool.BulletPool;
import s.yarlykov.utils.Regions;

public class EnemyShip extends Ship {

    private enum State {DESCENT, FIGHT}

    /**
     *     protected Vector2 velCurrent;           // Текущая скорость пули
     *     protected Vector2 velShip;              // Скорость корабля
     *     protected Vector2 velBullet;            // Скорость пули
     *     protected Vector2 pos0;                 // Начальная позиция корябля
     *
     *     protected TextureRegion[] regionsShip;
     *     protected TextureRegion regionBullet;
     *     protected BulletPool bulletPool;
     *     protected Rect worldBounds;
     *     protected Sound shootSound;
     *
     *     protected float reloadInterval;
     *     protected float reloadTimer;
     *     protected float bulletHeight;
     *     protected float health;
     *     protected float armor;
     *     protected int damage;
     */

    private Vector2 velDescent = new Vector2(0, -0.02f);
    private State state;

    public EnemyShip(BulletPool bulletPool, Sound shootSound, Sound explosionSound, Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.explosionSound = explosionSound;
    }

    public void set(TextureRegion[] regions,    //  Картинки корабля
                    float heightProp,           //  Пропорция отрисовки корабля
                    int frame,
                    TextureRegion regionBullet, //  Картинка пули
                    float bulletHeight,         //  Пропорция отрисовки пули
                    Vector2 velShip,            //  Скорость корабля
                    float velBullet,            //  Скорость пули
                    float reloadInterval,
                    int armor,                  //  Защита
                    int damage,                 //  Мощность выстрелов
                    int health) {               //  Текущее здоровье

        this.regions = regions;
        this.velShip = velShip;
        this.velCurrent.set(velDescent);
        this.frame = frame;
        this.regionBullet = regionBullet;
        this.bulletHeight = bulletHeight;
        this.velBullet.set(0, velBullet);
        this.reloadInterval = reloadInterval;
        this.armor = armor;
        this.damage = damage;
        this.health = health;
        this.halfHealth = health / 2;
        this.state = State.DESCENT;
        isDestroyed = false;
        setHeightProportion(heightProp);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (state) {
            case DESCENT:
                if (getTop() <= worldBounds.getTop()) {
                    velCurrent.set(velShip);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                reloadTimer += delta;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }
                if (getBottom() <= worldBounds.getBottom()) {
                    this.destroy();
                }
                break;
        }
    }

    protected void moveRight() {
        velCurrent.set(velShip);
    }
    protected void moveLeft() {
        velCurrent.set(velShip).rotate(180);
    }
    protected void stop() {
        velCurrent.setZero();
    }
}