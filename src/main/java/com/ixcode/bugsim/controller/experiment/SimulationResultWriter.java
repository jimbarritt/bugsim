/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.io.csv.CSVWriter;

import java.io.File;
import java.io.IOException;

/**
 *  Description : Writes the simulation results to a CSV File.
 */
public abstract class SimulationResultWriter {

    public SimulationResultWriter() {
    }


    public void saveCabbageExperimentResults(Simulation simulation, File output) throws IOException {
        CSVWriter out = new CSVWriter(output);

        writeSimulationHeader(out, simulation);
        writeResults(out, simulation);
        out.close();

    }

    protected abstract void writeResults(CSVWriter out, Simulation simulation);

    private void writeSimulationHeader(CSVWriter out, Simulation simulation) {
        out.writeProperty(Simulation.PROPERTY_TITLE, simulation.getTitle());
        out.writeProperty(Simulation.PROPERTY_EXECUTED_TIMESTEPS, simulation.getExecutedTimesteps());
        out.println();
    }
}
