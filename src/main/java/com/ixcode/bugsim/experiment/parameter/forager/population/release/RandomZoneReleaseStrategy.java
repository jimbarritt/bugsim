/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.release;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 9, 2007 @ 1:04:30 PM by jim
 * @deprecated 
 */
public class RandomZoneReleaseStrategy extends StrategyDefinition {

    public RandomZoneReleaseStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public RandomZoneReleaseStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static final String S_RANDOM_IN_ZONE = "randomInZone";
}
