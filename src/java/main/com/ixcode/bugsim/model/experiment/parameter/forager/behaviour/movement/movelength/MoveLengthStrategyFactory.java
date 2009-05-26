/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.movelength;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.simulation.model.agent.motile.movement.FixedMoveLengthGenerator;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 2:49:24 PM by jim
 */
public class MoveLengthStrategyFactory {


    public static StrategyRegistry getRegistry() {
        return MoveLengthStrategyFactory.REGISTRY;
    }

    public static MoveLengthStrategyBase createMoveLengthStrategy(StrategyDefinitionParameter moveLengthS, ParameterMap params, boolean forwardEvents) {
        return (MoveLengthStrategyBase)getRegistry().constructStrategy(moveLengthS, params, forwardEvents);

    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();

    static {
        REGISTRY.registerStrategy(FixedMoveLengthStrategy.class,  FixedMoveLengthGenerator.class,  FixedMoveLengthStrategy.S_FIXED_MOVELENGTH, "Fixed");

    }


}
