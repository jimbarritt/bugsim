/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.DerivedParameter;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class PredefinedReleaseImmigrationStrategyDefinition extends ImmigrationPatternStrategyBase {

    public PredefinedReleaseImmigrationStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public PredefinedReleaseImmigrationStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createStrategyS() {
        return new StrategyDefinitionParameter(PredefinedReleaseImmigrationStrategyDefinition.STRATEGY_NAME, PredefinedReleaseImmigrationStrategyDefinition.class.getName());
    }




    public static final String STRATEGY_NAME = "predefinedRelease";




}
