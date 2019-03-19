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
import s.yarlykov.sprite.Background;
import s.yarlykov.sprite.MainShip;
import s.yarlykov.sprite.Star;

public class GameScreen extends Base2DScreen {

    private Background background;
    private Texture backgroundTexture;
    private Texture mainShipTexture;
    private TextureAtlas atlas;
    private MainShip mainShip;

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
        mainShipTexture = atlas.findRegion("main_ship").getTexture();

        mainShip = new MainShip(atlas);
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
    }

    private void draw() {
        Gdx.gl.glClearColor(0.51f, 0.34f, 0.64f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        mainShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        mainShipTexture.dispose();
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
