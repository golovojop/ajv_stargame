package s.yarlykov.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.math.*;

public abstract class Base2DScreen implements Screen, InputProcessor {


    protected SpriteBatch batch;
    protected Vector2 touch;

    /**
     * Батчер работает с пространством OpenGL и конвертирует все входные переаметры
     * через матрицу Matrix4 (OpenGL все интерпретирует в 3D)
     */
    protected Matrix4 worldToGl;
    protected Matrix3 screenToWorld;
    private Rect screenBounds; 	// границы области рисования в пикселях
    private Rect worldBounds; 	// границы проекции мировых координат
    private Rect glBounds; 		// квадрат OpenGL

    public static final float WORLD_SCALE = 100f;

    @Override
    public void show() {
        System.out.println("sBase2DScreen: how");

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

    // Вызывается при старте программы и при каждом изменении размера экрана.
    // При этом размер поверхности OpenGL не изменяется, он всегда равер 2f x 2f.
    // А вот матрицу, которая преобразует координаты world в координаты OpenGL
    // нужно скорретировать.
    // Также нужно скорректировать матрицу преобразования screen -> world.
    @Override
    public void resize(int width, int height) {
        System.out.println("resize width = " + width + " height = " + height);

        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);		// Координаты центра автоматически
        screenBounds.setBottom(0);	    // пересчитаются

        System.out.println("screenBounds.x " + screenBounds.pos.x + " screenBounds.y " + screenBounds.pos.y);

        float aspect = width / (float) height;

        worldBounds.setHeight(WORLD_SCALE);
        worldBounds.setWidth(WORLD_SCALE * aspect);
        System.out.println("worldBounds.x " + worldBounds.pos.x + " worldBounds.y " + worldBounds.pos.y);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);

        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
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
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
//        System.out.println("touchUp touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
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
