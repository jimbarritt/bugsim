/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.diagnostic;

import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.physical.PhysicalAgentBase;

import java.util.ArrayList;
import java.util.List;

/**
 *  Description : Really stupid agen that just represents a line! so we can draw it on the screen and test the intersection stuff
 */
public class LineAgent extends PhysicalAgentBase  {
    public void setPoints(List points) {
        _points = points;
    }

    public static final String AGENT_CLASS_ID = "Line Agent";
    public LineAgent(Location location) {
        super(AGENT_CLASS_ID, location);
        _endLocation = location;
    }

    public void executeTimeStep(Simulation simulation) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Location getEndLocation() {
        return _endLocation;
    }

    public void setEndLocation(Location endLocation) {
        _endLocation = endLocation;
    }


    public List getPoints() {
        return _points;
    }

    private Location _endLocation;


    private List _points = new ArrayList();
}
