package s.yarlykov.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.math.Rect;
import s.yarlykov.math.Rnd;
import s.yarlykov.pool.EnemyPool;
import s.yarlykov.sprite.EnemyShip;

/**
 * Класс отвечает за то, чтобы периодически выводить на экран очередной вражеский
 * корабль. Эмитер запрашивает свободный корабль из пула и настраивает его. Далее
 * вся отрисовка выполняется пулом кораблей через методы работы с активными кораблями.
 */
public class EnemiesEmitter {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_SMALL_SHIP_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_BULLET = 0.01f;
    private static final float ENEMY_SMALL_BULLET_Vy = -0.3f;
    private static final int   ENEMY_SMALL_SHIP_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_SHIP_HEALTHHP = 1;

    private Rect worldBounds;

    private float generateInterval = 4f;
    private float generateTimer = 0f;

    private TextureRegion[] enemySmallRegion;
    private Vector2 enemySmallV = new Vector2(0f, -0.2f);

    private TextureRegion bulletRegion;

    private EnemyPool enemyPool;

    public EnemiesEmitter(TextureAtlas atlas, Rect worldBounds, EnemyPool enemyPool) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
        TextureRegion textureRegion0 = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(textureRegion0, 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            EnemyShip enemy = enemyPool.obtain();

            enemy.set(enemySmallRegion,
                    ENEMY_SMALL_SHIP_HEIGHT,
                    bulletRegion,
                    enemySmallV,
                    ENEMY_SMALL_BULLET_VY,
                    ENEMY_SMALL_RELOAD_INTERVAL,
                    100,
                    100,
                    100);

            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}

