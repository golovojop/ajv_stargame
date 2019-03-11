package s.yarlykov.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Base2DScreen;

public class MotionScreen extends Base2DScreen {

    private SpriteBatch batch;
    private Texture img;
    private Texture background;
    private Vector2 touch;
    private Application.ApplicationType appType;

    private Vector2 step;
    private Vector2 imgPos;

    private int SCAL = 2;

    public MotionScreen(Application.ApplicationType appType) {
        this.appType = appType;
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();

        switch (appType){
            case Android:
                img = new Texture("aship.png");
                SCAL = 6;
                break;
            case Desktop:
                img = new Texture("dship.png");
                break;
        }
        background = new Texture("space.jpg");
        touch = new Vector2();
        imgPos = new Vector2(0,0);
        step = new Vector2(0, 0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(img, imgPos.x, imgPos.y);
        batch.end();
        imgPos.add(step);

        // Остановка движения
        if(touch.cpy().sub(imgPos).len() <= SCAL) {
            step.setZero();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case ARROW_UP:
                step = (new Vector2(0, 1)).nor().scl(SCAL);
                break;
            case ARROW_DOWN:
                step = (new Vector2(0, -1)).nor().scl(SCAL);
                break;
            case ARROW_LEFT:
                step = (new Vector2(-1, 0)).nor().scl(SCAL);
                break;
            case ARROW_RIGHT:
                step = (new Vector2(1, 0)).nor().scl(SCAL);
                break;
                default:
                    break;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Устанавливаем цель движения таким образом, чтобы картинка остановилась
        // строго по центру над координатой тача.
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).sub(new Vector2(img.getWidth()/2, img.getHeight()/2));
        step = touch.cpy().sub(imgPos).nor().scl(SCAL);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
