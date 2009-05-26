/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.simulation.experiment.report.SimulationWriterBase;
import com.ixcode.framework.simulation.model.Simulation;

import java.io.File;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 * @deprecated
 */
public class SimulationParameterWriter extends SimulationWriterBase {


    public SimulationParameterWriter(boolean append, long loopIndex, File loopDir) {
        super(append);
        _loopIndex = loopIndex;
        _loopDir = loopDir;
    }

    protected void writeHeaders(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
//        List propertyNames = experiment.getProperties().getPropertyNames();
//        writeHeader(propertyNames, out);
        throw new UnsupportedOperationException("This method is no longer valid");
    }

    protected void writeResults(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
//        List propertyNames = experiment.getProperties().getPropertyNames();
//        writeData(propertyNames, out, experiment);
        throw new UnsupportedOperationException("This method is no longer valid");
    }

    private void writeHeader(List propertyNames, CSVWriter out) {

        final int columnCount = propertyNames.size() + SYSCOL_COUNT;
        CSVRow header = new CSVRow(columnCount);
        header.setString(0, Simulation.PROPERTY_TITLE);
        header.setString(1, "iteration");
        header.setString(2, Simulation.PROPERTY_EXECUTED_TIMESTEPS);
        header.setString(3, "outputDir");
        for (int i = SYSCOL_COUNT; i < columnCount; ++i) {
            String name = (String)propertyNames.get(i - SYSCOL_COUNT);
            header.setString(i, name);
        }

        out.writeRow(header);
    }

    private void writeData(List propertyNames, CSVWriter out, IExperiment experiment) {
        final int columnCount = propertyNames.size() + SYSCOL_COUNT;
        CSVRow data = new CSVRow(columnCount);
        Simulation simulation = experiment.getSimulation();
        data.setString(0, simulation.getTitle());
        data.setLong(1, _loopIndex + 1);
        data.setLong(2, simulation.getExecutedTimesteps());
        data.setString(3, _loopDir.getName());
        for (int i = SYSCOL_COUNT; i < columnCount; ++i) {
            String name = (String)propertyNames.get(i - SYSCOL_COUNT);
            data.setObject(i, experiment.getProperties().getPropertyValue(name));
        }

        out.writeRow(data);
    }

    private long _loopIndex;
    private File _loopDir;
    private final static int SYSCOL_COUNT = 4;

}
