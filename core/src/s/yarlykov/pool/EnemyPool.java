package s.yarlykov.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import s.yarlykov.base.Font;
import s.yarlykov.base.SpritesPool;
import s.yarlykov.math.Rect;
import s.yarlykov.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private Rect worldBounds;
    private Sound shootSound;
    private ExplosionPool explosionPool;
    private GlyphLayout layout;
    private Font font;

    private static final float FONT_SIZE = 0.015f;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, Sound shootSound, ExplosionPool explosionPool) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        this.explosionPool = explosionPool;
        this.layout = new GlyphLayout();

        font = new Font("font/gb.fnt", "font/gb.png");
        this.font.setSize(FONT_SIZE);
        this.font.setColor(255 / 255f, 201 / 255f, 14 / 255f, 1f);
    }

    @Override
    public void drawAllActive(SpriteBatch batch) {
        super.drawAllActive(batch);

        // Управление положением индикатора health
        // В процессе движения корабля вниз индикатор должен
        // подниматься от носа корабля к хвосту
        for(EnemyShip s : activeObjects) {
            String health = String.valueOf(s.getHealth());
            layout.setText(font, health);

            float remain = Math.abs(s.getBottom() - worldBounds.getBottom());
            float posX = (s.pos.x < 0) ? s.pos.x + s.getHalfWidth() : s.pos.x - s.getHalfWidth() - layout.width;
            float posY = s.getBottom() + layout.height + s.getHeight()*(1 - remain);
            font.draw(batch, health, posX, posY);
        }
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, shootSound, explosionPool, worldBounds);
    }

    @Override
    public void dispose() {
        font.dispose();
        super.dispose();
    }
}
