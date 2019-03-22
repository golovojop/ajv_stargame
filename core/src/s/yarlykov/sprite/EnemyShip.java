package s.yarlykov.sprite;

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

    private Vector2 velDescent = new Vector2(0, -0.15f);
    private float heightProp;
    private State state;

    public EnemyShip(BulletPool bulletPool, Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
    }

    public void set(TextureRegion[] regions,    //  Картинки корабля
                    float heightProp,           //  Пропорция отрисовки
                    TextureRegion regionBullet, //  Картинка пули
                    Vector2 velShip,            //  Скорость корабля
                    float velBullet,          //  Скорость пули корабля
                    float reloadInterval,
                    int armor,                  //  Защита
                    int damage,                 //  Мощность выстрелов ??
                    int health) {               //  Текущее здоровье

        this.regionsShip = regions;
        this.heightProp = heightProp;
        this.velShip = velShip;
        this.regionBullet = regionBullet;
        this.velBullet.set(0, velBullet);
        this.velCurrent.set(velDescent);
        this.reloadInterval = reloadInterval;
        this.armor = armor;
        this.damage = damage;
        this.health = health;
        this.regions = regions;
        state = State.DESCENT;
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