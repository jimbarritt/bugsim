/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.motile.movement.RandomWalkStrategy;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 4, 2007 @ 9:03:08 PM by jim
 */
public class RandomWalkMovementStrategy extends MovementStrategyBase {

    public RandomWalkMovementStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public RandomWalkMovementStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }


    public static StrategyDefinitionParameter createStrategyS(StrategyDefinitionParameter azimuthS, StrategyDefinitionParameter moveLengthS) {
        StrategyDefinitionParameter rw = new StrategyDefinitionParameter(S_RANDOM_WALK, RandomWalkStrategy.class.getName());
        addStrategyParams(rw, azimuthS, moveLengthS);
        return rw;
    }
    public static void addStrategyParams(StrategyDefinitionParameter base, StrategyDefinitionParameter azimuthS, StrategyDefinitionParameter moveLengthS) {
        MovementStrategyBase.addStrategyParams(base, azimuthS, moveLengthS);
    }

    public static final String S_RANDOM_WALK = "randomWalk";
}
