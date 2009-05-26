/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import java.util.Stack;

/**
 *  Description : Fired when a parameter value changes
 *  Created     : Jan 29, 2007 @ 1:39:56 PM by jim
 */
public class ParameterValueChangedEvent extends ParameterEvent {

    public ParameterValueChangedEvent(Parameter source, Object oldValue, Object newValue, Stack parameterPath) {
        super(source, parameterPath);
        _oldValue = oldValue;
        _newValue = newValue;        
    }

    public Object getOldValue() {
        return _oldValue;
    }

    public Object getNewValue() {
        return _newValue;
    }



    private Object _oldValue;
    private Object _newValue;

}
