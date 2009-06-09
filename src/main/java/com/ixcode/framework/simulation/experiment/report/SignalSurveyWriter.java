/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.report;

import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.AgentClassIdFilter;
import com.ixcode.framework.simulation.model.agent.surveyor.SignalSurveyingAgent;
import com.ixcode.framework.simulation.model.landscape.information.SignalSample;
import com.ixcode.framework.simulation.experiment.ISimulationExperiment;
import com.ixcode.framework.experiment.model.report.ExperimentReportController;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.io.*;
import java.text.DecimalFormat;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SignalSurveyWriter extends SimulationWriterBase {


    /**
     * @param experiment
     * @todo should really sort out simulation so that it knows about the experiment which created it - at the moment it has the old IExperiment interface there
     */
    public SignalSurveyWriter(File rootDir, ISimulationExperiment experiment) {
        super(true);
        _rootDir = rootDir;
        _experiment = experiment;
    }

    protected void writeHeaders(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        out.writeRow(SignalSurveyRow.HEADER_ROW);
    }


    protected void writeResults(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) throws IOException {
        List surveyors = simulation.getLiveAgents(new AgentClassIdFilter(SignalSurveyingAgent.AGENT_CLASS_ID));

        if (currentReplicate == 1) {// only do it for the first one!
            for (Iterator itr = surveyors.iterator(); itr.hasNext();) {
                SignalSurveyingAgent surveyor = (SignalSurveyingAgent)itr.next();
                if (surveyor.hasSurvey()) {
                    SignalSurveyRow row = new SignalSurveyRow();
                    row.setSurveyor(surveyor);
                    out.writeRow(row);
                    File matrixFile = new File(_rootDir, ExperimentReportController.makeIterationSummaryFilename("signal-survey-" + surveyor.getSignalSurfaceName(), _experiment.getExperimentPlan().getTrialName(), FORMAT.format(currentIteration), "mat"));
                    writeSurface(matrixFile, surveyor.getSurvey());
                }
            }
        }


    }

    private void writeSurface(File matrixFile, SignalSample[][] survey) throws IOException {
        PrintWriter out = null;

        try {
            out = new PrintWriter(new FileWriter(matrixFile));
            for (int y = 0; y < survey[0].length; ++y) {
                StringBuffer line = new StringBuffer();
                for (int x = 0; x < survey.length; ++x) {
                    line.append(survey[x][y].getDoubleSignalValue()).append(" ");
                }
                out.println(line);
            }


        } finally {
            if (out != null) {
                out.close();
            }
        }


    }

//            SignalSample[][] surface = surveyor.getSurvey();
//            writeSurface(out, surveyor, surface, experimentId, currentIteration, currentReplicate);

    private void writeSurface(CSVWriter out, SignalSurveyingAgent surveyor, SignalSample[][] survey, String experimentId, long currentIteration, long currentReplicate) {
        for (int y = 0; y < survey[0].length; ++y) {
            for (int x = 0; x < survey.length; ++x) {
//                SignalSurveyRow row = new SignalSurveyRow();
//                row.setExperimentId(experimentId);
//                row.setIteration(currentIteration);
//                row.setReplicate(currentReplicate);
//                row.setSurveyor(surveyor);
//                row.setSurveyPoint(x, y, survey[x][y]);
//                out.writeRow(row);
            }
        }
    }


    private static final Logger log = Logger.getLogger(SignalSurveyWriter.class);

    private File _rootDir;
    private ISimulationExperiment _experiment;
    private static final DecimalFormat FORMAT = new DecimalFormat("000");
}

