/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.model.ModelBase;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.motile.MotileAgentBase;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Description : Add this to the agents to kill them off when they go out of bounds
 * @todo maybe this should be an agent itself?
 */
public class EscapingAgentCatcher extends ModelBase implements PropertyChangeListener, ISimulationListener {
    public void stopListening() {
        _simulation.removeSimulationListener(this);
    }

    public void tidyUp() {
        stopListening();
        _simulation.setEscapingAgentCatcher(null);
    }

    public CartesianBounds getBounds() {
        return _bounds;
    }


    public static final String PROPERTY_NUMBER_OF_ESCAPED_AGENTS = "numberOfEscapedAgents";

    public EscapingAgentCatcher(Simulation simulation, CartesianBounds bounds, boolean isCircular, IAgentFilter agentFilter) {
        _simulation = simulation;
        _bounds = bounds;
        _isCircular = isCircular;
        _simulation.addSimulationListener(this);
        _agentFilter = agentFilter;
        _simulation.setEscapingAgentCatcher(this);

    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(MotileAgentBase.PROPERTY_LOCATION)) {
            IPhysicalAgent agent = (IPhysicalAgent)evt.getSource();
            RectangularCoordinate c = agent.getLocation().getCoordinate();

            boolean escaped = isPointOutsideBounds(c);

            if (escaped) {
                if (agent instanceof IMotileAgent) {
                    ((IMotileAgent)agent).escaped(_simulation);
                }
            }


        }
    }

    public boolean isPointOutsideBounds(RectangularCoordinate c) {

        boolean escaped = false;
        if (_isCircular) {
            escaped = !Geometry.isPointInCircleDouble(c.getDoubleX(), c.getDoubleY(), _bounds.getDoubleCentreX(), _bounds.getDoubleCentreY(), _bounds.getRadiusOfInnerCircle());
        } else {
            escaped = !_bounds.isInside(c);
        }
        return escaped;
    }

    public boolean pathEntersBounds(RectangularCoordinate rel, RectangularCoordinate proj) {
        boolean intersects = false;
        if (_isCircular) {
            boolean fastIx =  Geometry.lineInstersectsCircleDouble(rel.getDoubleX(), rel.getDoubleY(), proj.getDoubleX(), proj.getDoubleY(), _bounds.getDoubleCentreX(), _bounds.getDoubleCentreY(), _bounds.getDoubleHalfWidth());
            if (fastIx) {
                List ixs = Geometry.findLineSegmentCircleIntersections(_bounds.getDoubleCentreX(),_bounds.getDoubleCentreY(),  _bounds.getDoubleHalfWidth(), rel, proj, false);
                intersects = ixs.size() >1; // must enter AND EXIT
            }
        } else {
            throw new IllegalStateException("Rectangular Boundary Not supported");
        }
        return intersects;
    }



    public void agentAdded(Simulation simulation, IAgent agent) {
        if (_agentFilter.acceptAgent(agent)) {
            agent.addPropertyChangeListener(MotileAgentBase.PROPERTY_LOCATION, this);
        }
    }

    public void agentEscaped(Simulation simulation, IAgent agent) {
        if (_agentFilter.acceptAgent(agent)) {
            setNumberOfEscapedAgents(getNumberOfEscapedAgents() + 1); // various events get fired when we kill it so its important that our state is updated first.
            agent.removePropertyChangeListener(MotileAgentBase.PROPERTY_LOCATION, this);
        }
    }

    private void setNumberOfEscapedAgents(long escapedAgents) {
        Long oldVal = new Long(_numberOfEscapedAgents);
        _numberOfEscapedAgents = escapedAgents;
        firePropertyChangeEvent(PROPERTY_NUMBER_OF_ESCAPED_AGENTS, oldVal, new Long(_numberOfEscapedAgents));
    }

    public void agentDeath(Simulation simulation, IAgent agent) {
        if (_agentFilter.acceptAgent(agent)) {
            agent.removePropertyChangeListener(MotileAgentBase.PROPERTY_LOCATION, this);
        }
    }

    public void timestepCompleted(Simulation simulation) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void iterationCompleted(Simulation simulation) {

    }


    public long getNumberOfEscapedAgents() {
        return _numberOfEscapedAgents;
    }


    private Simulation _simulation;
    private CartesianBounds _bounds;
    private long _numberOfEscapedAgents;


    private boolean _isCircular;
    private IAgentFilter _agentFilter;
}


