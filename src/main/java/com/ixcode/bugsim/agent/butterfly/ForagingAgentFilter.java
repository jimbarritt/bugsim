/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.butterfly;

import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.bugsim.agent.butterfly.ButterflyAgent;

/**
 * Description : Filters only cabbages
 */
public class ForagingAgentFilter implements IAgentFilter {

    public static final IAgentFilter INSTANCE = new ForagingAgentFilter();


    public ForagingAgentFilter() {
            _mustBeAlive = false;
    }

    public ForagingAgentFilter(boolean mustBeAlive) {
        _mustBeAlive = mustBeAlive;
    }

    public boolean acceptAgent(IAgent agent) {
        boolean accept = false;
        if (agent != null && ButterflyAgent.class.isAssignableFrom(agent.getClass())) {
            if (_mustBeAlive) {
                accept = ((ButterflyAgent)agent).isAlive();
            } else {
                accept = true;
            }
        }
        return accept;
    }

    private boolean _mustBeAlive = false;
}
