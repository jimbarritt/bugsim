package com.ixcode.bugsim.agent.boundary;

import com.ixcode.framework.simulation.model.agent.physical.PhysicalAgentBase;
import com.ixcode.framework.simulation.model.landscape.Location;

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
