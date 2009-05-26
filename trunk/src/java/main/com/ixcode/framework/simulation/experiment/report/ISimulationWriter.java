/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.report;

import com.ixcode.framework.simulation.model.Simulation;

import java.io.File;
import java.io.IOException;

public interface ISimulationWriter {

    void write(File output, Simulation simulation, String experimentId, long currentIteration, long currentReplicant) throws IOException;
}
