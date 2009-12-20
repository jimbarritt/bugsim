/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.butterfly;

import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.bugsim.agent.butterfly.ButterflyAgent;

public interface IForagingStrategy {
    long getInitialNumberOfEggs();

    Location forage(ButterflyAgent butterfly, Location next, Simulation simulation);

    String getParameterSummary();
}
