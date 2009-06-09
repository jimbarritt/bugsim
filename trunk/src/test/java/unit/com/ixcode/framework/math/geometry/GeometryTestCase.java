/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import junit.framework.TestCase;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * TestCase for class : Geometry
 */
public class GeometryTestCase extends TestCase {

    /**
     * Azimuth: 280.32728871280096,
     * startLocation: x:4.310673061536413 y:34.90947782720577,
     * projectedLocation: x:-74.39330828270411 y:49.25114162766627,
     * cx: 56.5, cy: 56.5, r:56.5
     */
    public void testLineIntersectsCircle_2() {
        double xStart = 4.310673061536413;
        double yStart = 34.90947782720577;
        double xEnd = -74.39330828270411;
        double yEnd = 49.25114162766627;
        boolean instersects = Geometry.lineInstersectsCircleDouble(xStart, yStart, xEnd, yEnd, 56.5, 56.5, 56.5);
        List ixs = Geometry.findLineSegmentCircleIntersections(56.5, 56.5, 56.5,new RectangularCoordinate(xStart, yStart),new RectangularCoordinate( xEnd, yEnd), false);

    }

    public void testWrapThetaChange() {
        double delta = 0.00001;
        double actual = Geometry.wrapThetaChange(-190);
        assertEquals(170, actual, delta);

        actual = Geometry.wrapThetaChange(190);
        assertEquals(-170, actual, delta);

        actual = Geometry.wrapThetaChange(-190 + -360);
        assertEquals(170, actual, delta);

        actual = Geometry.wrapThetaChange(190 + 360);
        assertEquals(-170, actual, delta);

        actual = Geometry.wrapThetaChange(10);
        assertEquals(10, actual, delta);

        actual = Geometry.wrapThetaChange(-10);
        assertEquals(-10, actual, delta);
        actual = Geometry.wrapThetaChange(180);
        assertEquals(180, actual, delta);
    }

    public void testCreateCourseChange() {
        double delta = 0.000001;
        double actual = Geometry.createCourseChange(0, 20).getNewAzimuth();
        assertEquals(20d, actual, delta);

        actual = Geometry.createCourseChange(360, 710).getNewAzimuth();
        assertEquals(350d, actual, delta);

        actual = Geometry.createCourseChange(360, -20).getNewAzimuth();
        assertEquals(340d, actual, delta);

        actual = Geometry.createCourseChange(10, 179.005).getNewAzimuth();
        assertEquals(189.005d, actual, delta);
    }

    public void testCreateCourseChange_2() {
        double actualAz = Geometry.createWrappedCourseChange(0, -190).getNewAzimuth();
        double actualAot = Geometry.createWrappedCourseChange(0, -190).getAngleOfChange();
        System.out.println("az=" + actualAz + ", aot=" + actualAot);
    }

    public void testSegmentSegmentIntersection() {
            RectangularCoordinate a1 = new RectangularCoordinate(-7.5, 6.25);
            RectangularCoordinate a2 = new RectangularCoordinate(2.5, 11.25);

            RectangularCoordinate b1 = new RectangularCoordinate(-9.0, 9.5);
            RectangularCoordinate b2 = new RectangularCoordinate(-3.0, 6.5);


            RectangularCoordinate intersection = Geometry.findSegmentSegmentIntersection(a1, a2, b1, b2);
            assertNotNull(intersection);
            assertEquals(-5, intersection.getDoubleX(), 0);
            assertEquals(7.5, intersection.getDoubleY(), 0);

        }

        public void testSegmentSegmentIntersection_outsideSegment() {
            RectangularCoordinate a1 = new RectangularCoordinate(-7.5, 6.25);
            RectangularCoordinate a2 = new RectangularCoordinate(2.5, 11.25);

            RectangularCoordinate b1 = new RectangularCoordinate(-3.0, 6.5);
            RectangularCoordinate b2 = new RectangularCoordinate(1.0, 4.5);


            RectangularCoordinate intersection = Geometry.findSegmentSegmentIntersection(a1, a2, b1, b2);
            assertNull(intersection);

        }

        public void testSegmentSegmentIntersection_vertical() {
            RectangularCoordinate a1 = new RectangularCoordinate(-7.5, 6.25);
            RectangularCoordinate a2 = new RectangularCoordinate(2.5, 11.25);

            RectangularCoordinate b1 = new RectangularCoordinate(-5.0, 2);
            RectangularCoordinate b2 = new RectangularCoordinate(-5.0, 12);


            RectangularCoordinate intersection = Geometry.findSegmentSegmentIntersection(a1, a2, b1, b2);
            assertNotNull(intersection);
            assertEquals(-5, intersection.getDoubleX(), 0);
            assertEquals(7.5, intersection.getDoubleY(), 0);

        }

