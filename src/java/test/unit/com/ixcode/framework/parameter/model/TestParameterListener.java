/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 29, 2007 @ 2:57:24 PM by jim
 */
public class TestParameterListener implements IParameterListener, IParameterContainerListener {


    public void parameterDisconnected(ParameterDisconnectedEvent event) {
        _paramRemoved = true;
        _events.add(event);
    }

    public void parameterConnected(ParameterEvent evt) {
        _paramConnected = true;
        _events.add(evt);
    }

    public void parameterValueChanged(ParameterValueChangedEvent event) {
        _paramValueChanged = true;
        _events.add(event);
    }

    public void parameterRebound(Parameter source, Parameter oldValue, Parameter newValue) {
        _rebind = true;
        _events.add(source);

    }

    public void parameterReplaced(IParameterContainer source, Parameter oldP, Parameter newP) {
        _paramReplaced = true;
        _paramNames.add(source.toString());
    }

    public void reset() {
        _paramValueChanged = false;
        _paramReplaced = false;
        _events = new ArrayList();
    }

    public boolean isParamValueChanged() {
        return _paramValueChanged;
    }

    public boolean isParamReplaced() {
        return _paramReplaced;
    }

    public boolean isParamRemoved() {
        return _paramRemoved;
    }


    public List getParamNames() {
        return _paramNames;
    }

    public List getEvents() {
        return _events;
    }

    public ParameterValueChangedEvent getEvent(int index) {
        return (ParameterValueChangedEvent)_events.get(index);
    }


    public boolean isParamConnected() {
        return _paramConnected;
    }

    private boolean _paramValueChanged;
    private boolean _paramReplaced;
    private List _events = new ArrayList();
    private List _paramNames = new ArrayList();


    private boolean _paramRemoved;
    private boolean _paramConnected;
    private boolean _rebind;
}
