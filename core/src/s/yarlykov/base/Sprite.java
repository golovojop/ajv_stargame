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

    private TextureRegion[] regions;
    private Matrix3 toWorld;
    private float angle = 0f;
    private float scale = 1f;
    private int frame = 0;

    public Sprite(TextureRegion region, Matrix3 toWorld) {
        this.regions = new TextureRegion[1];
        this.regions[0] = region;
        this.toWorld = toWorld;

        /**
         * Настройка прямоугольника спрайта
         */
        Vector2 pos = (new Vector2()).set(0, 0).mul(toWorld);
        Vector2 dims = (new Vector2(region.getRegionWidth()/2, region.getRegionHeight()/2)).mul(toWorld);
        super.set(new Rect(pos.x, pos.y, dims.x, dims.y));
    }

    /**
     * Отрисовка спрайта
     * @param batch
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
}

