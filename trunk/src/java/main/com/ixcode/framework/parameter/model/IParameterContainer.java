/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import java.beans.PropertyChangeListener;

/**
 * Description : Represents something that can contain parameters...
 * Created     : Jan 27, 2007 @ 10:58:51 PM by jim
 */
public interface IParameterContainer {


    /**
     * @todo remove this from here! then can remove it from SDP
     * @param parameterName
     * @param listener
     */
    void addPropertyChangeListener(String parameterName, PropertyChangeListener listener);



    Object getParameterValue(String parameterName);

    void setParameterValue(String parameterName, Object value);

    Parameter getParameter(String parameterName);

    void addParameterContainerListener(IParameterContainerListener listener);

    void fireParameterReplacedEvent(Parameter oldP, Parameter newP);

    void removeParameterContainerListener(IParameterContainerListener listener);
}
