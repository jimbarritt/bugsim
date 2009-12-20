/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality;

import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.simulation.model.agent.physical.DirectLarvalMortalityStrategy;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 4, 2007 @ 9:03:54 PM by jim
 */

public class LarvalMortalityStrategyFactory {


    public static StrategyRegistry getRegistry() {
        return LarvalMortalityStrategyFactory.REGISTRY;
    }

    public static LarvalMortalityStrategyBase createLarvalMortalityStrategy(StrategyDefinitionParameter strategyS, ParameterMap parameterMap, boolean forwardEvents) {

        return (LarvalMortalityStrategyBase)LarvalMortalityStrategyFactory.getRegistry().constructStrategy(strategyS, parameterMap, forwardEvents);

    }

    public static StrategyDefinition createDefaultLarvalMortalityStrategy(String className, ParameterMap parameterMap,  boolean isForwardEvents) {
        return getRegistry().createDefaultStrategy(className, parameterMap);
    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {

        REGISTRY.registerStrategy(DirectLarvalMortalityStrategyDefinition.class, DirectLarvalMortalityStrategy.class, DirectLarvalMortalityStrategyDefinition.STRATEGY_NAME, "Direct Mortality");


    }
}
