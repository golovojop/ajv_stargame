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

    private static final Vector2 ENEMY_SHIP_VY = new Vector2(0f, -0.1f);
    private static final float ENEMY_SHIP_SHOOT_INTERVAL = 1f;
    private static final float ENEMY_SHIP_HEIGHT = 0.1f;
    private static final int ENEMY_SHIP_DAMAGE = 10;
    private static final int ENEMY_SHIP_HEALTH = 100;
    private static final int ENEMY_SHIP_ARMOR = 50;
    private static final int ENEMIES = 3;
    private static final int FRAME = 0;

    private static final float ENEMY_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_BULLET_VY = -0.3f;

    private int level;

    private Rect worldBounds;

    private float generateInterval = 3f;
    private float generateTimer = 0f;

    private TextureRegion[][] enemyRegions = new TextureRegion[ENEMIES][];

    private TextureRegion bulletRegion;
    private EnemyPool enemyPool;

    public EnemiesEmitter(TextureAtlas atlas, Rect worldBounds, EnemyPool enemyPool) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");

        // Загрузить картинки всех вражеских кораблей
        for(int i = 0; i < ENEMIES; i++){
            this.enemyRegions[i] = Regions.split(atlas.findRegion("enemy" + i), 1, 2, 2);
        }
    }

    /**
     * TODO: Сгенерить новый вражеский корабль для вывода на экран. Корабль
     * TODO: берется из пула перед отрисовкой инициализируется.
     */
    public void generate(float delta, int frags) {

        // Смена уровня учитывает кол-во сбитых врагов.
        level = frags / 10 + 1;

        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            EnemyShip enemy = enemyPool.obtain();

            int next = generateNum();

            enemy.set(enemyRegions[next],
                    ENEMY_SHIP_HEIGHT + (next * 0.03f),
                    FRAME,
                    bulletRegion,
                    ENEMY_BULLET_HEIGHT + (next * 0.02f),
                    ENEMY_SHIP_VY,
                    ENEMY_BULLET_VY,
                    ENEMY_SHIP_SHOOT_INTERVAL,
                    ENEMY_SHIP_ARMOR ,
                    ENEMY_SHIP_DAMAGE + next * 10,
                    ENEMY_SHIP_HEALTH + next * 10);

            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

    /**
     * Сгенерировать номер следующего вражеского корабля
     */
    public int generateNum() {
        int i = 0;

        float num = Rnd.nextFloat(0f, 10f);
        if(num >= 9f) i = (ENEMIES - 1);
        else if(num >= 6) i = (ENEMIES - 2);

        return i;
    }

    public int getLevel() {
        return level;
    }
}