/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment1d;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.bugsim.model.agent.boundary.IBoundaryAgentFactory;
import com.ixcode.bugsim.model.agent.matchstick.IMatchstickFactory;
import com.ixcode.bugsim.model.experiment.parameter.MatchstickParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.landscape.LandscapeParameters;
import com.ixcode.bugsim.model.experiment.report.MatchstickSummaryReporter;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterMapDebug;
import com.ixcode.framework.parameter.model.ParameterisedStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.SimulationExperimentBase;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Description : COntains all the information specific to our edge effect experiment.
 */
public class Experiment1d extends SimulationExperimentBase {

    public Experiment1d(Simulation simulation, ExperimentPlan plan) {
        super(simulation, plan, null, new MatchstickSummaryReporter());
    }

    public boolean haltSimulation(Simulation simulation) {
        return false;
    }


    public boolean isIterationComplete() {
        boolean isComplete = true;
        if (isComplete) {
            _simulation.tidyUp();
        }
        return isComplete;
    }

    public void initialiseExperiment(ExperimentPlan plan) {
        super.initialiseExperiment(plan);

    }


    public void initialiseIteration(ExperimentProgress progress, ParameterMap params) {

        if (BugsimMain.isDebug()) {
            ParameterMapDebug.debugParams(params);
        }
        _simulation.clean();
        _simulation.setArchiveRemovedAgents(SimulationCategory.getArchiveRemovedAgents(params));


        Landscape landscape = _simulation.getLandscape();
        LandscapeParameters.initialiseLandscape(landscape, params);


        _currentParameters = params;


        Map initObjects = SimulationCategory.createSimulationStrategyInitialisation(landscape.getSimulation());

        StrategyDefinitionParameter boundaryP = MatchstickParameters.getIntersectionBoundaryP(params).getStrategyDefinitionValue();
        _ixBoundaryFactory = (IBoundaryAgentFactory)ParameterisedStrategyFactory.createParameterisedStrategy(boundaryP, params, initObjects);
        _ixBoundaryFactory.createBoundaryAgent(_simulation);


        StrategyDefinitionParameter matchstickCreationP = MatchstickParameters.getMatchstickCreationStrategyP(params);
        _matchstickFactory = (IMatchstickFactory)ParameterisedStrategyFactory.createParameterisedStrategy(matchstickCreationP, params, initObjects);
        _matchstickFactory.createMatchstickPopulation(_simulation);


        fireIterationInitialisedEvent(_currentParameters, progress);

        if (Experiment1d.log.isInfoEnabled()) {
            Experiment1d.log.info("ITR: " + progress.getCurrentIterationFormatted() + ", REP: " + progress.getCurrentReplicateFormatted() + " - " + getParameterSummary());
        }


    }


    public String getParameterSummary() {
        return super.getParameterSummary();
    }


    public void agentAdded(Simulation simulation, IAgent agent) {
    }

    public void agentDeath(Simulation simulation, IAgent agent) {
    }

    /**
     * @param simulation
     * @param agent
     */
    public void agentEscaped(Simulation simulation, IAgent agent) {
    }


    public ParameterMap getCurrentParameters() {
        return _currentParameters;
    }


    private static final Logger log = Logger.getLogger(Experiment1d.class);

    private ParameterMap _currentParameters;
    private IMatchstickFactory _matchstickFactory;
    private IBoundaryAgentFactory _ixBoundaryFactory;
    private IBoundaryAgentFactory _outerReleaseBoundaryFactory;
}


