package s.yarlykov.sprite;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Sprite;

import static s.yarlykov.base.Base2DScreen.WORLD_SCALE;

public class StarShip extends Sprite {
    private static float V_LEN = 0.4f;

    private TextureRegion region;
    private Matrix3 matrix;
    private Vector2 touch;
    private Vector2 pos;
    private Vector2 buf;
    private Vector2 v;

    public StarShip(TextureRegion region, Matrix3 matrix) {
        super(region, matrix);

        setSize(WORLD_SCALE/7, WORLD_SCALE/7);
        this.matrix = matrix;
        this.touch = new Vector2();
        this.buf = new Vector2();
        this.v = new Vector2();
        this.region = region;
        this.pos = super.pos;
    }

    public void update() {
        buf.set(touch);
        if (buf.sub(pos).len() <= V_LEN) {
            pos.set(touch);
        } else {
            pos.add(v);
        }
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
        update();
    }

    public boolean touchDown(Vector2 _touch, int pointer) {
        this.touch = _touch;
        v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }

    /**
     * Управляем клавишами
     * @param keycode
     * @return
     */
    public boolean keyDown(int keycode) {

        Vector2 direct = new Vector2();

        switch (keycode){
            case ARROW_UP:
                direct.set(0, 1);
                break;
            case ARROW_DOWN:
                direct.set(0, -1);
                break;
            case ARROW_LEFT:
                direct.set(-1, 0);
                break;
            case ARROW_RIGHT:
                direct.set(1, 0);
                break;
            default:
                break;
        }

        // Фиктивная целевая точка, чтобы сдвинуть фигурку, если она неподвижна
        touch.set(WORLD_SCALE, WORLD_SCALE);
        v.set(direct).setLength(V_LEN);
        update();
        return true;
    }

}
