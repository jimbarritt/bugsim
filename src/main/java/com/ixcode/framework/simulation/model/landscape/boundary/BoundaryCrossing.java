/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.boundary;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BoundaryCrossing {
    public BoundaryCrossing(CartesianBounds boundary, boolean isCircular,RectangularCoordinate startCoord,
                            RectangularCoordinate endCoord,  RectangularCoordinate coordinate, double heading,
                            double distance, BoundaryCrossingType type, double I, double r, double q) {
        _boundary = boundary;
        _intersection = coordinate;
        _heading = heading;
        _distance = distance;
        _isCircular = isCircular;
        _crossingType = type;
        _startCoord = startCoord;
        _endCoord = endCoord;
        _I = I;
        _r = r;
        _q = q;
    }


    public CartesianBounds getBoundaryBounds() {
        return _boundary;
    }

    public RectangularCoordinate getIntersection() {
        return _intersection;
    }

    public double getHeading() {
        return _heading;
    }

    public double getDistance() {
        return _distance;
    }

    public boolean isCircular() {
        return _isCircular;
    }

    public BoundaryCrossingType getCrossingType() {
        return _crossingType;
    }


    public RectangularCoordinate getStartCoord() {
        return _startCoord;
    }

    public RectangularCoordinate getEndCoord() {
        return _endCoord;
    }

    public double getI() {
        return _I;
    }

    public double getR() {
        return _r;
    }

    public double getQ() {
        return _q;
    }

    public String toString() {
        return "_bounds=" + _boundary + ", start=" + _startCoord + ", end=" + _endCoord + ", crossedAt=" + _intersection + ", _heading=" + _heading +", distance=" + _distance + ", l=" + _I + ", r=" + _r + ", q=" + _q;
    }

    /**
     * Returns the euclidean distance from the boundary - works it out by calculating directly to the centre then taking off the radius
     * @return
     */
    public double getDistanceToBoundary() {
        return getDistanceToCenter() - _boundary.getRadiusOfInnerCircle();
    }

    public double getDistanceToCenter() {
        return _startCoord.calculateDistanceTo(_boundary.getCentre());
    }

    public double getEndDistanceToBoundary() {
        return _boundary.getRadiusOfInnerCircle() - getEndDistanceToCenter();
    }

    public double getEndDistanceToCenter() {
        return _endCoord.calculateDistanceTo(_boundary.getCentre());
    }


    private CartesianBounds _boundary;
    private RectangularCoordinate _intersection;
    private double _heading;
    private double _distance;
    private boolean _isCircular;
    private BoundaryCrossingType _crossingType;
    private RectangularCoordinate _startCoord;
    private RectangularCoordinate _endCoord;
    private double _I; // The heading of the centre of the boundary to the intersection (i.e. where it is around the boundary
    private double _r; // the radius of the boundary if its a circle (half the width)
    private double _q; // the distance from the centre to the intersection if its a circle this should always be the radius!!
}
