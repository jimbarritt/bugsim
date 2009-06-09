/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information;

import com.ixcode.framework.math.geometry.RectangularCoordinate;

/**
 *  Description : The results from surveying a single location
 */
public class SignalSample {

    public SignalSample(RectangularCoordinate coordinate, double surveyValue) {
        this(coordinate, new Double(surveyValue));
    }

    public SignalSample(RectangularCoordinate coordinate, Double informationValue) {
        _coordinate = coordinate;
        _signalValue = informationValue;
    }

    public RectangularCoordinate getCoordinate() {
        return _coordinate;
    }

    public Object getSignalValue() {
        return _signalValue;
    }

    public double getDoubleSignalValue() {
        return ((Double)_signalValue).doubleValue();
    }

    public String toString() {
        return "Signal Sample : " + _signalValue + ", From " + _coordinate;
    }

    private RectangularCoordinate _coordinate;
    private Object _signalValue;

}
