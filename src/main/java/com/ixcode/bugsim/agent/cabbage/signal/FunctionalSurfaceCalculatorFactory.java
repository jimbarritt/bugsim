/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.cabbage.signal;

import com.ixcode.bugsim.experiment.parameter.resource.CabbageParameters;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterisedStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.landscape.information.FunctionalSignalSurfaceCalculator;
import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSurfaceCalculator;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class FunctionalSurfaceCalculatorFactory implements ISignalSurfaceCalculatorFactory, IParameterisedStrategy {



    public void initialise(StrategyDefinitionParameter strategyParameter, ParameterMap params, Map initialisationObjects) {
        StrategyDefinitionParameter strategyP = CabbageParameters.getSignalFunctionStrategyP(strategyParameter);
        _signalFunction = (ISignalFunction)ParameterisedStrategyFactory.createParameterisedStrategy(strategyP,  params, initialisationObjects);
    }

    public String getParameterSummary() {
        return "SIGNALF=" + _signalFunction.getName();
    }

    public ISignalSurfaceCalculator createSignalSurfaceCalculator() {
        return  new FunctionalSignalSurfaceCalculator(_signalFunction);
    }


    private ISignalFunction _signalFunction;
}
