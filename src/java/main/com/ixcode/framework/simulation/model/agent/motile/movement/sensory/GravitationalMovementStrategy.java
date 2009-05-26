/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement.sensory;

import com.ixcode.framework.simulation.model.agent.motile.movement.GaussianAzimuthGenerator;
import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.Move;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.information.GravitationalCalculator;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class GravitationalMovementStrategy implements IMovementStrategy {

    public GravitationalMovementStrategy(GravitationalCalculator gravitationalCalculator,  int moveLength, Random random) {
        _gravitationalCalculator = gravitationalCalculator;

        _moveLength = moveLength;
         _headingGenerator = new GaussianAzimuthGenerator(random, 20);
    }

    public Move calculateNextLocation(Simulation simulation, IMotileAgent agent) {
        List attractors = findAttractorsWithinRange(agent, simulation.getLiveAgents());
        RectangularCoordinate newLocation = _gravitationalCalculator.calculateNetDisplacement(attractors, agent.getLocation().getCoordinate());

        AzimuthCoordinate displacement = agent.getLocation().getCoordinate().calculateAzimuthCoordinateTo(newLocation);

        double realAngle = displacement.getAzimuth();
        double stdDeviation = 60 * (1 - (displacement.getDistance() * 100));
        stdDeviation = (stdDeviation < 20) ? 20 : stdDeviation;
        CourseChange courseChange = _headingGenerator.generateCourseChange(realAngle);
        Location previous = agent.getLocation();
        Location next = new Location(previous.getCoordinate().moveTo(new AzimuthCoordinate(courseChange.getNewAzimuth(), _moveLength)));
        return new Move(next, courseChange, _moveLength); 



    }

    public String getParameterSummary() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Move calculateNextMove(Simulation simulation, IMotileAgent agent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    private List findAttractorsWithinRange(IPhysicalAgent agent, List agents) {
        List attractors = new ArrayList();
        for (Iterator itr = agents.iterator(); itr.hasNext();) {
            Object o = itr.next();
            if (o instanceof ISignalSource) {
                ISignalSource signalSource = (ISignalSource)o;
                if (signalSource.withinRange(agent.getLocation())) {
                    attractors.add(signalSource);
                }
            }
        }
        return attractors;
    }

    public CourseChange calculateNextHeading(Simulation simulation, IMotileAgent agent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double getInitialMoveLength() {
        return _moveLength;
    }

    private GravitationalCalculator _gravitationalCalculator;
    private GaussianAzimuthGenerator _headingGenerator;
    private int _moveLength;

}
