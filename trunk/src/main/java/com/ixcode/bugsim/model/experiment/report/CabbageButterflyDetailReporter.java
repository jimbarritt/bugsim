/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.report;

import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgent;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentFilter;
import com.ixcode.bugsim.model.agent.butterfly.population.*;
import com.ixcode.bugsim.model.agent.cabbage.EggCount;
import com.ixcode.bugsim.model.experiment.IBugsimExperiment;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.BehaviourCategory;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.ResourceCategory;
import com.ixcode.bugsim.model.experiment.report.butterfly.ButterflyHistoryWriter;
import com.ixcode.bugsim.model.experiment.report.butterfly.ButterflyStatistics;
import com.ixcode.bugsim.model.experiment.report.butterfly.ButterflyWriter;
import com.ixcode.bugsim.model.experiment.report.cabbage.*;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.report.ISummaryStatisticsReporter;
import com.ixcode.framework.experiment.model.report.ReportSummaryStatisticsRow;
import com.ixcode.framework.experiment.model.report.ReportSummaryStatistics;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.javabean.IntrospectionUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CabbageButterflyDetailReporter implements ISummaryStatisticsReporter {


    public CabbageButterflyDetailReporter(ScaledDistance outputScale) {
        _outputScale = outputScale;

    }

    public void reportResults(String rootPath, String experimentId, IExperiment experiment, ParameterMap currentParameters, ExperimentProgress progress, boolean paramStructureChanged, boolean outputDetails) throws IOException {
        IBugsimExperiment bugsimExperiment = (IBugsimExperiment)experiment;

        long iteration = progress.getCurrentIteration();
        long replicant = progress.getCurrentReplicate();
        Simulation simulation = bugsimExperiment.getSimulation();
        File rootDir = new File(rootPath);

        CabbageWriter writer = new CabbageWriter(bugsimExperiment.getResourceLayout().getResourceLayoutGridName(), _outputScale);
        CabbageStatistics stats;
        if (outputDetails) {
            stats = reportCabbages(rootDir, experimentId, progress, writer, simulation, iteration, replicant, bugsimExperiment);
        } else {
            stats = writer.calculateStatsWithoutOutput(simulation);
        }

        ButterflyWriter bfWriter = new ButterflyWriter(bugsimExperiment.getResourceLayout().getResourceLayoutGridName());
        ButterflyStatistics bfStats;
        if (log.isInfoEnabled()) {
            log.info("outputDetails=" + outputDetails);
        }
        if (outputDetails) {
            bfStats = reportButterflies(rootDir, simulation, experimentId, iteration, replicant, currentParameters, bfWriter);
        } else {
            bfStats = bfWriter.calculateStatsWithoutOutput(simulation);
        }

        _summaryStatistics = createSummaryStatistics(stats, bugsimExperiment);
    }

    private CabbageStatistics reportCabbages(File rootDir, String experimentId, ExperimentProgress progress, CabbageWriter writer, Simulation simulation, long iteration, long replicate, IBugsimExperiment bugsimExperiment) throws IOException {
        File outputFile = createOutputFile(rootDir, "cabbages", experimentId, progress.getCurrentIteration(), progress.getCurrentReplicate());
        writer.write(outputFile, simulation, experimentId, iteration, replicate);

        CabbageStatistics stats = writer.getStats();
        outputFile = createOutputFile(rootDir, "cabbage-stats", experimentId, progress.getCurrentIteration(), progress.getCurrentReplicate());
        CabbageStatsWriter statsWriter = new CabbageStatsWriter(stats, CabbageParameters.ISOLATION_CATEGORY.getName());
        statsWriter.write(outputFile, simulation, experimentId, iteration, replicate);

        ParameterMap params = bugsimExperiment.getCurrentParameters();
        boolean includeFinalEggCount = ResourceCategory.getIncludeFinalEggCount(params);


        IPopulationWeb populationWeb = bugsimExperiment.getPopulationWeb();
        if (!(populationWeb instanceof DiscreteGenerationPopulationWeb))  {
            throw new IllegalStateException("Can only handle discrete generation population webs at the moment");
        }
        DiscreteGenerationPopulationWeb dgpw = (DiscreteGenerationPopulationWeb)populationWeb;

        List eggCounts = new ArrayList();
        for (Iterator itr = dgpw.getPopulationFactories().iterator(); itr.hasNext();) {
            IPopulationFactory factory = (IPopulationFactory)itr.next();
            eggCounts.addAll(factory.getEggCounter().getEggCounts());
            if (includeFinalEggCount) {
                eggCounts.add(factory.getEggCounter().createEggCount(dgpw.getGenerationArchive().size() + 1, simulation));
            }
        }

        if (log.isInfoEnabled()) {
            log.info("Reporting : " + eggCounts.size() + " egg counts...");
        }

        EggCountSummaryWriter summaryCountWriter = new EggCountSummaryWriter(eggCounts);
        outputFile = createOutputFile(rootDir, "egg-counts", experimentId, progress.getCurrentIteration(), progress.getCurrentReplicate());
        summaryCountWriter.write(outputFile, simulation, experimentId, iteration, replicate);

        long countId = 1;
        for (Iterator itr = eggCounts.iterator(); itr.hasNext();) {
            EggCount eggCount = (EggCount)itr.next();
            EggCountWriter countWriter = new EggCountWriter(countId,  eggCount);
            String baseName = "egg-count-" + FORMAT.format(countId);
            outputFile = createOutputFile(rootDir, baseName, experimentId, progress.getCurrentIteration(), progress.getCurrentReplicate());
            countWriter.write(outputFile, simulation, experimentId, iteration, replicate);
            countId++;
        }


        return stats;
    }

    private ButterflyStatistics reportButterflies(File rootDir, Simulation simulation, String experimentId, long currentIteration, long currentReplicant, ParameterMap currentParameters, ButterflyWriter bfWriter) throws IOException {
        List allButterflies = simulation.getHistoricalAgents(ForagingAgentFilter.INSTANCE);
        boolean reportLifeHistory = BehaviourCategory.getRecordLifeHistory(currentParameters);
        if (log.isInfoEnabled()) {
            log.info("Number of butterflies to report: " + allButterflies.size() + " - reportLifeHistory=" + reportLifeHistory);
        }

        File outputFile = createOutputFile(rootDir, "foragers", experimentId, currentIteration, currentReplicant);
        bfWriter.write(outputFile, simulation, experimentId, currentIteration, currentReplicant);



        if (reportLifeHistory) {
            reportLifeHistories(allButterflies, simulation, rootDir, experimentId, currentIteration, currentReplicant);
        }

        
        return bfWriter.getStats();


    }

    private void reportLifeHistories(List allButterflies, Simulation simulation, File rootDir, String experimentId, long currentIteration, long currentReplicant) throws IOException {

        String filename = "forager-lifehistories";
        File outputFile = createOutputFile(rootDir, filename, experimentId, currentIteration, currentReplicant);

        int butterflyId =1;
        for (Iterator itr = allButterflies.iterator(); itr.hasNext();) {
            ButterflyAgent butterfly = (ButterflyAgent)itr.next();
            ButterflyHistoryWriter writer = new ButterflyHistoryWriter(butterflyId, butterfly.getStateHistory());
            writer.write(outputFile, simulation, experimentId, currentIteration, currentReplicant);
            butterflyId++;
        }
    }

    private ReportSummaryStatistics createSummaryStatistics(CabbageStatistics stats, IBugsimExperiment experiment) {
        ReportSummaryStatistics summaryStats = new ReportSummaryStatistics();
        IPopulationWeb populationWeb = experiment.getPopulationWeb();
        if (! (populationWeb instanceof DiscreteGenerationPopulationWeb)) {
            throw new IllegalStateException("Can only report Discrete Population Webs at the moment!");
        }
        DiscreteGenerationPopulationWeb dgpw = (DiscreteGenerationPopulationWeb)populationWeb;
        List discreteGenerations = dgpw.getGenerationArchive();
        for (Iterator itr = discreteGenerations.iterator(); itr.hasNext();) {
            DiscreteGeneration g= (DiscreteGeneration)itr.next();

            List immigrations = g.getImmigrationArchive();

            for (Iterator itrImmigration = immigrations.iterator(); itrImmigration.hasNext();) {
                DiscreteImmigration immigration = (DiscreteImmigration)itrImmigration.next();
                IPopulation population = immigration.getPopulation();
                summaryStats.addRow(createPopulationSummary("IMMIGRATION", g.getGenerationTime(), population));
            }

            List births = g.getBirthArchive();
            for (Iterator itrBirths = births.iterator(); itrBirths.hasNext();) {
                DiscreteBirth birth = (DiscreteBirth)itrBirths.next();
                IPopulation population = birth.getPopulation();
                summaryStats.addRow(createPopulationSummary("BIRTH", g.getGenerationTime(), population));
            }
        }


        // Going to have to work out what to do here ...
//        if (stats.hasAnalysisCategory(CabbageParameters.ISOLATION_CATEGORY)) {
//            CabbageStatisticEntry centre = stats.getStatisticEntry(CabbageParameters.ISOLATION_VALUE_CENTRE);
//            CabbageStatisticEntry edge = stats.getStatisticEntry(CabbageParameters.ISOLATION_VALUE_EDGE);
//            CabbageStatisticEntry corner = stats.getStatisticEntry(CabbageParameters.ISOLATION_VALUE_CORNER);
//
//            addSummaryStats(summaryRow, centre);
//            addSummaryStats(summaryRow, edge);
//            addSummaryStats(summaryRow, corner);
//
//
//            summaryRow.addStatistic("total.eggsPerPlantRatio", stats.getTotalEggsPerPlantRatio());
//            summaryRow.addStatistic("total.eggsPerPlant", stats.getTotalEggsPerPlant());
//            summaryRow.addStatistic("total.plants", stats.getTotalPlants());
//            summaryRow.addStatistic("total.eggs", stats.getTotalEggs());
//        }

        return summaryStats;
    }

    private ReportSummaryStatisticsRow  createPopulationSummary(String populationType, long generation, IPopulation population) {
        ReportSummaryStatisticsRow summaryRow = new ReportSummaryStatisticsRow();
        summaryRow.addStatistic("generation", generation);
        summaryRow.addStatistic("type", populationType);
        summaryRow.addStatistic("agent", IntrospectionUtils.getShortClassName(population.getAgentClass()));
//        summaryRow.addStatistic(ForagerPopulation.P_POPULATION_LABEL, population.getPopulationLabel());
        summaryRow.addStatistic(ForagerPopulation.P_POPULATION_SIZE, population.getPopulationSize());
        summaryRow.addStatistic(ForagerPopulation.P_FORAGERS_ALIVE, population.getForagersAlive());
        summaryRow.addStatistic(ForagerPopulation.P_FORAGERS_REMAINING, population.getForagersRemaining());
        summaryRow.addStatistic(ForagerPopulation.P_FORAGERS_DEAD, population.getForagersDead());
        summaryRow.addStatistic(ForagerPopulation.P_FORAGERS_ESCAPED, population.getForagersEscaped());
        summaryRow.addStatistic(ForagerPopulation.P_EGG_COUNT, population.getEggCount());
        summaryRow.addStatistic(ForagerPopulation.P_LARVAE_DEAD, population.getLaraveDead());
        return summaryRow;
    }

    private void addSummaryStats(ReportSummaryStatisticsRow summary, CabbageStatisticEntry entry) {
        String b = entry.getAnalysisValue().getDescription() + ".";
        summary.addStatistic(b + "plants", entry.getNumberOfPlants());
        summary.addStatistic(b + "eggs", entry.getTotalEggs());
        summary.addStatistic(b + "eggsPerPlant", entry.getEggsPerPlant());
        summary.addStatistic(b + "ratioEggsPerPlant", entry.getEggsPerPlantRatio());

    }


    public ReportSummaryStatistics getSummaryStats() {
        return _summaryStatistics;

    }

    protected File createOutputFile(File outputRoot, String filename, String experimentId, long currentIteration, long currentReplicant) {
        return new File(outputRoot, createReplicateOutputFilename(filename, experimentId, currentIteration, currentReplicant));
    }

    protected String createReplicateOutputFilename(String baseName, String experimentId, long currentIteration, long currentReplicant) {
        StringBuffer sb = new StringBuffer();

        sb.append("Replicate");

        sb.append("-").append(baseName).append(".csv");

        return sb.toString();
    }

    private ReportSummaryStatistics _summaryStatistics = new ReportSummaryStatistics();

    private DecimalFormat FORMAT = new DecimalFormat("000");
    private String _analysisCategoryName;
    private static final DecimalFormat FORMAT_6 = new DecimalFormat("000000");
    private static final Logger log = Logger.getLogger(CabbageButterflyDetailReporter.class);
    private ScaledDistance _outputScale;
}


