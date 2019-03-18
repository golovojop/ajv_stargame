package s.yarlykov;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;

import s.yarlykov.screen.MenuScreen;
import s.yarlykov.screen.MenuScreen2;
import s.yarlykov.screen.MotionScreen3;

public class Star2DGame extends Game {
    Application.ApplicationType appType;

    public Star2DGame(Application.ApplicationType appType) {
        this.appType = appType;
    }

    @Override
//    public void create() {
//        setScreen(new MotionScreen3(appType));
//    }
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
