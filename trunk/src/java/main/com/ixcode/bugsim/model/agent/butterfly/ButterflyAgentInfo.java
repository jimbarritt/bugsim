/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.butterfly;

import com.ixcode.framework.simulation.model.agent.AgentInfoBase;
import com.ixcode.framework.simulation.model.agent.IAgentInfo;
import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgentFactory;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ButterflyAgentInfo extends AgentInfoBase {

    public static final String ID = "butterfly-agent";
    public static final String DISPLAY_NAME = "Butterfly";

    public static IAgentInfo INSTANCE = new ButterflyAgentInfo();

    public ButterflyAgentInfo() {
        super(ID, DISPLAY_NAME, new ButterflyAgentFactory(), ForagingAgentFilter.INSTANCE);
    }

}
