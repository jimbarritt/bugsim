/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model;

import com.ixcode.framework.experiment.model.report.IExperimentReporter;
import com.ixcode.framework.experiment.model.report.ISummaryStatisticsReporter;
import com.ixcode.framework.experiment.model.report.ReportSummaryStatisticsRow;
import com.ixcode.framework.experiment.model.report.ReportSummaryStatistics;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterMapDebug;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class FakeExperiment implements IExperiment, ISummaryStatisticsReporter {

    public FakeExperiment() {
        _planWithManipulations = new FakeExperimentPlanWithManipulations(2);
        _endTimestep = _planWithManipulations.getNumberOfTimesteps();
    }

    public void reportProgress(ExperimentProgress progress) {

    }

    public void removeExperimentListener(IExperimentListener listener) {

    }

    public boolean hasIterationReporter() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasSummaryReporter() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IExperimentReporter getSummaryReporter() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addExperimentListener(IExperimentListener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getNumberOfReplicates() {
        return 1;
    }

    public ParameterMap getCurrentParameters() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getParameterSummary() {
        return "";
    }

    public String getName() {
        return "Test Experiment";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isIterationComplete() {
        if (log.isInfoEnabled()) {
            log.info("isIterationCOmplete: " +_currentTimestep + ",  " + _endTimestep );
        }
        return (_currentTimestep >= _endTimestep);
    }


    public void initialiseExperiment(ExperimentPlan plan) {
        if (log.isInfoEnabled()) {
            log.info("Initialise Experiment");
        }
        ParameterMapDebug.debugParams(plan.getParameterTemplate());
    }

    public void initialiseIteration(ExperimentProgress progress, ParameterMap params) {

        ParameterMapDebug.debugParams(params);
        String param1 = params.findParameter(FakeExperimentPlanWithManipulations.MANIPULATED_PARAM_1).getStringValue();
        String param2 = params.findParameter(FakeExperimentPlanWithManipulations.MANIPULATED_PARAM_2).getStringValue();
        String param3 = params.findParameter(FakeExperimentPlanWithManipulations.MANIPULATED_PARAM_3).getStringValue();
        long param4 = params.findParameter(FakeExperimentPlanWithManipulations.MANIPULATED_PARAM_4).getLongValue();
        long derived = params.findParameter(FakeExperimentPlanWithManipulations.DERIVED_PARAM).getLongValue();

        Parameter algorithmContainer = (Parameter)params.findParameter(FakeExperimentPlanWithManipulations.ALGORITHM_PARAM);
        StrategyDefinitionParameter algorithm = (StrategyDefinitionParameter)algorithmContainer.getValue();


        if (log.isInfoEnabled()) {
            log.info("Initialise Iteration : param1=" + param1+ ", param2=" + param2 + ", param3=" + param3 + ", param4=" + param4 + ", param4X1000=" + derived);
            log.info("AlgorithmContainer : " + algorithmContainer.getName() + " contains: " + algorithm.getName() + ", implemented by " + algorithm.getImplementingClassName());
            if (algorithm.getName().equals("algorithm1")) {
                String parameter1 = params.findParameter(FakeExperimentPlanWithManipulations.ALGORITHM1_PARAM_1).getStringValue();
                 if (log.isInfoEnabled()) {
                     log.info("Algorithm1 Parameter1= " + parameter1);
                 }
            }  else if (algorithm.getName().equals("algorithm2")) {
                String parameter1 = params.findParameter(FakeExperimentPlanWithManipulations.ALGORITHM2_PARAM_1).getStringValue();
                 if (log.isInfoEnabled()) {
                     log.info("Algorithm2 Parameter1= " + parameter1);
                 }
            }  else {
                throw new IllegalStateException("Unkown algorithm! " + algorithm.getName());
            }
        }

        _currentTimestep = 0;
    }

    public IExperimentReporter getIterationReporter() {
        return this;
    }

    public String getOutputDirectoryName() {
        return "test-experiment";
    }

    public void reportResults(String rootPath, String experimentId, IExperiment experiment, ParameterMap currentParameters, ExperimentProgress progress, boolean pramsChanged, boolean outputDetails) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("Iteration reporter Reporting iteration results to " + rootPath);
        }
    }

    public ReportSummaryStatistics getSummaryStats() {
        ReportSummaryStatisticsRow statsRow = new ReportSummaryStatisticsRow();
        statsRow.addStatistic("Statistic1", "value1");
        statsRow.addStatistic("Statistic2", "value1");
        statsRow.addStatistic("Statistic3", "value1");
        statsRow.addStatistic("Statistic4", "value1");
        ReportSummaryStatistics stats = new ReportSummaryStatistics();
        stats.addRow(statsRow);
        return stats;
    }


    public ExperimentPlan getExperimentPlan() {
        return _planWithManipulations;
    }

    public void executeTimestep() {
        _currentTimestep++;
        if (log.isInfoEnabled()) {
            log.info("Experiment Timestep : " + _currentTimestep);
        }
    }

    public void reportIterationResults(String rootPath, ExperimentProgress progress) {
        if (log.isInfoEnabled()) {
            log.info("Report Results to : " + rootPath);
            log.info("Progress:");
            log.info(progress);
        }
    }


    private static final Logger log = Logger.getLogger(FakeExperiment.class);
    private FakeExperimentPlanWithManipulations _planWithManipulations;

    private long _currentTimestep;
    private long _endTimestep;
}
