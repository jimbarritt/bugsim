/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent;

import com.ixcode.framework.simulation.model.agent.IAgentFactory;

public interface IAgentInfo {

    String getId();

    String getDisplayName();


    IAgentFactory getFactory();

    IAgentFilter getAgentFilter();
}
