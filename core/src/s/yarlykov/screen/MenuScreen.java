/**
 * Иконки, картинки, кнопки
 * https://opengameart.org/content/user-interface-ui-art-collection
 * https://www.imagefu.com/create/button
 *
 */

package s.yarlykov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Base2DScreen;
import s.yarlykov.math.Rect;
import s.yarlykov.sprite.Background;
import s.yarlykov.sprite.ButtonExit;
import s.yarlykov.sprite.ButtonOptions;
import s.yarlykov.sprite.ButtonPlay;
import s.yarlykov.sprite.LogoShip;
import s.yarlykov.sprite.Logo;
import s.yarlykov.sprite.Star;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MenuScreen extends Base2DScreen {

    private static final int STAR_COUNT = 128;

    private Game game;

    private Texture backgroundTexture;
    private Background background;

    private TextureAtlas atlas;
    private TextureAtlas buttons;
    private TextureAtlas flyLogo;
    private Star starList[];
//    private Sound sound;
    private Music music;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;
    private ButtonOptions buttonOptions;
    private Logo logo;
    private LogoShip flyingShip;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        backgroundTexture = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(backgroundTexture));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        buttons = new TextureAtlas("textures/buttonsMenu.pack");
        flyLogo = new TextureAtlas("textures/animation/animation.pack");

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/terminator.mp3"));
        music.setLooping(true);
        starList = new Star[STAR_COUNT];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new Star(atlas);
        }
        buttonPlay = new ButtonPlay(buttons, game);
        buttonExit = new ButtonExit(buttons);
        buttonOptions = new ButtonOptions(buttons, game);
        logo = new Logo(buttons);
        flyingShip = new LogoShip(flyLogo);
        music.play();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starList) {
            star.resize(worldBounds);
        }
        buttonPlay.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonOptions.resize(worldBounds);
        flyingShip.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta) {
        for (Star star : starList) {
            star.update(delta);
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0.51f, 0.34f, 0.64f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : starList) {
            star.draw(batch);
        }
        buttonPlay.draw(batch);
        buttonExit.draw(batch);
        buttonOptions.draw(batch);
        logo.draw(batch);
        flyingShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        music.stop();
        backgroundTexture.dispose();
        music.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonPlay.touchDown(touch, pointer);
        buttonExit.touchDown(touch, pointer);
        buttonOptions.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonPlay.touchUp(touch, pointer);
        buttonExit.touchUp(touch, pointer);
        buttonOptions.touchUp(touch, pointer);
        return false;
    }
}