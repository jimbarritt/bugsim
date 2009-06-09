/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information.function;

import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.ISimulationListener;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSurface;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSurfaceCalculator;
import com.ixcode.framework.simulation.model.landscape.information.InformationSourceAgentFilter;
import com.ixcode.framework.simulation.model.landscape.information.SignalSample;

import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class FunctionalSignalSurface implements ISignalSurface, ISimulationListener, IParameterisedStrategy {


    public FunctionalSignalSurface() {
    }

    public void initialise(StrategyDefinitionParameter strategyParameter, ParameterMap params, Map initialisationObjects) {


        StrategyDefinitionParameter strategyP = CabbageParameters.getSignalSurfaceCalculator(strategyParameter);
//        _calculatorFactory = (ISignalSurfaceCalculatorFactory)ParameterisedStrategyFactory.createParameterisedStrategy(strategyP, params, initialisationObjects);
        

    }

    public String getParameterSummary() {
        return "";
    }


    public FunctionalSignalSurface(String name, Landscape landscape, ISignalSurfaceCalculator calculator) {
        _name = name;
        _landscape = landscape;
        _calculator = calculator;
        _informationSources = _landscape.getSimulation().getLiveAgents(InformationSourceAgentFilter.INSTANCE);
        _landscape.getSimulation().addSimulationListener(this);
    }


    public SignalSample getInformationSample(RectangularCoordinate coordinate) {
        double value = _calculator.calculateSurfaceHeight(_informationSources, coordinate);
        return new SignalSample(coordinate, value);
    }


    public void agentAdded(Simulation simulation, IAgent agent) {
        if (InformationSourceAgentFilter.INSTANCE.acceptAgent(agent) && !_informationSources.contains(agent)) {
            _informationSources.add(agent);
        }
    }

    public void agentDeath(Simulation simulation, IAgent agent) {
        if (InformationSourceAgentFilter.INSTANCE.acceptAgent(agent) && _informationSources.contains(agent)) {
            _informationSources.remove(agent);
        }
    }

    public String getName() {
        return _name;
    }

    public void agentEscaped(Simulation simulation, IAgent agent) {

    }

    public void timestepCompleted(Simulation simulation) {

    }

    public void iterationCompleted(Simulation simulation) {

    }

    public void tidyUp() {
        _landscape.getSimulation().removeSimulationListener(this);
    }

    public ISignalSurfaceCalculator getCalculator() {
        return _calculator;
    }
    

    private String _name;
    private Landscape _landscape;
    private ISignalSurfaceCalculator _calculator;
    private List _informationSources;
}
