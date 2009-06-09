/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.scale;

import com.ixcode.framework.math.geometry.RectangularCoordinate;

/**
 *  Description : Adds units to a cartesian coordinate
 */
public class ScaledCartesianCoordinate extends RectangularCoordinate {


    /**
     * @todo need to implemtn scale properly - might not have a distance of 1!!!
     * @param coord
     * @param scale
     */
    public ScaledCartesianCoordinate(RectangularCoordinate coord, ScaledDistance scale) {
        this(coord.getDoubleX(), coord.getDoubleY(), scale.getUnits());
    }

    public ScaledCartesianCoordinate(double x, double y, IDistanceUnit units) {
        super(x, y);
        _units = units;
    }

    public IDistanceUnit getUnits() {
        return _units;
    }

    public RectangularCoordinate scaleCoordinate(ScaledDistance scale) {
        ScaledDistance xdist = new ScaledDistance(super.getDoubleX(), _units);
        ScaledDistance ydist = new ScaledDistance(super.getDoubleY(), _units);

        double x = xdist.scaleTo(scale);
        double  y = ydist.scaleTo(scale);
        return new RectangularCoordinate(x, y);

    }

    private IDistanceUnit _units;
}
