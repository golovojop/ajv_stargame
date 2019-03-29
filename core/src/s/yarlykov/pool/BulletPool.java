package s.yarlykov.pool;

import s.yarlykov.base.SpritesPool;
import s.yarlykov.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}