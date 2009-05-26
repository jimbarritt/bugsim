/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration;

import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedEggsAdultMortalityStrategy;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class TimedImmigrationStrategyDefinition extends ImmigrationStrategyBase {

    public TimedImmigrationStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public TimedImmigrationStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createStrategyS() {
        return new StrategyDefinitionParameter(TimedImmigrationStrategyDefinition.STRATEGY_NAME, LimitedEggsAdultMortalityStrategy.class.getName());
    }

    public static final String STRATEGY_NAME = "timed";




}
