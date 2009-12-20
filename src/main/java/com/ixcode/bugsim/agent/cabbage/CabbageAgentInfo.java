/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.cabbage;

import com.ixcode.framework.simulation.model.agent.AgentInfoBase;
import com.ixcode.framework.simulation.model.agent.IAgentInfo;
import com.ixcode.bugsim.agent.cabbage.CabbageAgentFactory;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CabbageAgentInfo extends AgentInfoBase {

    public static final String ID = "cabbage-agent";
    public static final String DISPLAY_NAME = "Cabbage";

    public static IAgentInfo INSTANCE = new CabbageAgentInfo();

    public CabbageAgentInfo() {
        super(ID, DISPLAY_NAME, new CabbageAgentFactory(), CabbageAgentFilter.INSTANCE);
    }

}
