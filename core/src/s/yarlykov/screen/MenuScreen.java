package s.yarlykov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Base2DScreen;
import s.yarlykov.base.Base2DScreenL2;
import s.yarlykov.sprite.BadLogic;

public class MenuScreen extends Base2DScreen {

    private BadLogic badLogic;
    private Texture img;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        badLogic = new BadLogic(new TextureRegion(img));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.51f, 0.34f, 0.64f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        badLogic.draw(batch);
        batch.end();
        badLogic.update();
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        badLogic.touchDown(touch, pointer);
        return false;
    }
}
