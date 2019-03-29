package s.yarlykov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Sprite;
import s.yarlykov.math.Rect;

public class LogoGameOver extends Sprite {

    public LogoGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        setHeightProportion(0.06f);
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(new Vector2(0, 0.1f));
    }
}
