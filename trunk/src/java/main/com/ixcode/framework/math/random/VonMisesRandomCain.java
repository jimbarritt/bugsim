/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.random;

import com.ixcode.framework.math.DiscreetValueMap;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.Geometry;

import java.util.Random;

/**
 *  Description : Von Mises RNG taken from Cain (1985)
 */
public class VonMisesRandomCain implements IRandom  {

    /**
     *
     * @param mean
     * @param k
     * @param n - number of segments to divide the circle into....
     */
    public VonMisesRandomCain(Random runif, double mean, double k, double n) {
        _arcLength = (Geometry.TWO_PI / n);
        DiscreetValueMap pdist = VonMisesRandomCain.createProbabilityDensity(n, k, mean);
       _monteCarlo = new MonteCarloRandom(pdist, runif);
        _runif = runif;

    }


    /**
     * From Cain (1985) First select a segment - this will be j - the angle which will be between -180 and +180
     * then take another random number between 0 and 1 and add it to j-1 so you get a real angle of
     * between j-1 and j
     *
     * Now this SHOULD be _arcLength * (j-1 + U) but this doesnt seem to work!
     *
     * The version below is dependant on there being 360 segments! as we first convert it to degrees and then take
     * a random number.
     *
     * j really needs to be the INDEX of the segment, NOT the actual degrees, THEN it would work....
     *
     * The problem was that we wanted it to be between -PI and + PI - Cains equation only works if you do from 0 to 360....
     *
     *
     * @param
     * @return   Is going to return a Random number between 0 and TWO_PI - we will then need to convert this to a change in direction...
     */
    public double nextDouble() {
        double j= _monteCarlo.nextIndex();
        double U =_runif.nextDouble();
        return _arcLength * (j-1+U);
    }



    /**
     *
     * @param N - number of divisions of the circle
     * @param k - kurtosis value (angle of turn)
     * @param mu - mean
     * @return
     */
    public static DiscreetValueMap createProbabilityDensity(double N, double k, double mu) {
        double arcLength = (Geometry.TWO_PI)/N;
        DiscreetValueMap valueMap = new DiscreetValueMap(0, Geometry.TWO_PI, arcLength, DoubleMath.DOUBLE_PRECISION_DELTA);
        return createNormalisedDistribution(valueMap, k, mu);
    }

    private static DiscreetValueMap createNormalisedDistribution(DiscreetValueMap valueMap, double k, double mu) {
        calculateDensityForXRadians(valueMap, k, mu);
        return DiscreetValueMap.createNormalised(valueMap);
    }


    private static void calculateDensityForXRadians(DiscreetValueMap x, double k, double mu) {
        double besselK = BesselFunction.calculateBesselI0ForK(k);
        double exprA = 1/(2*Geometry.TWO_PI*besselK);

        for (int i=0;i<x.getSize();++i) {
            double yi = exprA * Math.exp(k*Math.cos(x.getKey(i)-mu));
            x.setValue(i, yi);
        }


    }

    private MonteCarloRandom _monteCarlo;
    private double _arcLength;
    private Random _runif;
}
