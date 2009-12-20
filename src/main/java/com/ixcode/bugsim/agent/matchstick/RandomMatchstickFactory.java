/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.matchstick;

import com.ixcode.bugsim.model.agent.boundary.IBoundaryAgent;
import com.ixcode.bugsim.model.agent.boundary.IBoundaryAgentFactory;
import com.ixcode.bugsim.model.agent.boundary.NamedBoundaryAgentFilter;
import com.ixcode.bugsim.model.experiment.parameter.MatchstickParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterisedStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.boundary.IBoundary;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Description : Distributes matchsticks at random within a certain boundary.
 */
public class RandomMatchstickFactory implements IMatchstickFactory, IParameterisedStrategy {

    public void initialise(StrategyDefinitionParameter algorithmParameter, ParameterMap params, Map initialisationObjects) {
        _matchstickLength = MatchstickParameters.getMatchstickLength(params);

        _ixBoundaryName = MatchstickParameters.getIntersectionBoundaryName(params);
        _outerBoundaryP = MatchstickParameters.getOuterReleaseBoundaryP(algorithmParameter);


        _populationSize = MatchstickParameters.getPopulationSize(algorithmParameter);


        _ixBoundaryFilter = new NamedBoundaryAgentFilter(_ixBoundaryName);
        _params = params;


    }

    public String getParameterSummary() {
        return "";
    }

    public void createMatchstickPopulation(Simulation simulation) {
        List ixBoundaries = simulation.getLiveAgents(_ixBoundaryFilter);
        IBoundaryAgent ixBoundaryAgent = (IBoundaryAgent)ixBoundaries.get(0);
        IBoundary ixBoundary = ixBoundaryAgent.getBoundary();

        Map initObjects = SimulationCategory.createSimulationStrategyInitialisation(simulation);
        IBoundaryAgentFactory outerReleaseBoundaryFactory = (IBoundaryAgentFactory)ParameterisedStrategyFactory.createParameterisedStrategy(_outerBoundaryP, _params, initObjects);
        IBoundaryAgent outerBoundaryAgent = outerReleaseBoundaryFactory.createBoundaryAgent(simulation);
        IBoundary outerBoundary = outerBoundaryAgent.getBoundary();
        CartesianBounds boundaryBounds = outerBoundary.getBounds();

        if (log.isInfoEnabled()) {
            log.info("Creating " + _populationSize + " matchsticks...");
        }
        for (int i=0;i<_populationSize;++i) {
            RectangularCoordinate startCoord = null;
            while (startCoord == null) {
                RectangularCoordinate randomCoord = Geometry.generateUniformRandomCoordinate(simulation.getRandom(), boundaryBounds);
                if (!ixBoundary.isInside(randomCoord) && outerBoundary.isInside(randomCoord)) {
                    startCoord = randomCoord;
                }
            }

            double theta = Geometry.generateUniformRandomAzimuthChange(simulation.getRandom());
            simulation.addAgent(new MatchstickAgent(new Location(startCoord), _matchstickLength, theta, _ixBoundaryName, false));
        }

    }

    private double _matchstickLength;
    private String _ixBoundaryName;

    private IAgentFilter _ixBoundaryFilter;
    private long _populationSize;
    private StrategyDefinitionParameter _outerBoundaryP;

    private ParameterMap _params;
    private static final Logger log = Logger.getLogger(RandomMatchstickFactory.class);
}
