/**
 * Операции с матрицами
 * https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/math/Matrix4.html#scale-float-float-float-
 */

package s.yarlykov.math;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

/**
 * Утилита для работы с матрицами
 */
public class MatrixUtils {

    private MatrixUtils() {
    }

    /**
     * Расчёт матрицы перехода 4x4
     * @param mat итоговая матрица преобразований
     * @param src исходный квадрат
     * @param dst итоговый квадрат
     */
    public static void calcTransitionMatrix(Matrix4 mat, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();

        mat.idt().translate(dst.pos.x, dst.pos.y, 0f).scale(scaleX, scaleY, 1f).translate(-src.pos.x, -src.pos.y, 0f);

        // Это для дебага
//        System.out.println("M4");
//        System.out.println(mat.idt().translate(dst.pos.x, dst.pos.y, 0f) + "\n");
//        System.out.println(mat.scale(scaleX, scaleY, 1f) + "\n");
//        System.out.println(mat.translate(-src.pos.x, -src.pos.y, 0f) + "\n");

    }

    /**
     * Расчёт матрицы перехода 3x3
     * @param mat итоговая матрица преобразований
     * @param src исходный квадрат
     * @param dst итоговый квадрат
     */
    public static void calcTransitionMatrix(Matrix3 mat, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();
        mat.idt().translate(dst.pos.x, dst.pos.y).scale(scaleX, scaleY).translate(-src.pos.x, -src.pos.y);

        // Это для дебага
//        System.out.println("M3");
//        System.out.println(mat.idt().translate(dst.pos.x, dst.pos.y) + "\n");
//        System.out.println(mat.scale(scaleX, scaleY) + "\n");
//        System.out.println(mat.translate(-src.pos.x, -src.pos.y) + "\n");



    }
}
