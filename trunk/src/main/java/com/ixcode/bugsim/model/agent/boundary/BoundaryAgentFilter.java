/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.boundary;

import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.IAgent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BoundaryAgentFilter implements IAgentFilter {

    public static IAgentFilter INSTANCE = new BoundaryAgentFilter();

    public BoundaryAgentFilter() {
    }

    public boolean acceptAgent(IAgent agent) {
        boolean accept = false;
        if (IBoundaryAgent.class.isAssignableFrom(agent.getClass())) {
            accept = true;
        }
        return accept;
    }

}
