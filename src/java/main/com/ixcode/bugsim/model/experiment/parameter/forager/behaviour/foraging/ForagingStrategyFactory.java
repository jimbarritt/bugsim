/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.foraging;

import com.ixcode.bugsim.model.agent.butterfly.EggLayingForagingStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 1:30:20 PM by jim
 */
public class ForagingStrategyFactory {

    public static StrategyRegistry getRegistry() {
        return REGISTRY;
    }

    public static ForagingStrategyBase createForagingStrategy(StrategyDefinitionParameter foragingS, ParameterMap parameterMap, boolean forwardEvents) {
        return (ForagingStrategyBase)getRegistry().constructStrategy(foragingS,  parameterMap, forwardEvents);
    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {
        REGISTRY.registerStrategy(EggLayingForagingStrategyDefinition.class, EggLayingForagingStrategy.class, EggLayingForagingStrategyDefinition.P_STRATEGY_NAME, "Egg Laying");
    }
}
