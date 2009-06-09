/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent;

import com.ixcode.framework.simulation.model.agent.IAgent;

public interface IAgentFilter {

    boolean acceptAgent(IAgent agent);

}
