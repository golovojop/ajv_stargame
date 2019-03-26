package s.yarlykov.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import s.yarlykov.base.SpritesPool;
import s.yarlykov.math.Rect;
import s.yarlykov.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private Rect worldBounds;
    private Sound shootSound;
    private Sound explosionSound;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, Sound shootSound, Sound explosionSound) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        this.explosionSound = explosionSound;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, shootSound, explosionSound, worldBounds);
    }
}
