package s.yarlykov.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import s.yarlykov.math.Rect;
import s.yarlykov.sprite.Bullet;

/**
 * Пул управляет жизненным циклом объектов:
 * - создание
 * - очистка
 * - update
 * - resize
 */

public abstract class SpritesPool<T extends Sprite> {
    protected final List<T> activeObjects = new ArrayList<T>();
    protected final List<T> freeObjects = new ArrayList<T>();

    protected abstract T newObject();

    /**
     * Получить свободный объект из пула
     */
    public T obtain() {
        T object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            /**
             * Удалить последний в списке (по индексу) и получить удаленный объект
             */
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        System.out.println(this.getClass().getName() + " active/free: " + activeObjects.size() + "/" + freeObjects.size());
        return object;
    }

    /**
     * Переместить объект в пул свободных объектов
     */
    public void free(T object) {
        Iterator it = activeObjects.iterator();

        while (it.hasNext()) {
            T next = (T) it.next();
            if (next.equals(object)) {
                it.remove();
                freeObjects.add(object);
                break;
            }
        }
        System.out.println(this.getClass().getName() + " active/free: " + activeObjects.size() + "/" + freeObjects.size());
    }

    public void updateAllActive(float delta) {
        for (T sprite : activeObjects) {
            if (!sprite.isDestroyed()) sprite.update(delta);
        }
    }

    public void drawAllActive(SpriteBatch batch) {
        for (T sprite : activeObjects) {
            if (!sprite.isDestroyed()) sprite.draw(batch);
        }
    }

    public void freeAllDestroyedActiveSprites() {
        Iterator it = activeObjects.iterator();

        while (it.hasNext()) {
            T next = (T) it.next();
            if (next.isDestroyed()) {
                it.remove();
                next.flushDestroy();
                freeObjects.add(next);
                break;
            }
        }
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }
}
