/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.mortality;

import com.ixcode.bugsim.agent.butterfly.mortality.LimitedEggsAdultMortalityStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class LimitedEggsAdultMortalityStrategyDefinition extends AdultMortalityStrategyBase {

    public LimitedEggsAdultMortalityStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public LimitedEggsAdultMortalityStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createDefaultStrategyS() {
       return createStrategyS();
    }
    public static StrategyDefinitionParameter createStrategyS() {
        return new StrategyDefinitionParameter(LimitedEggsAdultMortalityStrategyDefinition.S_LIMITED_EGGS, LimitedEggsAdultMortalityStrategy.class.getName());
    }

    public static final String S_LIMITED_EGGS = "limitedEggs";




}
