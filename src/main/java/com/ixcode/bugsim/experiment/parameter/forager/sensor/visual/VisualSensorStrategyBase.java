/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.sensor.visual;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 3:30:57 PM by jim
 */
public abstract class VisualSensorStrategyBase extends StrategyDefinition  {

    public VisualSensorStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public VisualSensorStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }
}
