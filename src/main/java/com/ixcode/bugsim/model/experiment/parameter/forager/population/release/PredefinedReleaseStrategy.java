/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.population.release;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 9, 2007 @ 1:03:02 PM by jim
 * @deprecated 
 */
public class PredefinedReleaseStrategy extends StrategyDefinition  {

    public PredefinedReleaseStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public PredefinedReleaseStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static final String S_PREDEFINED = "predefined";
    public static final String P_BIRTH_PREDEFINED_PARAMETERS = "parameters";
}
