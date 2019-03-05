/**
 * Materials:
 * https://github.com/libgdx/libgdx/wiki/Spritebatch%2C-textureregions%2C-and-sprite
 * https://habr.com/ru/post/143405/
 *
 * Написание игры:
 * https://habr.com/ru/post/224223/
 *
 */

package s.yarlykov;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StarGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture backgrround;

	@Override
	public void create () {
		batch = new SpriteBatch();
		backgrround = new Texture("space.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		// Set background
		batch.draw(backgrround, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		backgrround.dispose();
	}
}
