/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import java.beans.PropertyChangeListener;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Feb 1, 2007 @ 4:55:22 PM by jim
 */
public interface IStrategyDefinitionContainer {

    StrategyDefinition getStrategyDefinition(String parameterName);

    void replaceStrategyDefinition(String parameterName, StrategyDefinition strategyDefinition);


    void addStrategyDefinitionContainerListener(IStrategyDefinitionContainerListener listener);

    void removeStrategyDefinitionContainerListener(IStrategyDefinitionContainerListener listener);

    ParameterMap getParameterMap();

    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
