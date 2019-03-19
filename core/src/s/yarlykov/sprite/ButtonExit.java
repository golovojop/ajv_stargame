package s.yarlykov.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.ScaledButton;
import s.yarlykov.math.Rect;

public class ButtonExit extends ScaledButton {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("exit_button"));
        setHeightProportion(0.2f);
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(new Vector2(posX + getWidth(), posY));
    }

    @Override
    protected void action() {
        Gdx.app.exit();
    }
}
