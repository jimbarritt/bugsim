/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape;

import com.ixcode.framework.math.geometry.RectangularCoordinate;

import java.io.Serializable;

/**
 * Description : ${CLASS_DESCRIPTION}
 *
 * @todo could think about making this derived from coordinate
 * @todo or  just removing it completely!!
 */
public class Location implements Serializable {

    public Location(double x, double y) {
        this(new RectangularCoordinate(x, y));
    }

    public Location(RectangularCoordinate coordinate) {
        _coordinate = coordinate;
    }

    public RectangularCoordinate getCoordinate() {
        return _coordinate;
    }

    public double getDoubleX() {
        return _coordinate.getDoubleX();
    }

    public double getDoubleY() {
        return _coordinate.getDoubleY();
    }


    public String toString() {
        return _coordinate.toString();
    }

    private RectangularCoordinate _coordinate;
}
