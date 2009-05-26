/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.dispersal;

import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.dispersal.DispersalDistanceRecorder;

/**
 *  Description : Filters only cabbages
 */
public class DispersalRecorderAgentFilter implements IAgentFilter {

    public static final IAgentFilter INSTANCE = new DispersalRecorderAgentFilter();

    public boolean acceptAgent(IAgent agent) {
        boolean accept = false;
        if (agent != null && DispersalDistanceRecorder.class.isAssignableFrom(agent.getClass())) {
            accept = true;
        }
        return accept;
    }
}
