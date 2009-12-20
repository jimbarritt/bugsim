/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement.sensory;

import com.ixcode.bugsim.agent.butterfly.FieldOfViewVisualStrategy;
import com.ixcode.bugsim.agent.butterfly.IVisionStrategy;
import com.ixcode.bugsim.agent.butterfly.IForagingStrategy;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.motile.IVisualAgent;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.Move;
import com.ixcode.framework.simulation.model.agent.physical.PhysicalAgentBase;
import com.ixcode.framework.simulation.model.landscape.Location;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class MockViewer extends PhysicalAgentBase implements IVisualAgent , IMotileAgent {

    public MockViewer(Location location) {
        super("DummyViewer", location);
        _visionStrategy = new FieldOfViewVisualStrategy(100, 100);
    }

    public void executeTimeStep(Simulation simulation) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public IVisionStrategy getVisionStrategy() {
        return _visionStrategy;
    }

    public IForagingStrategy getForagingStrategy() {
        return null;
    }

    public void moveTo(Move move, Location newLocation) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void moveTo(double newHeading, double direction) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public double getAzimuth() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double getInitialMoveLength() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IMovementStrategy getMovementStrategy() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private FieldOfViewVisualStrategy _visionStrategy;
}
