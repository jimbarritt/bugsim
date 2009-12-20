/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.cabbage.signal;

import com.ixcode.bugsim.model.experiment.parameter.resource.ResourceCategory;
import com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.FunctionalSignalSurfaceStrategy;
import com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.function.SignalFunctionStrategyBase;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.information.FunctionalSignalSurfaceCalculator;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSurface;
import com.ixcode.framework.simulation.model.landscape.information.function.FunctionalSignalSurface;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class FunctionalSignalSurfaceFactory implements ISignalSurfaceFactory, IParameterisedStrategy {

    public void initialise(StrategyDefinitionParameter surfaceS, ParameterMap params, Map initialisationObjects) {

        FunctionalSignalSurfaceStrategy functionalSurface = new FunctionalSignalSurfaceStrategy(surfaceS, params);

        _signalFunctionStrategy = functionalSurface.getSignalFunction();
        _calculator = new FunctionalSignalSurfaceCalculator(_signalFunctionStrategy.createSignalFunctionImplementation(initialisationObjects));
    }

    public String getParameterSummary() {
        return "";
    }

    public ISignalSurface createSignalSurface(Landscape landscape) {

        return new FunctionalSignalSurface(ResourceCategory.CABBAGE_SIGNAL_SURFACE_NAME, landscape, _calculator);
    }


    private SignalFunctionStrategyBase _signalFunctionStrategy;
    private FunctionalSignalSurfaceCalculator _calculator;
}
