/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration;

import com.ixcode.framework.parameter.model.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:24:27 PM by jim
 */
public class ImmigrationStrategyBase extends StrategyDefinition {

    public ImmigrationStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params) {
        this(sparam, params, false);
    }

    public ImmigrationStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);

    }


    public static void addParameters(StrategyDefinitionParameter strategyS) {

    }

    protected StrategyDefinition createStrategyDefinition(String parameterName, StrategyDefinitionParameter strategyS) {

            return super.createStrategyDefinition(parameterName, strategyS);

    }



}
