package s.yarlykov.sprite;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Sprite;

public class StarShip extends Sprite {
    private static float V_LEN = 0.06f;

    private Vector2 pos;
    private Vector2 touch;
    private Vector2 v;
    private Vector2 buf;

    public StarShip(TextureRegion region) {
        super(region);

        setSize(0.2f, 0.2f);
        this.pos = new Vector2();
        this.touch = new Vector2();
        this.v = new Vector2();
        this.buf = new Vector2();
    }

    public void update() {
        buf.set(touch);
        if (buf.sub(pos).len() <= V_LEN) {
            pos.set(touch);
        } else {
            pos.add(v);
        }
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch = touch;
        v = touch.cpy().sub(pos).setLength(V_LEN);
        return false;
    }

}
