/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent;

import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;

import java.io.Serializable;

public interface IAgentFactory extends Serializable {
    IPhysicalAgent createAgent(Location location);
}
