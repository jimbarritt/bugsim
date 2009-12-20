/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.mortality;

import com.ixcode.bugsim.agent.butterfly.mortality.LimitedAgeAndEggsAdultMortalityStrategy;
import com.ixcode.bugsim.agent.butterfly.mortality.LimitedAgeAdultMortalityStrategy;
import com.ixcode.bugsim.agent.butterfly.mortality.LimitedEggsAdultMortalityStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.parameter.model.StrategyDefinition;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 4, 2007 @ 9:03:54 PM by jim
 */

public class AdultMortalityStrategyFactory {


    public static StrategyRegistry getRegistry() {
        return REGISTRY;
    }

    public static AdultMortalityStrategyBase createAdultMortalityStrategy(StrategyDefinitionParameter strategyS, ParameterMap parameterMap, boolean forwardEvents) {

        return (AdultMortalityStrategyBase)getRegistry().constructStrategy(strategyS, parameterMap, forwardEvents);

    }

    public static StrategyDefinition createDefaultAdultMortalityStrategy(String className, ParameterMap parameterMap, boolean isForwardEvents) {
        return getRegistry().createDefaultStrategy(className, parameterMap);
    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {

        REGISTRY.registerStrategy(LimitedEggsAdultMortalityStrategyDefinition.class, LimitedEggsAdultMortalityStrategy.class, LimitedEggsAdultMortalityStrategyDefinition.S_LIMITED_EGGS, "Limited Eggs");
        REGISTRY.registerStrategy(LimitedAgeAdultMortalityStrategyDefinition.class, LimitedAgeAdultMortalityStrategy.class, LimitedAgeAdultMortalityStrategyDefinition.S_LIMITED_AGE, "Limited Age");
        REGISTRY.registerStrategy(LimitedAgeAndEggsAdultMortalityStrategyDefinition.class, LimitedAgeAndEggsAdultMortalityStrategy.class, LimitedAgeAndEggsAdultMortalityStrategyDefinition.S_LIMITED_AGE_AND_EGGS, "Limited Age And Eggs");

    }
}
