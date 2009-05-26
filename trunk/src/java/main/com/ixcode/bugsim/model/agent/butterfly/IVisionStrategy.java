/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.butterfly;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;

import java.util.List;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 */
public interface IVisionStrategy {

    List getVisibleAgents();

    void update(Simulation simulation, RectangularCoordinate coord, double heading, IAgentFilter filter);

    double calculateApparency(IPhysicalAgent agent);

    double getLuminosityGamma();

    double getVisualSignalThreshold();

    void setVisionEnabled(boolean visionEnabled);
}
