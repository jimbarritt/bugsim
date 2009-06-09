/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent;

import java.io.Serializable;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public abstract class AgentInfoBase implements IAgentInfo , Serializable {

    protected AgentInfoBase(String id, String displayName, IAgentFactory factory, IAgentFilter filter) {
        _id = id;
        _displayName = displayName;
        _factory = factory;
        _filter = filter;
    }


    public String getId() {
        return _id;
    }

    public String getDisplayName() {
        return _displayName;
    }

    public IAgentFactory getFactory() {
        return _factory;
    }

    public IAgentFilter getAgentFilter() {
        return _filter;
    }

    private String _id;
    private String _displayName;
    private IAgentFactory _factory;
    private IAgentFilter _filter;
}
