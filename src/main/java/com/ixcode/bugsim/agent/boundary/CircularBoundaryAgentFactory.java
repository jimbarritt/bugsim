/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.boundary;

import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeParameters;
import com.ixcode.framework.math.geometry.CartesianDimensions;
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
public class CircularBoundaryAgentFactory implements IParameterisedStrategy, IBoundaryAgentFactory {

    public void initialise(StrategyDefinitionParameter algorithmParameter, ParameterMap params, Map initialisationObjects) {
         _location = LandscapeParameters.getLocation(algorithmParameter);
        _dimensions = BoundaryParameters.getBoundaryDimensions(algorithmParameter);
        _name = BoundaryParameters.getBoundaryName(algorithmParameter);

    }

    public String getParameterSummary() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public IBoundaryAgent createBoundaryAgent(Simulation simulation) {
        CircularBoundaryAgent agent = new CircularBoundaryAgent(_name, new Location(_location.getDoubleX(), _location.getDoubleY()), _dimensions.getDoubleWidth());
        simulation.addAgent(agent);
        return agent;
    }

    private RectangularCoordinate _location;
    private CartesianDimensions _dimensions;
    private String _name;
}
