/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.butterfly.mortality;

import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.LimitedAgeAdultMortalityStrategyDefinition;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.physical.IAdultMortalityStrategy;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LimitedAgeAdultMortalityStrategy implements IAdultMortalityStrategy , IParameterisedStrategy {


    public LimitedAgeAdultMortalityStrategy() {
    }

    public void initialise(StrategyDefinitionParameter strategyS, ParameterMap params, Map initialisationObjects) {
        LimitedAgeAdultMortalityStrategyDefinition lsd = new LimitedAgeAdultMortalityStrategyDefinition(strategyS,params,  false);
        _maxAge = lsd.getMaxAge();
    }

    public LimitedAgeAdultMortalityStrategy(long maxAge) {
        _maxAge = maxAge;
    }


    public boolean isAlive(IPhysicalAgent agent) {
        return (agent.getAge() < _maxAge);
    }

    public String toString() {
        return "LimitedAgeMortality: maxAge=" + _maxAge;
    }

    public String getParameterSummary() {
        return "MXAG=" + _maxAge;
    }



    private long _maxAge;
}
