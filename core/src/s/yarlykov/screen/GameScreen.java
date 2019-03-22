package s.yarlykov.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import s.yarlykov.base.Base2DScreen;
import s.yarlykov.math.Rect;
import s.yarlykov.pool.BulletPool;
import s.yarlykov.pool.EnemyPool;
import s.yarlykov.sprite.Background;
import s.yarlykov.sprite.EnemyShip;
import s.yarlykov.sprite.MainShip;
import s.yarlykov.sprite.Star;
import s.yarlykov.utils.EnemiesEmitter;

public class GameScreen extends Base2DScreen {

    private Background background;
    private Texture backgroundTexture;
    private TextureAtlas atlas;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private EnemiesEmitter enemiesEmitter;
    private Texture mainShipTexture;


    private Game game;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        backgroundTexture = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(backgroundTexture));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(bulletPool, worldBounds, null);
        enemiesEmitter = new EnemiesEmitter(atlas, worldBounds, enemyPool);
        mainShipTexture = atlas.findRegion("main_ship").getTexture();
        mainShip = new MainShip(atlas, "main_ship");

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        mainShip.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta) {
        mainShip.update(delta);
        bulletPool.updateAllActive(delta);
        enemyPool.updateAllActive(delta);
        enemiesEmitter.generate(delta);
    }

    private void draw() {
        Gdx.gl.glClearColor(0.51f, 0.34f, 0.64f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        mainShip.draw(batch);
        bulletPool.drawAllActive(batch);
        enemyPool.drawAllActive(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
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
}
