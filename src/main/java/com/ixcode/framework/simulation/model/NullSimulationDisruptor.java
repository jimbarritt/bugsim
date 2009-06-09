/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model;

import com.ixcode.framework.simulation.model.ISimulationDisruptor;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class NullSimulationDisruptor implements ISimulationDisruptor {

    public static final ISimulationDisruptor INSTANCE = new NullSimulationDisruptor();

    public boolean haltSimulation(Simulation simulation) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
