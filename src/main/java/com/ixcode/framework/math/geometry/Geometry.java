/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import com.ixcode.framework.math.BigDecimalMath;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.MatrixMath;
import com.ixcode.framework.math.random.UniformRandom;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public final class Geometry {


    /**
     * @return true if the coordinate is inside the circle including the edge.
     */
    public static boolean isCoordinateInCircle(RectangularCoordinate coord, RectangularCoordinate centre, CartesianDimensions dimensions) {
        return isPointInCircleDouble(coord.getDoubleX(), coord.getDoubleY(), centre.getDoubleX(), centre.getDoubleY(), dimensions.getInnerRadius());
    }

    /**
     * @return true if the coordinate is outside the circle, including the edge, so points on the edge are not counted (they are INSIDE)
     */
    public static boolean isCoordinateOutsideCircle(RectangularCoordinate coord, RectangularCoordinate centre, CartesianDimensions dimensions) {
        return !isPointInCircleDouble(coord.getDoubleX(), coord.getDoubleY(), centre.getDoubleX(), centre.getDoubleY(), dimensions.getInnerRadius());
    }

    public static boolean isCoordinateOnEdgeOfCircle(RectangularCoordinate coord, RectangularCoordinate centre, CartesianDimensions dimensions) {
        return isPointOnEdgeOfCircle(coord.getDoubleX(), coord.getDoubleY(), centre.getDoubleX(), centre.getDoubleY(), dimensions.getInnerRadius());
    }

    /**
     * Basically works out the distance between the point and the centre of the circle
     * and sees if this is less than the radius. if it is it must be in the circle!
     * <p/>
     * does it by comparing r^2 == d^2 so you dont have to sqrt the results.
     * <p/>
     * if r == d then r^2 must == d^2
     * <p/>
     * If its right on the radius it counts as in.
     *
     * @param x  - x coord of point to test
     * @param y  - y coord of point to test
     * @param sX - x coord of centre of circle
     * @param sY - y coord of centre of circle
     * @param r  - radius of circle
     * @return wether the point is in the circle or not
     */
    public static boolean isPointInCircleDouble(double x, double y, double sX, double sY, double radius) {
        double a = sX - x;
        double b = sY - y;
        double d = a * a + b * b;

        double r2 = radius * radius;

        return (d < r2) ? true : DoubleMath.precisionEquals(d, r2, DoubleMath.DOUBLE_PRECISION_DELTA);
    }

    public static boolean isPointOnEdgeOfCircle(double x, double y, double sX, double sY, double r) {
        double a = sX - x;
        double b = sY - y;
        double d = a * a + b * b;
        double r2 = r * r;

        return DoubleMath.precisionEquals(d, r2, DoubleMath.DOUBLE_PRECISION_DELTA);
    }


    /**
     * Given 2 lines tells you where they intersect, returns null if they dont
     *
     * @param start1
     * @param end1
     * @param start2
     * @param end2
     * @return
     */
    public static RectangularCoordinate calculateLineIntersection(RectangularCoordinate start1, RectangularCoordinate end1, RectangularCoordinate start2, RectangularCoordinate end2) {
        double x1 = start1.getDoubleX();
        double y1 = start1.getDoubleY();
        double x2 = end1.getDoubleX();
        double y2 = end1.getDoubleY();
        double x3 = start2.getDoubleX();
        double y3 = start2.getDoubleY();
        double x4 = end2.getDoubleX();
        double y4 = end2.getDoubleY();

        Point2D p;

        return null;

    }

    public static boolean isPointWithinLineSegmentBounds(double x, double y, RectangularCoordinate a1, RectangularCoordinate a2, boolean includeEndPoints) {
        double minX = Math.min(a1.getDoubleX(), a2.getDoubleX());
        double minY = Math.min(a1.getDoubleY(), a2.getDoubleY());

        double maxX = Math.max(a1.getDoubleX(), a2.getDoubleX());
        double maxY = Math.max(a1.getDoubleY(), a2.getDoubleY());

        boolean within = false;
        if (includeEndPoints) {
            within = DoubleMath.precisionBetweenInclusive(minX, x, maxX, DoubleMath.DOUBLE_PRECISION_DELTA) && DoubleMath.precisionBetweenInclusive(minY, y, maxY, DoubleMath.DOUBLE_PRECISION_DELTA);
        } else {
            within =DoubleMath.precisionBetweenExclusive(minX, x, maxX, DoubleMath.DOUBLE_PRECISION_DELTA) && DoubleMath.precisionBetweenInclusive(minY, y, maxY, DoubleMath.DOUBLE_PRECISION_DELTA);
        }
        return within;
    }

    /**
     * @param xStart  of the line
     * @param yStart
     * @param xEnd    of the line
     * @param yEnd
     * @param xCentre of the circle
     * @param yCentre of the circle
     * @param r
     * @return
     */
    public static boolean lineInstersectsCircle(double xStart, double yStart, double xEnd, double yEnd, double xCentre, double yCentre, double r) {
        boolean intersects = false;
        double closestPointDistance = closestPointToLineSegment(xStart, yStart, xEnd, yEnd, xCentre, yCentre);

        BigDecimal D = BigDecimalMath.accurateIn(closestPointDistance);
        BigDecimal ar = BigDecimalMath.accurateIn(r);
        BigDecimal r2 = BigDecimalMath.accurate(ar.multiply(ar), BigDecimalMath.SCALE_OUT);
//        System.out.println("ClosestPointDistance is: " + Math.sqrt(closestPointDistance));
        if (D.compareTo(r2) <= 0) {
            intersects = true;
        }
        return intersects;


    }

    /**
     * Can;t always trust this one - better to back it up with findLineCircleIntersections aswell and check that they are not ==0.
     * @param xStart
     * @param yStart
     * @param xEnd
     * @param yEnd
     * @param xCentre
     * @param yCentre
     * @param r
     * @return
     */
    public static boolean lineInstersectsCircleDouble(double xStart, double yStart, double xEnd, double yEnd, double xCentre, double yCentre, double r) {
        boolean intersects = false;

        double D = closestPointToLineSegment(xStart, yStart, xEnd, yEnd, xCentre, yCentre);
        double r2 = r * r;

        if (DoubleMath.precisionLessThanEqual(D, r2, DoubleMath.DOUBLE_PRECISION_DELTA)) {
            intersects = true;
        }
        return intersects;


    }


    public static List findLineSegmentCircleIntersections(RectangularCoordinate center, double radius, RectangularCoordinate startSegment, RectangularCoordinate endSegment, boolean includeEndPoints) {
        return findLineSegmentCircleIntersections(center.getDoubleX(), center.getDoubleY(), radius, startSegment, endSegment, includeEndPoints);
    }

    /**
     * From http://vb-helper.com/howto_net_line_circle_intersections.html
     *
     * @param cx
     * @param cy
     * @param radius
     * @return
     */
    public static List findLineSegmentCircleIntersections(double cx, double cy, double radius, RectangularCoordinate a1, RectangularCoordinate a2, boolean includeEndPoints) {
        List intersections = new ArrayList();
        double dx;
        double dy;
        double A;
        double B;
        double C;
        double det;
        double t;
        double ix1;
        double iy1;
        double ix2;
        double iy2;
        double x1 = a1.getDoubleX();
        double y1 = a1.getDoubleY();
        double x2 = a2.getDoubleX();
        double y2 = a2.getDoubleY();

        dx = x2 - x1;
        dy = y2 - y1;

        A = dx * dx + dy * dy;
        B = 2 * (dx * (x1 - cx) + dy * (y1 - cy));
        C = (x1 - cx) * (x1 - cx) + (y1 - cy) * (y1 - cy) - radius * radius;

        det = B * B - 4 * A * C;
        if ((A <= 1e-16) || (det < 0)) { //0.00000001
            // No real solutions.

        } else if (det == 0) {
            // One solution.
            t = -B / (2 * A);
            ix1 = x1 + t * dx;
            iy1 = y1 + t * dy;
            if (isPointWithinLineSegmentBounds(ix1, iy1, a1, a2, includeEndPoints)) {
                intersections.add(new RectangularCoordinate(ix1, iy1));
            }
        } else {
            // Two solutions.
            t = (-B + Math.sqrt(det)) / (2 * A);
            ix1 = x1 + t * dx;
            iy1 = y1 + t * dy;

            if (isPointWithinLineSegmentBounds(ix1, iy1, a1, a2, includeEndPoints)) {
                intersections.add(new RectangularCoordinate(ix1, iy1));
            }

            t = (-B - Math.sqrt(det)) / (2 * A);
            ix2 = x1 + t * dx;
            iy2 = y1 + t * dy;

            if (isPointWithinLineSegmentBounds(ix2, iy2, a1, a2, includeEndPoints)) {
                intersections.add(new RectangularCoordinate(ix2, iy2));
            }
        }


        return intersections;
    }

    /**
     * From http://vb-helper.com/howto_net_line_circle_intersections.html
     *
     * @param cx
     * @param cy
     * @param radius
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static List findLineCircleIntersections(double cx, double cy, double radius, double x1, double y1,
                                                   double x2, double y2) {
        List intersections = new ArrayList();
        double dx;
        double dy;
        double A;
        double B;
        double C;
        double det;
        double t;
        double ix1;
        double iy1;
        double ix2;
        double iy2;

        dx = x2 - x1;
        dy = y2 - y1;

        A = dx * dx + dy * dy;
        B = 2 * (dx * (x1 - cx) + dy * (y1 - cy));
        C = (x1 - cx) * (x1 - cx) + (y1 - cy) * (y1 - cy) - radius * radius;

        det = B * B - 4 * A * C;
        if ((A <= 0.0000001) || (det < 0)) {
            // No real solutions.

        } else if (det == 0) {
            // One solution.
            t = -B / (2 * A);
            ix1 = x1 + t * dx;
            iy1 = y1 + t * dy;
            intersections.add(new RectangularCoordinate(ix1, iy1));
        } else {
            // Two solutions.
            t = (-B + Math.sqrt(det)) / (2 * A);
            ix1 = x1 + t * dx;
            iy1 = y1 + t * dy;


            intersections.add(new RectangularCoordinate(ix1, iy1));
            t = (-B - Math.sqrt(det)) / (2 * A);
            ix2 = x1 + t * dx;
            iy2 = y1 + t * dy;

            intersections.add(new RectangularCoordinate(ix2, iy2));
        }


        return intersections;
    }

    /**
     * Copied from Line2D.ptDistSegSq
     * Returns the square of the distance from a point to a line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the specified endpoints.
     * If the specified point intersects the line segment in between the
     * endpoints, this method returns 0.0.
     *
     * @param X1,&nbsp;Y1 the coordinates of the beginning of the
     *                    specified line segment
     * @param X2,&nbsp;Y2 the coordinates of the end of the specified
     *                    line segment
     * @param PX,&nbsp;PY the coordinates of the specified point being
     *                    measured against the specified line segment
     * @return a double value that is the square of the distance from the
     *         specified point to the specified line segment.
     */
    public static double closestPointToLineSegment(double X1, double Y1,
                                                   double X2, double Y2,
                                                   double PX, double PY) {
        // Adjust vectors relative to X1,Y1
        // X2,Y2 becomes relative vector from X1,Y1 to end of segment
        X2 -= X1;
        Y2 -= Y1;
        // PX,PY becomes relative vector from X1,Y1 to test point
        PX -= X1;
        PY -= Y1;
        double dotprod = PX * X2 + PY * Y2;
        double projlenSq;
        if (dotprod <= 0.0) {
            // PX,PY is on the side of X1,Y1 away from X2,Y2
            // distance to segment is length of PX,PY vector
            // "length of its (clipped) projection" is now 0.0
            projlenSq = 0.0;
        } else {
            // switch to backwards vectors relative to X2,Y2
            // X2,Y2 are already the negative of X1,Y1=>X2,Y2
            // to get PX,PY to be the negative of PX,PY=>X2,Y2
            // the dot product of two negated vectors is the same
            // as the dot product of the two normal vectors
            PX = X2 - PX;
            PY = Y2 - PY;
            dotprod = PX * X2 + PY * Y2;
            if (dotprod <= 0.0) {
                // PX,PY is on the side of X2,Y2 away from X1,Y1
                // distance to segment is length of (backwards) PX,PY vector
                // "length of its (clipped) projection" is now 0.0
                projlenSq = 0.0;
            } else {
                // PX,PY is between X1,Y1 and X2,Y2
                // dotprod is the length of the PX,PY vector
                // projected on the X2,Y2=>X1,Y1 vector times the
                // length of the X2,Y2=>X1,Y1 vector
                projlenSq = dotprod * dotprod / (X2 * X2 + Y2 * Y2);
            }
        }
        // Distance to line is now the length of the relative point
        // vector minus the length of its projection onto the line
        // (which is zero if the projection falls outside the range
        //  of the line segment).
        double lenSq = PX * PX + PY * PY - projlenSq;
        if (lenSq < 0) {
            lenSq = 0;
        }
        return lenSq;
    }

    /**
     * @param x      location to test X
     * @param y      location to test Y
     * @param x1     Origin of rectangle X
     * @param y1     Origin of rectangle Y
     * @param width  Width of Rectangle
     * @param height Height Of Rectangle
     * @return true if the point is inside the rectangle or on its border!
     */
    public static boolean isPointInRectangle(double x, double y, double x1, double y1, double width, double height) {
        return ((x1 <= x) && (x <= (x1 + width)) && (y1 <= y) && (y <= (y1 + height)));
    }


    public static RectangularCoordinate findCoordAroundPerimeterOfCircle(CartesianBounds bounds, double azimuth) {
        RectangularCoordinate coord;
        RectangularCoordinate centre = new RectangularCoordinate(bounds.getDoubleCentreX(), bounds.getDoubleCentreY());
        double d = bounds.getRadiusOfInnerCircle();
        coord = centre.moveTo(new AzimuthCoordinate(azimuth, d));
        return coord;
    }

    public static RectangularCoordinate findCoordAroundPerimeter(double distance, CartesianBounds bounds) {
        double x = bounds.getDoubleX();
        double y = bounds.getDoubleY();
        double w = bounds.getDoubleWidth();
        double h = bounds.getDoubleHeight();
        RectangularCoordinate coord = null;
        if (distance < bounds.getDoubleWidth()) {
            coord = new RectangularCoordinate(x + distance, y);
        } else if (distance < (w + h)) {
            coord = new RectangularCoordinate(x + w, y + (distance - w));
        } else if (distance < (w + h + w)) {
            coord = new RectangularCoordinate((x + w) - (distance - w - h), y + h);
        } else {
            coord = new RectangularCoordinate(x, (y + h) - (distance - w - h - w));
        }
        return coord;
    }

    /**
     * So the radius of the circle enclosing a square is simply given by the
     * old pythagorus - h^2 = a^2 + b^2 (sq of hyp = sum of the squares of the other 2 sides)
     * <p/>
     * The triangle is formed from the centre of the circle out along the bottom of the square and with the hyp being the radius
     * <p/>
     * or the distance between the centre of the square and any of its corners!
     *
     * @param bounds
     * @return
     */
    public static double radiusOfEnclosingCircleDouble(double centreX, double centreY, double x, double y) {
        return distanceBetweenTwoPointsDouble(centreX, centreY, x, y);
    }

    public static double distanceBetweenTwoPointsDouble(double x1, double y1, double x2, double y2) {
        double a = x2 - x1;
        double b = y2 - y1;
        return Math.sqrt((a * a) + (b * b));

    }


    public static double calculateHypotenuse(double opposite, double adjacent) {
        return Math.sqrt((opposite * opposite) + (adjacent * adjacent));
    }

    /**
     * from http://mathworld.wolfram.com/Line-LineIntersection.html
     *
     * @return
     */
    public static RectangularCoordinate findSegmentSegmentIntersection(RectangularCoordinate a1, RectangularCoordinate a2, RectangularCoordinate b1, RectangularCoordinate b2) {
       return  findSegmentSegmentIntersection(a1, a2, b1,b2, true); 
    }
    public static RectangularCoordinate findSegmentSegmentIntersection(RectangularCoordinate a1, RectangularCoordinate a2, RectangularCoordinate b1, RectangularCoordinate b2, boolean includeEndPoints) {
        RectangularCoordinate intersection = null;

        double dxA = a1.getDoubleX() - a2.getDoubleX();
        double dyA = a1.getDoubleY() - a2.getDoubleY();

        double dxB = b1.getDoubleX() - b2.getDoubleX();
        double dyB = b1.getDoubleY() - b2.getDoubleY();

        double detDenomenator = MatrixMath.determinant(dxA, dyA, dxB, dyB);

        if (DoubleMath.equals(detDenomenator, 0)) {
            return null;
        }

        double detA = MatrixMath.determinant(a1.getDoubleX(), a1.getDoubleY(), a2.getDoubleX(), a2.getDoubleY());
        double detB = MatrixMath.determinant(b1.getDoubleX(), b1.getDoubleY(), b2.getDoubleX(), b2.getDoubleY());

        double detNumeratorX = MatrixMath.determinant(detA, dxA, detB, dxB);
        double detNumeratorY = MatrixMath.determinant(detA, dyA, detB, dyB);

        double x = detNumeratorX / detDenomenator;
        double y = detNumeratorY / detDenomenator;

        boolean insegmentA = isPointWithinLineSegmentBounds(x, y, a1, a2, includeEndPoints);
        boolean insegmentB = isPointWithinLineSegmentBounds(x, y, b1, b2, includeEndPoints);

        if (insegmentA && insegmentB) {
            intersection = new RectangularCoordinate(x, y);
        }
        return intersection;

    }

    public static RectangularCoordinate generateUniformRandomCoordinate(Random random, CartesianBounds bounds) {
        double x = (random.nextDouble() * bounds.getDoubleWidth()) + bounds.getDoubleX();
        double y = (random.nextDouble() * bounds.getDoubleHeight()) + bounds.getDoubleY();
        return new RectangularCoordinate(x, y);
    }

    /**
     * if circular is true, it generates ina circle which has diameter of the bounds (i.e. inside it)
     *
     * @param random
     * @param bounds
     * @param circular
     * @return
     */
    public static RectangularCoordinate generateRandomCoordOnPerimeter(Random random, CartesianBounds bounds, boolean circular) {

        RectangularCoordinate coord;
        if (circular) {
            double theta = generateUniformRandomAzimuthChange(random);
            coord = findCoordAroundPerimeterOfCircle(bounds, theta);
        } else {
            double perimeterLength = bounds.getDoublePerimeterLength();
            double distance = random.nextDouble() * perimeterLength;
            coord = findCoordAroundPerimeter(distance, bounds);
        }
        return coord;

    }

    /**
     * generates a simple random angle between 0 and 369.999999
     * We deliberately
     *
     * @return
     */
    public static double generateUniformRandomTheta() {
        return 0; //@todo implement this?:)
    }

    /**
     * @param random
     * @return
     */
    public static BigDecimal generateUniformRandomAngle(Random random) {
        return UniformRandom.generateUniformRandom(BigDecimalMath.accurateIn(359.99999999999999999999), random);
    }

    /**
     * Generates an angle between 0 and 360 degrees - this is an azimuth so 0 represents North.
     * We have tested tha accuracy of our trig functions to 1e-2 for angles. If we want more we might have to loose some
     * <p/>
     * of our accuracy elsewhere,
     *
     * @param random
     * @return
     */
    public static double generateUniformRandomAzimuthChange(Random random) {
        return Math.toDegrees(UniformRandom.generateUniformRandomDouble(Math.PI, -Math.PI, random));

    }


    public boolean circleIntersectsCircle(double x1, double y1, double r1, double x2, double y2, double r2, double precision) {
        double d = distanceBetweenTwoPointsDouble(x1, y1, x2, y2);
        return DoubleMath.precisionLessThanEqual(d, r1 + r2, precision);
    }


    /**
     * The generators generate a change in direction between -180 and 180 or -pi and +pi
     * it is then calculated modulo 360 so it is always within 360...
     * <p/>
     * It also wraps the angle of change so it can never exceed +/-180 however,
     * be warned - this means that it might appear to "swap" directions...
     * <p/>
     * so -190 anticlockwise becomes 170 CLOCKWISE!
     * <p/>
     * Modulus (%) operator doesnt work the same way as it does in R where -80 %% 360 is 280
     * so we have to do it manually...
     *
     * @param currentAzimuth
     * @param thetaChange
     * @return
     */
    public static CourseChange createWrappedCourseChange(double currentAzimuth, double thetaChange) {
        double changedAzimuth = (currentAzimuth + thetaChange) % 360;
        double newAzimuth = (changedAzimuth < 0) ? 360 + changedAzimuth : changedAzimuth;

        double thetaChangeWrapped = wrapThetaChange(thetaChange);

        DirectionOfChange direction = calculateDirectionOfChange(thetaChangeWrapped);
        return new CourseChange(newAzimuth, direction, thetaChangeWrapped);
    }

    private static DirectionOfChange calculateDirectionOfChange(double thetaChangeWrapped) {
        DirectionOfChange direction = DirectionOfChange.NONE;
        if (thetaChangeWrapped > 0) {
            direction = DirectionOfChange.CLOCKWISE;
        } else if (thetaChangeWrapped < 0) {
            direction = DirectionOfChange.ANTI_CLOCKWISE;
        }
        return direction;
    }

    /**
     * Ok so this is quite complex - theres probably a clever mathematical way to do it...
     * it can be visualised by drawing a circle with the arrows going around for each direction.
     *
     * clockwise and anti clockwise can be given "sides" decided by how many 180's fit into it so if you
     * first write down how many 180s you will see
     * <code>
     * ANTICLOCKWISE:
     *
     * LEFT     RIGHT
     * 0        1
     * 2        3
     * 4        5
     *
     * CLOCKWISE:
     *
     * LEFT     RIGHT
     * 1        0
     * 3        2
     * 5        4
     *
     *
     * We call anything which is a modulo of 2, SIDE 2 and the other side is SIDE 1
     *
     * Now we can work out wether the WRAPPED turn is going to be Anticlockwise or clockwise (-ve or +ve)
     * we call this the "sign"
     *
     * when the original direction was anticlockwise, anything on side 2 is -ve
     *
     * when original direction was clockwise, anything on side 1 is -ve
     *
     * Once we know the sign we can take the absolute change (remainder of the input change modulo 180)
     *
     * and if the sign is -ve, do modChange-180 and if +ve to 180-modchange
     *
     * so if modchange is -190, answer is +170
     * if modchange is +190, answer is -170.
     *
     * if 180, answer is 180
     * </code>
     * @param thetaChange
     * @return
     */
    public static double wrapThetaChange(double thetaChange) {
        double wrapped = thetaChange;
        if (Math.abs(thetaChange) > 180) {
            int factor = (int)thetaChange / 180;
            double thetaChangeMod = Math.abs(thetaChange % 180);

            int side = (factor % 2 == 0) ? 2 : 1;
            int sign = 1;
            if (thetaChange > 0 && side == 1) {
                sign = -1;
            } else if (thetaChange < 0 && side == 2) {
                sign = -1;
            }

            wrapped = (sign == 1) ? 180 - thetaChangeMod : thetaChangeMod - 180;
        }

        return wrapped;
    }

    public static CourseChange createCourseChange(double currentAzimuth, double thetaChange) {
        double changedAzimuth = (currentAzimuth + thetaChange) % 360;
        double newAzimuth = (changedAzimuth < 0) ? 360 + changedAzimuth : changedAzimuth;

        DirectionOfChange direction = calculateDirectionOfChange(thetaChange);

        return new CourseChange(newAzimuth, direction, thetaChange);

    }

    /**
     * Can ONLY use this when generating azimuths between 0 and 360 - if generating +/-180
     * use theta change ones
     *
     * @param currentAzimuth
     * @param newAzimuth
     * @return
     */
    public static CourseChange createCourseChangeFromNewAzimuth(double currentAzimuth, double newAzimuth) {
        DirectionOfChange direction = DirectionOfChange.NONE;
        double thetaChange = 0;
        if (newAzimuth < 180) {
            direction = DirectionOfChange.CLOCKWISE;
            thetaChange = newAzimuth;
        } else if (newAzimuth >= 180) {
            direction = DirectionOfChange.ANTI_CLOCKWISE;
            thetaChange = newAzimuth - 360;
        }

        double changedAzimuth = (currentAzimuth + thetaChange) % 360;
        return new CourseChange(changedAzimuth, direction, thetaChange);
    }

    public static double modulo360radians(double xRadians) {
        return xRadians % TWO_PI;
    }


    public static final double TWO_PI = Math.PI * 2;
}
