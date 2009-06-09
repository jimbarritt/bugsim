/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math;

import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.math.random.*;
import com.ixcode.framework.math.stats.SummaryStatistics;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class IxMathTestCase extends TestCase {


    public void testSequenceDouble_1() {
        double[] expected = new double[] {1, 2, 3, 4, 5, 6};
        double[] actual = DoubleMath.createSequenceDouble(1, 6, 1, DoubleMath.DOUBLE_PRECISION_DELTA);
        assertEquals(expected, actual, 0.0001);
    }

    public void testSequenceDouble_2() {
            double[] expected = new double[] {0, 1, 2, 3, 4, 5};
            double[] actual = DoubleMath.createSequenceDouble(0, 5, 1, DoubleMath.DOUBLE_PRECISION_DELTA);
            assertEquals(expected, actual, 0.0001);
        }

    public void testSequenceDouble_3() {
        double[] expected = new double[] {6,5, 4, 3, 2, 1};
        double[] actual = DoubleMath.createSequenceDouble(6, 1, -1, DoubleMath.DOUBLE_PRECISION_DELTA);
        assertEquals(expected, actual, 0.0001);
    }

    public void testSequenceDouble_4() {
            double[] expected = new double[] {5, 4, 3, 2, 1, 0};
            double[] actual = DoubleMath.createSequenceDouble(5, 0, -1, DoubleMath.DOUBLE_PRECISION_DELTA);
            assertEquals(expected, actual, 0.0001);
        }


    public static void assertEquals(double[] expected, double[] actual, double delta) {
        if (expected.length != actual.length) {
            fail("arrays not of equal length");
        }
        for (int i=0;i<expected.length;++i) {
            assertEquals(expected[i], actual[i], delta);
        }
    }
    public void testUniformRandomDouble() {
        final int ITERATIONS = 100;

        double results[] = new double[ITERATIONS];
        for (int i = 0; i < ITERATIONS; ++i) {
            results[i] = UniformRandom.generateUniformRandomDouble(6, 1, RANDOM);
        }

        double max = DoubleMath.maxOf(results);
        double min = DoubleMath.minOf(results);
        System.out.println("N=" + ITERATIONS + ", max=" + max + ", min=" + min);
    }

    public void testUniformRandomAngleDouble() {
        final int ITERATIONS = 1000;

        double results[] = new double[ITERATIONS];
        for (int i = 0; i < ITERATIONS; ++i) {
            results[i] = Geometry.generateUniformRandomAzimuthChange(RANDOM);

        }

        double max = DoubleMath.maxOf(results);

        double min = DoubleMath.minOf(results);

          if (log.isInfoEnabled()) {
            log.info("N=" + ITERATIONS + ", max=" + max + ", min=" + min);
        }
        assertTrue(max > min);
        assertTrue(DoubleMath.precisionLessThanEqual(max, Math.toDegrees(2 * Math.PI), DoubleMath.DOUBLE_PRECISION_DELTA));



    }

    public void testNextBeta() {
        RandomNumberGenerator random = new RandomNumberGenerator(System.currentTimeMillis());

        for (int i = 0; i < 100; ++i) {
            double y = BetaRandom.generateBeta(random, 80, 3, 3);
            System.out.println(i + "," + y);
        }
    }

    public void testAccurate() {
        BigDecimal d = BigDecimalMath.accurate(new BigDecimal("9.000000001"), BigDecimalMath.SCALE);
        if (log.isInfoEnabled()) {
            log.info("d=" + d);
        }
    }

    public void testDoubleComparison() {
        assertTrue(DoubleMath.equals(0.000000001, 0));
        assertTrue(DoubleMath.equals(39.000000001, 39));
        assertTrue(DoubleMath.equals(38.999999999, 39));
    }


    /**
     *
     */
    public void testPrecisionEqual() {
        double a = 0.000001;
        double b = 0.000001;
        assertTrue(DoubleMath.precisionEquals(a, b, DoubleMath.DOUBLE_PRECISION_DELTA));

        a = 0.000001;
        b = 0.0000001;
        assertFalse(DoubleMath.precisionEquals(a, b, DoubleMath.DOUBLE_PRECISION_DELTA));

        a = 0.000001;
        b = 0.0000005;
        assertTrue(DoubleMath.precisionEquals(a, b, DoubleMath.DOUBLE_PRECISION_DELTA));


        a = 0.000001;
        b = 0.00000049;
        assertFalse(DoubleMath.precisionEquals(a, b, DoubleMath.DOUBLE_PRECISION_DELTA));

        a = 0.000001;
        b = 0.00000089;
        assertTrue(DoubleMath.precisionEquals(a, b, DoubleMath.DOUBLE_PRECISION_DELTA));

        a = 0.000001;
        b = 0.00000001;
        assertFalse(DoubleMath.precisionEquals(a, b, DoubleMath.DOUBLE_PRECISION_DELTA));

        a = 0.000001;
        b = 0.00000099;
        assertTrue(DoubleMath.precisionEquals(a, b, DoubleMath.DOUBLE_PRECISION_DELTA));

    }


    /**
     * See Essential Java For Scientists and Engineers pp 120-122
     */
    public void testRounding() {
        double[] result = solveLinearEquationDouble(0.2038d, 0.1218d, 0.2014d, 0.4071d, 0.2436d, 0.4038d);
        if (log.isInfoEnabled()) {
            log.info("x = " + result[0]);
            log.info("y = " + result[1]);
        }

        result = solveLinearEquationDoubleFourFigure(0.2038d, 0.1218d, 0.2014d, 0.4071d, 0.2436d, 0.4038d);
        if (log.isInfoEnabled()) {
            log.info("x = " + result[0]);
            log.info("y = " + result[1]);
        }
//        assertEquals(-2d, result[0], 0);
//        assertEquals(5, result[1], 0);
    }


    /**
     * Given
     * <p/>
     * ax + by = c
     * dx + ey = f
     * <p/>
     * Solve by:
     * <p/>
     * x = (ce-bf)/(ae-bd)
     * y = (af-cd)/(ae-bd)
     * See Essential Java For Scientists and Engineers pp 120-122
     *
     * @param a
     */
    public static double[] solveLinearEquationDouble(double a, double b, double c, double d, double e, double f) {
        double[] result = new double[2];

        double x = ((c * e) - (b * f)) / ((a * e) - (b * d));
        double y = ((a * f) - (c * d)) / ((a * e) - (b * d));
        result[0] = x;
        result[1] = y;
        return result;
    }

    /**
     * Does the same as IxMath.solveLinearEquationDouble but with 4 figure accuracy
     * <p/>
     * See Essential Java For Scientists and Engineers pp 120-122
     */
    public static double[] solveLinearEquationDoubleFourFigure(double a, double b, double c, double d, double e, double f) {
        double[] result = new double[2];

        double accuracy = 1e4;

        double ce = Math.floor(c * e * accuracy) / accuracy;
        double bf = Math.floor(b * f * accuracy) / accuracy;
        double ae = Math.floor(a * e * accuracy) / accuracy;
        double bd = Math.floor(b * d * accuracy) / accuracy;
        double af = Math.floor(a * f * accuracy) / accuracy;
        double cd = Math.floor(c * d * accuracy) / accuracy;


        double x = (ce - bf) / ((ae) - (bd));
        double y = ((af) - (cd)) / ((ae) - (bd));
        result[0] = x;
        result[1] = y;
        return result;
    }


    private double line1Equation(double x) {
        return 0.5 * x + 10d;
    }

    private double line2Equation(double x) {
        return -0.5 * x + 5d;
    }


    public void testSpeedOfBigDecimal() {

        final long N = 100;
        double testValueA = 567.456785434;
        double testValueB = 34.5678732;
        long start = System.currentTimeMillis();

        for (int i = 0; i < N; ++i) {
            BigDecimal a = BigDecimalMath.accurateOut(testValueA);
            BigDecimal b = BigDecimalMath.accurateOut(testValueB);

            BigDecimal result = BigDecimalMath.divide(a, b);

        }
        long end = System.currentTimeMillis();

        long timeTaken = end - start;

        log.info("time taken for BigDecimal calc: " + timeTaken + " ms, " + ((double)timeTaken / 1000) + " s");
        start = System.currentTimeMillis();


        for (int i = 0; i < N; ++i) {
            double a = testValueA;
            double b = testValueB;

            double result = a / b;

        }
        end = System.currentTimeMillis();

        timeTaken = end - start;

        log.info("time taken for Double calc: " + timeTaken + " ms, " + ((double)timeTaken / 1000) + " s");

    }

    public void testAccuracy() {
        double a = 345.6778823932323;
        double b = 23.34556344;
        double c = 3.425;
        double result = ((a / b * c) / c + 2) + Math.sqrt(b);

        BigDecimal A = BigDecimalMath.accurateIn(a);
        BigDecimal B = BigDecimalMath.accurateIn(b);
        BigDecimal C = BigDecimalMath.accurateIn(c);


        BigDecimal R1 = BigDecimalMath.divide(BigDecimalMath.divide(A, B).multiply(C), C.add(BigDecimalMath.accurateIn(2))).add(BigDecimalMath.accurateIn(Math.sqrt(B.doubleValue())));
        BigDecimal R2 = BigDecimalMath.accurateOut(result);

        log.info("a: " + a);
        log.info("a: " + b);
        log.info("result: " + result);

        log.info("A: " + A);
        log.info("B: " + B);

        log.info("R1: " + R1);
        log.info("R2: " + R2);

    }

    public void testInclusiveSqrt_10() {
        runSqrtTest(10.0d, 4);
    }


    public void testInclusiveSqrt_36() {
        runSqrtTest(36.0d, 6);
    }

    public void testInclusiveSqrt_36_5() {
        runSqrtTest(36.5d, 7);
    }



    private void runSqrtTest(double x, int expected) {
        assertEquals("inclusiveSqrt(" + x + ")", DoubleMath.inclusiveSqrt(x), expected);
    }

    public void testCalculateStdDeviationDouble() {
        double[] testValues = new double[]{2.5, 7.5, 2.5, 7.5, 2.5, 7.5};

        double stdDeviation = SummaryStatistics.calculateStdDeviationDouble(testValues);
        assertEquals("stdDeviation", 2.738613d, stdDeviation, 0.00001);

    }

    public void testRandomGaussian() {
        int replicants = 10000;
        double standardDeviation = 5;
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

    
    public void testAssertEqualsDouble() {
        assertEquals("test double assert equals", 2.5, 2.5, 0.0);
    }




    public void testGenerateCoordOnPerimeter() {
        CartesianDimensions dimensions = new CartesianDimensions(10000);
        CartesianBounds bounds = new CartesianBounds(0, 0, 10000, 10000);
        RectangularCoordinate center = bounds.getCentre();
        double radius = bounds.getRadiusOfInnerCircle();
        double cx = center.getDoubleX();
        double cy = center.getDoubleY();

        for (int degree = 0; degree < 359; ++degree) {
            for (double subdegree = 0; subdegree <= 1; subdegree+= 1e-1) {
                double theta = degree + subdegree;
                runGeneratedHeadingTest(center, theta, radius, cx, cy, dimensions);
            }
        }

        for (int degree = 0; degree < 359; ++degree) {
            for (double subdegree = 0; subdegree <= 1; subdegree+= 1e-2) {
                double azimuth = degree + subdegree;
                RectangularCoordinate point = center.moveTo(new AzimuthCoordinate(azimuth, radius + 1e-6));
                boolean isInCircle = Geometry.isPointInCircleDouble(point.getDoubleX(), point.getDoubleY(), cx, cy, radius);
                assertFalse("Should be outside!: " + point, isInCircle);
                boolean isIn = Geometry.isCoordinateInCircle(point, center, dimensions);
                assertFalse("Should be outside!: " + point, isIn);
                boolean isOnEdge = Geometry.isCoordinateOnEdgeOfCircle(point, center, dimensions);
                assertFalse("Should be outside!: " + point, isOnEdge);
                boolean isOutside = Geometry.isCoordinateOutsideCircle(point, center, dimensions);
                assertTrue("Should outside!: " + point, isOutside);
            }
        }

    }



    public void testStrangeAngle() {
        CartesianDimensions dimensions = new CartesianDimensions(10000);
                CartesianBounds bounds = new CartesianBounds(0, 0, 10000, 10000);
                RectangularCoordinate center = bounds.getCentre();
                double radius = bounds.getRadiusOfInnerCircle();
                double cx = center.getDoubleX();
                double cy = center.getDoubleY();

          runGeneratedHeadingTest(center, 300, radius, cx, cy, dimensions);
    }
    private void runGeneratedHeadingTest(RectangularCoordinate center, double azimuth, double radius, double cx, double cy, CartesianDimensions dimensions) {
        RectangularCoordinate point = center.moveTo(new AzimuthCoordinate(azimuth, radius));
        boolean isInCircle = Geometry.isPointInCircleDouble(point.getDoubleX(), point.getDoubleY(), cx, cy, radius);
        assertTrue("Should be in circle!: " + point, isInCircle);
        boolean isIn = Geometry.isCoordinateInCircle(point, center, dimensions);
        assertTrue("Should be in circle!: " + point, isIn);
        boolean isOnEdge = Geometry.isCoordinateOnEdgeOfCircle(point, center, dimensions);
        assertTrue("Should be on edge of circle!: " + point, isOnEdge);
        boolean isOutside = Geometry.isCoordinateOutsideCircle(point, center, dimensions);
        assertFalse("Should inside!: " + point, isOutside);
    }

    public void testGenerateRandomCoordOnPerimeter() {
        CartesianBounds b = new CartesianBounds(0, 0, 10000, 10000);
        boolean isCircular = true;
        final int MAX_COORDS = 100;
        List coords = new ArrayList();

        for (int iCoord = 0; iCoord < MAX_COORDS; ++iCoord) {
            RectangularCoordinate coord = Geometry.generateRandomCoordOnPerimeter(RANDOM, b, isCircular);
            coords.add(coord);
            if (!Geometry.isPointInCircleDouble(coord.getDoubleX(), coord.getDoubleY(), b.getDoubleCentreX(), b.getDoubleCentreY(), b.getRadiusOfInnerCircle())){
                fail("Coordinate: " + coord + " is NOT in circle!");
            }

            System.out.println("Coord: " + coord);

        }


    }


    public void testPoisson() {

        double mu = 4d;
        final int N = 10000;
        Map results = new HashMap();
        for (int i = 0; i < N; ++i) {
            Integer p = new Integer(PoissonRandom.generatePoissonRandom(RANDOM, mu));
            if (!results.containsKey(p)) {
                results.put(p, new Integer(0));
            }
            Integer freq = (Integer)results.get(p);
            results.put(p, new Integer(freq.intValue() + 1));
        }

        System.out.println("p, frequency");
        List sortedKeys = new ArrayList(results.keySet());
        Collections.sort(sortedKeys);
        long total = 0;
        for (Iterator itr = sortedKeys.iterator(); itr.hasNext();) {
            Integer key = (Integer)itr.next();
            Integer freq = (Integer)results.get(key);
            System.out.println(key + "," + freq);
            total += freq.intValue();
        }

        assertEquals("total", N, total);
    }

    private static final Random RANDOM = new Random();
    private static final Logger log = Logger.getLogger(IxMathTestCase.class);
}
