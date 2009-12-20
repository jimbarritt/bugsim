/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.boundary;

import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeParameters;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.BoundaryParameters;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.Location;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LinearBoundaryAgentFactory implements IParameterisedStrategy, IBoundaryAgentFactory {

    public void initialise(StrategyDefinitionParameter algorithmParameter, ParameterMap params, Map initialisationObjects) {
         _location = LandscapeParameters.getLocation(algorithmParameter);
        _length = BoundaryParameters.getLinearBoundaryLength(algorithmParameter);
        _halfLength = _length / 2;
        _name = BoundaryParameters.getBoundaryName(algorithmParameter);
    }

    public String getParameterSummary() {
        return "";
    }

    public IBoundaryAgent createBoundaryAgent(Simulation simulation) {
        LinearBoundaryAgent agent = new LinearBoundaryAgent(_name, new Location(_location.getDoubleX()-_halfLength, _location.getDoubleY()), new Location(_location.getDoubleX() + _halfLength, _location.getDoubleY()));
        simulation.addAgent(agent);
        return agent;
    }

    private RectangularCoordinate _location;
    private double _length;
    private double _halfLength;
    private String _name;
}
