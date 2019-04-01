package s.yarlykov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Sprite;
import s.yarlykov.math.Rect;

public class Logo extends Sprite {

    public Logo(TextureAtlas atlas) {
        super(atlas.findRegion("logo"));
        setHeightProportion(0.2f);
        setScale(0.6f);
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(new Vector2(0, 0.35f));
    }
}
