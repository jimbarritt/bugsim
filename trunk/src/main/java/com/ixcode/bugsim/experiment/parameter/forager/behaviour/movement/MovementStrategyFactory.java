/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.simulation.model.agent.motile.movement.RandomWalkStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.SensoryRandomWalkStrategy;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 4, 2007 @ 9:03:54 PM by jim
 */

public class MovementStrategyFactory {


    public static StrategyRegistry getRegistry() {
        return REGISTRY;
    }

    public static MovementStrategyBase createMovementStrategy(StrategyDefinitionParameter movementS, ParameterMap parameterMap, boolean forwardEvents) {

        return (MovementStrategyBase)getRegistry().constructStrategy(movementS, parameterMap, forwardEvents);

    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {
        // We only document a single random walk in the parameter summary - in effect - we only really need the Sensory one and so the normal one is a bit like an abstract class.
//        REGISTRY.registerStrategy(RandomWalkMovementStrategy.class, RandomWalkStrategy.class, RandomWalkMovementStrategy.S_RANDOM_WALK, "Random Walk");
        REGISTRY.registerStrategy(SensoryRandomWalkMovementStrategy.class, SensoryRandomWalkStrategy.class, SensoryRandomWalkMovementStrategy.S_SENSORY_RANDOM_WALK, "Random Walk");
    }
}
