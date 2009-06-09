package com.ixcode.framework.experiment.model;

import com.ixcode.framework.experiment.model.report.IExperimentReporter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.agent.surveyor.SignalSurveyingAgent;

public interface IExperiment {

    boolean isIterationComplete();

    void initialiseExperiment(ExperimentPlan plan);
    void initialiseIteration(ExperimentProgress progress, ParameterMap params);

    ExperimentPlan getExperimentPlan();

    void executeTimestep();

    IExperimentReporter getIterationReporter();

    String getOutputDirectoryName();

    String getName();

    void addExperimentListener(IExperimentListener listener);
    void removeExperimentListener(IExperimentListener listener);


    // @todo move this to ExperimentPLan
    long getNumberOfReplicates();

    /**
     * Not sure about this - but basically a way of sommarising the parameters for easy display - should prob live on the parameter objects somewhere.
     * @return
     */
    String getParameterSummary();

    ParameterMap getCurrentParameters();


    IExperimentReporter getSummaryReporter();

    boolean hasIterationReporter();

    boolean hasSummaryReporter();

    void reportProgress(ExperimentProgress progress);

        
}
 
