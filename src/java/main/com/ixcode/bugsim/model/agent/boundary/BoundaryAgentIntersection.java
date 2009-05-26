/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.boundary;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BoundaryAgentIntersection {

    public BoundaryAgentIntersection(IBoundaryAgent boundary, IPhysicalAgent agent, RectangularCoordinate coord) {
        _boundary = boundary;
        _agent = agent;
        _coord = coord;
    }


    public IBoundaryAgent getBoundaryAgent() {
        return _boundary;
    }

    public IPhysicalAgent getAgent() {
        return _agent;
    }

    public RectangularCoordinate getCoord() {
        return _coord;
    }

    private IBoundaryAgent _boundary;
    private IPhysicalAgent _agent;
    private RectangularCoordinate _coord;
}
