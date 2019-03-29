package s.yarlykov.base;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.math.MatrixUtils;
import s.yarlykov.math.Rect;

public abstract class Base2DScreen implements Screen, InputProcessor {

    public static final float WORLD_SCALE = 1f;

    protected SpriteBatch batch;

    protected Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    private Rect screenBounds; // границы области рисования в пикселях
    protected Rect worldBounds; // границы проекции мировых координат
    private Rect glBounds; // квадрат OpenGL

    protected Vector2 touch;

    /**
     * Методы жизненного цикла экрана (Activity)
     * -----------------------------------------
     *     public void show();
     *     public void resize(int width, int height);
     *     public void render(float delta);
     *     public void pause();
     *     public void resume();
     *     public void hide();
     *     public void dispose();
     */

    /**
     * События, которые принимает экран
     *     public boolean keyDown(int keycode)
     *     public boolean keyUp(int keycode)
     *     public boolean keyTyped(char character)
     *     public boolean touchDown(int screenX, int screenY, int pointer, int button)
     *     public boolean touchUp(int screenX, int screenY, int pointer, int button)
     *     public boolean touchDragged(int screenX, int screenY, int pointer)
     *     public boolean touchDragged(Vector2 touch, int pointer)
     *     public boolean mouseMoved(int screenX, int screenY)
     *     public boolean scrolled(int amount)
     */

    @Override
    public void show() {
        System.out.println("show");
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        this.worldToGl = new Matrix4();
        this.screenToWorld = new Matrix3();
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0, 0, 1f, 1f);
        this.touch = new Vector2();
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize width = " + width + " height = " + height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(WORLD_SCALE);
        worldBounds.setWidth(WORLD_SCALE * aspect);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);

        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);

        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {
        System.out.println("resize worldBounds.width = " + worldBounds.getWidth() + " worldBounds.height = " + worldBounds.getHeight());
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY = " + screenY + " pointer = " + pointer + " button = " + button);
        /**
         * Координаты тача нужно сконвертировать в world
         */
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        System.out.println("touchDown touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp screenX = " + screenX + " screenY = " + screenY + " pointer = " + pointer + " button = " + button);
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        /**
         * Координаты тача нужно сконвертировать в world
         */
        touchUp(touch, pointer);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        System.out.println("touchUp touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
        /**
         * Координаты тача нужно сконвертировать в world
         */
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
