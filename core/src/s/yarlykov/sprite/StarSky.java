package s.yarlykov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import s.yarlykov.base.Base2DScreen;
import s.yarlykov.base.Sprite;

public class StarSky extends Sprite {

    public StarSky(TextureRegion region, float aspect) {

        super(region);
        System.out.print("aspect " + aspect);
        setSize(Base2DScreen.worldScale * aspect, Base2DScreen.worldScale);
    }
}
