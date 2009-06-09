/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.stats;

import com.ixcode.framework.math.stats.SummaryStatistics;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DescriptiveStatistics {

    public DescriptiveStatistics(double[] values) {
        _N = values.length;
        _mean = SummaryStatistics.calculateMeanDouble(values);
        double ssq = SummaryStatistics.calculateSumOfSquareOfDeviationsFromMean(values, _mean);
        _variance = SummaryStatistics.calculateVariance(values, ssq);
        _stdDeviation = SummaryStatistics.calculateStdDeviationDoubleWithVariance(_variance);
        _stdError =   _stdDeviation  / Math.sqrt(_N);
        _meanMinusErr = _mean - _stdError;
        _meanPlusErr = _mean + _stdError;
    }

    public double getMean() {
        return _mean;
    }

    public double getVariance() {
        return _variance;
    }

    public double getStdDeviation() {
        return _stdDeviation;
    }

    public int getN() {
        return _N;
    }

    public double getStdError() {
        return _stdError;
    }

    public double getMeanMinusErr() {
        return _meanMinusErr;
    }

    public double getMeanPlusErr() {
        return _meanPlusErr;
    }

    private double _mean;
    private double _variance;
    private double _stdDeviation;
    private int _N;
    private double _stdError;

    private double _meanMinusErr;
    private double _meanPlusErr;
}
