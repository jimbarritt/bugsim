/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.random;

import java.util.Random;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class PoissonRandom {
    /**
     * Taken from jims thesis to test that it works, this should generate random integers in a poisson distribution
     * with a mean of mu
     *
     * @param random
     * @param mu
     * @return
     */

    public static int generatePoissonRandom(Random random, double mu) {
        int poisson = 0;
        if (mu <= 0) {
            poisson = 0;
        } else {
            int i = 0;
            double tt = Math.exp(-mu);
            double s = random.nextDouble();
            while (s > tt) {
                s = s * random.nextDouble();
                i++;
            }
            poisson = i;
        }
        return poisson;
    }
}
