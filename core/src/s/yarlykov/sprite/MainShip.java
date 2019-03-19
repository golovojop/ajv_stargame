package s.yarlykov.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Sprite;
import s.yarlykov.math.Rect;

import static s.yarlykov.base.Base2DScreen.WORLD_SCALE;

public class MainShip extends Sprite {

    private static float V_LEN = 0.004f;
    private TextureRegion[] regions;
    private Vector2 touchTarget;
    private Vector2 buf;
    private Vector2 v;

    public MainShip(TextureRegion[] region) {
        super(region[1]);

        this.touchTarget = new Vector2();
        this.buf = new Vector2();
        this.v = new Vector2();
        this.regions = regions;
    }

    public void update() {
        buf.set(touchTarget);
        if (buf.sub(pos).len() <= V_LEN) {
            pos.set(touchTarget);
        } else {
            pos.add(v);
        }
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
        update();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        // Отрисовать корабль в центре окна
        pos.set(worldBounds.pos);
        // Установить размер корабля 1/10 экрана world
        setSize(worldBounds.getWidth()/10, worldBounds.getHeight()/10);
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        this.touchTarget = touch;
        v.set(touchTarget.cpy().sub(pos)).setLength(V_LEN);
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
        touchTarget.set(WORLD_SCALE, WORLD_SCALE);
        v.set(direct).setLength(V_LEN);
        update();
        return true;
    }



}
