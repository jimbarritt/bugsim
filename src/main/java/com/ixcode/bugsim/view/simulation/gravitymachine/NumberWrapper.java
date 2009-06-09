/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class NumberWrapper extends Number {


    public NumberWrapper(double doubleValue) {
        this(new Double(doubleValue));
    }
    
    public NumberWrapper(Double doubleValue) {
        _doubleValue = doubleValue;
    }

    public int intValue() {
        return _doubleValue.intValue();
    }

    public long longValue() {
        return _doubleValue.longValue();
    }

    public float floatValue() {
        return _doubleValue.floatValue();
    }

    public double doubleValue() {
        return _doubleValue.doubleValue();
    }

    private Double _doubleValue;
}
