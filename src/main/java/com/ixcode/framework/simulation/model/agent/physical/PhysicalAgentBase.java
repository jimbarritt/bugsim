/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.physical;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.AgentBase;
import com.ixcode.framework.simulation.model.landscape.Location;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 *
 * Make this extend ModelBase to get property change support and put in property change support to the base clasee
 */
public abstract class PhysicalAgentBase extends AgentBase implements IPhysicalAgent, Serializable {
    public int getAge() {
        return _age;
    }

    protected void incrAge() {
       _age++;
    }

    public void executeTimeStep(Simulation simulation) {
       //defualt implementation is to do nothing.
    }

    protected void behaviourOccured(AgentBehaviour behaviour) {
        _behaviourRecord.put(behaviour.getName(), new Integer(_age));
    }

    public int getAgeOfLastBehaviour(AgentBehaviour agentBehaviour) {
        int age = -1;
        if (_behaviourRecord.containsKey(agentBehaviour.getName())) {
            age = ((Integer)_behaviourRecord.get(agentBehaviour.getName())).intValue();
        }
        return age;
    }


    public static final String PROPERTY_LOCATION = "location";

    protected PhysicalAgentBase(String agentClassId, Location location) {
        super(agentClassId);
        _location = location;

    }


    public List lineIntersections(RectangularCoordinate startCoord, RectangularCoordinate endCoord) {
        return null;
    }

    public List circleIntersections(RectangularCoordinate center, double radius) {
        return null;
    }


    public Location getLocation() {
        return _location;
    }

    protected void setLocation(Location location) {
        Location oldValue = _location;
       _location = location;
        firePropertyChangeEvent(PROPERTY_LOCATION, oldValue, _location);
    }

    public void kill(Simulation simulation) {
        simulation.registerAgentDeath(this);
        setAlive(false);
        setEscaped(false); // cannot be dead and escaped
        if (log.isDebugEnabled()) {
            log.debug("killAgent: " + getId());
        }
    }

    public void escaped(Simulation simulation) {
        simulation.registerAgentEscaped(this);
        setEscaped(true);
        if (log.isDebugEnabled()) {
            log.debug("escapedAgent: " + getId());
        }
    }

    protected void setAlive(boolean alive) {
        _isAlive = alive;
    }



    protected void setEscaped(boolean escaped) {
        _isEscaped = escaped;
    }

    public boolean isDead() {
        return !_isAlive;
    }

    public boolean isAlive() {
        return _isAlive;
    }

    public boolean isEscaped() {
        return _isEscaped;
    }

    public boolean isInSimulation() {
        return !_isEscaped;
    }


    public boolean containsPoint(RectangularCoordinate coord) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean intersectsLine(RectangularCoordinate p1, RectangularCoordinate p2) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private Location _location;
    private boolean _isAlive = true;
    private boolean _isEscaped = false;

    private static final Logger log = Logger.getLogger(PhysicalAgentBase.class);
    private int _age;
    private Map _behaviourRecord = new HashMap();
}
