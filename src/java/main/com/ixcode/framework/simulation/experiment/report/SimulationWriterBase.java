/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.report;

import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.simulation.model.Simulation;

import java.io.File;
import java.io.IOException;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public abstract class SimulationWriterBase  implements ISimulationWriter {

    protected SimulationWriterBase(boolean append) {
        _append = append;
    }


    public void write(File output, Simulation simulation, String experimentId, long currentIteration, long currentReplicant) throws IOException {
        boolean writeHeaders = false;
        if (_append && !output.exists())  {
            writeHeaders = true;
        } else if (!_append) {
            writeHeaders = true;
        }

        CSVWriter out = new CSVWriter(output, _append);
        if (writeHeaders) {
            writeHeaders(out, simulation, experimentId, currentIteration, currentReplicant);
        }

        writeResults(out, simulation, experimentId, currentIteration, currentReplicant);
        out.close();

    }

    public boolean isAppend() {
        return _append;
    }

    protected abstract void writeHeaders(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate);
    protected abstract void writeResults(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) throws IOException;
    boolean _append;

}
