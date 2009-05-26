/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.simulation.timescale;

import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.timescale.ContinuousGenerationsTimescale;
import com.ixcode.framework.simulation.model.timescale.DiscreteGenerationsTimescale;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 4, 2007 @ 9:03:54 PM by jim
 */

public class TimescaleStrategyFactory {


    public static StrategyRegistry getRegistry() {
        return TimescaleStrategyFactory.REGISTRY;
    }

    public static TimescaleStrategyBase createTimescaleStrategy(StrategyDefinitionParameter strategyS, ParameterMap parameterMap, boolean forwardEvents) {

        return (TimescaleStrategyBase)getRegistry().constructStrategy(strategyS, parameterMap, forwardEvents);

    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {

        REGISTRY.registerStrategy(ContinuousTimescaleStrategyDefinition.class, ContinuousGenerationsTimescale.class, ContinuousTimescaleStrategyDefinition.STRATEGY_NAME, "Continuous Generations");
        REGISTRY.registerStrategy(DiscreteTimescaleStrategyDefinition.class, DiscreteGenerationsTimescale.class, DiscreteTimescaleStrategyDefinition.STRATEGY_NAME, "Discrete Generations");



    }
}
