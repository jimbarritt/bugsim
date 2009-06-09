/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.math.DiscreetValueMap;
import com.ixcode.framework.math.LongMath;
import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.random.MonteCarloRandom;
import com.ixcode.framework.math.random.VonMisesRandomCain;
import com.ixcode.framework.javabean.IntrospectionUtils;
import junit.framework.TestCase;

import java.util.Random;
import java.text.DecimalFormat;

/**
 * TestCase for class : VonMisesAzimuthGenerator
 */
public class VonMisesAzimuthGeneratorTestCase extends TestCase {

    public void testCreateCourseChange() {

    }

    public void testPerformance() {
        double k = .05;
        double n = 360;
        double sd = 80;
        int[] TEST_N = new int[]{100,500,  1000, 5000, 10000};//,5000, 10000};
        int REPLICATES = 100;

        IAzimuthGenerator g1 = new VonMisesAzimuthGenerator(new Random(), k, n, true);
        IAzimuthGenerator g2 = new GaussianAzimuthGenerator(new Random(), sd);

        double[] G1_TIMES=new double[TEST_N.length];
        double[] G2_TIMES=new double[TEST_N.length];

        for (int i=0;i<TEST_N.length;++i) {
            G1_TIMES[i]=testGeneratorPerformance(TEST_N[i], REPLICATES, g1);
            G2_TIMES[i]=testGeneratorPerformance(TEST_N[i], REPLICATES, g2);
        }

        System.out.println("Trial, VM.Mean.Time.ms, GAUSS.Mean.Time.ms");
        for (int i=0;i<TEST_N.length;++i) {
            System.out.println(TEST_N[i] + ", " + F.format(G1_TIMES[i]) + ", " + F.format(G2_TIMES[i]));
        }


    }


    private double testGeneratorPerformance(int n, int replicates, IAzimuthGenerator g) {
        long[] times = new long[replicates];
        for (int i=0;i<replicates;++i) {
            times[i]=runTest(n, g);
        }

        double mean = LongMath.mean(times);
        System.out.println("[" + IntrospectionUtils.getShortClassName(g.getClass()) +  "] Executed " + n + " calculations in mean time (n=" + replicates + ") of : " + F.format(mean) + " ms");
        return mean;
    }

    private long runTest(int n, IAzimuthGenerator g) {
        long start = System.currentTimeMillis();
        CourseChange c = null;
        double currentHeading = 0;
        for (int i = 0; i < n; ++i) {
            currentHeading = g.generateCourseChange(currentHeading).getNewAzimuth();
        }
        long stop = System.currentTimeMillis();

        return stop-start;
    }

    public void testCreateCourseChange_2() {
        double heading = 0;
        double newHeading = 350;
        CourseChange c = Geometry.createCourseChange(heading, newHeading);
        System.out.println("c: " + c);
    }

    public void testDistribution() {

        double k = 8;
        double n = 200;

        DiscreetValueMap pdist = VonMisesRandomCain.createProbabilityDensity(n, k, 0);
        System.out.println("pdist:");
        debugDist(pdist);

        DiscreetValueMap cdist = DiscreetValueMap.createCumulative(pdist);
        System.out.println("cdist:");
        debugDist(cdist);

        MonteCarloRandom mc = new MonteCarloRandom(pdist, new Random());
        System.out.println("MonteCarlo Output:");
        System.out.print("c(");
        for (int i = 0; i < 100; ++i) {
            System.out.print("" + Math.toDegrees(mc.nextDouble()));
            if (i < 99) {
                System.out.print(",");
            }
        }
        System.out.println(")");

        VonMisesAzimuthGenerator g = new VonMisesAzimuthGenerator(new Random(), k, n, true);

        System.out.println("Generator Output:");
        System.out.print("c(");
        for (int i = 0; i < 100; ++i) {
            System.out.print("" + g.generateCourseChange(0).getAngleOfChange());
            if (i < 99) {
                System.out.print(",");
            }
        }
        System.out.println(")");

    }

    private void debugDist(DiscreetValueMap pdist) {
        System.out.print("c(");
        for (int i = 0; i < pdist.getValues().length; ++i) {
            System.out.print("" + pdist.getValues()[i]);
            if (i < (pdist.getValues().length - 1)) {
                System.out.print(",");
            }
        }
        System.out.println(")");
    }

    public void testGenerate() {
        double k = 3;
        double n = 100;
        VonMisesAzimuthGenerator g = new VonMisesAzimuthGenerator(new Random(), k, n, false);

        for (int i = 0; i < 10; i++) {
            CourseChange cc = g.generateCourseChange(10);
            System.out.println("Azimuth[" + i + "] - " + cc.getNewAzimuth() + " : change: " + cc.getAngleOfChange() + " direction : " + cc.getDirectionOfChange());
        }
    }

    private static final DecimalFormat F = new DecimalFormat("0.000");
}
