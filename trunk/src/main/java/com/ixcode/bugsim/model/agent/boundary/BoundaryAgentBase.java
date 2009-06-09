/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.boundary;

import com.ixcode.framework.simulation.model.agent.physical.PhysicalAgentBase;
import com.ixcode.framework.simulation.model.landscape.Location;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public abstract class BoundaryAgentBase extends PhysicalAgentBase implements IBoundaryAgent {

    protected BoundaryAgentBase(String agentClassId, Location location, String name) {
        super(agentClassId, location);
        _name = name;
    }

    public String getName() {
         return _name;
    }

    private String _name;

}
