package s.yarlykov.math;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

/**
 * Прямоугольник
 *
 */
public class Rect {

    public final Vector2 pos = new Vector2(); // позиция по центра прямоугольника
    protected float halfWidth; // половина ширины
    protected float halfHeight; // половина высоты

    public Rect() {}

    public Rect(Rect from) {
        this(from.pos.x, from.pos.y, from.getHalfWidth(), from.getHalfHeight());
    }

    public Rect(float x, float y, float halfWidth, float halfHeight) {
        pos.set(x, y);
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    public float getLeft() {
        return pos.x - halfWidth;
    }

    public void setHalfWidth(float halfWidth) {
        this.halfWidth = halfWidth;
    }

    public void setHalfHeight(float halfHeight) {
        this.halfHeight = halfHeight;
    }

    public float getTop() {
        return pos.y + halfHeight;
    }

    public float getRight() {
        return pos.x + halfWidth;
    }

    public float getBottom() {
        return pos.y - halfHeight;
    }


    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getWidth() {
        return halfWidth * 2f;
    }

    public float getHeight() {
        return halfHeight * 2f;
    }


    public void set(Rect from) {
        pos.set(from.pos);
        halfWidth = from.halfWidth;
        halfHeight = from.halfHeight;
    }

    // Установить позицию левого нижнего угла.
    // Одновременно корректируем положение центра
    public void setLeft(float left) {
        pos.x = left + halfWidth;
    }

    public void setTop(float top) {
        pos.y = top - halfHeight;
    }

    public void setRight(float right) {
        pos.x = right - halfWidth;
    }

    public void setBottom(float bottom) {
        pos.y = bottom + halfHeight;
    }


    public void setWidth(float width) {
        this.halfWidth = width / 2f;
    }

    public void setHeight(float height) {
        this.halfHeight = height / 2f;
    }

    // Установить размер. Координаты центра не меняются
    public void setSize(float width, float height) {
        this.halfWidth = width / 2f;
        this.halfHeight = height / 2f;
    }

    public Rect getScaled(float scale) {
        return new Rect(pos.x * scale, pos.y * scale, getHalfWidth() * scale, getHalfHeight() * scale);
    }

    // Проверка: Вектор touch попадает в мой прямоугольник
    public boolean isMe(Vector2 touch) {
        return touch.x >= getLeft() && touch.x <= getRight() && touch.y >= getBottom() && touch.y <= getTop();
    }

    // Проверка: Rect other не пересекается со мной
    public boolean isOutside(Rect other) {
        return getLeft() > other.getRight() || getRight() < other.getLeft() || getBottom() > other.getTop() || getTop() < other.getBottom();
    }

    @Override
    public String toString() {
        return "Rectangle: pos" + pos + " size(" + getWidth() + ", " + getHeight() + ")";
    }
}