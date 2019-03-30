package s.yarlykov.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import s.yarlykov.base.Sprite;
import s.yarlykov.math.Rect;
import s.yarlykov.math.Rnd;

public class Ufo extends Sprite {
    private int currentFrame = 0;
    private int minFrame = 1;
    private int maxFrame = 15;
    private String atlasKey = "magic";
    private Rect worldBounds;
    private Vector2 vY;
    private MainShip ship;

    private float appearanceInterval = 20f;
    private float appearanceTimer = 0;

    public Ufo(final TextureAtlas atlas, MainShip ship) {
        super(atlas.findRegion("magic1"));
        this.vY = new Vector2(0, -0.18f);
        this.ship = ship;

        Timer.schedule(new Timer.Task() {
                           @Override
                           public void run() {
                               currentFrame++;
                               if (currentFrame > maxFrame) currentFrame = minFrame;
                               regions[0] = atlas.findRegion(atlasKey + currentFrame);
                           }
                       }
                , 0, 1 / 16.0f);

        setHeightProportion(0.16f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        isDestroyed = true;
    }

    @Override
    public void update(float delta) {
        if(ship.getHealth() < (float)(MainShip.MS_START_HP / 2)) {
            appearanceTimer += delta;
            if (appearanceTimer >= appearanceInterval) {
                isDestroyed = false;
                appearanceTimer = 0;
                resetPosition();
            } else {
                if (getBottom() <= worldBounds.getBottom()) {
                    this.destroy();
                } else {
                    pos.mulAdd(vY, delta);
                }
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!isDestroyed) super.draw(batch);
    }

    private void resetPosition() {
        pos.x = Rnd.nextFloat(worldBounds.getLeft() + getHalfWidth(), worldBounds.getRight() - getHalfWidth());
        setBottom(worldBounds.getTop());
    }
}
