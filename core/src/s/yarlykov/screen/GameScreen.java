package s.yarlykov.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

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
import s.yarlykov.sprite.Ufo;
import s.yarlykov.utils.EnemiesEmitter;

public class GameScreen extends Base2DScreen {

    private enum State {PLAYING, PAUSE, GAME_OVER}

    private Background background;
    private Texture backgroundTexture;
    private TextureAtlas atlas;
    private TextureAtlas atlasUfo;
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

    private State state;
    private State tmpState;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHealth;
    private StringBuilder sbLevel;

    private Game game;

    private static final int STAR_COUNT = 32;
    private static final float FONT_SIZE = 0.015f;
    private static final float PADDING = 0.01f;
    private static final String FRAGS = "Frags: ";
    private static final String LEVEL = "Level: ";
    private static final String HEALTH = "HP: ";

    private static float HP_BORDER_WIDTH = 0.28f;
    private static float HP_BORDER_HEIGHT = 0.032f;

    private int frags = 0;
    private ShapeRenderer srHealth;

    private Ufo ufo;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();

        state = State.PLAYING;

        font = new Font("font/gb.fnt", "font/gb.png");
        font.setSize(FONT_SIZE);
        font.setColor(Color.CYAN);
        sbFrags = new StringBuilder();
        sbHealth = new StringBuilder();
        sbLevel = new StringBuilder();

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();

        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        backgroundTexture = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(backgroundTexture));
        atlas = new TextureAtlas("textures/mainAtlas.pack");
        atlasUfo = new TextureAtlas("textures/magic/magic.pack");

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, worldBounds, bulletSound, explosionPool);

        enemiesEmitter = new EnemiesEmitter(atlas, worldBounds, enemyPool);
        mainShip = new MainShip(atlas, "main_ship", bulletPool, explosionPool, laserSound);
        ufo = new Ufo(atlasUfo, mainShip);

        logoGameOver = new LogoGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, game);

        starList = new StarBlink[STAR_COUNT];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new StarBlink(atlas);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);

        srHealth = new ShapeRenderer();
        srHealth.setProjectionMatrix(worldToGl);
        srHealth.setAutoShapeType(true);

        background.resize(worldBounds);
        mainShip.resize(worldBounds);
        logoGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
        ufo.resize(worldBounds);

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

        switch (state) {
            case PLAYING:
                mainShip.update(delta);
                bulletPool.updateAllActive(delta);
                enemyPool.updateAllActive(delta);
                enemiesEmitter.generate(delta, frags);
                ufo.update(delta);
                break;
            case GAME_OVER:
                logoGameOver.update(delta);
                buttonNewGame.update(delta);
                ufo.destroy();
                break;
                default:
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

        switch (state) {
            case PLAYING:
                mainShip.draw(batch);
                bulletPool.drawAllActive(batch);
                enemyPool.drawAllActive(batch);
                ufo.draw(batch);
                break;
            case GAME_OVER:
                logoGameOver.draw(batch);
                buttonNewGame.draw(batch);
                break;
                default:
        }

        explosionPool.drawAllActive(batch);
        batch.end();

        healthBar();

        batch.begin();
        printInfo();
        batch.end();
    }

    private void healthBar() {
        // ?????????????? ???????????? ??????????????
        srHealth.begin(ShapeRenderer.ShapeType.Filled);
        srHealth.setAutoShapeType(true);
        srHealth.setColor(mainShip.getHealth() > 50 ? Color.PURPLE : Color.RED);
        float width = HP_BORDER_WIDTH * ((float) mainShip.getHealth() / (float) MainShip.MS_START_HP);
        srHealth.rect(HP_BORDER_WIDTH / 2 - width, worldBounds.getTop() - (HP_BORDER_HEIGHT + PADDING), width, HP_BORDER_HEIGHT);
        srHealth.end();

        // ?????????? ???????????? ?????????? ???????????? ??????????????
        srHealth.begin(ShapeRenderer.ShapeType.Line);
        srHealth.setColor(Color.CYAN);
        srHealth.rect(-HP_BORDER_WIDTH / 2, worldBounds.getTop() - (HP_BORDER_HEIGHT + PADDING), HP_BORDER_WIDTH, HP_BORDER_HEIGHT);
        srHealth.end();
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
        atlasUfo.dispose();
        backgroundTexture.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        font.dispose();
        srHealth.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        switch (state) {
            case PLAYING:
                mainShip.touchDown(touch, pointer);
                break;
            case GAME_OVER:
                buttonNewGame.touchDown(touch, pointer);
                break;
                default:
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        switch (state) {
            case PLAYING:
                mainShip.touchUp(touch, pointer);
                break;
            case GAME_OVER:
                buttonNewGame.touchUp(touch, pointer);
                break;
                default:
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(state == State.PLAYING){
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public void pause() {
        super.pause();
        tmpState = state;
        state = State.PAUSE;
    }

    @Override
    public void resume() {
        super.resume();
        state = tmpState;
    }

    /**
     * ?????????????????? ?????????????????? ?? ?????????????? ?? ???????????????????????? ????????????????
     */
    private void checkCollisions() {
        if (state == State.GAME_OVER || state == State.PAUSE) {
            return;
        }

        // ???????????????????? ???????????????????????? ????????????????
        enemyPool.getActiveObjects().stream()
                .filter(s -> !s.isDestroyed())
                .forEach(enemy -> {
                    float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
                    if (enemy.pos.dst(mainShip.pos) < minDist) {
                        enemy.hit(enemy.getHealth());
                        mainShip.hit(mainShip.getHealth());
                    }
                });

        // ?????????????? ????????????????
        if (ufo.pos.dst(mainShip.pos) < ufo.getHalfWidth()) {
            mainShip.addHealth(MainShip.MS_START_HP);
            if (!ufo.getUsed()) {
                ufo.setHeightProportion(0.6f);
                ufo.setUsed(true);

            }
        }

        // ???????????????????? ?????????????????? ????????
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
                                        // ?????????????? ???????????? ????????????
                                        if (s.isDestroyed()) {
                                            frags++;
                                        }
                                    }
                                });
                    } else {
                        if (mainShip.isBulletCollision(b)) {
                            mainShip.hit(b.getDamage());
                            b.destroy();
                        }
                    }
                });

        state = mainShip.isDestroyed() ? State.GAME_OVER : State.PLAYING;
    }

//    private void checkCollisions() {
//        if (mainShip.isDestroyed()) {
//            return;
//        }
//
//        /**
//         * ???????????????????????? ????????????????.
//         * ?????????????????? ???????? - ???????????? ?????????????????????? ?????????? ????????????????
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
//         * ?????????????????? ???? ???????? ?????????????? ???????????? ?????????????????? ??????????????
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

    public void printInfo() {
        sbFrags.setLength(0);
        sbLevel.setLength(0);
        sbHealth.setLength(0);
        float yPadding = PADDING * 2;
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + PADDING, worldBounds.getTop() - yPadding);
        font.draw(batch, sbLevel.append(LEVEL).append(enemiesEmitter.getLevel()), worldBounds.getRight() - PADDING, worldBounds.getTop() - yPadding, Align.right);
        font.draw(batch, sbHealth.append(HEALTH).append(mainShip.getHealth()).append("/" + MainShip.MS_START_HP), worldBounds.pos.x, worldBounds.getTop() - yPadding, Align.center);
    }
}
