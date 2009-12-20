/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.butterfly.population;

import com.ixcode.bugsim.agent.butterfly.ForagerAgentStrategies;
import com.ixcode.bugsim.agent.butterfly.ForagingAgentBehaviour;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.physical.AgentBehaviour;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.AgentClassFilter;
import com.ixcode.framework.math.geometry.RectangularCoordinate;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Mar 1, 2007 @ 5:10:54 PM by jim
 */
public interface IForagerFactory {

    ForagerAgentStrategies getForagerStrategies();

    IMotileAgent createForager(RectangularCoordinate location, double azimuth, ForagingAgentBehaviour behvaiour);

    AgentClassFilter getAgentFilter();

    Class getAgentClass();
}
