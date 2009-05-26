/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.report;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.report.IReportWriter;
import com.ixcode.framework.io.csv.CSVWriter;

import java.io.File;
import java.io.IOException;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public abstract class ReportWriterBase implements IReportWriter {
    protected ReportWriterBase(boolean append) {
        _append = append;
    }


    public void write(File output, IExperiment experiment, String experimentOutputId,  ParameterMap currentParameters, ExperimentProgress progress) throws IOException {
        boolean writeHeaders = false;
        if (_append && !output.exists()) {
            writeHeaders = true;
        } else if (!_append) {
            writeHeaders = true;
        }

        CSVWriter out = new CSVWriter(output, _append);
        if (writeHeaders) {
            writeHeaders(out, experiment, experimentOutputId,  currentParameters, progress);
        }

        writeResults(out, experiment, experimentOutputId,  currentParameters, progress);
        out.close();

    }

    protected abstract void writeHeaders(CSVWriter out, IExperiment experiment, String experimentId,  ParameterMap currentParameters, ExperimentProgress progress);

    protected abstract void writeResults(CSVWriter out, IExperiment experiment, String experimentId,  ParameterMap currentParameters, ExperimentProgress progress);

    public boolean isAppend() {
        return _append;
    }


    boolean _append;

}
