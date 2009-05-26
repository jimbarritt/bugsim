/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.math.random;

import com.ixcode.framework.math.DiscreetValueMap;
import com.ixcode.framework.math.DoubleMath;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class WrappedCauchyRandom {

    private WrappedCauchyRandom() {

    }

    /**
     *
     * @param N - number of divisions of the circle
     * @param rho - kurtosis value (angle of turn)
     * @param mu - mean
     * @return
     */
    public static DiscreetValueMap createProbabilityDensity(double N, double rho, double mu) {
        if (rho<0 || rho > 1) {
            throw new IllegalArgumentException("Must pass rho between 0 and 1 - you passed : " + rho);
        }
        double arcLength = (2*Math.PI)/N;
        DiscreetValueMap valueMap = new DiscreetValueMap(0, 2*Math.PI, arcLength, DoubleMath.DOUBLE_PRECISION_DELTA);
        return WrappedCauchyRandom.createNormalisedDistribution(valueMap, rho, mu);
    }

    private static DiscreetValueMap createNormalisedDistribution(DiscreetValueMap valueMap, double rho, double mu) {
        WrappedCauchyRandom.calculateDensityForXRadians(valueMap, rho, mu);
        return DiscreetValueMap.createNormalised(valueMap);
    }


    /**
     * From CircStat R package
     *    (1 - rho^2)/((2 * pi) * (1 + rho^2 - 2 * rho * cos(theta -
        mu)))
     * @param x
     * @param k
     * @param mu
     */
    private static void calculateDensityForXRadians(DiscreetValueMap x, double rho, double mu) {

        double rhoSq = Math.pow(rho, 2);
        double oneMinusrhoSq = 1-rhoSq;
        double onePlusRhoSqMinusTwoRho = 1+rhoSq-2*rho;

        for (int i=0;i<x.getSize();++i) {
            double yi = oneMinusrhoSq /(TWO_PI*(onePlusRhoSqMinusTwoRho*Math.cos(x.getKey(i)-mu)));
            x.setValue(i, yi);
        }


    }
    private static final double TWO_PI = 2*Math.PI;
}
