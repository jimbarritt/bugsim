/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment1b;

import com.ixcode.framework.simulation.experiment.report.DispersalDistanceWriter;
import com.ixcode.bugsim.model.experiment.experiment1b.Experiment1b;
import com.ixcode.framework.experiment.model.report.ExperimentReportController;
import com.ixcode.framework.experiment.model.report.IExperimentReporter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.simulation.model.Simulation;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class Experiment1bSummaryReporter implements IExperimentReporter {


    public Experiment1bSummaryReporter() {


    }

    public void reportResults(String rootPath, String experimentId, IExperiment experiment, ParameterMap currentParameters, ExperimentProgress progress, boolean paramStructureChanged, boolean outputDetails) throws IOException {
        Experiment1b mddExperiment = (Experiment1b)experiment;

        long iteration = progress.getCurrentIteration();
        long replicant = progress.getCurrentReplicate();
        Simulation simulation = mddExperiment.getSimulation();
        File rootDir = new File(rootPath);



        reportDispersalDistances(rootDir, simulation,experiment,  experimentId, iteration, replicant);


    }

    /**
      * In theory there could be more than one recorder ....
      *
      * @throws java.io.IOException
      */
     private void reportDispersalDistances(File rootDir, Simulation simulation, IExperiment experiment, String experimentId, long currentIteration, long currentReplicant) throws IOException {
         DispersalDistanceWriter writer = new DispersalDistanceWriter();


         File outputFile = new File(rootDir, ExperimentReportController.makeIterationSummaryFilename("summary-dispersal", experiment.getExperimentPlan().getTrialName(), null));
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
    private static final Logger log = Logger.getLogger(Experiment1bSummaryReporter.class);
}
