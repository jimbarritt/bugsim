/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.immigration;

import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.bugsim.agent.butterfly.immigration.InitialImmigrationStrategy;
import com.ixcode.bugsim.agent.butterfly.immigration.ScheduledImmigrationStrategy;
import com.ixcode.bugsim.agent.butterfly.immigration.TimedImmigrationStrategy;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 4, 2007 @ 9:03:54 PM by jim
 */

public class ImmigrationStrategyFactory {


    public static StrategyRegistry getRegistry() {
        return ImmigrationStrategyFactory.REGISTRY;
    }

    public static ImmigrationStrategyBase createImmigrationStrategy(StrategyDefinitionParameter strategyS, ParameterMap parameterMap, boolean forwardEvents) {

        return (ImmigrationStrategyBase)getRegistry().constructStrategy(strategyS, parameterMap, forwardEvents);

    }

    public static StrategyDefinition createDefaultImmigrationStrategy(String className, ParameterMap parameterMap, boolean isForwardEvents) {
        return getRegistry().createDefaultStrategy(className, parameterMap);
    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {

        REGISTRY.registerStrategy(InitialImmigrationStrategyDefinition.class, InitialImmigrationStrategy.class, InitialImmigrationStrategyDefinition.STRATEGY_NAME, "Initial Immigration");
        REGISTRY.registerStrategy(ScheduledImmigrationStrategyDefinition.class, ScheduledImmigrationStrategy.class, ScheduledImmigrationStrategyDefinition.STRATEGY_NAME, "Scheduled Immigration");
        REGISTRY.registerStrategy(TimedImmigrationStrategyDefinition.class, TimedImmigrationStrategy.class, TimedImmigrationStrategyDefinition.STRATEGY_NAME, "Timed Immigration");

    }
}
