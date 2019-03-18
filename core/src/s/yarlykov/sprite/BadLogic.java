package s.yarlykov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Sprite;


public class BadLogic extends Sprite {

    private static float V_LEN = 0.006f;

    private Vector2 touch;
    private Vector2 pos;
    private Vector2 buf;
    private Vector2 v;

    public BadLogic(TextureRegion region) {
        super(region);
        setSize(0.5f, 0.5f);
        this.pos = new Vector2();
        touch = new Vector2();
        v = new Vector2();
        buf = new Vector2();
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
