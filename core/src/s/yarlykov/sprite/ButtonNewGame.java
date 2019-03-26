package s.yarlykov.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.ScaledButton;
import s.yarlykov.math.Rect;
import s.yarlykov.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

    private Game game;

    public ButtonNewGame(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
        setHeightProportion(0.06f);
    }

    @Override
    public void resize(Rect worldBounds) {

        pos.set(new Vector2(0, -0.1f));
    }

    @Override
    protected void action() {
        game.setScreen(new GameScreen(game));
    }
}
