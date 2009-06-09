/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.random;

/**
 *  Description : Provides an implementation of the bessel function (From Batschlett - Circular Distributions
 *                except for the rectangular integration part which is from wikkepedia - need a reference for it.
 */
public class BesselFunction {

    private BesselFunction() {

    }


    /**
     * @todo Need to write this up and explain it!!
     * @param k
     * @return
     */
    public static double calculateBesselI0ForK(double k) {
        double arcs = (1/(2*Math.PI)); // Represents the number of radians in a circle as a proportion of 1.
        double integral = rectangularBesselI0Integration(k);
        return arcs*integral;
    }

    /**
     * #From wikkepedia : http://en.wikipedia.org/wiki/Rectangle_method
     * @param k
     * @return
     */
    private static double rectangularBesselI0Integration(double k) {
        double areaSum = 0;
        double n = 100; // Sample size - increase to get more accurate - 100 seems to work well.
        double a = 0;
        double b = 2* Math.PI;
        double deltaX = (b-a)/n; // the width of the rectangle

        for (int i=1;i<=n;++i) {
            double thetaRadians = a+(i-.5)*deltaX; // Represents the midpoint of the rectangle
            double areaAtI = besselI0Integral(k, thetaRadians) * deltaX; // heigth of rectangle * width of rectangle
            areaSum += areaAtI;

        }
        return areaSum;
    }


    /**
     * Calculates the hieght of the bessel function at thetaRadians for a given k
     * @param k
     * @param thetaRadians
     * @return
     */
    private static double besselI0Integral(double k, double thetaRadians) {
        return Math.exp(k*Math.cos(thetaRadians));
    }


}
