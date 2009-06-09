/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile;

import com.ixcode.framework.math.geometry.ICoordinatePath;
import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.physical.PhysicalAgentBase;
import com.ixcode.framework.simulation.model.agent.motile.movement.Move;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.LocationPath;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public abstract class MotileAgentBase extends PhysicalAgentBase implements IMotileAgent {


    protected MotileAgentBase(String agentClassId, Location location, double radius, double initialDirection, boolean recordHistory) {
        super(agentClassId, location);
        _recordHistory = recordHistory;
        _locationPath = new LocationPath(location);
        _heading = initialDirection;
        _radius = radius;
        _initialLocation = location;

    }

    /**
     * Calculates the displacement distance this butterflie was at after a certain age.
     * If its not recording history, it will allways return its current location distance
     *
     * @param age
     * @return
     */
    public double calculateDisplacementDistance() {
        return _initialLocation.getCoordinate().calculateDistanceTo(getLocation().getCoordinate());
    }

    public Location getInitialLocation() {
        return _initialLocation;
    }


    protected void addNewLocationToPath(Location newLocation) {
        _locationPath.addLocation(newLocation);
    }

    public Location getLocation(int iStep) {
        return _locationPath.getLocation(iStep);
    }


    public ICoordinatePath getLocationPathAsCoordinatePath() {
        return _locationPath;
    }

    public void moveTo(double azimuth, double distance) {
        RectangularCoordinate newCoord = getLocation().getCoordinate().moveTo(new AzimuthCoordinate(azimuth, distance));
        Location newLocation = new Location(newCoord);
        moveTo(newLocation, azimuth, null);

    }

    public void moveTo(Move move, Location newLocation) {
        double heading = getLocation().getCoordinate().calculateAzimuthTo(newLocation.getCoordinate());
        moveTo( newLocation, heading, move);

    }

    public void moveTo(Location newLocation, double heading, Move move) {
        setHeading(heading);
        super.setLocation(newLocation);
        _lastMove = move;        
        addNewLocationToPath(newLocation);
    }

    public double getAzimuth() {
        return _heading;
    }


    public double getRadius() {
        return _radius;
    }

    public boolean getIsRecordHistory() {
        return _recordHistory;
    }

    public void setHeading(double heading) {
        _heading = heading;
    }

    /**
     * Added everytime this agent crosses a boundary so you can keep track of them.
     *
     * @param ix
     */
    public void addBoundaryIntersection(RectangularCoordinate ix) {
        _boundaryIntersections.add(ix);
    }

    public List getBoundaryIntersections() {
        return _boundaryIntersections;
    }


    public Move getLastMove() {
        return _lastMove;
    }


    private LocationPath _locationPath;

    private double _heading;
    private double _radius;
    private boolean _recordHistory;
    private Location _initialLocation;
    //private RectangularCoordinate _boundaryIntersection;
    private List _boundaryIntersections = new ArrayList();

    private Move _lastMove;
}
