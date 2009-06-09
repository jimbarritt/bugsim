/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.scale;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class Distance {

    
    private double _value;
    private IDistanceUnit _units;

    public Distance(double value, IDistanceUnit units) {
        _value = value;
        _units = units;
    }

    public double getValue() {
        return _value;
    }

    public IDistanceUnit getUnits() {
        return _units;
    }
}
