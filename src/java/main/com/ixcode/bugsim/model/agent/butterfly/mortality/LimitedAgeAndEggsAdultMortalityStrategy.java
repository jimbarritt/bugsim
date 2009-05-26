/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.butterfly.mortality;

import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedAgeAdultMortalityStrategy;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LimitedAgeAndEggsAdultMortalityStrategy extends LimitedAgeAdultMortalityStrategy {


    public LimitedAgeAndEggsAdultMortalityStrategy() {
          _limitedEggsStrategy = new LimitedEggsAdultMortalityStrategy();
    }




    public boolean isAlive(IPhysicalAgent agent) {
        return super.isAlive(agent) && _limitedEggsStrategy.isAlive(agent);
    }

    public String toString() {
        return "Limited age and Eggs mortality: " + super.getParameterSummary();
    }

    public String getParameterSummary() {
        return super.getParameterSummary() + ", " + _limitedEggsStrategy.getParameterSummary();
    }

    private LimitedEggsAdultMortalityStrategy _limitedEggsStrategy;
}
