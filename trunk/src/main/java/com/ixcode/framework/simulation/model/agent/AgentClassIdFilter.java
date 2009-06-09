/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class AgentClassIdFilter implements IAgentFilter {
    public AgentClassIdFilter(String agentClassId) {
        _classId = agentClassId;
    }

    public boolean acceptAgent(IAgent agent) {
        return (agent.getAgentClassId().equals(_classId));
    }

    private String _classId;
}
