/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.physical;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.landscape.Location;

import java.util.List;

public interface IPhysicalAgent extends IAgent {


    Location getLocation();

    /**
     * Kill this agent!
     * @param simulation
     */
    void kill(Simulation simulation);


    boolean isEscaped();

    boolean isDead();

    boolean isAlive();

    boolean isInSimulation();

    int getAge();

    boolean containsPoint(RectangularCoordinate coord);

    boolean intersectsLine(RectangularCoordinate p1, RectangularCoordinate p2);

    /**
     * @return a List of RectangularCoordinates which are the intersections.
     * @param startCoord
     * @param endCoord
     * @return
     */
    List lineIntersections(RectangularCoordinate startCoord, RectangularCoordinate endCoord);

    List circleIntersections(RectangularCoordinate center, double radius);

    int getAgeOfLastBehaviour(AgentBehaviour keyAgentBehaviour);
}
