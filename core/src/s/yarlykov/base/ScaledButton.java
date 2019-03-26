package s.yarlykov.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.math.Rect;

public abstract class ScaledButton extends Sprite {

    protected static final float PRESS_SCALE = 0.6f;
    protected static final float NORMAL_SCALE = 0.8f;
    protected static final float posX = -0.20f;
    protected static final float posY = -0.36f;

    private int pointer;
    private boolean isPressed;

    public ScaledButton(TextureRegion region) {
        super(region);
        setScale(NORMAL_SCALE);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isPressed || !isMe(touch)) {
            return false;
        }
        this.pointer = pointer;
        setScale(PRESS_SCALE);
        isPressed = true;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (this.pointer != pointer || !isPressed) {
            return false;
        }
        if (isMe(touch)) {
            action();
        }
        isPressed = false;
        setScale(NORMAL_SCALE);
        return false;
    }

    // Проверка: Вектор touch попадает в мой прямоугольник
    @Override
    public boolean isMe(Vector2 touch) {
        // Кнопка отрисована с масштабированием поэтому нужно проверять попадание в масштабированный
        // прямоугольник. Координаты центра тоже меняются.
        Rect scaled = getScaled(NORMAL_SCALE);

        float spot = 0.09f;

//        System.out.println(String.format("%f, %f, %f, %f",
//                scaled.pos.x - spot,
//                scaled.pos.x + spot,
//                scaled.pos.y + spot,
//                scaled.pos.y - spot));
//
//        System.out.println(touch.x > scaled.pos.x - spot);
//        System.out.println(touch.x < scaled.pos.x + spot);
//        System.out.println(touch.y < scaled.pos.y + spot);
//        System.out.println(touch.y > scaled.pos.y - spot);

        return touch.x > scaled.pos.x - spot
                && touch.x < scaled.pos.x + spot
                && touch.y < scaled.pos.y + spot
                && touch.y > scaled.pos.y - spot;

    }

    protected abstract void action();
}
