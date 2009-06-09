/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class Intersection {

    public Intersection() {
    }

    public Intersection(RectangularCoordinate intersection) {
        _intersection = intersection;
    }

    public boolean intersects() {
        return _intersection != null;
    }

    public RectangularCoordinate getCoordinate() {
        return _intersection;
    }

    /**
     * The distance along the line where the intersection is.
     * @return
     */
    public double getDistance() {
        return _distance;
    }

    private RectangularCoordinate _intersection;
    private double _distance;

}
