/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.release;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:24:27 PM by jim
 * @deprecated 
 */
public class ReleaseStrategyBase extends StrategyDefinition {

    public ReleaseStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public ReleaseStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

}
