/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CoordinateDistributorTestCase extends TestCase {


    public void testDistributePointsInSquare_10_10_16() {
        List expected = createExpectedPoints();

        runTest(10, 10, 16, expected);

    }

    public void testDistributePointsInSquare_10_10_14() {
        List expected = createExpectedPoints();
        expected.remove(expected.size() - 1);
        expected.remove(expected.size() - 1);

        runTest(10, 10, 14, expected);

    }


    private void runTest(double extentX, double extentY, int numberOfPoints, List expected) {
        CoordinateDistributor d = new CoordinateDistributor();
        List actual = d.distributePointsInSquare(extentX, extentY, numberOfPoints, DistributionType.INNER);
        assertEquals("Coordinate distribution", expected, actual);
    }

    private List createExpectedPoints() {
        List expected = new ArrayList();
        for (int iX = 1; iX < 5; ++iX) {
            double x = iX * 2;
            for (int iY = 1; iY < 5; ++iY) {
                double y = iY * 2;
                expected.add(new RectangularCoordinate(x, y));
            }
        }
        return expected;
    }


    public void testDistributePointsBySeparation() {

        CoordinateDistributor d = new CoordinateDistributor();
        List results = d.distributePointsBySeparation(4, 3, 10.0, false);

        int i=0;
        for (Iterator itr = results.iterator(); itr.hasNext();) {
            RectangularCoordinate coordinate = (RectangularCoordinate)itr.next();
            System.out.println("[" + i + "] - " + coordinate);
            i++;
        }




    }


    public void testCalculateBoundsForDistributedPoints() {
        CoordinateDistributor d = new CoordinateDistributor();
        CartesianBounds actual = d.calculateBoundsForSeparation(4, 3, 10.0, false);
        CartesianBounds expected = new CartesianBounds(0, 0, 30, 20);
        assertEquals(expected, actual);

    }


    public void assertEquals( CartesianBounds expected, CartesianBounds actual) {
        assertEquals("X", expected.getDoubleX(), actual.getDoubleX(), 0.0);
        assertEquals("Y", expected.getDoubleY(), actual.getDoubleY(), 0.0);
        assertEquals("height", expected.getDoubleWidth(), actual.getDoubleWidth(), 0.0);
        assertEquals("width", expected.getDoubleHeight(), actual.getDoubleHeight(), 0.0);

    }
}
