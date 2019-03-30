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

    private static final Vector2 ENEMY_SHIP_VY = new Vector2();
    private static final float ENEMY_SHIP_SHOOT_INTERVAL = 1.6f;
    private static final float ENEMY_SHIP_HEIGHT = 0.1f;
    private static final int ENEMY_SHIP_DAMAGE = 10;
    private static final int ENEMY_SHIP_HEALTH = 100;
    private static final int ENEMY_SHIP_ARMOR = 50;
    private static final int ENEMY_TYPES = 3;
    private static final int FRAME = 0;

    private static final float ENEMY_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_BULLET_VY = -0.3f;

    private int level;

    private Rect worldBounds;

    private float generateInterval = 3f;
    private float generateTimer = 0f;

    private TextureRegion[][] enemyRegions = new TextureRegion[ENEMY_TYPES][];

    private TextureRegion bulletRegion;
    private EnemyPool enemyPool;

    public EnemiesEmitter(TextureAtlas atlas, Rect worldBounds, EnemyPool enemyPool) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.ENEMY_SHIP_VY.set(0f, -0.08f);

        // Загрузить картинки всех вражеских кораблей
        for(int i = 0; i < ENEMY_TYPES; i++){
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

            int byType = randomEnemyType();

            enemy.set(enemyRegions[byType],
                    ENEMY_SHIP_HEIGHT + (byType * 0.03f),
                    FRAME,
                    bulletRegion,
                    ENEMY_BULLET_HEIGHT + (byType * 0.02f),
                    ENEMY_SHIP_VY.add(0, -(level-1) * 0.01f),
                    ENEMY_BULLET_VY -(level-1) * 0.01f ,
                    ENEMY_SHIP_SHOOT_INTERVAL,
                    ENEMY_SHIP_ARMOR ,
                    ENEMY_SHIP_DAMAGE + byType * 10,
                    ENEMY_SHIP_HEALTH + byType * 10);

            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

    /**
     * Определить тип следующего вражеского корабля
     */
    public int randomEnemyType() {
        int i = 0;

        float num = Rnd.nextFloat(0f, 10f);
        if(num >= 9f) i = (ENEMY_TYPES - 1);
        else if(num >= 6) i = (ENEMY_TYPES - 2);

        return i;
    }

    public int getLevel() {
        return level;
    }
}