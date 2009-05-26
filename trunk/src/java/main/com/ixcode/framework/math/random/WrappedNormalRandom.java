/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.math.random;

import com.ixcode.framework.math.DiscreetValueMap;
import com.ixcode.framework.math.DoubleMath;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class WrappedNormalRandom {

    private WrappedNormalRandom() {

    }

    /**
     * From CircStats R Package
     *
     * @param N
     * @param sd in RADIANS!!!
     * @param mu
     * @param tolerance default in CircStats is 1e-05
     * @return
     */
    public static DiscreetValueMap createProbabilityDensity(double N, double sd, double mu, double tolerance) {
        double arcLength = (2 * Math.PI) / N;
        DiscreetValueMap valueMap = new DiscreetValueMap(0, 2 * Math.PI, arcLength, DoubleMath.DOUBLE_PRECISION_DELTA);
        return WrappedNormalRandom.createNormalisedDistribution(valueMap, sd, mu, tolerance);
    }

    private static DiscreetValueMap createNormalisedDistribution(DiscreetValueMap valueMap, double sd, double mu, double tolerance) {
        WrappedNormalRandom.calculateDensityForXRadians(valueMap, sd, mu, tolerance);
        return DiscreetValueMap.createNormalised(valueMap);
    }


    /**
     * From CircStat R package
     * if (missing(rho)) {
     * rho <- exp(-sd^2/2)
     * }
     * if (rho < 0 | rho > 1)
     * stop("rho must be between 0 and 1")
     * var <- -2 * log(rho)
     * term <- function(theta, mu, var, k) {
     * 1/sqrt(var * 2 * pi) * exp(-((theta - mu + 2 * pi * k)^2)/(2 *
     * var))
     * }
     * <p/>
     * mu)))
     *
     * @param x
     * @param mu
     */
    private static void calculateDensityForXRadians(DiscreetValueMap x, double sd, double mu, double tolerance) {
        double rho = Math.exp(-(Math.pow(sd, 2) / 2));

        if (rho < 0 || rho > 1) {
            throw new IllegalArgumentException("Rho must be between 0 and 1 - you passed sd=" + sd + " whic makes rho: " + rho);
        }

        double var = -2 * Math.log(rho);


        for (int i = 0; i < x.getSize(); ++i) {
            double yi = calculateDensityAt(x.getKey(i), var, mu, tolerance);
            x.setValue(i, yi);
        }


    }

    /**
     * k <- 0
     * Next <- term(theta, mu, var, k)
     * delta <- 1
     * while (delta > tol) {
     * k <- k + 1
     * Last <- Next
     * Next <- Last + term(theta, mu, var, k) + term(theta,
     * mu, var, -k)
     * delta <- abs(Next - Last)
     * }
     * Next
     *
     * @param theta
     * @param var
     * @param mu
     * @param tolerance
     * @return
     */
    private static double calculateDensityAt(double theta, double var, double mu, double tolerance) {
        double k = 0;
        double next = wrappedNormalTerm(theta, mu, var, k);
        double last =0;
        double delta = 1;
        while (delta > tolerance) {
            k=k+1;
            last=next;
            next=last+wrappedNormalTerm(theta, mu, var, k) + wrappedNormalTerm(theta, mu, var, -k);
            delta=Math.abs(next-last);
        }
        return next;
    }

    private static double wrappedNormalTerm(double theta, double mu, double var, double k) {
        return 1 / Math.sqrt(var * TWO_PI) * Math.exp(-(Math.pow((theta - mu + TWO_PI* k), 2)) / (2 * var));
    }

    private static final double TWO_PI = 2 * Math.PI;
}
