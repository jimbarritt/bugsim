/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality;

import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedAgeAndEggsAdultMortalityStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class LimitedAgeAndEggsAdultMortalityStrategyDefinition extends LimitedAgeAdultMortalityStrategyDefinition {

    public LimitedAgeAndEggsAdultMortalityStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public LimitedAgeAndEggsAdultMortalityStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return createStrategyS(100);
    }
    
    public static StrategyDefinitionParameter createStrategyS(long maxAge) {
        StrategyDefinitionParameter limitedAgeS = new StrategyDefinitionParameter(S_LIMITED_AGE_AND_EGGS, LimitedAgeAndEggsAdultMortalityStrategy.class.getName());

        LimitedAgeAdultMortalityStrategyDefinition.addParameters(limitedAgeS, maxAge);

        return limitedAgeS;
    }

    public static final String S_LIMITED_AGE_AND_EGGS = "limitedAgeAndEggs";


}
