package s.yarlykov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Sprite;
import s.yarlykov.math.Rect;
import s.yarlykov.math.Rnd;

public class StarBlink extends Sprite {

    private float starHeight;
    private Rect worldBounds;
    private float blinkStep;

    public StarBlink(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        starHeight = 0f;
        blinkStep = Rnd.nextFloat(0.00001f, 0.0001f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (starHeight >= 0.01f) {
            starHeight = 0.0f;
            pos.set(randomPos());
        } else {
            starHeight += blinkStep;
        }
        setHeightProportion(starHeight);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        pos.set(randomPos());
    }

    private Vector2 randomPos() {
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        return new Vector2(posX, posY);
    }
}
