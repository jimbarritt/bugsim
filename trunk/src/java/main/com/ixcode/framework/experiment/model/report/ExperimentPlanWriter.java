/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.report;

import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.parameter.model.ParameterMap;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentPlanWriter extends ReportWriterBase {



    public ExperimentPlanWriter() {
        super(false);

    }

    protected void writeHeaders(CSVWriter out, IExperiment experiment, String experimentId, ParameterMap currentParameters, ExperimentProgress progress) {
        CSVRow header = new CSVRow(8);
        header.setString(0, "experiment.name");
        header.setString(1, "plan.name");
        header.setString(2, "plan.trial.name");
        header.setString(3, "experiment.id");
        header.setString(4, "plan.description");
        header.setString(5, "experiment.iterations");
        header.setString(6, "experiment.replicates");
        header.setString(7, "experiment.outputDirName");

        out.writeRow(header);
    }


    protected void writeResults(CSVWriter out, IExperiment experiment, String experimentId, ParameterMap currentParameters, ExperimentProgress progress) {
        CSVRow data = new CSVRow(8);

        data.setString(0, experiment.getExperimentPlan().getExperimentName());
        data.setString(1, experiment.getExperimentPlan().getName());
        data.setString(2, experiment.getExperimentPlan().getTrialName());
        data.setString(3, experimentId);
        data.setString(4, experiment.getExperimentPlan().getDescription());
        data.setLong(5, progress.getIterationCount());
        data.setLong(6, progress.getReplicateCount());
        data.setString(7, experiment.getExperimentPlan().getOutputDirName());



        out.writeRow(data);
    }


}
