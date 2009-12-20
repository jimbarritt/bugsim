/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.resource.signal;

import com.ixcode.bugsim.agent.cabbage.signal.IResourceSignalFactory;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterisedStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 25, 2007 @ 12:00:07 PM by jim
 */
public abstract class SignalStrategyBase extends StrategyDefinition {

    protected SignalStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params, boolean isForwardEvents) {
        super(sparam, params,isForwardEvents);
    }


    public IResourceSignalFactory createSignalFactory(Map initObjects) {
        return (IResourceSignalFactory)ParameterisedStrategyFactory.createParameterisedStrategy(super.getStrategyS(), super.getParameterMap(), initObjects);

    }
}
