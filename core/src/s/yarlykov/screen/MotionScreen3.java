package s.yarlykov.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import s.yarlykov.base.Base2DScreen;
import s.yarlykov.sprite.StarShip;
import s.yarlykov.sprite.StarSky;

public class MotionScreen3 extends Base2DScreen {

    private Application.ApplicationType appType;
    private StarSky background = null;
    private StarShip ship = null;
    private Texture imgShip;
    private Texture imgBack;
    private String shipName;

    public MotionScreen3(Application.ApplicationType appType) {
        super();
        this.appType = appType;
    }

    @Override
    public void show() {
        super.show();
        shipName = (appType == Application.ApplicationType.Android) ? "aship.png" : "dship.png";

        imgShip = new Texture(shipName);
        imgBack = new Texture("space.jpg");

        background = new StarSky(new TextureRegion(imgBack), screenToWorld);
        ship = new StarShip(new TextureRegion(imgShip), screenToWorld);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        ship.draw(batch);
        batch.end();
        ship.update();
    }

    @Override
    public void dispose() {
        imgShip.dispose();
        imgBack.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("MotionScreen3: touchDown");
        System.out.println("Mouse.X = " + screenX + " Mouse.Y = " + screenY);

        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        return touchDown(touch, pointer);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        ship.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("MotionScreen3: keyDown " + keycode);
        return ship.keyDown(keycode);
    }
}
