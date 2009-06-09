/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.matchstick;

import com.ixcode.bugsim.model.agent.boundary.BoundaryAgentFilter;
import com.ixcode.bugsim.model.agent.boundary.BoundaryAgentIntersection;
import com.ixcode.bugsim.model.agent.boundary.IBoundaryAgent;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.ISimulationListener;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.physical.PhysicalAgentBase;
import com.ixcode.framework.simulation.model.landscape.Location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class MatchstickAgent extends PhysicalAgentBase implements ISimulationListener {
    public static final String AGENT_CLASS_ID = "matchstickAgent";

    public MatchstickAgent(Location location, double length, double heading) {
        this(location, length, heading, null);
    }
    public MatchstickAgent(Location location, double length, double heading, String boundaryName) {
        this(location, length, heading, boundaryName, true);

    }
    public MatchstickAgent(Location location, double length, double heading, String boundaryName, boolean startInCentre) {
        super(AGENT_CLASS_ID, location);
        _length = length;

        if (startInCentre) {
            _startCoord = location.getCoordinate().moveTo(new AzimuthCoordinate(heading, length / 2));
            _endCoord = location.getCoordinate().moveTo(new AzimuthCoordinate(AzimuthCoordinate.getReverseAzimuth(heading), length / 2));
        } else {
            _startCoord = location.getCoordinate();
            _endCoord = location.getCoordinate().moveTo(new AzimuthCoordinate(heading, length));
        }

        _heading = heading;
        _boundaryName = boundaryName;

        
    }


    public RectangularCoordinate getStartCoord() {
        return _startCoord;
    }

    public RectangularCoordinate getEndCoord() {
        return _endCoord;
    }

    public double getHeading() {
        return _heading;
    }

    public double calculateIntersectionD(RectangularCoordinate i) {
        double dx = i.getDoubleX() - _startCoord.getDoubleX();
        double dy = i.getDoubleY() - _startCoord.getDoubleY();
        return Math.sqrt((dx * dx) + (dy * dy));
    }


    public void registerSimulation(Simulation simulation) {
        super.registerSimulation(simulation);
        checkForIntersections(simulation);
        simulation.addSimulationListener(this);
    }


    public void tidyUp(Simulation simulation) {
        super.tidyUp(simulation);
        simulation.removeSimulationListener(this);
    }

    public void agentAdded(Simulation simulation, IAgent agent) {
        if (BoundaryAgentFilter.INSTANCE.acceptAgent(agent)) {
            checkForIntersection((IBoundaryAgent)agent);
        }
    }


    /**
     * Check to see if the matchstick has intersected with any other Physical agents if so add
     * the intersection location to the list
     *
     * @param simulation
     */
    public void executeTimeStep(Simulation simulation) {
        // Only need to do this if either the matchstick or the boundary can move... then need to clear down all the intersections
//        checkForIntersections(simulation);
    }

    private void checkForIntersections(Simulation simulation) {
        List boundaryAgents = simulation.getLiveAgents(BoundaryAgentFilter.INSTANCE);

        for (Iterator itr = boundaryAgents.iterator(); itr.hasNext();) {
            IBoundaryAgent boundary = (IBoundaryAgent)itr.next();

            checkForIntersection(boundary);

        }
    }

    public void iterationCompleted(Simulation simulation) {

    }


    private void checkForIntersection(IBoundaryAgent boundary) {
        if (_boundaryName != null && _boundaryName.equals(boundary.getName())) {
            List intersections = boundary.calculateIntersections(this);

            if (intersections != null && intersections.size() > 0) {
                addIntersections(intersections);
            }
        }
    }

    private void addIntersections(List intersections) {
        _intersections.addAll(intersections);
        super.firePropertyChangeEvent("intersections", null, intersections);
    }



    public List getIntersections() {
        return _intersections;
    }

    public double getLength() {
        return _length;
    }

    public List lineIntersections(RectangularCoordinate startCoord, RectangularCoordinate endCoord) {

        RectangularCoordinate intersection = Geometry.findSegmentSegmentIntersection(startCoord, endCoord, _startCoord, _endCoord);
        List intersections = null;

        if (intersection != null) {
            intersections = new ArrayList();
            intersections.add(intersection);
        }

        return intersections;

    }

    public List circleIntersections(RectangularCoordinate center, double radius) {
        List ixs =  Geometry.findLineSegmentCircleIntersections(center, radius, _startCoord, _endCoord, true);
        return ixs;
    }

    public BoundaryAgentIntersection getClosestIntersection() {
        double min = Double.MAX_VALUE;
        BoundaryAgentIntersection closestIx = null;
        if (_intersections.size()>0) {
            for (Iterator itr = _intersections.iterator(); itr.hasNext();) {
                BoundaryAgentIntersection ix = (BoundaryAgentIntersection)itr.next();
                double d = calculateIntersectionD(ix.getCoord());
                if (DoubleMath.precisionLessThanEqual(d, min, DoubleMath.DOUBLE_PRECISION_DELTA)) {
                    closestIx = ix;
                }
            }
        }
        return closestIx;
    }

    public String toString() {
        return "Matchstick Agent(" + super.getId() + "), length: " + _length + ", instersectionCount: " + _intersections.size() + ", heading: " + _heading;
    }

    public void agentDeath(Simulation simulation, IAgent agent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void agentEscaped(Simulation simulation, IAgent agent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void timestepCompleted(Simulation simulation) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private double _length;
    private List _intersections = new ArrayList();
    private RectangularCoordinate _startCoord;
    private RectangularCoordinate _endCoord;
    private double _heading;

    private String _boundaryName;
}
