/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.grid;

import com.ixcode.framework.math.geometry.*;
import junit.framework.TestCase;

import java.util.Random;
import java.util.List;

/**
 * TestCase for class : Grid
 */
public class GridTestCase extends TestCase {

    public void testGetContainingSquares() {
        CartesianBounds b = new CartesianBounds(0, 0, 100, 100);
        Grid g = GridFactory.createGrid(null, "testGrid", b.getDoubleX(), b.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight(), 10, 10, false);

        RectangularCoordinate start = new RectangularCoordinate(0, 0);
        RectangularCoordinate end = new RectangularCoordinate(100, 100);
        List containingSquares = g.getContainingGridSquares(start, end);
        assertEquals("size", 100, containingSquares.size());

        start = new RectangularCoordinate(10, 10);
        end = new RectangularCoordinate(15, 15);
        containingSquares = g.getContainingGridSquares(start, end);
        assertEquals("size", 1, containingSquares.size());

        start = new RectangularCoordinate(10, 10);
        end = new RectangularCoordinate(20, 20);
        containingSquares = g.getContainingGridSquares(start, end);
        assertEquals("size", 4, containingSquares.size());

        start = new RectangularCoordinate(10, 10);
        end = new RectangularCoordinate(30, 20);
        containingSquares = g.getContainingGridSquares(start, end);
        assertEquals("size", 6, containingSquares.size());


    }

    public void testIsInBounds() {
        CartesianBounds b = new CartesianBounds(10, 10, 10, 10);
        boolean isCircular = true;
        Grid g = GridFactory.createGrid(null, "testGrid", b.getDoubleX(), b.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight(), 1, 1, isCircular);

//        assertTrue("in", g.isInBounds(new RectangularCoordinate(15, 15)));
//        assertTrue("in", g.isInBounds(new RectangularCoordinate(10, 15)));
//        assertTrue("in", g.isInBounds(new RectangularCoordinate(20, 15)));
//        assertTrue("in", g.isInBounds(new RectangularCoordinate(15, 20)));
//        assertTrue("in", g.isInBounds(new RectangularCoordinate(15, 10)));
//
//        assertFalse("in", g.isInBounds(new RectangularCoordinate(11, 19)));
//
//        assertFalse("in", g.isInBounds(new RectangularCoordinate(14.99999, 15)));

        boolean intersects = Geometry.lineInstersectsCircle(20, 15, 25, 25, 15, 15, 5);
        assertTrue(intersects);

    }


    /**
     * bounds =- x=-0.07 : y=-0.07 : w=113.14 : h=113.14
     * <p/>
     * start=(x=5.009 : y=79.923), end=(x=-74.318 : y=69.562)
     * start=(x=6.096 : y=30.821), end=(x=-64.031  y=-7.679
     */
    public void testCrossedBoundary() {
        CartesianBounds b = new CartesianBounds(-0.07, -0.07, 113.14, 113.14);
        boolean isCircular = true;
        Grid g = GridFactory.createGrid(null, "testGrid", b.getDoubleX(), b.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight(), 1, 1, isCircular);

        RectangularCoordinate ac = new RectangularCoordinate(6.096, 30.821);
        RectangularCoordinate bc = new RectangularCoordinate(-64.031, -7.679);

        Intersection ix = g.enteredBoundary(ac, bc);

        assertNotNull(ix);

    }

    /**
     * x=23.230870 : y=102.251120), b =(x=-56.761550 : y=101.149838), bb=(x=-0.07 : y=-0.07 : w=113.14 : h=113.14
     */
    public void testLineIntersectsCircle() {

        RectangularCoordinate a = new RectangularCoordinate(23.230870, 102.251120);
        RectangularCoordinate b = new RectangularCoordinate(-56.761550, 101.149838);
        CartesianBounds bb = new CartesianBounds(-0.068542, -0.068542, 113.137085, 113.137085);
//        CartesianBounds bb = new CartesianBounds(0 ,0 ,113,113);

        boolean intersects = Geometry.lineInstersectsCircle(a.getDoubleX(), a.getDoubleY(), b.getDoubleX(), b.getDoubleY(), bb.getDoubleCentreX(), bb.getDoubleCentreY(), bb.getRadiusOfInnerCircle());
        assertFalse(intersects);

    }


