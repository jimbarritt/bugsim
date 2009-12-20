/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern;

import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.bugsim.agent.butterfly.immigration.pattern.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 4, 2007 @ 9:03:54 PM by jim
 */

public class ImmigrationPatternStrategyFactory {


    public static StrategyRegistry getRegistry() {
        return ImmigrationPatternStrategyFactory.REGISTRY;
    }

    public static ImmigrationPatternStrategyBase createImmigrationPatternStrategy(StrategyDefinitionParameter strategyS, ParameterMap parameterMap, boolean forwardEvents) {

        return (ImmigrationPatternStrategyBase)getRegistry().constructStrategy(strategyS, parameterMap, forwardEvents);

    }

    public static StrategyDefinition createDefaultImmigrationPatternStrategy(String className, ParameterMap parameterMap, boolean isForwardEvents) {
        return getRegistry().createDefaultStrategy(className, parameterMap);
    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {

//        REGISTRY.registerStrategy(PredefinedReleaseImmigrationStrategyDefinition.class, PredefinedReleaseImmigrationPattern.class, PredefinedReleaseImmigrationStrategyDefinition.STRATEGY_NAME, "Predefined Release");
        REGISTRY.registerStrategy(RandomReleaseImmigrationPatternStrategyDefinition.class, RandomReleaseImmigrationPattern.class, RandomReleaseImmigrationPatternStrategyDefinition.STRATEGY_NAME, "Random Border");
//        REGISTRY.registerStrategy(PredefinedEggsImmigrationStrategyDefinition.class, PredefinedEggsImmigrationPattern.class, PredefinedEggsImmigrationStrategyDefinition.STRATEGY_NAME, "Predefined Eggs");
        REGISTRY.registerStrategy(FixedLocationImmigrationStrategyDefinition.class, FixedLocationReleaseImmigrationPattern.class, FixedLocationImmigrationStrategyDefinition.STRATEGY_NAME, "Fixed Location");
        REGISTRY.registerStrategy(RandomPointReleaseImmigrationStrategyDefinition.class, RandomPointReleaseImmigrationPattern.class, RandomPointReleaseImmigrationStrategyDefinition.STRATEGY_NAME, "Random Point");


    }
}
