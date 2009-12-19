/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.action;

import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgentInfo;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgentInfo;
import com.ixcode.framework.simulation.model.agent.IAgentInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class AgentInfoRegistry implements Serializable {


    public AgentInfoRegistry() {

        registerAgentType(CabbageAgentInfo.INSTANCE);
        registerAgentType(ButterflyAgentInfo.INSTANCE);

    }

    public void registerAgentType(IAgentInfo agentInfo) {
        _agentTypes.put(agentInfo.getId(), agentInfo);
        _index.add(agentInfo.getId());
    }

    public List getTypeIds() {
        return _index;
    }

    public IAgentInfo getInfo(String id) {
        return (IAgentInfo)_agentTypes.get(id);
    }

    private Map _agentTypes = new HashMap();
    private List _index = new ArrayList();
}
