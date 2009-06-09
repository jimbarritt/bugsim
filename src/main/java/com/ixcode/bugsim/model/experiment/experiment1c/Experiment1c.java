/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment1c;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgent;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentBehaviour;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentFilter;
import com.ixcode.bugsim.model.agent.butterfly.population.SimplePopulationFactory;
import com.ixcode.bugsim.model.agent.butterfly.population.IPopulationWeb;
import com.ixcode.bugsim.model.agent.cabbage.EggCounter;
import com.ixcode.bugsim.model.agent.cabbage.layout.IResourceLayout;
import com.ixcode.bugsim.model.experiment.BugsimExperimentBase;
import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.model.experiment.report.BoundaryCrossingSummaryReporter;
import com.ixcode.bugsim.model.experiment.report.CabbageButterflyDetailReporter;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.boundary.BoundaryCrossingRecorderAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Description : COntains all the information specific to our edge effect experiment.
 */
public class Experiment1c extends BugsimExperimentBase {

    public Experiment1c(Simulation simulation, ExperimentPlan plan) {
        super(simulation, plan, new CabbageButterflyDetailReporter(DistanceUnitRegistry.SCALE_ONE_M), new BoundaryCrossingSummaryReporter());
        _foragerFactory = new SimplePopulationFactory();
    }

    public boolean haltSimulation(Simulation simulation) {
        return _eggCounter.getTotalEggs() >= _maxNumberOfEggs;
    }


    public boolean isIterationComplete() {
        boolean isComplete = !_foragerFactory.hasMoreForagers() || haltSimulation(_simulation);
        if (isComplete) {
            _simulation.tidyUp();
        }
        return isComplete;
    }

    public void initialiseExperiment(ExperimentPlan plan) {
        super.initialiseExperiment(plan);
        boolean includeEscapedAgents = SimulationCategory.getIncludeEscapedAgentsInHistory(plan.getParameterTemplate());
        if (!includeEscapedAgents) {
            _simulation.setAgentArchiveFilter(new IAgentFilter() {
                public boolean acceptAgent(IAgent agent) {
                    boolean accept = true;
                    if (agent instanceof ButterflyAgent) {
                        ButterflyAgent bf = (ButterflyAgent)agent;
                        if (bf.getBehaviour() == ForagingAgentBehaviour.ESCAPED) {
                            accept = false;
                        }
                    }

                    return accept;
                }
            });
        }
    }


    public void initialiseIteration(ExperimentProgress progress, ParameterMap params) {

        if (BugsimMain.isDebug()) {
            ParameterMapDebug.debugParams(params);
        }
        _simulation.clean();


        _simulation.setArchiveRemovedAgents(SimulationCategory.getArchiveRemovedAgents(params));


        Landscape landscape = _simulation.getLandscape();
//        LandscapeParameters.initialiseLandscape(landscape, params);


        initialiseCabbagePatch(landscape, params);

//        ButterflyParameters.initialiseForagerFactory(_foragerFactory, _simulation, params);

        _currentParameters = params;

        if (_boundaryRecorder != null) {
            _boundaryRecorder.stopListening();
        }

//        Parameter recordBoundaryCrossingsP = params.findParameter(SimulationCategory.SIMULATION_RECORD_BOUNDARY_CROSSINGS);
//        boolean recordCrossings = recordBoundaryCrossingsP.getBooleanValue();
//        if (recordCrossings) {
//            _boundaryRecorder = ButterflyParameters.initialiseBoundaryRecorder(_simulation, params);
//        }
//        _REC_BOUNDARY = recordCrossings;


        fireIterationInitialisedEvent(_currentParameters, progress);


        if (Experiment1c.log.isInfoEnabled()) {
            Experiment1c.log.info("ITR: " + progress.getCurrentIterationFormatted() + ", REP: " + progress.getCurrentReplicateFormatted() + " - " + getParameterSummary());
        }

    }


    public String getParameterSummary() {
        return super.getParameterSummary() + ", REC_BOUNDARY=" + _REC_BOUNDARY + ", " + _foragerFactory.getParameterSummary() + ", " + _resourceLayout.getParameterSummary() + ", " + ButterflyParameters.getParameterSummary(_currentParameters);
    }


    public void initialiseCabbagePatch(Landscape landscape, ParameterMap params) {
        Parameter layoutP = CabbageParameters.getCabbageLayoutP(params);
        StrategyDefinitionParameter layoutStrategy = (StrategyDefinitionParameter)layoutP.getValue();

        Map initObjects = SimulationCategory.createSimulationStrategyInitialisation(landscape.getSimulation());
        _resourceLayout = (IResourceLayout)ParameterisedStrategyFactory.createParameterisedStrategy(layoutStrategy, params, initObjects);
        _resourceLayout.createCabbages(landscape.getSimulation());


    }

    public IResourceLayout getResourceLayout() {
        return _resourceLayout;
    }

    public long getMaxNumberOfEggs() {
        return _maxNumberOfEggs;
    }

    private void setMaxNumberOfEggs(long maxNumberOfEggs) {
        Object oldVal = new Long(_maxNumberOfEggs);
        _maxNumberOfEggs = maxNumberOfEggs;
        super.firePropertyChangeEvent(Experiment1c.PROPERTY_MAX_NUMBER_OF_EGGS, oldVal, new Long(_maxNumberOfEggs));
    }

    public void agentAdded(Simulation simulation, IAgent agent) {
        if (_butterflyFilter.acceptAgent(agent)) {
            _foragerFactory.foragerBirth(simulation, agent);
        }
    }

    public void agentDeath(Simulation simulation, IAgent agent) {
        if (_butterflyFilter.acceptAgent(agent)) {
            _foragerFactory.foragerDeath(simulation, agent);
        }
    }

    public void iterationCompleted(Simulation simulation) {

    }

    /**
     * @param simulation
     * @param agent
     * @todo what if its not just butterflies!!
     */
    public void agentEscaped(Simulation simulation, IAgent agent) {
        if (_butterflyFilter.acceptAgent(agent)) {
            _foragerFactory.foragerEscaped(simulation, agent);
        }
    }


    public ParameterMap getCurrentParameters() {
        return _currentParameters;
    }


    public EggCounter getEggCounter() {
        return _eggCounter;
    }

    private static final Logger log = Logger.getLogger(Experiment1c.class);


    public IPopulationWeb getPopulationWeb() {
//        return _foragerFactory;
        return null;
    }



    private ParameterMap _currentParameters;
    public static final String PROPERTY_MAX_NUMBER_OF_EGGS = "maxNumberOfEggs";

    private long _maxNumberOfEggs;
    private EggCounter _eggCounter ;



    private SimplePopulationFactory _foragerFactory;
    private IAgentFilter _butterflyFilter = ForagingAgentFilter.INSTANCE;
    private BoundaryCrossingRecorderAgent _boundaryRecorder;
    private boolean _REC_BOUNDARY = false;
    private IResourceLayout _resourceLayout;
}
