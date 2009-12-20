/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.boundary;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.boundary.IBoundary;
import com.ixcode.framework.simulation.model.landscape.boundary.LinearBoundary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 * @todo maybe move this into the framework in the boundary package.
 */
public class LinearBoundaryAgent extends BoundaryAgentBase{

    public static final String AGENT_CLASS_ID = "lineBoundaryAgent";

    public LinearBoundaryAgent(String name, Location startLocation, Location endLocation) {
        super(AGENT_CLASS_ID, startLocation, name);
        _boundary = new LinearBoundary(startLocation.getCoordinate(), endLocation.getCoordinate());
    }

    public IBoundary getBoundary() {
        return _boundary;
    }

    public LinearBoundary getLinearBoundary() {
        return _boundary;
    }


    /**
     * The line boundary is simple the circular boundary will not be so simple.
     * @param physicalAgent
     * @return
     */
    public List calculateIntersections(IPhysicalAgent physicalAgent) {
        List ixs = new ArrayList();
        List ixCoords =  physicalAgent.lineIntersections(_boundary.getStartLocation(), _boundary.getEndLocation());
        for (Iterator itr = ixCoords.iterator(); itr.hasNext();) {
            RectangularCoordinate coordinate = (RectangularCoordinate)itr.next();
            ixs.add(new BoundaryAgentIntersection(this, physicalAgent, coordinate));
        }
        return ixs;
    }


    private LinearBoundary _boundary;
}


