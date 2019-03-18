package s.yarlykov.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.ScaledButton;
import s.yarlykov.math.Rect;
import s.yarlykov.screen.GameScreen;

public class ButtonPlay extends ScaledButton {

    private Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("play_button"));
        this.game = game;
        setHeightProportion(0.2f);
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(new Vector2(posX, posY));
    }

    @Override
    protected void action() {
        game.setScreen(new GameScreen(game));
    }
}
