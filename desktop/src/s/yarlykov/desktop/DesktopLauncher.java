package s.yarlykov.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import s.yarlykov.Star2DGame;
import s.yarlykov.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        /**
         * Задать размеры и пропорции экрана.
         * Отключить resize
         */
        float aspect = 3f/4f;
        config.width = 400;
        config.height = (int) (config.width / aspect);
        config.resizable = false;

		new LwjglApplication(new Star2DGame(Application.ApplicationType.Desktop), config);
	}
}
