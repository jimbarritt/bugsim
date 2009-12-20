/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.butterfly;

import com.ixcode.framework.simulation.model.agent.physical.AgentBehaviour;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 */
public interface IMotivationStrategy {

    String getName();

    double calculateMotivationLevel(IMotileAgent agent);

    AgentBehaviour getKeyAgentBehaviour();

    
}
