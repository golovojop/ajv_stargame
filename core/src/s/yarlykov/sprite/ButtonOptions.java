package s.yarlykov.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Base2DScreen;
import s.yarlykov.base.OnOptionsHandler;
import s.yarlykov.base.ScaledButton;
import s.yarlykov.math.Rect;
import s.yarlykov.screen.MenuScreen;

public class ButtonOptions extends ScaledButton {

    private Game game;
    private OnOptionsHandler optionsHandler;

    public ButtonOptions(TextureAtlas atlas, Game game, OnOptionsHandler optionsHandler) {
        super(atlas.findRegion("optionst_button"));
        this.game = game;
        this.optionsHandler= optionsHandler;
        setHeightProportion(0.2f);
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(new Vector2(posX + 2 * getWidth(), posY));
    }

    @Override
    protected void action() {
        optionsHandler.apply();
    }
}
