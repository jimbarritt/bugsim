/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

/**
 * Description : Rather than use property change listener we use this one =PCL can get a bit confused with swing.
 * Created     : Jan 28, 2007 @ 11:16:56 PM by jim
 */
public interface IParameterListener {

    /**
     * Fired when the value of a parameter changes
     * @param event
     */
    void parameterValueChanged(ParameterValueChangedEvent event);


    /**
     * Fired when a parameter is added to the parameter map - more explicitly : when one it is added to a container whose parent is connected to the map.
     * parameter containers will fire this event for all their children aswell....
     * @param evt
     */
    void parameterConnected(ParameterEvent evt);

    /**
     * Fired when a parameter is removed from the parameter map
     * @param event
     */
    void parameterDisconnected(ParameterDisconnectedEvent event);


    /**
     * Called when we have changed or replaced a parameter or a StrategyDefinition....
     *
     * You can be guaranteed that the Parameter map will now be in the *NEW* i.e. modified condition.
     * The source parameter may not be the one you were listening to as a top level parameter will cause all of
     * its childrens' observers to have this event fired aswell.
     * @param source
     * @param oldValue
     * @param newValue
     */
    void parameterRebound(Parameter source, Parameter oldValue, Parameter newValue);
}
