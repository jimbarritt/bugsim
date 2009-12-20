/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.report;

import com.ixcode.framework.simulation.experiment.report.BoundaryCrossingWriter;
import com.ixcode.framework.experiment.model.report.ExperimentReportController;
import com.ixcode.framework.experiment.model.report.IExperimentReporter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.bugsim.experiment.IBugsimExperiment;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class BoundaryCrossingSummaryReporter implements IExperimentReporter {


    public BoundaryCrossingSummaryReporter() {


    }

    public void reportResults(String rootPath, String experimentId, IExperiment experiment, ParameterMap currentParameters, ExperimentProgress progress, boolean paramStructureChanged, boolean outputDetails) throws IOException {
        IBugsimExperiment edgeEffectExperiment = (IBugsimExperiment)experiment;

        long iteration = progress.getCurrentIteration();
        long replicant = progress.getCurrentReplicate();
        Simulation simulation = edgeEffectExperiment.getSimulation();
        File rootDir = new File(rootPath);



        reportBoundaryCrossings(rootDir, simulation,edgeEffectExperiment,  experimentId, iteration, replicant);


    }

    /**
      * In theory there could be more than one recorder ....
      *
      * @throws java.io.IOException
      */
     private void reportBoundaryCrossings(File rootDir, Simulation simulation, IBugsimExperiment experiment, String experimentId, long currentIteration, long currentReplicant) throws IOException {
         BoundaryCrossingWriter writer = new BoundaryCrossingWriter(experiment);


         File outputFile = new File(rootDir, ExperimentReportController.makeIterationSummaryFilename("summary-boundary", experiment.getExperimentPlan().getTrialName(), null));
         writer.write(outputFile, simulation, experimentId, currentIteration, currentReplicant);
     }



    protected String createOutputFilename(String baseName, String experimentId, long currentIteration, long currentReplicant) {
        StringBuffer sb = new StringBuffer();
        sb.append(experimentId);
        sb.append("-").append(FORMAT.format(new Long(currentIteration)));
        sb.append("-").append(FORMAT.format(new Long(currentReplicant)));
        sb.append("-").append(baseName).append(".csv");

        return sb.toString();
    }

    private Format FORMAT = new DecimalFormat("000");
    private static final Logger log = Logger.getLogger(BoundaryCrossingSummaryReporter.class);
}
