package s.yarlykov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;

import s.yarlykov.base.Base2DScreen;
import s.yarlykov.base.Sprite;

public class StarSky extends Sprite {

    public StarSky(TextureRegion region, Matrix3 m) {
        super(region, m);
//        setSize(Base2DScreen.worldScale * aspect, Base2DScreen.worldScale);
    }
}
