/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class PolarCoordinate {


    public PolarCoordinate(double phi, double r) {
        _phi = phi;
        _r = r;
    }

    public final double getDistance() {
        return _r;
    }

    /**
     *
     * @return the angle as measured from the positive x axis being 0, anti clockwise
     */
    public final double getAngle() {
        return _phi;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PolarCoordinate that = (PolarCoordinate)o;

        if (Double.compare(that._phi, _phi) != 0) return false;
        if (Double.compare(that._r, _r) != 0) return false;

        return true;
    }

    public int hashCode() {
        int result;
        long temp;
        temp = _phi != +0.0d ? Double.doubleToLongBits(_phi) : 0L;
        result = (int)(temp ^ (temp >>> 32));
        temp = _r != +0.0d ? Double.doubleToLongBits(_r) : 0L;
        result = 29 * result + (int)(temp ^ (temp >>> 32));
        return result;
    }


    private final double _phi;
    private final double _r;



}
