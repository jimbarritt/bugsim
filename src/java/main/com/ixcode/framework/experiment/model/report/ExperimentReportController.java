/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.report;

import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.xml.ExperimentPlanXML;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterMapXML;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Description : Controlls the reporting from the experiment.
 * <p/>
 * It deals with creating an output directory and then works out what folder each iteration should go in then
 * delegates to a more specific reporter (which each experiment provides)
 */
public class ExperimentReportController implements IExperimentReporter {
    public ExperimentReportController() {

    }

    public void reportResults(String rootPath, String experimentId, IExperiment experiment, ParameterMap currentParameters, ExperimentProgress progress, boolean paramsChanged, boolean outputDetails) throws IOException {
        String iterationNumberFormatted = format3Places(progress.getCurrentIteration());
        String replicantNumnberFormatted = format3Places(progress.getCurrentReplicate());

        File root = new File(rootPath);
        File iterationRootDir = makeIterationRootDir(rootPath, iterationNumberFormatted);
        File replicantRootDir = makeReplicantRootDir(iterationRootDir.getAbsolutePath(), replicantNumnberFormatted);



        if (!iterationRootDir.exists()) {
            iterationRootDir.mkdirs();
        }

        if (outputDetails) {
            replicantRootDir.mkdirs();
        }

        reportExperimentPlan(root, experiment, experimentId, currentParameters, progress);


        if (log.isInfoEnabled()) {
            log.info("Reporting ITR: " + iterationNumberFormatted + ", REP:  " + replicantNumnberFormatted + ", rootPath=" + rootPath + ", iterationDir=" + replicantRootDir.getName());
        }

        if (experiment.hasIterationReporter()) {
            experiment.getIterationReporter().reportResults(replicantRootDir.getAbsolutePath(), experimentId, experiment, currentParameters, progress, paramsChanged, outputDetails);
        }

        IExperimentReporter experimentSummary = experiment.getSummaryReporter();
        if (experimentSummary != null) {
            experimentSummary.reportResults(iterationRootDir.getAbsolutePath() ,experimentId,  experiment, currentParameters, progress, paramsChanged, outputDetails);
        }

        reportIterationSummaryResults(experiment, iterationRootDir, experimentId, currentParameters, progress, replicantRootDir, paramsChanged);
    }


    private void reportExperimentPlan(File root, IExperiment experiment, String experimentId, ParameterMap currentParameters, ExperimentProgress progress) throws IOException {
        File out = new File(root, makeExperimentSummaryFilename("plan", experiment.getOutputDirectoryName(), experimentId, experiment.getExperimentPlan().getName(), experiment.getExperimentPlan().getTrialName(), null, "csv"));

        if (!out.exists()) {
            ExperimentPlanWriter writer = new ExperimentPlanWriter();
            writer.write(out, experiment, experimentId, currentParameters, progress);
            if (log.isInfoEnabled()) {
                log.info("Plan : " + out.getAbsolutePath() );
            }
        }

        File xmlout = new File(root, makeExperimentSummaryFilename("plan", experiment.getOutputDirectoryName(), experimentId, experiment.getExperimentPlan().getName(), experiment.getExperimentPlan().getTrialName(), null, "xml"));

        if (!xmlout.exists()) {
            ExperimentPlanXML.INSTANCE.exportPlan(xmlout, experiment.getExperimentPlan(), true);
            if (log.isInfoEnabled()) {
                log.info("Plan XML: " + xmlout.getAbsolutePath() );
            }
        }

    }
    private void reportIterationSummaryResults(IExperiment experiment, File outputRoot, String experimentId, ParameterMap currentParameters, ExperimentProgress progress, File iterationRootDir, boolean paramsChanged) throws IOException {

//        File out = createSummaryParametersFile(outputRoot, experiment.getOutputDirectoryName(), experimentId, experiment.getExperimentPlan().getName(), experiment.getExperimentPlan().getTrialName(), paramsChanged);

        File out = new File(outputRoot, makeIterationSummaryFilename("params", experiment.getExperimentPlan().getTrialName(), format3Places(progress.getCurrentIteration())));
        boolean append = !paramsChanged;


        if (!out.exists()) {
            IterationSummaryParameterWriter writer = new IterationSummaryParameterWriter(iterationRootDir, append);
            writer.write(out, experiment, experimentId, currentParameters, progress);

            if (log.isInfoEnabled()) {
                log.info("Summary Parameters : " + out);
            }


        }


        out = new File(outputRoot, makeIterationSummaryFilename("stats",experiment.getExperimentPlan().getTrialName(), format3Places(progress.getCurrentIteration())));

        IterationSummaryStatisticsWriter statsWriter = new IterationSummaryStatisticsWriter(iterationRootDir, true);
        statsWriter.write(out, experiment, experimentId, currentParameters, progress);
        if (log.isInfoEnabled()) {
            log.info("Summary Statistics : " + out);
        }




        File xmlSummaryOut = new File(outputRoot, makeIterationSummaryFilename("params", experiment.getExperimentPlan().getTrialName(), format3Places(progress.getCurrentIteration()), "xml"));

        if (!xmlSummaryOut.exists()) {
            ParameterMapXML.exportParams(xmlSummaryOut,experiment.getExperimentPlan().getDescription(),  currentParameters);
        }

    }

    public static String makeIterationSummaryFilename(String baseName, String trialName, String fileNumber) {
        return  makeIterationSummaryFilename(baseName, trialName, fileNumber, "csv");
    }
    public static String makeIterationSummaryFilename(String baseName, String trialName, String fileNumber, String ext) {
        String t = (trialName.length()==0) ? "" : "-" + trialName;
        String fn =  (fileNumber != null) ? "-" + fileNumber : "";

        return "Iteration-"  + baseName + "." + ext;
    }

    public static String makeExperimentSummaryFilename(String baseName, String experimentName, String experimentId,  String planName, String trialName, String fileNumber, String ext) {
        String t = (trialName.length()==0) ? "" : "-" + trialName;
        String fn =  (fileNumber != null) ? "-" + fileNumber : "";

        return "Experiment-"  + baseName + "." + ext;
    }

    private File makeIterationRootDir(String experimentRootPath, String formattedIterationNumber) {
        return new File(experimentRootPath, "Iteration-" + formattedIterationNumber);
    }
    private File makeReplicantRootDir(String experimentRootPath, String replicantNumberFormatted) {
        return new File(experimentRootPath, "Replicate-" + replicantNumberFormatted);

    }


    private String format3Places(long currentIteration) {
        return FORMAT.format(currentIteration);
    }


    private static final NumberFormat FORMAT = new DecimalFormat("000");


    private static final Logger log = Logger.getLogger(ExperimentReportController.class);

    private int _maxSummaryFiles = 6;
    private String _currentFileId;
}
