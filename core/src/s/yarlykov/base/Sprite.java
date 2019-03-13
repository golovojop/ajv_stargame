package s.yarlykov.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.math.Rect;

public class Sprite extends Rect {

    private TextureRegion[] regions;
    private Matrix3 toWorld;
    private float angle = 0f;
    private float scale = 1f;
    private int frame = 0;

    public Sprite(TextureRegion region, Matrix3 toWorld) {
        super(0, 0, region.getRegionWidth()/2, region.getRegionHeight()/2);
        this.regions = new TextureRegion[1];
        this.regions[0] = region;
        this.toWorld = toWorld;
    }

    public void draw(SpriteBatch batch) {
        Rect rect = new Rect(super.pos.x, super.pos.y, super.halfWidth, super.halfHeight);
        rect.pos.mul(toWorld);
        Vector2 dims = new Vector2(rect.getHalfWidth(), rect.getHalfHeight());
        rect.setHalfWidth(dims.x);
        rect.setHalfHeight(dims.y);

//        batch.draw(
//                regions[frame],
//                getLeft(), getBottom(),
//                halfWidth, halfHeight,
//                getWidth(), getHeight(),
//                scale, scale,
//                angle
//        );

        batch.draw(
                regions[frame],
                rect.getLeft(), rect.getBottom(),
                rect.getHalfWidth(), rect.getHalfHeight(),
                rect.getWidth(), rect.getHeight(),
                scale, scale,
                angle
        );

    }
}

