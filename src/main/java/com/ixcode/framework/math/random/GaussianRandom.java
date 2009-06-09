/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.random;

import java.util.Random;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class GaussianRandom {

    /**
     * Encapsulates the Gausaian normal distribution density function for a given value of X:
     * <latex>P(x)=\frac{1}{\sigma \sqrt{2\pi}}e^{-\frac{(x-\mu)^2}{2\sigma}}</latex>
     * double exponent = Math.pow(x - mean, 2) / (2 * sd);
        return (Math.exp(-exponent) / sd * Math.sqrt(TWO_PI));
     * @param x
     * @return the P value for this X
     */
    public static double calculateGaussianP(double x, double mean, double sd) {
        double firstPart = 1/(sd*Math.sqrt(2*Math.PI));
        double exponent = Math.pow((x-mean),2)/(2*(Math.pow(sd, 2)));
        double secondPart = Math.exp(-exponent);
        return (firstPart*secondPart);

//        double exponent = Math.pow(x-mean, 2) / (2 * sd);
//        double denominator = sd * Math.sqrt(TWO_PI);
//        return Math.exp(-exponent) / denominator;

    }

    public static double generateGaussian(Random random, double standardDeviation, double mean) {
        return ((random.nextGaussian() * standardDeviation) + mean);

    }


    protected static final double TWO_PI = 2 * Math.PI;
}
