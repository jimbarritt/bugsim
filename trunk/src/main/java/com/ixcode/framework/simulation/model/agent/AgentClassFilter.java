/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.model.agent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class AgentClassFilter implements IAgentFilter {
    public AgentClassFilter(Class agentClass) {
        _class = agentClass;
    }

    public boolean acceptAgent(IAgent agent) {
        return (_class.isAssignableFrom(agent.getClass()));
    }

    public Class getAgentClass() {
        return _class;
    }

    private Class _class;
}
