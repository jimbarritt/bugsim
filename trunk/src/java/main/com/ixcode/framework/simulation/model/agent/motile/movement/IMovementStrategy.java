/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.landscape.Location;

public interface IMovementStrategy {

    Move calculateNextLocation(Simulation simulation, IMotileAgent butterflyAgent);

    String getParameterSummary();

    double getInitialMoveLength();


}
