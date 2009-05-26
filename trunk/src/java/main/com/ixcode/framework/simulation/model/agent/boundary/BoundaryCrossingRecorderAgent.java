/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.boundary;

import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.Intersection;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.ISimulationListener;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.AgentBase;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.motile.MotileAgentBase;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryCrossing;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryCrossingType;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import org.apache.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : Records when agents cross a boundary (can be circular)
 *
 * @todo should be able to extract a base class from this and the dispersal Recorder
 * @todo maybe we can have this draw itself through a renderer
 */
public class BoundaryCrossingRecorderAgent extends AgentBase implements PropertyChangeListener, ISimulationListener {


    public static final String AGENT_CLASS_ID = "BoundaryRecorder";



    public BoundaryCrossingRecorderAgent(Grid boundaryGrid, Simulation simulation, IAgentFilter filter) {
        super(BoundaryCrossingRecorderAgent.AGENT_CLASS_ID);
        _agentFilter = filter;

        listenToAgents(simulation.getLiveAgents(_agentFilter));
        simulation.addSimulationListener(this);

        _simulation = simulation;
        _boundaryGrid = boundaryGrid;

    }

    public void tidyUp(Simulation simulation) {
        super.tidyUp(simulation);
        stopListening();
    }


    public void timestepCompleted(Simulation simulation) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void stopListening() {
        _simulation.removeSimulationListener(this);
    }

    public List getCrossings() {
        return _crossings;
    }

    public void agentAdded(Simulation simulation, IAgent agent) {
        if (_agentFilter.acceptAgent(agent)) {

            agent.addPropertyChangeListener(MotileAgentBase.PROPERTY_LOCATION, this);
        }
    }

    public void agentDeath(Simulation simulation, IAgent agent) {
        if (_agentFilter.acceptAgent(agent)) {
            if (log.isDebugEnabled()) {
                log.debug("BoundaryRecorder stop listneing to dead : " + agent.getId());
            }
            agent.removePropertyChangeListener(MotileAgentBase.PROPERTY_LOCATION, this);
        }
    }

    public void agentEscaped(Simulation simulation, IAgent agent) {
        if (_agentFilter.acceptAgent(agent)) {
            if (log.isDebugEnabled()) {
                log.debug("BoundaryRecorder stop listneing to escaped : " + agent.getId());
            }
            agent.removePropertyChangeListener(MotileAgentBase.PROPERTY_LOCATION, this);
        }
    }

    public void iterationCompleted(Simulation simulation) {

    }

    private void listenToAgents(List agents) {
        for (Iterator itr = agents.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            agent.addPropertyChangeListener(MotileAgentBase.PROPERTY_LOCATION, this);
        }
    }

    /**
     * Note this relies on the agent having set its heading correctly before setting the new location
     * if the heading is not set yet then this will record the previous heading!
     *
     * @param evt
     */
    public void propertyChange(PropertyChangeEvent evt) {
        MotileAgentBase agent = (MotileAgentBase)evt.getSource();


        RectangularCoordinate oldCoord = ((Location)evt.getOldValue()).getCoordinate();
        RectangularCoordinate newCoord = ((Location)evt.getNewValue()).getCoordinate();


        Intersection intersection = _boundaryGrid.enteredBoundary(oldCoord, newCoord);
        if (intersection.intersects()) {
            RectangularCoordinate ix = intersection.getCoordinate();
            CartesianBounds boundaryBounds = _boundaryGrid.getBounds();
            boolean isCircular = _boundaryGrid.isCircular();
            BoundaryCrossing crossing = createCrossing(ix, newCoord, agent, boundaryBounds, isCircular, oldCoord, BoundaryCrossingType.ENTRY);
            if (log.isDebugEnabled()) {
                log.debug("Boundary Crossing: agent: " + agent.getId() + ", " + crossing);
            }
            _crossings.add(crossing);
        }

    }

    private BoundaryCrossing createCrossing(RectangularCoordinate ix, RectangularCoordinate newCoord, MotileAgentBase agent,
                                            CartesianBounds boundaryBounds, boolean circular, RectangularCoordinate oldCoord, BoundaryCrossingType crossingType) {
        double d = ix.calculateDistanceTo(newCoord);
        double h = agent.getAzimuth();
        AzimuthCoordinate displacement = boundaryBounds.getCentre().calculateAzimuthCoordinateTo(ix);
        double I = displacement.getAzimuth();
        double r = boundaryBounds.getDoubleWidth() / 2;
        double q = displacement.getDistance();
        BoundaryCrossing crossing = new BoundaryCrossing(boundaryBounds, circular, oldCoord, newCoord, ix, h, d, crossingType, I, r, q);

        agent.addBoundaryIntersection(ix);
        return crossing;
    }


    public void executeTimeStep(Simulation simulation) {

    }

    public void executeAction(Simulation simulation) {
        Long timestep = new Long(simulation.getExecutedTimesteps() + 1);


    }


    private double[] toDoubleArray(List doubleList) {
        double[] disps = new double[doubleList.size()];
        int iBf = 0;
        for (Iterator iDisp = doubleList.iterator(); iDisp.hasNext();) {
            Double d = (Double)iDisp.next();
            disps[iBf] = d.doubleValue();
            iBf++;
        }
        return disps;
    }


    public String toString() {
        return "BoundaryRecorder: boundary=" + _boundaryGrid;
    }


    private IAgentFilter _agentFilter;
    private static final Logger log = Logger.getLogger(BoundaryCrossingRecorderAgent.class);

    private List _crossings = new ArrayList();
    private Grid _boundaryGrid;
    private Simulation _simulation;
}
