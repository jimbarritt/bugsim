/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.cabbage;

import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.agent.IAgentFactory;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.bugsim.agent.cabbage.CabbageAgent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CabbageAgentFactory implements IAgentFactory {

    public IPhysicalAgent createAgent(Location location) {
        return new CabbageAgent(ID++, location, 6);
    }

    private static int ID = 0;
}
