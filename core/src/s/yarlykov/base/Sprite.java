package s.yarlykov.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.math.Rect;

public class Sprite extends Rect {

    public final static int ARROW_UP = 19;
    public final static int ARROW_DOWN = 20;
    public final static int ARROW_LEFT = 21;
    public final static int ARROW_RIGHT = 22;

    protected TextureRegion[] regions;
    private float angle = 0f;
    private float scale = 1f;
    private int frame = 0;

    public Sprite(TextureRegion region) {
        this.regions = new TextureRegion[1];
        this.regions[0] = region;
    }

    /**
     * TODO: Отрисовка спрайта
     */
    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    /**
     * TODO: Установка пропорций сторон
     */
    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void update(float delta) {
    }

    public void resize(Rect worldBounds) {
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

}

