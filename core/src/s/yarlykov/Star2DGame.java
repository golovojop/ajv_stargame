package s.yarlykov;

import com.badlogic.gdx.Game;

import s.yarlykov.screen.MenuScreen;

public class Star2DGame extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen());

    }
}
