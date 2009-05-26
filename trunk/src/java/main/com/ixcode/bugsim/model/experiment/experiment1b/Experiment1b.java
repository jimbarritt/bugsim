/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment1b;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentFilter;
import com.ixcode.bugsim.model.agent.butterfly.population.SimplePopulationFactory;
import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterMapDebug;
import com.ixcode.framework.simulation.experiment.SimulationExperimentBase;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.dispersal.DispersalDistanceRecorder;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class Experiment1b extends SimulationExperimentBase {

    public Experiment1b(Simulation simulation, ExperimentPlan plan) {
        super(simulation, plan, new Experiment1bDetailReporter(), new Experiment1bSummaryReporter());
        _foragerFactory = new SimplePopulationFactory();
    }


    public boolean haltSimulation(Simulation simulation) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isIterationComplete() {
        boolean isComplete = !_foragerFactory.hasMoreForagers();
        if (isComplete) {
            _simulation.tidyUp();
        }
        return isComplete;
    }


    public void agentDeath(Simulation simulation, IAgent agent) {
        _foragerFactory.foragerDeath(simulation, agent);
    }




    public void initialiseIteration(ExperimentProgress progress,  ParameterMap params) {
        _archiveRemovedAgents = SimulationCategory.getArchiveRemovedAgents(params);
        if (BugsimMain.isDebug()) {
            ParameterMapDebug.debugParams(params);
        }
        _simulation.clean();
        _simulation.setArchiveRemovedAgents(_archiveRemovedAgents);


        Landscape landscape = _simulation.getLandscape();
//        LandscapeParameters.initialiseLandscape(landscape, params);
//
//        ButterflyParameters.initialiseForagerFactory(_foragerFactory, _simulation, params);

        DispersalDistanceRecorder recorder = new DispersalDistanceRecorder(_simulation, ForagingAgentFilter.INSTANCE);
        _simulation.addAgent(recorder);

        _currentParameters = params;

        fireIterationInitialisedEvent(_currentParameters, progress);

        if (log.isInfoEnabled()) {
            log.info("ITR: " + progress.getCurrentIterationFormatted() + ", REP: " + progress.getCurrentReplicateFormatted()+ " - " + getParameterSummary());
        }
    }


    public ParameterMap getCurrentParameters() {
        return _currentParameters;
    }

    public String getParameterSummary() {
        return super.getParameterSummary() + ", " + _foragerFactory.getParameterSummary() + ", " + ButterflyParameters.getParameterSummary(_currentParameters);
    }

    public SimplePopulationFactory getButterflyFactory() {
        return _foragerFactory;
    }


    private boolean _archiveRemovedAgents;
    private ParameterMap _currentParameters;
    private static final Logger log = Logger.getLogger(Experiment1b.class);
    private SimplePopulationFactory _foragerFactory;
}
