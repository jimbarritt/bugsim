/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.math.geometry.DirectionOfChange;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterisedStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Random;

/**
 * Description : This strategy encapsulates the basics for doing a random walk.
 *                  By changing the implementation of the heading generator you can change
 *                the method of random walk from a pure random (Uniform) to a correlated (von mises or gaussian or wrapped normal)
 */
public class RandomWalkStrategy implements IMovementStrategy, IParameterisedStrategy {

    public RandomWalkStrategy() {
    }


    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialiseationObjects) {
        Simulation simulation = SimulationCategory.getSimulation(initialiseationObjects);

        _random = simulation.getRandom();

        StrategyDefinitionParameter azimuthS = ButterflyParameters.getAzimuthS(strategyP);
        _azimuthGenerator = (IAzimuthGenerator)ParameterisedStrategyFactory.createParameterisedStrategy(azimuthS, params, initialiseationObjects);

        StrategyDefinitionParameter moveLengthS = ButterflyParameters.getMoveLengthS(strategyP);
        _moveLengthGenerator = (IMoveLengthGenerator)ParameterisedStrategyFactory.createParameterisedStrategy(moveLengthS, params, initialiseationObjects);

    }



    public String getParameterSummary() {
        return _moveLengthGenerator.getParameterSummary()  + ", " + _azimuthGenerator;
    }


    public RandomWalkStrategy(Random random, double stdDeviation, double moveLength) {
        _azimuthGenerator = new GaussianAzimuthGenerator(random, stdDeviation);
        _moveLengthGenerator = new FixedMoveLengthGenerator(moveLength);
    }


    public IAzimuthGenerator getAzimuthGenerator() {
        return _azimuthGenerator;
    }

    public IMoveLengthGenerator getMoveLengthGenerator() {
        return _moveLengthGenerator;
    }

    public double getInitialMoveLength() {
        return _moveLengthGenerator.getInitialMoveLength();
    }

    // this was to test it out as it didnt seem to be working proper actually a bug in the calcualte angle to method
//    public Move calculateNextMove(Simulation simulation, IMotileAgent agent) {
//        double newHeading = agent.getHeading();
//        if (agent.getAge() > 0) { //we already have a direction when we start.
//            CourseChange c = _azimuthGenerator.generateCourseChange(newHeading, _stdDeviation);
//            newHeading = c.getNewAzimuth();
//        }
//
//        Location next = new Location(agent.getLocation().getCoordinate().moveTo(newHeading, _moveLength));
//        return new Move(next, newHeading, _moveLength);
//    }

    public Move calculateNextLocation(Simulation simulation, IMotileAgent agent) {
        double mvlen = _moveLengthGenerator.calculateMoveLength(agent);


        CourseChange courseChange = agent.getAge() ==0 ? new CourseChange(agent.getAzimuth(), DirectionOfChange.NONE, 0) : _azimuthGenerator.generateCourseChange(agent.getAzimuth());
        double azimuth = courseChange.getNewAzimuth();
        RectangularCoordinate current = agent.getLocation().getCoordinate();
        RectangularCoordinate next = current.moveTo(new AzimuthCoordinate(azimuth, mvlen));

        return new Move(new Location(next), courseChange, mvlen);
    }







    public Random getRandom() {
        return _random;
    }


    public String toString() {
        return "RandomWalk: , " + getParameterSummary();
    }


    private IAzimuthGenerator _azimuthGenerator;


    private static final Logger log = Logger.getLogger(RandomWalkStrategy.class);
    private Random _random;

    private IMoveLengthGenerator _moveLengthGenerator;
}
