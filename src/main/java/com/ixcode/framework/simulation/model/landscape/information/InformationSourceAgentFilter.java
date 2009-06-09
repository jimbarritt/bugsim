/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information;

import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class InformationSourceAgentFilter implements IAgentFilter {
    public static final IAgentFilter INSTANCE = new InformationSourceAgentFilter();

    public boolean acceptAgent(IAgent agent) {
        return ISignalSource.class.isAssignableFrom(agent.getClass());
    }
}
