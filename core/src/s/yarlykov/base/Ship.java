package s.yarlykov.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Ship extends Sprite {

    public Ship() {
    }

    public Ship(TextureRegion region) {
        super(region);
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    abstract protected void shoot();
    abstract protected void moveRight();
    abstract protected void moveLeft();
    abstract protected void stop();

}
