/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.boundary;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.boundary.CircularBoundary;
import com.ixcode.framework.simulation.model.landscape.boundary.IBoundary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 * @todo maybe move this into the framework in the boundary package.
 * @todo rename to be elliptical boundary and have a dimension instead of a radius ?
 */
public class CircularBoundaryAgent extends BoundaryAgentBase implements IBoundaryAgent {


    public static final String AGENT_CLASS_ID = "CircularBoundaryAgent";

    public CircularBoundaryAgent(String name, Location location, double radius) {
        super(CircularBoundaryAgent.AGENT_CLASS_ID, location, name);
       _boundary = new CircularBoundary(location.getCoordinate(), radius);
    }



    public IBoundary getBoundary() {
        return _boundary;
    }

    public CircularBoundary getCircularBoundary() {
        return _boundary;
    }

    /**
     * The line boundary is simple the circular boundary will not be so simple.
     * @param physicalAgent
     * @return
     */
    public List calculateIntersections(IPhysicalAgent physicalAgent) {
        List ixs = new ArrayList();
        List ixCoords =  physicalAgent.circleIntersections(_boundary.getLocation(), _boundary.getRadius());
        for (Iterator itr = ixCoords.iterator(); itr.hasNext();) {
            RectangularCoordinate coordinate = (RectangularCoordinate)itr.next();
            ixs.add(new BoundaryAgentIntersection(this, physicalAgent, coordinate));
        }
        return ixs;

    }


    private CircularBoundary _boundary;
}
