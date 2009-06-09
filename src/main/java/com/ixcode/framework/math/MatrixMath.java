/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class MatrixMath {
    /**
     * Returns the determinant of the matrix:
     * <p/>
     * |a b|
     * |c d|
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @return
     */
    public static double determinant(double a, double b, double c, double d) {
        return (a * d) - (b * c);
    }
}
