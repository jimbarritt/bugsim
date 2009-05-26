/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.random;

import com.ixcode.framework.math.BigDecimalMath;

import java.util.Random;
import java.math.BigDecimal;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class UniformRandom {
    public static double generateUniformRandomDouble(double max, double min, Random random) {
        return (max - min) * (random.nextDouble()) + min;
    }

    public static BigDecimal generateUniformRandom(BigDecimal max, Random random) {
        return max.multiply(BigDecimalMath.accurateOut(random.nextDouble()));
    }

    public static int generateUniformRandomInteger(Random random, int max) {
        return random.nextInt(max);
    }
}