    public void testEnteredOrExitedBoundary() {
        CartesianBounds b = new CartesianBounds(0, 0, 10000, 10000);
        boolean isCircular = true;
        Grid g = GridFactory.createGrid(null, "testGrid", b.getDoubleX(), b.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight(), 1, 1, isCircular);
        RectangularCoordinate centre = b.getCentre();
        double radius = b.getRadiusOfInnerCircle();

        for (int degree = 0; degree < 359; ++degree) {
            for (double subdegree = 0; subdegree <= 1; subdegree += 1e-2) {
                double azimuth = degree + subdegree;
                RectangularCoordinate start = centre.moveTo(new AzimuthCoordinate(azimuth, radius));
                RectangularCoordinate endOutside = centre.moveTo(new AzimuthCoordinate(azimuth, radius + 1e-6));
                RectangularCoordinate endInside = centre.moveTo(new AzimuthCoordinate(azimuth, radius - 1e-6));

                Intersection ixOutside = g.exitedBoundary(start, endOutside);
                assertNotNull(ixOutside);
                assertTrue("should intersect at start! azimuth=" + azimuth + ", " + start, ixOutside.intersects());
                RectangularCoordinate ixCoord = ixOutside.getCoordinate();
                assertEquals(start, ixCoord);

                Intersection ixInside = g.enteredBoundary(start, endInside);
                assertNotNull(ixInside);
                assertTrue("should intersect at start! azimuth=" + azimuth + ", " + start, ixInside.intersects());
                RectangularCoordinate ixCoordIn = ixInside.getCoordinate();
                assertEquals(start, ixCoordIn);

                //And double check that they DONT intersect the other way!
                Intersection ixInsideReverse = g.exitedBoundary(start, endInside);
                assertNotNull(ixInsideReverse);
                assertFalse("should NOT intersect at start! azimuth=" + azimuth + ", " + start, ixInsideReverse.intersects());

                Intersection ixOutsideReverse = g.enteredBoundary(start, endOutside);
                assertNotNull(ixOutsideReverse);
                assertFalse("should NOT intersect at start! azimuth=" + azimuth + ", " + start, ixOutsideReverse.intersects());

            }
        }


    }


    public void testEnteredBoundary_specific() {
        CartesianBounds b = new CartesianBounds(0, 0, 10000, 10000);
        boolean isCircular = true;
        Grid g = GridFactory.createGrid(null, "testGrid", b.getDoubleX(), b.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight(), 1, 1, isCircular);
        RectangularCoordinate centre = b.getCentre();
        double radius = b.getRadiusOfInnerCircle();

        double theta = 0.01;
        RectangularCoordinate start = centre.moveTo(new AzimuthCoordinate(theta, radius));
        RectangularCoordinate end = centre.moveTo(new AzimuthCoordinate(theta, radius + 1e-6));

        Intersection ix = g.enteredBoundary(start, end);
        assertNotNull(ix);
        assertTrue("should intersect at start! theta=" + theta + ", " + start, ix.intersects());
        RectangularCoordinate ixCoord = ix.getCoordinate();
        assertEquals(start, ixCoord);


    }

    public void testEnteredBoundary_specific_1() {
        CartesianBounds b = new CartesianBounds(0, 0, 10000, 10000);
        boolean isCircular = true;
        Grid g = GridFactory.createGrid(null, "testGrid", b.getDoubleX(), b.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight(), 1, 1, isCircular);
        RectangularCoordinate centre = b.getCentre();
        double radius = b.getRadiusOfInnerCircle();

        double azimuth = 0.07;
        RectangularCoordinate start = centre.moveTo(new AzimuthCoordinate(azimuth, radius));
        RectangularCoordinate end = centre.moveTo(new AzimuthCoordinate(azimuth, radius + 1e-6));

        Intersection ix = g.enteredBoundary(start, end);
        assertNotNull(ix);
        assertTrue("should intersect at start! azimuth=" + azimuth + ", " + start, ix.intersects());
        RectangularCoordinate ixCoord = ix.getCoordinate();
        assertEquals(start, ixCoord);


    }

    private static final Random RANDOM = new Random();

}
