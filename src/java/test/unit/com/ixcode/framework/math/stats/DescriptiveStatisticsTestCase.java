/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.stats;

import junit.framework.TestCase;

import java.text.DecimalFormat;

import com.ixcode.framework.math.stats.DescriptiveStatistics;

/**
 * TestCase for class : DescriptiveStatistics
 */
public class DescriptiveStatisticsTestCase extends TestCase {


    /**
     * expected values calculated in R
     */
    public void testStats() {
        double[] values = new double[] {10, 20, 30, 10, 40, 23, 67, 34, 11, 1, 23};

        String expectedMean  = "24.45455";
        String expectedVariance = "334.6727";
        String expectedSD = "18.29406";
        String expectedSE = "5.515867";
        String expectedMMinus = "18.93868";
        String expectedMPlus = "29.97041";

        DescriptiveStatistics s = new DescriptiveStatistics(values);
        assertEquals("mean", expectedMean, F5.format(s.getMean()));
        assertEquals("var", expectedVariance, F4.format(s.getVariance()));
        assertEquals("SD", expectedSD, F5.format(s.getStdDeviation()));
        assertEquals("SE", expectedSE, F6.format(s.getStdError()));
        assertEquals("M-", expectedMMinus, F5.format(s.getMeanMinusErr()));
        assertEquals("M+", expectedMPlus, F5.format(s.getMeanPlusErr()));


    }


    private static DecimalFormat F4 = new DecimalFormat("0.0000");
    private static DecimalFormat F5 = new DecimalFormat("0.00000");
    private static DecimalFormat F6 = new DecimalFormat("0.000000");
}
