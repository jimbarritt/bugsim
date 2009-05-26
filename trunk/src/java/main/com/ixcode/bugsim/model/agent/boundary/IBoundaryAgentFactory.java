/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.boundary;

import com.ixcode.framework.simulation.model.Simulation;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 *
 * //        RectangularCoordinate center = landscape.getLogicalBounds().getCentre();
//        LinearBoundaryAgent linearBoundary = new LinearBoundaryAgent(new Location(center.getDoubleX() - 50, center.getDoubleY()), new Location(center.getDoubleX() + 50, center.getDoubleY()));

//         CircularBoundaryAgent circularBoundary = new CircularBoundaryAgent(new Location(center.getDoubleX() , center.getDoubleY()-50), 50d);
//        _simulation.addAgent(circularBoundary);

 */
public interface IBoundaryAgentFactory {

    IBoundaryAgent createBoundaryAgent(Simulation simulation);
}
