/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.model.timescale;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.bugsim.experiment.parameter.simulation.timescale.DiscreteTimescaleStrategyDefinition;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 28, 2007 @ 6:34:16 PM by jim
 */
public class DiscreteGenerationsTimescale implements ITimescale {

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        DiscreteTimescaleStrategyDefinition dgt = new DiscreteTimescaleStrategyDefinition(strategyP, params, false);
        _timestepLimit = dgt.getTimestepLimit();
        _generationLimit = dgt.getGenerationLimit();

    }

    public String getParameterSummary() {
        return null;
    }

    public long getTimestepLimit() {
        return _timestepLimit;
    }

    public long getGenerationLimit() {
        return _generationLimit;
    }

    private long _timestepLimit;
    private long _generationLimit;
}
