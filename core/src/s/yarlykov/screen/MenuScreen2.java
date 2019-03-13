package s.yarlykov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Base2DScreen;

public class MenuScreen2 extends Base2DScreen {
    private static float V_LEN = 0.06f;

    private Texture img;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 buf;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        touch = new Vector2();
        pos = new Vector2(0, 0);
        v = new Vector2();
        buf = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.51f, 0.34f, 0.64f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, pos.x, pos.y, 1f, 1f);
        batch.end();
        buf.set(touch);
        if (buf.sub(pos).len() <= V_LEN) {
            pos.set(touch);
        } else {
            pos.add(v);
        }
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("MenuScreen2: touchDown");
        System.out.println("Mouse.X = " + screenX + " Mouse.Y = " + screenY);

        touch.set(screenX, Gdx.graphics.getHeight() - screenY);

        /**
         * Вектор скорости нужно задавать в координатах world
         */
        v.set(touch.cpy().mul(screenToWorld).sub(pos)).setLength(V_LEN);
        return super.touchDown(screenX, screenY, pointer, button);    }
}