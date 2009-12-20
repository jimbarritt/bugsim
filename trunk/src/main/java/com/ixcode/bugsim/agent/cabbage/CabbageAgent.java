/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.cabbage;

import com.ixcode.framework.math.BigDecimalMath;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.physical.PhysicalAgentBase;
import com.ixcode.framework.simulation.model.agent.resource.IResourceAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CabbageAgent extends PhysicalAgentBase implements ISignalSource, IResourceAgent {


    public static final String AGENT_CLASS_ID = "Cabbage";

    public CabbageAgent(long cabbageId, Location location, BigDecimal radius) {
        super(AGENT_CLASS_ID, location);
        _radius = radius;
        _radiusDouble = radius.doubleValue();
        _cabbageId = cabbageId;

    }

    public CabbageAgent(long cabbageId, Location location, double radius) {
        super(AGENT_CLASS_ID, location);
        _radius = BigDecimalMath.accurateOut(radius);
        _radiusDouble = radius;
        _cabbageId = cabbageId;
    }

    public void executeTimeStep(Simulation simulation) {

    }

    /**
     * @return
     */
    public double getRadiusDouble() {
        return _radiusDouble;
    }



    public double getSize() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean withinRange(Location location) {
        return true;
    }

    /**
     * @todo implement contains point with a circular boundary agent
     * @param coord
     * @return
     */
    public boolean containsPoint(RectangularCoordinate coord) {
        return Geometry.isPointInCircleDouble(coord.getDoubleX(), coord.getDoubleY(), super.getLocation().getDoubleX(), super.getLocation().getDoubleY(), getRadiusDouble());
    }

    public boolean intersectsLine(RectangularCoordinate p1, RectangularCoordinate p2) {
        RectangularCoordinate centre = getLocation().getCoordinate();
        return Geometry.lineInstersectsCircleDouble(p1.getDoubleX(), p1.getDoubleY(), p2.getDoubleX(), p2.getDoubleY(), centre.getDoubleX(), centre.getDoubleY(), getRadiusDouble());
    }


    public List lineIntersections(RectangularCoordinate startCoord, RectangularCoordinate endCoord) {
        return Geometry.findLineSegmentCircleIntersections(super.getLocation().getCoordinate(), _radiusDouble, startCoord, endCoord, true);
    }


    public long getResourceId() {
        return _cabbageId;
    }

    public long getEggCount() {
        return _eggCount;
    }

    public void setEggCount(long eggCount) {
        long oldValue = _eggCount;
        _eggCount = eggCount;
        super.firePropertyChangeEvent(PROPERTY_EGG_COUNT, oldValue, _eggCount);
    }

    public void addEgg() {
        setEggCount(getEggCount() + 1);
    }

    public void resetEggCount() {
        setEggCount(0);
    }



    public String toString() {
        return "Cabbage" + super.getLocation() + ": eggCount: " + _eggCount + ", radius: " + _radius;
    }

    private BigDecimal _radius = BigDecimalMath.accurateOut(10.0);
    private double _radiusDouble=10d;

    private long _cabbageId;

    private long _eggCount = 0;
    public static final String PROPERTY_EGG_COUNT = "eggCount";
}