        public void testSegmentSegmentIntersection_parallel() {
            RectangularCoordinate a1 = new RectangularCoordinate(-7.5, 6.25);
            RectangularCoordinate a2 = new RectangularCoordinate(2.5, 11.25);

            RectangularCoordinate b1 = new RectangularCoordinate(-9.5, 8.25);
            RectangularCoordinate b2 = new RectangularCoordinate(4.5, 13.25);

            RectangularCoordinate intersection = Geometry.findSegmentSegmentIntersection(a1, a2, b1, b2);
            assertNull(intersection);


        }

        public void testCalculateAngle() {
            double r = 0.598765;
            double thetaDeg = 0;
            AngleResult result = null;

            List failedAngles = new ArrayList();
            for (int i = 0; i < 361; ++i) {
                result = angleTest(thetaDeg, r, new Integer(15));

                System.out.println(result);

                thetaDeg += 1;
            }


        }

        private static class AngleResult {
            public AngleResult(double theta, double calcR, double x, double y) {
                _theta = theta;
                _calcR = calcR;
                _x = x;
                _y = y;
            }

            public double getTheta() {
                return _theta;
            }

            public double getCalcR() {
                return _calcR;
            }

            public double getX() {
                return _x;
            }

            public double getY() {
                return _y;
            }

            public String toString() {
                return "theta=" + _theta + ", calcR=" + _calcR + ", x=" + _x + ", y=" + _y;
            }

            private double _theta;
            private double _calcR;
            private double _x;
            private double _y;
        }

