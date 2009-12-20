/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.matchstick;

import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.bugsim.agent.cabbage.CabbageAgentFilter;
import com.ixcode.bugsim.agent.cabbage.CabbageAgent;

/**
 *  Description : Filters only cabbages
 */
public class MatchstickAgentFilter implements IAgentFilter {

    public static final IAgentFilter INSTANCE = new MatchstickAgentFilter();

    public boolean acceptAgent(IAgent agent) {
        boolean accept = false;
        if (agent != null && MatchstickAgent.class.isAssignableFrom(agent.getClass())) {
            accept = true;
        }
        return accept;
    }
}
