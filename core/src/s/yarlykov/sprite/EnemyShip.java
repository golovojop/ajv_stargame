package s.yarlykov.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Ship;
import s.yarlykov.base.Sprite;
import s.yarlykov.math.Rect;
import s.yarlykov.utils.Regions;

public class EnemyShip extends Ship {

    private TextureAtlas atlas;
    private float heightProp;
    private Vector2 pos0;
    private Vector2 v;
    private Vector2 vS;
    private Vector2 vB;
    private Vector2 direction;
    private int lives;
    private int bullets;
    private float damage;
    private float armor;
    private float health;
    private Rect worldBounds;

    public EnemyShip(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public void set(String region,      //  Название региона
                    int rows,           //  Строки
                    int cols,           //  Столбцы
                    int frames,         //  Кол-во фреймов
                    float heightProp,   //  Пропорция отрисовки
                    Vector2 pos0,       //  Начальная позиция
                    Vector2 vS,         //  Скорость корабля
                    Vector2 vB,         //  Скорость пули корабля
                    int bullets,        //  Количество пуль
                    int lives,          //  Кол-во жизней
                    float armor,        //  Защита
                    float damage,       //  Мощность выстрелов ??
                    float health) {     //  Текущее здоровье

        this.heightProp = heightProp;
        this.pos0 = pos0;
        this.vS = vS;
        this.vB = vB;
        this.bullets = bullets;
        this.lives = lives;
        this.armor = armor;
        this.damage = damage;
        this.health = health;
        this.v = vS.cpy();

        regions = Regions.split(atlas.findRegion(region), rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(heightProp);
        this.worldBounds = worldBounds;

        // Отрисовать корабль вверху с отступом 0.03f
        pos.set(pos0.x, worldBounds.getTop() - halfHeight - 0.03f);
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);

        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            moveLeft();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            moveRight();
        }
    }

    protected void shoot(){}

    protected void moveRight() {
        v.set(vS);
    }

    protected void moveLeft() {
        v.set(vS).rotate(180);
    }

    protected void stop() {
        v.setZero();
    }
}