package s.yarlykov.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Base2DScreen;
import s.yarlykov.base.Font;
import s.yarlykov.math.Rect;
import s.yarlykov.pool.BulletPool;
import s.yarlykov.pool.EnemyPool;
import s.yarlykov.pool.ExplosionPool;
import s.yarlykov.sprite.Background;
import s.yarlykov.sprite.ButtonNewGame;
import s.yarlykov.sprite.LogoGameOver;
import s.yarlykov.sprite.MainShip;
import s.yarlykov.sprite.StarBlink;
import s.yarlykov.utils.EnemiesEmitter;

public class GameScreen extends Base2DScreen {

    private Background background;
    private Texture backgroundTexture;
    private TextureAtlas atlas;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private EnemiesEmitter enemiesEmitter;
    private ExplosionPool explosionPool;

    private LogoGameOver logoGameOver;
    private ButtonNewGame buttonNewGame;

    private StarBlink starList[];

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;

    private boolean gameOver;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHealth;
    private StringBuilder sbLevel;

    private Game game;

    private static final int STAR_COUNT = 32;
    private static final float FONT_SIZE = 0.02f;
    private static final float FONT_PADDING = 0.005f;
    private static final String FRAGS = "Frags: ";
    private int frags;


    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        backgroundTexture = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(backgroundTexture));
        atlas = new TextureAtlas("textures/mainAtlas.pack");

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, worldBounds, bulletSound, explosionPool);

        enemiesEmitter = new EnemiesEmitter(atlas, worldBounds, enemyPool);
        mainShip = new MainShip(atlas, "main_ship", bulletPool, explosionPool, laserSound);

        logoGameOver = new LogoGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, game);

        starList = new StarBlink[STAR_COUNT];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new StarBlink(atlas);
        }

//        font = new Font("font/font.fnt", "font/font.png");
        font = new Font("font/gb.fnt", "font/gb.png");
        font.setSize(FONT_SIZE);
        sbFrags = new StringBuilder();
        sbHealth = new StringBuilder();
        sbLevel = new StringBuilder();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        mainShip.resize(worldBounds);
        logoGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);

        for (StarBlink star : starList) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void update(float delta) {
        explosionPool.updateAllActive(delta);
        for (StarBlink star : starList) {
            star.update(delta);
        }

        if(gameOver) {
            logoGameOver.update(delta);
            buttonNewGame.update(delta);
        } else {
            mainShip.update(delta);
            bulletPool.updateAllActive(delta);
            enemyPool.updateAllActive(delta);
            enemiesEmitter.generate(delta);
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0.51f, 0.34f, 0.64f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);

        for (StarBlink star : starList) {
            star.draw(batch);
        }

        if(gameOver) {
            logoGameOver.draw(batch);
            buttonNewGame.draw(batch);
        } else {
            mainShip.draw(batch);
            bulletPool.drawAllActive(batch);
            enemyPool.drawAllActive(batch);
        }
        explosionPool.drawAllActive(batch);
        printInfo();
        batch.end();
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    @Override
    public void dispose() {
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
        atlas.dispose();
        backgroundTexture.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        buttonNewGame.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        buttonNewGame.touchUp(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    /**
     * Проверить попадание в корабли и столкновение кораблей
     */
    private void checkCollisions() {

        // Отработать столкновение кораблей
        enemyPool.getActiveObjects().stream()
                .filter(s -> !s.isDestroyed())
                .forEach(enemy -> {
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst(mainShip.pos) < minDist) {
                enemy.hit(enemy.getHealth());
                mainShip.hit(mainShip.getHealth());
            }
        });

        // Отработать попадание пуль
        bulletPool.getActiveObjects().stream()
                .filter(b -> !b.isDestroyed())
                .forEach(b -> {

                    if (b.getOwner().getClass() == MainShip.class) {
                        enemyPool.getActiveObjects().stream()
                                .filter(s -> !s.isDestroyed())
                                .forEach(s -> {
                                    if (s.isBulletCollision(b)) {
                                        s.hit(b.getDamage());
                                        b.destroy();
//                                        System.out.println("checkCollisions: enemyShip health = " + s.getHealth());
                                    }
                                });
                    } else {
                        if (mainShip.isBulletCollision(b)) {
                            mainShip.hit(b.getDamage());
                            b.destroy();
//                            System.out.println("checkCollisions: mainShip health = " + mainShip.getHealth());
                        }
                    }
                });

        gameOver = mainShip.isDestroyed();
    }

//    private void checkCollisions() {
//        if (mainShip.isDestroyed()) {
//            return;
//        }
//
//        /**
//         * Столкновение кораблей.
//         * Наносимый урон - полное уничтожение обоих кораблей
//         */
//        List<EnemyShip> enemyList = enemyPool.getActiveObjects();
//
//        for (EnemyShip enemy : enemyList) {
//            if (enemy.isDestroyed()) {
//                continue;
//            }
//            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
//
//            if (enemy.pos.dst(mainShip.pos) < minDist) {
//                enemy.hit(enemy.getHealth());
//                mainShip.hit(mainShip.getHealth());
//                return;
//            }
//        }
//
//        /**
//         * Попадание во враж корабль пулями основного корабля
//         */
//        List<Bullet> bulletList = bulletPool.getActiveObjects();
//
//        for (EnemyShip enemy : enemyList) {
//            if (enemy.isDestroyed()) {
//                continue;
//            }
//            for (Bullet bullet : bulletList) {
//                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
//                    continue;
//                }
//                if (enemy.isBulletCollision(bullet)) {
//                    enemy.hit(bullet.getDamage());
//                    bullet.destroy();
//                }
//            }
//        }

    public void printInfo(){
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append("TEST"), worldBounds.getLeft() + FONT_PADDING, worldBounds.getTop() - FONT_PADDING);
    }
}
