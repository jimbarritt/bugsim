/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model;

/**
 * This allows us to stop the iteration any time inbetween each agent's timestep.
 * be sure not to put anything too complex in terms of time here or it'll slow the ass of the run.
 */
public interface ISimulationDisruptor {

    boolean haltSimulation(Simulation simulation);
}
