/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.immigration;

import com.ixcode.bugsim.agent.butterfly.mortality.LimitedEggsAdultMortalityStrategy;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class ScheduledImmigrationStrategyDefinition extends ImmigrationStrategyBase {

    public ScheduledImmigrationStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public ScheduledImmigrationStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createStrategyS() {
        return new StrategyDefinitionParameter(ScheduledImmigrationStrategyDefinition.STRATEGY_NAME, LimitedEggsAdultMortalityStrategy.class.getName());
    }

    public static final String STRATEGY_NAME = "scheduled";




}
