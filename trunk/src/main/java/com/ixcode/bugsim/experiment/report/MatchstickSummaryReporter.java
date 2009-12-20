/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.report;

import com.ixcode.framework.experiment.model.report.IExperimentReporter;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.experiment.ISimulationExperiment;
import com.ixcode.bugsim.experiment.report.matchstick.MatchstickIntersectionsWriter;

import java.io.IOException;
import java.io.File;
import java.text.Format;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class MatchstickSummaryReporter implements IExperimentReporter {


    public void reportResults(String rootPath, String experimentId, IExperiment experiment, ParameterMap currentParameters, ExperimentProgress progress, boolean paramStructureChanged, boolean outputDetails) throws IOException {
        MatchstickIntersectionsWriter writer = new MatchstickIntersectionsWriter();
        File outputFile = createOutputFile(new File(rootPath), "matchstick-intersections",experimentId, progress.getCurrentIteration(),progress.getCurrentReplicate());
        writer.write(outputFile, ((ISimulationExperiment)experiment).getSimulation(),experimentId, progress.getCurrentIteration(),progress.getCurrentReplicate());
    }


    protected File createOutputFile(File outputRoot, String filename, String experimentId, long currentIteration, long currentReplicant) {
        return new File(outputRoot, createOutputFilename(filename, experimentId, currentIteration, currentReplicant));
    }

    protected String createOutputFilename(String baseName, String experimentId, long currentIteration, long currentReplicant) {
            StringBuffer sb = new StringBuffer();
            sb.append(experimentId);
            sb.append("-").append(baseName).append(".csv");

            return sb.toString();
        }


    private Format FORMAT = new DecimalFormat("000");
    private static final Logger log = Logger.getLogger(MatchstickSummaryReporter.class);
}
