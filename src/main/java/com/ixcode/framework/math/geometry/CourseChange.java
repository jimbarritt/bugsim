/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

/**
 * Description : Just knowing the old heading and the new heading is not enough - we need to know
 * wether it changed clockwise or anticlockwise.
 */
public class CourseChange {

    public CourseChange(double newAzimuth, DirectionOfChange directionOfChange, double angleOfChange) {
        _newAzimuth = newAzimuth;
        _directionOfChange = directionOfChange;
        _angleOfChange = angleOfChange;
    }

    public double getNewAzimuth() {
        return _newAzimuth;
    }

    public DirectionOfChange getDirectionOfChange() {
        return _directionOfChange;
    }

    public double getAngleOfChange() {
        return _angleOfChange;
    }

    public String toString() {
        return "CourseChange (newHeading:" + _newAzimuth + ", directionOfChange:" + _directionOfChange +  ", angleOfChange:" + _angleOfChange;
    }

    private double _newAzimuth;
    private DirectionOfChange _directionOfChange;
    private double _angleOfChange;
}
