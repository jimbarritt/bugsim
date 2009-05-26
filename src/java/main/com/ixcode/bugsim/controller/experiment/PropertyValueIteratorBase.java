/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

/**
 *  Description : ${CLASS_DESCRIPTION}
 * @deprecated 
 */
public abstract class PropertyValueIteratorBase implements ILoopPropertyValueIterator {

    public PropertyValueIteratorBase(String propertyName, Object startValue, Object maxValue, Object increment) {
        _startValue = startValue;
        _propertyName = propertyName;
        _startValue = startValue;
        _maxValue = maxValue;
        _incrementValue = increment;
    }


    public Object getStartValue() {
        return _startValue;
    }

    public String getPropertyName() {
        return _propertyName;
    }

    public Object getMaxValue() {
        return _maxValue;
    }

    public Object getIncrementValue() {
        return _incrementValue;
    }

    private String _propertyName;
    private Object _startValue;
    private Object _maxValue;
    private Object _incrementValue;


}
