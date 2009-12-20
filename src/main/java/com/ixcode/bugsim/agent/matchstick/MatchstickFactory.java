/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.matchstick;

import com.ixcode.bugsim.experiment.parameter.MatchstickParameters;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.Location;

import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class MatchstickFactory implements IMatchstickFactory, IParameterisedStrategy {

    public void initialise(StrategyDefinitionParameter algorithmParameter, ParameterMap params, Map initialisationObjects) {
        _startTheta = MatchstickParameters.getStartHeading(algorithmParameter);
        _endTheta = MatchstickParameters.getEndHeading(algorithmParameter);
        _thetaIncrement = MatchstickParameters.getHeadingIncrement(algorithmParameter);
        _droppingDistance = MatchstickParameters.getMatchstickDroppingDistance(algorithmParameter);

        _matchstickLength = MatchstickParameters.getMatchstickLength(params);        
        _ixBoundaryName = MatchstickParameters.getIntersectionBoundaryName(params);


    }

    public String getParameterSummary() {
        return "";
    }

    public void createMatchstickPopulation(Simulation simulation) {

        Landscape l = simulation.getLandscape();
        RectangularCoordinate centre = l.getLogicalBounds().getCentre();
        double y = centre.getDoubleY() + _droppingDistance;
        double theta = _startTheta;
        
        while (theta < _endTheta) {
            simulation.addAgent(new MatchstickAgent(new Location(centre.getDoubleX(), y), _matchstickLength, theta, _ixBoundaryName));
            theta += _thetaIncrement;
        }

    }

    private double _startTheta;
    private double _endTheta;
    private double _thetaIncrement;
    private double _matchstickLength;
    private double _droppingDistance;
    private String _ixBoundaryName;
}
