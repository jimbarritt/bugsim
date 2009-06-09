/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import java.text.DecimalFormat;

/**
 * Azimuth is an angle measured from 0Degrees being North and clockwise.
 *  Description : A coordinate represented by an azimuth and a distance from the orgin.
 *
 * The final keyword is used here to preserve immutability. It doesnt stop you creating a subclass but does stop you messing with whats in the base class.
 */
public class AzimuthCoordinate {

    /**
     * This is like this so that this class depends on the polar class but not the other way round
     * @param polarCoordinate
     */
    public AzimuthCoordinate(PolarCoordinate polarCoordinate) {
        _theta = convertPolarToAzimuthAngle(polarCoordinate.getAngle());
        _r = polarCoordinate.getDistance();
    }




    public AzimuthCoordinate(double azimuth, double distance) {
        _theta = azimuth;
        _r = distance;
    }

    public final double getAzimuth() {
        return _theta;
    }

    public final double getDistance() {
        return _r;
    }

    public final PolarCoordinate asPolar() {
        return new PolarCoordinate(convertAzimuthToPolarAngle(_theta), _r);
    }


    public static double convertAzimuthToPolarAngle(double azimuth) {
        return restrictAngle360((360+90)-azimuth);
    }

    public static double convertPolarToAzimuthAngle(double polarAngle) {
        return restrictAngle360((360-polarAngle)+90);
    }

    /**
     * Finds the opposite heading to this one
     *
     * @param heading
     * @return
     */
    public static double getReverseAzimuth(double heading) {
        return (heading + 180 > 360) ? (heading + 180) - 360 : heading + 180;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AzimuthCoordinate that = (AzimuthCoordinate)o;

        if (Double.compare(that._r, _r) != 0) return false;
        if (Double.compare(that._theta, _theta) != 0) return false;

        return true;
    }

    public int hashCode() {
        int result;
        long temp;
        temp = _theta != +0.0d ? Double.doubleToLongBits(_theta) : 0L;
        result = (int)(temp ^ (temp >>> 32));
        temp = _r != +0.0d ? Double.doubleToLongBits(_r) : 0L;
        result = 29 * result + (int)(temp ^ (temp >>> 32));
        return result;
    }

    public static double applyAzimuthChange(double currentHeading, double thetaChange) {
        double newHeading = (currentHeading + thetaChange) % 360;
        if (newHeading > 360) {
            newHeading = newHeading - 360;
        } else if (newHeading < 0) {
            newHeading = newHeading + 360;
        }
        return newHeading;

    }

    public static double restrictAngle360(double theta) {
        return theta % 360;
    }

    public double angularDistanceFrom(double thetaFrom) {
        return calculateAngularDistanceFrom(thetaFrom, _theta);
    }

    public static double calculateAngularDistanceFrom(double thetaFrom, double thetaTo) {
        double angularDistance;
        if (thetaTo > thetaFrom) {
            angularDistance = (thetaTo - thetaFrom);
        } else if (thetaTo < thetaFrom)  {
            angularDistance = (360 - thetaFrom) + thetaTo;
        } else {
            angularDistance = 0;
        }
        return angularDistance;
    }

    public String toString() {
        return "theta=" + F2.format(_theta) + ", r=" + F2.format(_r);
    }

    private static final DecimalFormat F2 = new DecimalFormat("0.00");
    private final double _theta;
    private final double _r;
}
