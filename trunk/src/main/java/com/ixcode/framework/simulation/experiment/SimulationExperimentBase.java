/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment;

import com.ixcode.bugsim.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.IExperimentListener;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.report.IExperimentReporter;
import com.ixcode.framework.model.ModelBase;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.ISimulationDisruptor;
import com.ixcode.framework.simulation.model.ISimulationListener;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public abstract class SimulationExperimentBase extends ModelBase implements ISimulationExperiment, ISimulationListener, ISimulationDisruptor {

    protected SimulationExperimentBase(Simulation simulation, ExperimentPlan plan, IExperimentReporter reporter) {
        this(simulation, plan, reporter,  null);
    }
    protected SimulationExperimentBase(Simulation simulation, ExperimentPlan plan, IExperimentReporter reporter, IExperimentReporter summaryReporter) {
        _simulation = simulation;
        _simulation.addSimulationListener(this);
        _plan = plan;
        _iterationReporter = reporter;
        _outputDirName = plan.getOutputDirName();
        _experimentName = plan.getExperimentName();
        _summaryReporter = summaryReporter;
    }


    public void reportProgress(ExperimentProgress progress) {

    }

    public void timestepCompleted(Simulation simulation) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void agentEscaped(Simulation simulation, IAgent agent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void initialiseExperiment(ExperimentPlan plan) {
        _simulation.initialiseRandom();
        _simulation.clean();

        ParameterMap params = plan.getParameterTemplate();

        _numberOfReplicates = SimulationCategory.getNumberOfReplicates(plan.getParameterTemplate());


        Landscape landscape = new Landscape(_simulation);
        _simulation.setLandscape(landscape);

        fireExperimentInitialisedEvent(plan);
        if (log.isInfoEnabled()) {
            log.info("initialisedExperiment: replicants= " + _numberOfReplicates);
            log.info("Plan : " + plan.getName());
            log.info("Trial : " + plan.getTrialName());
            log.info("Description : " + plan.getDescription());
        }



    }

    public void executeTimestep() {
        _simulation.executeTimeStep(this);
    }


    public void addExperimentListener(IExperimentListener listener) {
        _listeners.add(listener);
    }

    public void removeExperimentListener(IExperimentListener listener) {
        _listeners.remove(listener);
    }

    /**
     * @todo move these two to the experiment controller ?
     */
    protected void fireExperimentInitialisedEvent(ExperimentPlan plan) {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            IExperimentListener listener = (IExperimentListener)itr.next();
            listener.experimentInitialised(this, plan);
        }
    }


    protected void fireIterationInitialisedEvent(ParameterMap currentParams, ExperimentProgress progress) {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            IExperimentListener listener = (IExperimentListener)itr.next();
            listener.iterationInitialised(this, currentParams, progress);
        }
    }

    public void agentAdded(Simulation simulation, IAgent agent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public Simulation getSimulation() {
        return _simulation;
    }


    public ExperimentPlan getExperimentPlan() {
        return _plan;
    }

    public final IExperimentReporter getIterationReporter() {
        return _iterationReporter;
    }

    public IExperimentReporter getSummaryReporter() {
        return _summaryReporter;
    }

    public boolean hasIterationReporter() {
        return _iterationReporter != null;
    }

    public boolean hasSummaryReporter() {
        return _summaryReporter != null;
    }

    public long getNumberOfReplicates() {
        return _numberOfReplicates;
    }

    public String getOutputDirectoryName() {
        return _outputDirName;
    }

    public String getName() {
        return _experimentName;
    }

    public String getParameterSummary() {
        return "PLAN: " + _plan.getName() + ", TRIAL:" + _plan.getTrialName() + ", " + _simulation.getParameterSummary();
    }


    public void iterationCompleted(Simulation simulation) {

    }

    public void agentDeath(Simulation simulation, IAgent agent) {

    }


    protected Simulation _simulation;
    private ExperimentPlan _plan; // @todo - not sure if we should both keep this and ket it passed in to us in clean experiment
    private IExperimentReporter _iterationReporter;
    private long _numberOfReplicates;
    private List _listeners = new ArrayList();
    private static final Logger log = Logger.getLogger(SimulationExperimentBase.class);
    private String _outputDirName;
    private String _experimentName;
    private IExperimentReporter _summaryReporter;
}
