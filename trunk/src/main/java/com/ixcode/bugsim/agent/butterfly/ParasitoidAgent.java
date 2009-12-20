/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.butterfly;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.physical.AgentBehaviour;
import com.ixcode.framework.simulation.model.agent.physical.IAdultMortalityStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.landscape.Location;

import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 4, 2007 @ 9:59:12 PM by jim
 */
public class ParasitoidAgent extends ButterflyAgent{

    public static final String AGENT_CLASS_ID = "ParasitoidAgent";
    public ParasitoidAgent(RectangularCoordinate location, double azimuth, ForagerAgentStrategies strategies, boolean recordHistory, AgentBehaviour behaviour) {
        super(location, azimuth, strategies, recordHistory, behaviour);
    }

    public ParasitoidAgent(Location location, double initialDirection, double I, double radius, IMovementStrategy movementStrategy, IForagingStrategy foragingStrategy, IAdultMortalityStrategy adultMortalityStrategy, boolean recordHistory, ForagingAgentBehaviour initialBehaviour, IVisionStrategy visionStrategy, IOlfactionStrategy olfactionStrategy, List motivationStrategies) {
        super(location, initialDirection, I, radius, movementStrategy, foragingStrategy, adultMortalityStrategy, recordHistory, initialBehaviour, visionStrategy, olfactionStrategy, motivationStrategies);
    }
}
