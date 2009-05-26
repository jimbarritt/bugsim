/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile;

import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.Move;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.bugsim.model.agent.butterfly.IForagingStrategy;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public interface IMotileAgent extends IPhysicalAgent {

    /**
     * Moves to the new location and calculates the direction of the agent based on this location
     * @param move
     * @param newLocation
     */
    void moveTo(Move move, Location newLocation);

    /**
     * If you want to override or already know the direction then use this function instead of moveto
     * @param newHeading
     * @param direction
     */
    void moveTo(double newHeading, double direction);

    double getAzimuth();



     void escaped(Simulation simulation);



    IMovementStrategy getMovementStrategy();

    IForagingStrategy getForagingStrategy();
}