        private AngleResult angleTest(double thetaDeg, double r, Integer scale) {
            double thetaRad = Math.toRadians(thetaDeg);

            double x = r * Math.cos(thetaRad);
            double y = r * Math.sin(thetaRad);

            if (scale != null) {
                x = new BigDecimal(x).setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue();
                y = new BigDecimal(y).setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            double calcrR = Math.sqrt(x * x + y * y);

            return new AngleResult(thetaDeg, calcrR, x, y);


        }

    public void testSegmentSegmentIntersection_matchstick() {
            RectangularCoordinate a1 = new RectangularCoordinate(50, 100);
            RectangularCoordinate a2 = new RectangularCoordinate(150, 100);

            RectangularCoordinate b1 = new RectangularCoordinate(110, 127.32050807568878);
            RectangularCoordinate b2 = new RectangularCoordinate(90, 92.67949192431124);

            RectangularCoordinate intersection = Geometry.findSegmentSegmentIntersection(b1, b2, a1, a2);
            assertNotNull(intersection);
            assertEquals(94.226497, intersection.getDoubleX(), 0.000001);
            assertEquals(100, intersection.getDoubleY(), 0.00000000001);


        }

    public void testApplyHeadingChange() {
            assertEquals("40-360", 40, AzimuthCoordinate.applyAzimuthChange(40, -360), 0.0);
            assertEquals("270-360", 270, AzimuthCoordinate.applyAzimuthChange(270, -360), 0.0);
            assertEquals("40+360", 40, AzimuthCoordinate.applyAzimuthChange(40 , 360), 0.0);
            assertEquals("40-720", 40, AzimuthCoordinate.applyAzimuthChange(40, -720), 0.0);
            assertEquals("40+7200", 40, AzimuthCoordinate.applyAzimuthChange(40, 720), 0.0);
            assertEquals("40-770", 350, AzimuthCoordinate.applyAzimuthChange(40 ,-770), 0.0);

        }

    public void testPointInCircle() {
        int x = 10;
        int y = 10;
        int sX = 20;
        int sY = 20;
        int r = 20;
        boolean inCircle = Geometry.isPointInCircleDouble(x, y, sX, sY, r);
        assertTrue("Point should be in circle", inCircle);

    }

    public void testPointInCircle2() {
        double sX = 15;
        double sY = 15;
        double r = 5;



        assertTrue("in", Geometry.isPointInCircleDouble(15, 15, sX, sY, r));
        assertTrue("in", Geometry.isPointInCircleDouble(10, 15, sX, sY, r));
        assertTrue("in", Geometry.isPointInCircleDouble(20, 15, sX, sY, r));
        assertTrue("in", Geometry.isPointInCircleDouble(15, 20, sX, sY, r));
        assertTrue("in", Geometry.isPointInCircleDouble(15, 10, sX, sY, r));
        assertTrue("in", Geometry.isPointInCircleDouble(14.99999, 10, sX, sY, r));
        assertTrue("in", Geometry.isPointInCircleDouble(15, 19.99999, sX, sY, r));
        assertTrue("in", Geometry.isPointInCircleDouble(19.99999, 15, sX, sY, r));

        assertFalse("out", Geometry.isPointInCircleDouble(11, 19, sX, sY, r));
        assertFalse("out", Geometry.isPointInCircleDouble(15, 9.999999, sX, sY, r));
        assertFalse("out", Geometry.isPointInCircleDouble(14.99999, 9.999999, sX, sY, r));
        assertFalse("out", Geometry.isPointInCircleDouble(20.00001, 15, sX, sY, r));
        assertFalse("out", Geometry.isPointInCircleDouble(20, 14.990, sX, sY, r));
    }

    public void testPointInCircle_false() {
            int x = 10;
            int y = 10;
            int sX = 20;
            int sY = 20;
            int r = 5;
            boolean inCircle = Geometry.isPointInCircleDouble(x, y, sX, sY, r);
            assertFalse("Point should be in circle", inCircle);

        }


        public void testLineIntersectsCircle() {
            double x1 = 5;
            double y1 = 15;
            double x2 = 15;
            double y2 = 5;
            double sX = 10;
            double sY = 10;
            double r = 10;
            boolean intersects = Geometry.lineInstersectsCircle(x1, y1, x2, y2, sX, sY, r);
            assertTrue("Line should intersect circle", intersects);
        }

        public void testLineIntersectsCircle_false() {
            double x1 = -5;
            double y1 = 5.00001;
            double x2 = 15;
            double y2 = 5.00001;
            double sX = 0;
            double sY = 0;
            double r = 5;
            boolean intersects = Geometry.lineInstersectsCircle(x1, y1, x2, y2, sX, sY, r);
            assertFalse("Line should NOT intersect circle", intersects);
        }

        //102.294, y=298.484)) : ((x=101.435, y=298.996)) vs (x=210.000, y=230.000
        //(-108.565) * (68.48399999999998) - (-106.706) * (68.99599999999998) = -72.67828399999962
        public void testLineIntersectsCircle_false_2() {
            double x1 = 101.435d;
            double y1 = 298.996d;
            double x2 = 102.294d;
            double y2 = 298.484d;
            double sX = 210;
            double sY = 230;
            double r = 5;
            boolean intersects = Geometry.lineInstersectsCircle(x1, y1, x2, y2, sX, sY, r);
            assertFalse("Line should NOT intersect circle", intersects);
        }

        //    x=222.412, y=189.710)) : ((x=202.823, y=193.745)) vs (x=210.000, y=190.000)
        public void testLineIntersectsCircle_true_3() {
            double x1 = 202.823;
            double y1 = 193.745;
            double x2 = 222.412;
            double y2 = 189.710;
            double sX = 210;
            double sY = 190;
            double r = 5;
            boolean intersects = Geometry.lineInstersectsCircle(x1, y1, x2, y2, sX, sY, r);
            assertTrue("Line should  intersect circle", intersects);
        }


        //((x=143.004, y=268.491)) : ((x=133.583, y=286.133)) vs (x=190.000, y=190.000
        public void testLineIntersectsCircle_false_3() {
            double x1 = 133;
            double y1 = 286;
            double x2 = 143;
            double y2 = 268;
            double sX = 190.0;
            double sY = 190.0;
            double r = 5;
            Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
            System.out.println("Intersects rect : " + line.intersects(sX - r, sY - r, r * 2, r * 2));


            boolean intersects = Geometry.lineInstersectsCircle(x1, y1, x2, y2, sX, sY, r);
            assertFalse("Line should NOT intersect circle", intersects);
        }

        public void testLineIntersectsCircle_true_4() {
            double x1 = 5;
            double y1 = 15;
            double x2 = 14;
            double y2 = 11;
            double sX = 10;
            double sY = 10;
            double r = 3;
            boolean intersects = Geometry.lineInstersectsCircle(x1, y1, x2, y2, sX, sY, r);
            assertTrue("Line should NOT intersect circle", intersects);
        }

        public void testLineIntersectsCircle_true_5() {
            double x1 = 6;
            double y1 = 12;
            double x2 = 13;
            double y2 = 13;
            double sX = 10;
            double sY = 10;
            double r = 5;
            boolean intersects = Geometry.lineInstersectsCircle(x1, y1, x2, y2, sX, sY, r);
            assertTrue("Line should  intersect circle", intersects);

        }

        public void testLineIntersectsCircle_false_5() {
            double x1 = 1005.567;
            double y1 = 1004.23;
            double x2 = 1020.564;
            double y2 = 1002.123;
            double sX = 1000.879;
            double sY = 100.12;
            double r = 3;
            boolean intersects = Geometry.lineInstersectsCircle(x1, y1, x2, y2, sX, sY, r);
            assertFalse("Line should NOT intersect circle", intersects);
        }

        public void testIntersection() {
            int x1 = 5;
            int y1 = 15;
            int x2 = 15;
            int y2 = 5;
            int sX = 10;
            int sY = 10;
            int r;

//        IxMath.lineIntersectsCircle()
        }

        public void testPointIsInRectangle_false() {
            int x = 0;
            int y = 0;
            int x1 = 10;
            int y1 = 10;
            int width = 10;
            int height = 10;

            assertFalse(Geometry.isPointInRectangle(x, y, x1, y1, width, height));
        }

        public void testPointIsInRectangle_border() {
            int x = 10;
            int y = 10;
            int x1 = 10;
            int y1 = 10;
            int width = 10;
            int height = 10;

            assertTrue(Geometry.isPointInRectangle(x, y, x1, y1, width, height));
        }

        public void testPointIsInRectangle_true() {
            int x = 15;
            int y = 15;
            int x1 = 10;
            int y1 = 10;
            int width = 10;
            int height = 10;

            assertTrue(Geometry.isPointInRectangle(x, y, x1, y1, width, height));
        }

        public void testPointOnPerimeter() {
            CartesianBounds bounds = new CartesianBounds(0, 0, 10, 10);
            RectangularCoordinate coord = Geometry.findCoordAroundPerimeter(5, bounds);
            assertEquals(5, coord.getDoubleX(), 0.0);
            assertEquals(0, coord.getDoubleY(), 0.0);

            coord = Geometry.findCoordAroundPerimeter(15, bounds);
            assertEquals(10, coord.getDoubleX(), 0.0);
            assertEquals(5, coord.getDoubleY(), 0.0);

            coord = Geometry.findCoordAroundPerimeter(25, bounds);
            assertEquals(5, coord.getDoubleX(), 0.0);
            assertEquals(10, coord.getDoubleY(), 0.0);

            coord = Geometry.findCoordAroundPerimeter(35, bounds);
            assertEquals(0, coord.getDoubleX(), 0.0);
            assertEquals(5, coord.getDoubleY(), 0.0);
        }


        public void testCircleInSquare() {

            CartesianBounds square = new CartesianBounds(0, 0, 10, 10);

            double rr = (square.getRadiusOfEnclosingCircle() + 10d) * 2;


            Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, rr, rr);


            CartesianBounds square2 = new CartesianBounds(0, 0, circle.getWidth(), circle.getHeight());


        }


