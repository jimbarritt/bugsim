package com.ixcode.framework.math;

import com.ixcode.framework.math.random.*;
import com.ixcode.framework.math.stats.*;
import com.ixcode.bugsim.fixture.*;
import static org.junit.Assert.*;
import org.junit.*;

import java.util.*;

@FunctionalTest
public class GaussianRandomGeneratorTest {

    private static final Random RANDOM = new Random();

    @Test
    public void testRandomGaussian() {
         int replicants = 100000;
         double standardDeviation = 10;
         double mean = 0;
         double[] results = new double[replicants];
         for (int i = 0; i < replicants; ++i) {
             double random = GaussianRandom.generateGaussian(RANDOM, standardDeviation, mean);
             results[i] = random;
         }

         double actualMean = SummaryStatistics.calculateMeanDouble(results);
         double actualSD = SummaryStatistics.calculateStdDeviationDouble(results);
         assertEquals("mean", 0d, actualMean, 0.1);
         assertEquals("Standard Deviation", standardDeviation, actualSD, 0.5);
     }


}
