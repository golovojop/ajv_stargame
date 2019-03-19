package s.yarlykov.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Base2DScreen;
import s.yarlykov.base.Sprite;
import s.yarlykov.math.Rect;

import static s.yarlykov.base.Base2DScreen.WORLD_SCALE;

public class StarSky extends Sprite {

    public StarSky(TextureRegion region) {
        super(region);
        setSize(WORLD_SCALE * Gdx.graphics.getWidth() / Gdx.graphics.getHeight(), WORLD_SCALE);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}
