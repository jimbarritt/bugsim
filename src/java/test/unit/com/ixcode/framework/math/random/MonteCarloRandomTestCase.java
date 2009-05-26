/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.random;

import junit.framework.TestCase;

import java.util.Random;

/**
 * TestCase for class : MonteCarloRandom
 */
public class MonteCarloRandomTestCase extends TestCase {


    public static void testNormalDistribution() {
        MonteCarloRandom random = MonteCarloRandom.generateNormalDistribution(RANDOM, -180, 180, 1, 0, 60);
        random.nextDouble();
    }


    private static final Random RANDOM = new Random();

}
