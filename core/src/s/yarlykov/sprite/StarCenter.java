package s.yarlykov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Sprite;
import s.yarlykov.math.Rect;
import s.yarlykov.math.Rnd;

public class StarCenter extends Sprite {

    private float starHeight;
    private Rect worldBounds;
    private Vector2 v;

    public StarCenter(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        float vX = Rnd.nextFloat(-0.3f, 0.3f);
        float vY = Rnd.nextFloat(-0.3f, 0.3f);
        v = new Vector2(vX, vY);
        starHeight = 0.009f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
        setHeightProportion(starHeight);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        pos.set(0, 0);
    }

    private void checkAndHandleBounds() {
        if (!worldBounds.isMe(pos)) {
            pos.set(0, 0);
        }
    }
}
