/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.report;
                                                                                        
import com.ixcode.bugsim.model.experiment.IBugsimExperiment;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.report.ExperimentReportController;
import com.ixcode.framework.experiment.model.report.IExperimentReporter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.experiment.report.SignalSurveyWriter;
import com.ixcode.framework.simulation.experiment.report.DispersalDistanceWriter;
import com.ixcode.framework.simulation.model.Simulation;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentXSummaryReporter implements IExperimentReporter {

    public void reportResults(String rootPath, String experimentId, IExperiment experiment, ParameterMap currentParameters, ExperimentProgress progress, boolean paramStructureChanged, boolean outputDetails) throws IOException {
        IBugsimExperiment bugsimExperiment = (IBugsimExperiment)experiment;

        long iteration = progress.getCurrentIteration();
        long replicate = progress.getCurrentReplicate();
        Simulation simulation = bugsimExperiment.getSimulation();
        boolean reportDispersalStatistics = SimulationCategory.getRecordDispersalStatistics(currentParameters);

        File rootDir = new File(rootPath);

          if (log.isInfoEnabled()) {
              log.info("informationSurveys=true : dispersalStatistics=" + reportDispersalStatistics);
          }
        reportInformationSurveys(rootDir, simulation, bugsimExperiment, experimentId, iteration, replicate);


        if (reportDispersalStatistics) {
            DispersalDistanceWriter writer = new DispersalDistanceWriter();
             File outputFile = new File(rootDir, ExperimentReportController.makeIterationSummaryFilename("dispersal", experiment.getExperimentPlan().getTrialName(), null));
             writer.write(outputFile, simulation, experimentId, progress.getCurrentIteration(), progress.getCurrentReplicate());
        }

    }

    private void reportInformationSurveys(File rootDir, Simulation simulation, IBugsimExperiment experiment, String experimentId, long iteration, long replicate) throws IOException {
        SignalSurveyWriter writer = new SignalSurveyWriter(rootDir, experiment);

        File outputFile = new File(rootDir, ExperimentReportController.makeIterationSummaryFilename("signal-survey", experiment.getExperimentPlan().getTrialName(),FORMAT.format(iteration)));
        writer.write(outputFile, simulation, experimentId, iteration, replicate);



    }

    private static final Logger log = Logger.getLogger(ExperimentXSummaryReporter.class);
    private static final DecimalFormat FORMAT = new DecimalFormat("000");
}
