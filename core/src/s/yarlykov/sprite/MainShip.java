package s.yarlykov.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Sprite;
import s.yarlykov.math.Rect;

import static s.yarlykov.base.Base2DScreen.WORLD_SCALE;

public class MainShip extends Sprite {
    private static float V_LEN = 0.6f;
    private static final int NOT_TOUCHED = -1;
    private boolean isPressedRight;
    private boolean isPressedLeft;

    private Vector2 v = new Vector2();
    private Vector2 v0 = new Vector2(V_LEN, 0);
    private Rect worldBounds;

    private int leftPointer = NOT_TOUCHED;
    private int rightPointer = NOT_TOUCHED;

    private TextureRegion[] regions;
    private Vector2 touchTarget;
    private Vector2 buf;

    public MainShip(TextureRegion[] region) {
        super(region[1]);
        // Установить размер корабля в пропорции 0.15 от экрана
        setHeightProportion(0.15f);

        this.regions = regions;
    }

    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        // Отрисовать корабль по центру внизу с отступом 0.02f
        pos.set(worldBounds.pos.x, worldBounds.getBottom() + halfHeight + 0.02f);
    }

    /**
     * Методы для работы с тачпадом. Тачпад визуально делится вертикально
     * пополам. Далее делается проверка в какой половине произошел тач.
     * На соновании этого выбирвается направление движения. Учитывается
     * возможность мультитача.
     *
     * public boolean touchDown(Vector2 touch, int pointer)
     * public boolean touchUp(Vector2 touch, int pointer)
     */

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != NOT_TOUCHED) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != NOT_TOUCHED) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = NOT_TOUCHED;
            if (rightPointer != NOT_TOUCHED) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = NOT_TOUCHED;
            if (leftPointer != NOT_TOUCHED) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }


    /**
     * Методы для управления клавиатурой
     * public void keyDown(int keycode)
     * public void keyUp(int keycode)
     *
     *
     * Если при удержании клавиши нажимается противоположная,
     * то направление движения меняется
     */
    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
            case Input.Keys.A:
                isPressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                isPressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
    }

    /**
     * Если при отпускании клавиши противоположная удерживается,
     * нажатой, то направление меняется. Если противиположная
     * клавиша не удерживается, то останов.
     */

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
            case Input.Keys.A:
                isPressedLeft = false;
                if (isPressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                isPressedRight = false;
                if (isPressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
    }

    private void shoot(){}

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }
}
