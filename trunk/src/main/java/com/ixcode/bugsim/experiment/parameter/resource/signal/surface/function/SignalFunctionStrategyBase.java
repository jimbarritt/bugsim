/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.resource.signal.surface.function;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterisedStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;

import java.util.Map;

/**
 *  Description : Calculates the signal surface based on a function which is set up as a parameter.
 *  Created     : Jan 25, 2007 @ 12:44:50 PM by jim
 */
public abstract class SignalFunctionStrategyBase extends StrategyDefinition {

    public SignalFunctionStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public ISignalFunction createSignalFunctionImplementation(Map initObjects) {
        return (ISignalFunction)ParameterisedStrategyFactory.createParameterisedStrategy(super.getStrategyS(),  super.getParameterMap(), initObjects);
    }
}
