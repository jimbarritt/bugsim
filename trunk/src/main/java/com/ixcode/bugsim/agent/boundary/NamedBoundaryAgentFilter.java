/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.boundary;

import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.IAgent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class NamedBoundaryAgentFilter implements IAgentFilter {

    

    public NamedBoundaryAgentFilter(String searchName) {
        _searchName = searchName;
    }

    public boolean acceptAgent(IAgent agent) {
        boolean accept = false;
        if (IBoundaryAgent.class.isAssignableFrom(agent.getClass())) {
            if (_searchName.equals(((IBoundaryAgent)agent).getName())){
                accept = true;
            }
        }
        return accept;
    }

    private String _searchName;

}
