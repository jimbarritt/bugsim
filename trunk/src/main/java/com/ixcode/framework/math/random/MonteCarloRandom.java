/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.random;

import com.ixcode.framework.math.DiscreetValueMap;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.stats.SummaryStatistics;

import java.util.Random;

/**
 * Description : This class contains several utilities for generating Monte Carlo
 * style random distributions. These are discreet probability densities. It also
 * has facilities for merging them.
 */
public class MonteCarloRandom implements IRandom{

    public MonteCarloRandom(DiscreetValueMap pdistribution, Random random) {
        _pdistribution = pdistribution;
        _cdistribution = DiscreetValueMap.createCumulative(pdistribution);
        _random = random;
        validateDistribution();
    }

    private void validateDistribution() {
        double sum = SummaryStatistics.calculateSum(_pdistribution.getValues());
        if (!DoubleMath.precisionEquals(1, sum, _pdistribution.getPrecision())) {
            throw new IllegalArgumentException("Tried to construct a MonteCarloRandom object with a pdistribution that does not sum to 1! - sum is : " + sum);
        }
        double lastValue = _cdistribution.getValues()[_cdistribution.getValues().length-1];
        if (!DoubleMath.precisionEquals(1, lastValue, _pdistribution.getPrecision())) {
            throw new IllegalArgumentException("Tried to construct a MonteCarloRandom object with a pdistribution that does not sum to 1! - sum is : " + sum);
        }
    }


    /**
     * @return
     */
    public double nextDouble() {
        double rnd = _random.nextDouble();
        return _cdistribution.selectFromCumulativeProbability(rnd);
    }

    public int nextIndex() {
        double rnd = _random.nextDouble();
        return _cdistribution.selectIndexFromCumulativeProbability(rnd);
    }

    /**
     * Generates a discreet probability distribution based on the normal distribution with a given sd and mean
     * It does it by calculating a probability value using the normail density function for each category and then normailising the results
     *
     * @param standardDeviation
     * @param mean
     * @return
     */
    public static MonteCarloRandom generateNormalDistribution(Random random, double from, double to, double by, double mean, double standardDeviation) {
        DiscreetValueMap pdistribution = new DiscreetValueMap(from, to, by, DoubleMath.DOUBLE_PRECISION_DELTA);

        if (standardDeviation > 0) {
            for (int i = 0; i < pdistribution.getSize(); ++i) {
                double p = GaussianRandom.calculateGaussianP(pdistribution.getKey(i), mean, standardDeviation);
                pdistribution.setValue(i, p);
            }
        } else {
            for (int i = 0; i < pdistribution.getSize(); ++i) {
                if (DoubleMath.precisionEquals(mean, pdistribution.getKey(i), DoubleMath.DOUBLE_PRECISION_DELTA)) {
                    pdistribution.setValue(i, 1);
                }
            }
        }
        DiscreetValueMap normalised = DiscreetValueMap.createNormalised(pdistribution);
        return new MonteCarloRandom(normalised, random);
    }


    public DiscreetValueMap getPDistribution() {
        return _pdistribution;
    }



    private DiscreetValueMap _pdistribution;

    private Random _random;
    private DiscreetValueMap _cdistribution;
}
