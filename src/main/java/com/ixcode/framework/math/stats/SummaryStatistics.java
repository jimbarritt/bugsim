/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.stats;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class SummaryStatistics {
    /**
     * Tells you what the standard deviation of a set of values is.
     *
     * @param values a list of Double objects
     * @return
     */
    public static double calculateStdDeviationDouble(double[] values) {
        double mean = calculateMeanDouble(values);
        return calculateStdDeviationDouble(values, mean);
    }

    public static double calculateStdDeviationDouble(double[] values, double mean) {
        double sumSqrDeviations = calculateSumOfSquareOfDeviationsFromMean(values, mean);
        double variance = calculateVariance(values, sumSqrDeviations);
        return calculateStdDeviationDoubleWithVariance(variance);
    }

    public static double calculateStdDeviationDoubleWithVariance(double variance) {
        return Math.sqrt(variance);
    }

    /**
     * From mathworld :
     * Note that the sample variance  defined above is not an unbiased estimator
     * for the population variance . In order to obtain an unbiased estimator for ,
     * it is necessary to instead define a "bias-corrected sample variance"
     * <p/>
     * Strictly speaking the variance is the sum of the squares / the number of observations.
     * <p/>
     * This is when we know the mean to be the true mean.
     * <p/>
     * However when dealing with stochastic processes, we know only that we have a SAMPLE of the true population
     * for some reason we therefore take N-1 as the denominator....
     *
     * @param values
     * @param sumOfSqrDeviations
     * @return
     */
    public static double calculateVariance(double[] values, double sumOfSqrDeviations) {
        return sumOfSqrDeviations / (values.length - 1);
    }

    public static double calculateSumOfSquareOfDeviationsFromMean(double[] values, double mean) {
        int i;
        double sumSqrDeviations = 0;
        double[] deviations = new double[values.length];
        for (i = 0; i < values.length; ++i) {
            deviations[i] = mean - values[i];
            sumSqrDeviations += Math.pow(deviations[i], 2);
        }
        return sumSqrDeviations;
    }

    public static double calculateMeanDouble(double[] values) {
        double sum = 0;
        for (int i = 0; i < values.length; ++i) {
            sum += values[i];
        }
        return sum / values.length;
    }

    public static double calculateSum(double[] values) {
        double sum = 0;
        for (int i=0;i<values.length;++i) {
            sum += values[i];
        }
        return sum;
    }
}
