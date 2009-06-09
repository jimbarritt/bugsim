/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Feb 1, 2007 @ 4:57:23 PM by jim
 */
public interface IStrategyDefinitionContainerListener {

    void strategyDefinitionReplaced(IStrategyDefinitionContainer source, String parameterName, StrategyDefinition oldStrategy, StrategyDefinition newStrategy);

}
