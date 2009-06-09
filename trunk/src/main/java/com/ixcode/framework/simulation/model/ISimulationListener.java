/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model;

import com.ixcode.framework.simulation.model.agent.IAgent;

public interface ISimulationListener {


    void agentAdded(Simulation simulation, IAgent agent);

    void agentDeath(Simulation simulation, IAgent agent);

    void agentEscaped(Simulation simulation, IAgent agent);

    //Dont add iteration completed here!! use ExperimentListener instead...Experim

}
