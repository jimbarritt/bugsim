/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.model.agent.boundary;

import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.bugsim.agent.butterfly.ButterflyAgent;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 1, 2007 @ 12:04:33 PM by jim
 */
public class BoundaryCrossingRecorderAgentFilter implements IAgentFilter {

    public boolean acceptAgent(IAgent agent) {
        boolean accept = false;
        if (agent != null && BoundaryCrossingRecorderAgent.class.isAssignableFrom(agent.getClass())) {
            accept = true;
        }
        return accept;
    }

    public static final IAgentFilter INSTANCE = new BoundaryCrossingRecorderAgentFilter();
}
