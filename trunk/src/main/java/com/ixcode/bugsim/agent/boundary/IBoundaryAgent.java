/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.boundary;

import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.boundary.IBoundary;

import java.util.List;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 */
public interface IBoundaryAgent  {

    String getName();

    List calculateIntersections(IPhysicalAgent physicalAgent);

    IBoundary getBoundary();



}
