/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import com.ixcode.framework.math.DoubleMath;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Description : Represents a cartesian, rectangular coordinate (i.e. has an x and a y)
 */
public class RectangularCoordinate implements Serializable {


    public RectangularCoordinate(double x, double y) {
        _doubleX = x;
        _doubleY = y;
    }


    public final double getDoubleX() {
        return _doubleX;
    }

    public final double getDoubleY() {
        return _doubleY;
    }

    public RectangularCoordinate findClosestCoordinate(List others) {
        double currentMin = Double.MAX_VALUE;
        RectangularCoordinate min = null;

        for (Iterator itr = others.iterator(); itr.hasNext();) {
            RectangularCoordinate coordinate = (RectangularCoordinate)itr.next();
            double d = calculateDistanceTo(coordinate);
            if (d < currentMin) {
                min = coordinate;
            }
        }
        return min;

    }

    public RectangularCoordinate moveBy(double xIncr, double yIncr) {
        return new RectangularCoordinate(_doubleX + xIncr, _doubleY + yIncr);
    }

    public RectangularCoordinate moveTo(AzimuthCoordinate coordinate) {
        return moveTo(coordinate.asPolar());
    }

    public RectangularCoordinate moveTo(PolarCoordinate coordinate) {
        double thetaRadians = Math.toRadians(coordinate.getAngle());
        double xIncr = Math.cos(thetaRadians) * coordinate.getDistance();
        double yIncr = Math.sin(thetaRadians) * coordinate.getDistance();

        return new RectangularCoordinate(_doubleX + xIncr, _doubleY + yIncr);
    }


    /**
     * @param rectangularCoordinate
     * @return
     */
    public double calculateAzimuthTo(RectangularCoordinate rectangularCoordinate) {
        double xIncr = rectangularCoordinate.getDoubleX() - _doubleX;
        double yIncr = rectangularCoordinate.getDoubleY() - _doubleY;

        double phi = calculatePolarAngleTo(xIncr, yIncr);
        return AzimuthCoordinate.convertPolarToAzimuthAngle(phi);

    }

    public AzimuthCoordinate calculateAzimuthCoordinateTo(double newX, double newY) {
        return calculateAzimuthCoordinateTo(new RectangularCoordinate(newX, newY));
    }

    public AzimuthCoordinate calculateAzimuthCoordinateTo(RectangularCoordinate rectangularCoordinate) {
        return new AzimuthCoordinate(calculatePolarCoordinateTo(rectangularCoordinate));
    }


    public PolarCoordinate calculatePolarCoordinateTo(RectangularCoordinate rectangularCoordinate) {
        double xIncr = rectangularCoordinate.getDoubleX() - _doubleX;
        double yIncr = rectangularCoordinate.getDoubleY() - _doubleY;

        double phi = calculatePolarAngleTo(xIncr, yIncr);

        double distance = Geometry.calculateHypotenuse(xIncr, yIncr);

        return new PolarCoordinate(phi, distance);


    }

    /**
     * #From Batschelet, E.  1981.  Circular Statistics In Biology. Academic Press, San Fransisco.
     * #P.240
     * #{Batschelet, 1981 #8052}
     * # Except it doesnt seem to produce the right angle when you go into fourth quadrant (x>0 && y<0) so i
     * # added 360
     *
     * @param x
     * @param y
     * @return
     */
    private static double calculatePolarAngleTo(double x, double y) {
        double phi;
        if (x > 0 && y < 0) {
            phi = 360 + Math.toDegrees(Math.atan(y / x));
        } else if (x > 0) {
            phi = Math.toDegrees(Math.atan(y / x));
        } else if (x < 0) {
            phi = 180 + Math.toDegrees(Math.atan(y / x));
        } else if (x == 0 && y > 0) {
            phi = PHI_90;
        } else if (x == 0 && y < 0) {
            phi = PHI_270;
        } else if (x == 0 && y == 0) {
            phi = NAN;
        } else {
            throw new IllegalStateException("Unexpected state of x and y in calculatePolarAngleTo - " + x + ", " + y);
        }
        return phi;
    }


    public double calculateDistanceTo(RectangularCoordinate coord) {
        return calculateDistanceTo(coord.getDoubleX(), coord.getDoubleY());
    }

    public double calculateDistanceTo(double x, double y) {
        double xIncr = x - _doubleX;
        double yIncr = y - _doubleY;
        return Geometry.calculateHypotenuse(xIncr, yIncr);
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final RectangularCoordinate that = (RectangularCoordinate)o;

        if (!DoubleMath.precisionEquals(that._doubleX, _doubleX, _precision)) return false;
        if (!DoubleMath.precisionEquals(that._doubleY, _doubleY, _precision)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        long temp;
        temp = _doubleX != +0.0d ? Double.doubleToLongBits(_doubleX) : 0L;
        result = (int)(temp ^ (temp >>> 32));
        temp = _doubleY != +0.0d ? Double.doubleToLongBits(_doubleY) : 0L;
        result = 29 * result + (int)(temp ^ (temp >>> 32));
        return result;
    }

    public String toString() {
        return _format.format(this);
    }

    public double getChangeInX(RectangularCoordinate other) {
        return _doubleX - other.getDoubleX();
    }

    public double getChangeInY(RectangularCoordinate other) {
        return _doubleY - other.getDoubleY();
    }


    final double _doubleX;
    final double _doubleY;
    private static final DecimalFormat F3 = DoubleMath.DECIMAL_FORMAT;

    private static final double PHI_90 = 90d;
    private static final double PHI_270 = 270d;
    private static final double NAN = Double.NaN;

    private final double _precision = DoubleMath.DOUBLE_PRECISION_DELTA;

    private RectangularCoordinateFormat _format = new RectangularCoordinateFormat();
}
