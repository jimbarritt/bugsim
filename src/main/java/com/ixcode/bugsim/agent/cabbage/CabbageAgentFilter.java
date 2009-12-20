/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.cabbage;

import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.bugsim.agent.cabbage.CabbageAgent;

/**
 *  Description : Filters only cabbages
 */
public class CabbageAgentFilter implements IAgentFilter {

    public static final IAgentFilter INSTANCE = new CabbageAgentFilter();

    public boolean acceptAgent(IAgent agent) {
        boolean accept = false;
        if (agent != null && CabbageAgent.class.isAssignableFrom(agent.getClass())) {
            accept = true;
        }
        return accept;
    }
}
