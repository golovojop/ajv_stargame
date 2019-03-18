package s.yarlykov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import s.yarlykov.base.Sprite;
import s.yarlykov.math.Rect;

public class FlyingShip extends Sprite {
    private int currentFrame = 1;
    private int minFrame = 1;
    private int maxFrame = 7;
    private String atlasKey = "anim";

    public FlyingShip(final TextureAtlas atlas) {
        super(atlas.findRegion("anim01"));

        Timer.schedule(new Timer.Task(){
                           @Override
                           public void run() {
                               currentFrame++;
                               if(currentFrame > maxFrame)
                                   currentFrame = minFrame;

                               atlasKey = String.format("anim%02d", currentFrame);
                               regions[0] = atlas.findRegion(atlasKey);
                           }
                       }
                ,0,1/30.0f);

        setHeightProportion(0.2f);
        setScale(1.2f);
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(new Vector2(0, 0f));
    }
}
