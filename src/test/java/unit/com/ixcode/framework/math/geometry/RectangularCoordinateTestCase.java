/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import junit.framework.TestCase;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class RectangularCoordinateTestCase extends TestCase {

    public void testCalculateDisplacementTo_1_1() {
        AzimuthCoordinate expected = new AzimuthCoordinate(45, 1.414);
        runDisplacementTest(1, 1, expected);
    }

    public void testCalculateDisplacementTo_minus1_minus1() {
        AzimuthCoordinate expected = new AzimuthCoordinate(225, 1.414);
        runDisplacementTest(-1, -1, expected);
    }


    public void testCalculateDisplacementTo_minus1_1() {
        AzimuthCoordinate expected = new AzimuthCoordinate(315, 1.414);
        runDisplacementTest(-1, 1, expected);
    }

    public void testCalculateDisplacementTo_1_minus1() {
        AzimuthCoordinate expected = new AzimuthCoordinate(135, 1.414);
        runDisplacementTest(1, -1, expected);
    }


    public void testCalculateDisplacementTo_minus10_minus20() {
        AzimuthCoordinate expected = new AzimuthCoordinate(206.56505, 22.36067);
        runDisplacementTest(-10, -20, expected);
    }

    public void testCalculateDisplacementTo_minus1_minus20() {
        AzimuthCoordinate expected = new AzimuthCoordinate(182.862405, 20.02498);
        runDisplacementTest(-1, -20, expected);
    }

    public void testCalculateDisplacementTo_minus1_20() {
        AzimuthCoordinate expected = new AzimuthCoordinate(357.13759, 20.02498);
        runDisplacementTest(-1, 20, expected);
    }


    public void testCalculateDisplacementTo_1_0() {
        AzimuthCoordinate expected = new AzimuthCoordinate(90, 1);
        runDisplacementTest(1, 0, expected);
    }

    public void testCalculateDisplacementTo_0_1() {
        AzimuthCoordinate expected = new AzimuthCoordinate(0, 1);
        runDisplacementTest(0, 1, expected);
    }

    public void testCalculateDisplacementTo_minus1_0() {
        AzimuthCoordinate expected = new AzimuthCoordinate(270, 1);
        runDisplacementTest(-1, 0, expected);
    }

    public void testCalculateDisplacementTo_0_minus1() {
        AzimuthCoordinate expected = new AzimuthCoordinate(180, 1);
        runDisplacementTest(0, -1, expected);
    }

    public void testCalculateDisplacementTo_0_0() {
        AzimuthCoordinate expected = new AzimuthCoordinate(Double.NaN, 0);
        runDisplacementTest(0, 0, expected);
    }

    public void testCalculateDistance() {
        RectangularCoordinate c1 = new RectangularCoordinate(80.375, 0.625);
        RectangularCoordinate c2 = new RectangularCoordinate(80.125, 0.625);

        double d1 = c1.calculateDistanceTo(c2);
        double d2 = c2.calculateDistanceTo(c1);

        assertEquals("Should be the same!!", d1, d2, 0.0);
        System.out.println("d1: " + d1);
        System.out.println("d2: " + d2);

    }
    public void testCalculateDistance_1() {
        RectangularCoordinate c1 = new RectangularCoordinate(80.375, 0.625);
        RectangularCoordinate c2 = new RectangularCoordinate(68.6875, 335.625);

        double d1 = c1.calculateDistanceTo(c2);
        double d2 = c2.calculateDistanceTo(c1);

        assertEquals("Should be the same!!", d1, d2, 0.0);
        System.out.println("d1: " + d1);
        System.out.println("d2: " + d2);

    }


    private void runDisplacementTest(double newX, double newY, AzimuthCoordinate expectedDisplacement) {
        RectangularCoordinate start = new RectangularCoordinate(0, 0);
        AzimuthCoordinate actual = start.calculateAzimuthCoordinateTo(newX, newY);

        assertEquals(expectedDisplacement, actual);
//        System.out.println("Displacement was : " + actual);
    }

    public static void assertEquals(AzimuthCoordinate expected, AzimuthCoordinate actual ) {
        assertEquals(expected.getDistance(), actual.getDistance(), 0.001);
        if (Double.isNaN(expected.getAzimuth())) {
            assertTrue(Double.isNaN(actual.getAzimuth()));
        } else {
            assertEquals(expected.getAzimuth(), actual.getAzimuth(), 0.001);
        }

    }

    /**
     * currentLocation=(x=18.372, y=25.830),
     * headingBeforeEgg=94.98, after=5.15,
     * locationBefore=(x=28.335, y=24.961),
     * after=(x=26.569, y=26.569)
     */
    public void testCalculateAngleTo() {
        RectangularCoordinate c = new RectangularCoordinate(18.372, 25.830);
        RectangularCoordinate c1 = new RectangularCoordinate(28.335, 24.961);
        RectangularCoordinate c2 = new RectangularCoordinate(26.569, 26.569);

        double heading1 = c.calculateAzimuthTo(c1);
        double heading2 = c.calculateAzimuthTo(c2);

        System.out.println("Heading 1= " + heading1);
        System.out.println("Heading 2= " + heading2);

    }


    public void testMoveTo() {
        RectangularCoordinate c= new RectangularCoordinate(10, 10);
        RectangularCoordinate new1 = c.moveTo(new AzimuthCoordinate(-103, 1));
        RectangularCoordinate new2 = c.moveTo(new AzimuthCoordinate(360-103, 1));
        System.out.println("new1: " + new1 + ", new2: " + new2);

        new1 = c.moveTo(new AzimuthCoordinate(-270, 1));
        new2 = c.moveTo(new AzimuthCoordinate(360-270, 1));
        System.out.println("new1: " + new1 + ", new2: " + new2);

        double p1 = AzimuthCoordinate.convertAzimuthToPolarAngle(-103);
        double p2 = AzimuthCoordinate.convertAzimuthToPolarAngle(360-103);
        System.out.println("p1: " + p1 + ", p2: " + p2);

    }

}
