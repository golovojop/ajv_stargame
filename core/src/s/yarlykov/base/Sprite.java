package s.yarlykov.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import s.yarlykov.math.Rect;

public class Sprite extends Rect {

    private TextureRegion[] regions;
    private float angle = 0f;
    private float scale = 1f;
    private int frame = 0;

    public Sprite(TextureRegion region) {
        this.regions = new TextureRegion[1];
        this.regions[0] = region;
    }

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

