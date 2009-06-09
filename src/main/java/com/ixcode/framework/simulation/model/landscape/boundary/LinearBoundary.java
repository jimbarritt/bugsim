/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.boundary;

import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LinearBoundary extends BoundaryBase {


    public LinearBoundary(RectangularCoordinate startLocation, RectangularCoordinate endLocation) {
        super(createLinearBounds(startLocation, endLocation), BoundaryShape.LINEAR);
        _endLocation = endLocation;
        _startLocation = startLocation;
        _endLocation = endLocation;
    }



    private static CartesianBounds createLinearBounds(RectangularCoordinate startLocation, RectangularCoordinate endLocation) {
        return new CartesianBounds(startLocation.getDoubleX(), startLocation.getDoubleY(), endLocation.getDoubleX() - startLocation.getDoubleX(), endLocation.getDoubleY() - startLocation.getDoubleY());
    }

    public RectangularCoordinate getStartLocation() {
        return _startLocation;
    }

    public RectangularCoordinate getEndLocation() {
        return _endLocation;
    }

    /**
     * @param coord
     * @return
     * @todo need to calculate it if its not just horizontal - is point on line
     */
    public boolean isInside(RectangularCoordinate coord) {
        boolean yEquals = DoubleMath.precisionEquals(coord.getDoubleY(), _startLocation.getDoubleY(), DoubleMath.DOUBLE_PRECISION_DELTA);
        boolean xBetween = DoubleMath.precisionBetweenInclusive(_startLocation.getDoubleX(), coord.getDoubleX(), _endLocation.getDoubleX(), DoubleMath.DOUBLE_PRECISION_DELTA);
        return yEquals && xBetween;
    }

    public boolean isOnEdge(RectangularCoordinate coordinate) {
        return isInside(coordinate);
    }

    public boolean isOutside(RectangularCoordinate coordinate) {
        return !isInside(coordinate);
    }

    public BoundaryShape getShape() {
        return BoundaryShape.LINEAR;
    }


    private RectangularCoordinate _startLocation;
    private RectangularCoordinate _endLocation;

}
