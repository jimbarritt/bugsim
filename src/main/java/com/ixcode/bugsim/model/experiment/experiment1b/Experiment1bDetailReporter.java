/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment1b;

import com.ixcode.bugsim.model.experiment.report.butterfly.ButterflyHistoryWriter;
import com.ixcode.bugsim.model.experiment.report.butterfly.ButterflyWriter;
import com.ixcode.bugsim.model.experiment.report.butterfly.ButterflyStatistics;
import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgent;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentFilter;
import com.ixcode.bugsim.model.agent.butterfly.population.SimplePopulationFactory;
import com.ixcode.framework.experiment.model.report.ISummaryStatisticsReporter;
import com.ixcode.framework.experiment.model.report.ReportSummaryStatisticsRow;
import com.ixcode.framework.experiment.model.report.ReportSummaryStatistics;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.math.stats.DescriptiveStatistics;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.IExperiment;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class Experiment1bDetailReporter implements ISummaryStatisticsReporter {


    public Experiment1bDetailReporter() {


    }

    public void reportResults(String rootPath, String experimentId, IExperiment experiment, ParameterMap currentParameters, ExperimentProgress progress, boolean paramStructureChanged, boolean outputDetails) throws IOException {
        Experiment1b mddExperiment = (Experiment1b)experiment;

        long iteration = progress.getCurrentIteration();
        long replicant = progress.getCurrentReplicate();
        Simulation simulation = mddExperiment.getSimulation();
        File rootDir = new File(rootPath);

        ButterflyWriter bfWriter = new ButterflyWriter();
        ButterflyStatistics bfStats;
        if (outputDetails) {
            bfStats = reportButterflies(rootDir, simulation, experimentId, iteration, replicant, currentParameters, bfWriter);
        } else {
            bfStats = bfWriter.calculateStatsWithoutOutput(simulation);
        }



        _summaryStatistics = createSummaryStatistics(bfStats, mddExperiment);
    }


    private ButterflyStatistics reportButterflies(File rootDir, Simulation simulation, String experimentId, long currentIteration, long currentReplicant, ParameterMap currentParameters, ButterflyWriter bfWriter) throws IOException {
        List allButterflies = simulation.getHistoricalAgents(ForagingAgentFilter.INSTANCE);
        if (log.isInfoEnabled()) {
            log.info("Number of butterflies to report: " + allButterflies.size());
        }

        File outputFile = createOutputFile(rootDir, "butterflies", experimentId, currentIteration, currentReplicant);
        bfWriter.write(outputFile, simulation, experimentId, currentIteration, currentReplicant);

        boolean reportLifeHistory = currentParameters.findParameter(ButterflyParameters.BUTTERFLY_RECORD_BUTTERFLY_LIFE_HISTORY).getBooleanValue();
        if (reportLifeHistory) {
            reportLifeHistories(allButterflies, simulation, rootDir, experimentId, currentIteration, currentReplicant);
        }


        return bfWriter.getStats();

                                                                            
    }

    private void reportLifeHistories(List allButterflies, Simulation simulation, File rootDir, String experimentId, long currentIteration, long currentReplicant) throws IOException {

        int butterflyId = 1;
        for (Iterator itr = allButterflies.iterator(); itr.hasNext();) {
            ButterflyAgent butterfly = (ButterflyAgent)itr.next();
            String filename = "butterfly-" + FORMAT_6.format(new Long(butterflyId));
            File outputFile = createOutputFile(rootDir, filename, experimentId, currentIteration, currentReplicant);
            ButterflyHistoryWriter writer = new ButterflyHistoryWriter(butterflyId, butterfly.getStateHistory());
            writer.write(outputFile, simulation, experimentId, currentIteration, currentReplicant);
            butterflyId++;
        }
    }

    private ReportSummaryStatistics createSummaryStatistics(ButterflyStatistics bfStats, Experiment1b experiment) {
        ReportSummaryStatisticsRow summaryRow = new ReportSummaryStatisticsRow();

        SimplePopulationFactory bf = experiment.getButterflyFactory();
        summaryRow.addStatistic(SimplePopulationFactory.P_FORAGERS_ALIVE, bf.getForagersAlive());
        summaryRow.addStatistic(SimplePopulationFactory.P_FORAGERS_REMAINING, bf.getForagersRemaining());
        summaryRow.addStatistic(SimplePopulationFactory.P_FORAGERS_DEAD, bf.getForagersDead());
        summaryRow.addStatistic(SimplePopulationFactory.P_FORAGERS_ESCAPED, bf.getForagersEscaped());

        DescriptiveStatistics stats = bfStats.getDescriptiveStats();
        if (stats != null) { // maybe if we didnt record the dead butterflies
            summaryRow.addStatistic(ButterflyStatistics.DISPSQ_N, stats.getN());
            summaryRow.addStatistic(ButterflyStatistics.DISPSQ_MEAN, stats.getMean());
            summaryRow.addStatistic(ButterflyStatistics.DISPSQ_VARIANCE, stats.getVariance());
            summaryRow.addStatistic(ButterflyStatistics.DISPSQ_STD_ERROR, stats.getStdError());
            summaryRow.addStatistic(ButterflyStatistics.DISPSQ_MEAN_MINUS_ERR, stats.getMeanMinusErr());
            summaryRow.addStatistic(ButterflyStatistics.DISPSQ_MEAN_PLUS_ERR, stats.getMeanPlusErr());
        }

        ReportSummaryStatistics summaryStats = new ReportSummaryStatistics();
        summaryStats.addRow(summaryRow);

//        if (log.isInfoEnabled()) {
//            log.info("Butterfly Statistics: " + bfStats);
//        }


        return summaryStats;
    }


    public ReportSummaryStatistics getSummaryStats() {
        return _summaryStatistics;

    }

    protected File createOutputFile(File outputRoot, String filename, String experimentId, long currentIteration, long currentReplicant) {
        return new File(outputRoot, createOutputFilename(filename, experimentId, currentIteration, currentReplicant));
    }

    protected String createOutputFilename(String baseName, String experimentId, long currentIteration, long currentReplicant) {
        StringBuffer sb = new StringBuffer();
        sb.append(experimentId);
        sb.append("-").append(FORMAT.format(new Long(currentIteration)));
        sb.append("-").append(FORMAT.format(new Long(currentReplicant)));
        sb.append("-").append(baseName).append(".csv");

        return sb.toString();
    }

    private ReportSummaryStatistics _summaryStatistics = new ReportSummaryStatistics();
    private String _cabbagePatchGridName;
    private Format FORMAT = new DecimalFormat("000");
    private String _analysisCategoryName;
    private static final DecimalFormat FORMAT_6 = new DecimalFormat("000000");
    private static final Logger log = Logger.getLogger(Experiment1bDetailReporter.class);
}
