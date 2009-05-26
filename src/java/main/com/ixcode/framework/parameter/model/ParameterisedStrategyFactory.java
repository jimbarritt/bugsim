/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

import com.ixcode.bugsim.BugsimMain;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ParameterisedStrategyFactory {


    public static IParameterisedStrategy createParameterisedStrategy(StrategyDefinitionParameter strategyParam, ParameterMap parameters, Map initialisationObjects) {
        try {
            String className = strategyParam.getImplementingClassName();
            IParameterisedStrategy strategy = (IParameterisedStrategy)BugsimMain.instantiateClass(className);

            strategy.initialise(strategyParam, parameters, initialisationObjects);
            return strategy;
        } catch (Throwable t) {
            throw new RuntimeException(t); //@todo sort this out! maybe error handler passed in?
        }
    }
}
