/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.butterfly.mortality;

import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.physical.IAdultMortalityStrategy;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.bugsim.agent.butterfly.ButterflyAgent;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LimitedEggsAdultMortalityStrategy implements IAdultMortalityStrategy , IParameterisedStrategy {

    public LimitedEggsAdultMortalityStrategy() {
    }

    public void initialise(StrategyDefinitionParameter algorithmParameter, ParameterMap params, Map initialisationObjects) {

    }

    public boolean isAlive(IPhysicalAgent agent) {
        ButterflyAgent bf = (ButterflyAgent)agent;
        return (bf.getEggCount() >0);
    }

    public String getParameterSummary() {
        return "";
    }

    
}
