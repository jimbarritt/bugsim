/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.simulation.timescale;

import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:24:27 PM by jim
 */
public abstract class TimescaleStrategyBase extends StrategyDefinition {

    public TimescaleStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public TimescaleStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }


    
}
