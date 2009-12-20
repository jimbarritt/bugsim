/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.agent;


import com.ixcode.bugsim.agent.boundary.CircularBoundaryAgent;
import com.ixcode.bugsim.agent.boundary.LinearBoundaryAgent;
import com.ixcode.bugsim.agent.butterfly.ButterflyAgent;
import com.ixcode.bugsim.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.agent.matchstick.MatchstickAgent;
import com.ixcode.framework.simulation.model.agent.diagnostic.LineAgent;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : This is where you register the renderers for any new agents you create.
 */
public class AgentRendererRegistry {

    public static final AgentRendererRegistry INSTANCE = new AgentRendererRegistry();

    private AgentRendererRegistry() {
        


        registerAgentRenderer(CabbageAgent.AGENT_CLASS_ID, new CabbageAgentRenderer());
        registerAgentRenderer(ButterflyAgent.AGENT_CLASS_ID, new ButterflyAgentRenderer());
        registerAgentRenderer(LineAgent.AGENT_CLASS_ID, new LineAgentRenderer());
        registerAgentRenderer(MatchstickAgent.AGENT_CLASS_ID, new MatchstickAgentRenderer());
        registerAgentRenderer(LinearBoundaryAgent.AGENT_CLASS_ID, new LinearBoundaryAgentRenderer());
        registerAgentRenderer(CircularBoundaryAgent.AGENT_CLASS_ID, new CircularBoundaryAgentRenderer());

    }


    public IAgentRenderer getRendererForAgent(IPhysicalAgent agent) {
        return (IAgentRenderer)_renderers.get(agent.getAgentClassId());
    }

    public IAgentRenderer getRendererForAgent(String agentClassId) {
        return (IAgentRenderer)_renderers.get(agentClassId);
    }

    public void registerAgentRenderer(String agentClassId, IAgentRenderer renderer) {
        _renderers.put(agentClassId, renderer);

    }

    private Map _renderers = new HashMap();
}