    /**
     *
     */
    public void testFindLineIntersectsCircle() {
        List intersections = Geometry.findLineCircleIntersections(10, 10, 5, 5, 5, 5, 15);
        assertEquals("should be 1", 1, intersections.size());
        assertCoordinatesEqual(new RectangularCoordinate(5, 10), (RectangularCoordinate)intersections.get(0));

        intersections = Geometry.findLineCircleIntersections(10, 10, 5, 2, 5, 3, 15);
        assertEquals("NUMBER OF INTERSECTIONs", 0, intersections.size());


        intersections = Geometry.findLineCircleIntersections(10, 10, 5, 5, 3, 17, 14);
        assertEquals("NUMBER OF INTERSECTIONs", 2, intersections.size());
        assertCoordinatesEqual(new RectangularCoordinate(14.6476, 11.8436), (RectangularCoordinate)intersections.get(0));
        assertCoordinatesEqual(new RectangularCoordinate(7.759881, 5.529891), (RectangularCoordinate)intersections.get(1));


        intersections = Geometry.findLineCircleIntersections(10, 10, 5, 16, 8, 4, 15);
        assertEquals("NUMBER OF INTERSECTIONs", 2, intersections.size());
        assertCoordinatesEqual(new RectangularCoordinate(6.4814, 13.5524), (RectangularCoordinate)intersections.get(0));
        assertCoordinatesEqual(new RectangularCoordinate(14.8242, 8.6858), (RectangularCoordinate)intersections.get(1));

    }

    private void assertCoordinatesEqual(RectangularCoordinate c1, RectangularCoordinate c2) {
        assertEquals("x", c1.getDoubleX(), c2.getDoubleX(), 0.001);
        assertEquals("y", c1.getDoubleY(), c2.getDoubleY(), 0.001);
    }





}
