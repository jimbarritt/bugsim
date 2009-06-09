/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment2a;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgent;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentBehaviour;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentFilter;
import com.ixcode.bugsim.model.agent.butterfly.population.SimplePopulationFactory;
import com.ixcode.bugsim.model.agent.butterfly.population.IPopulationWeb;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.model.agent.cabbage.EggCounter;
import com.ixcode.bugsim.model.agent.cabbage.layout.IResourceLayout;
import com.ixcode.bugsim.model.experiment.BugsimExperimentBase;
import com.ixcode.bugsim.model.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.landscape.LandscapeParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.ResourceCategory;
import com.ixcode.bugsim.model.experiment.report.CabbageButterflyDetailReporter;
import com.ixcode.bugsim.model.experiment.report.ExperimentXSummaryReporter;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.model.EscapingAgentCatcher;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.boundary.BoundaryCrossingRecorderAgent;
import com.ixcode.framework.simulation.model.agent.motile.movement.GaussianAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.RandomWalkStrategy;
import com.ixcode.framework.simulation.model.agent.physical.IAdultMortalityStrategy;
import com.ixcode.framework.simulation.model.agent.surveyor.SignalSurveyingAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Random;

/**
 * Description : COntains all the information specific to our sensory perception experiment.
 */
public class Experiment2a extends BugsimExperimentBase {

    public static final String OUTPUT_DIR_NAME = "sense";
    public static final String EXPERIMENT_NAME = "Experiment 2a";

    /**
     * @todo make reporters parameterised aswell.
     * @param simulation
     * @param plan
     */
    public Experiment2a(Simulation simulation, ExperimentPlan plan) {
        super(simulation, plan, new CabbageButterflyDetailReporter(DistanceUnitRegistry.SCALE_ONE_CM), new ExperimentXSummaryReporter());
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


        BugsimParameterMap bugsimParams = new BugsimParameterMap(params, false);

        //@todo make the simulation a parameterisable strategy ?
        _simulation.setArchiveRemovedAgents(SimulationCategory.getArchiveRemovedAgents(params));



        Landscape landscape = _simulation.getLandscape();

        //@todo make the landscape a Parameterisable Strategy ?
        LandscapeParameters.initialiseLandscape(landscape, bugsimParams);


        initialiseCabbagePatch(landscape, params);

        initialiseInformationSurveyor(params);


//        ButterflyParameters.initialiseForagerFactory(_foragerFactory, _simulation, params);

        _currentParameters = params;

//        initialiseBoundaryRecorder(params);


        fireIterationInitialisedEvent(_currentParameters, progress);


        if (Experiment2a.log.isInfoEnabled()) {
            Experiment2a.log.info("ITR: " + progress.getCurrentIterationFormatted() + ", REP: " + progress.getCurrentReplicateFormatted() + " - " + getParameterSummary());
        }

    }

    private void initialiseInformationSurveyor(ParameterMap params) {
        if (_surveyor == null)  {
            _surveyor = new SignalSurveyingAgent(this, ResourceCategory.CABBAGE_SIGNAL_SURFACE_NAME, 50d);
            _simulation.addAgent(_surveyor);
        }
    }

    private void initialiseBoundaryRecorder(ParameterMap params) {
        if (_boundaryRecorder != null) {
            _boundaryRecorder.stopListening();
        }

//        Parameter recordBoundaryCrossingsP = params.findParameter(SimulationCategory.SIMULATION_RECORD_BOUNDARY_CROSSINGS);
//        boolean recordCrossings = recordBoundaryCrossingsP.getBooleanValue();
//        if (recordCrossings) {
//            _boundaryRecorder = ButterflyParameters.initialiseBoundaryRecorder(_simulation, params);
//        }
//        _REC_BOUNDARY = recordCrossings;
    }


    public String getParameterSummary() {
//        return super.getParameterSummary() + ", REC_BOUNDARY=" + _REC_BOUNDARY + ", NUM_EGGS=" + _maxNumberOfEggs + ", " + _butterflyFactory.getParameterSummary() + ", "  + _cabbageFactory.getParameterSummary() + ", " + ButterflyParameters.getParameterSummary(_currentParameters);
          return super.getParameterSummary() + ", REC_BOUNDARY=" + _REC_BOUNDARY + ", NUM_EGGS=" + _maxNumberOfEggs + ", " + ", "  + _resourceLayout.getParameterSummary() + ", " + ButterflyParameters.getParameterSummary(_currentParameters);
    }


    public void initialiseCabbagePatch(Landscape landscape, ParameterMap params) {
        Parameter layoutP = CabbageParameters.getCabbageLayoutP(params);
        StrategyDefinitionParameter layoutStrategy = (StrategyDefinitionParameter)layoutP.getValue();

        Map initObjects = SimulationCategory.createSimulationStrategyInitialisation(landscape.getSimulation());
        _resourceLayout = (IResourceLayout)ParameterisedStrategyFactory.createParameterisedStrategy(layoutStrategy, params, initObjects);
        _resourceLayout.createCabbages(landscape.getSimulation());





    }




    public long getMaxNumberOfEggs() {
        return _maxNumberOfEggs;
    }

    private void setMaxNumberOfEggs(long maxNumberOfEggs) {
        Object oldVal = new Long(_maxNumberOfEggs);
        _maxNumberOfEggs = maxNumberOfEggs;
        super.firePropertyChangeEvent(Experiment2a.PROPERTY_MAX_NUMBER_OF_EGGS, oldVal, new Long(_maxNumberOfEggs));
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


    public IResourceLayout getResourceLayout() {
        return _resourceLayout;
    }

    public ParameterMap getCurrentParameters() {
        return _currentParameters;
    }


    public EggCounter getEggCounter() {
        return _eggCounter;
    }

    private static final Logger log = Logger.getLogger(Experiment2a.class);


    public IPopulationWeb getPopulationWeb() {
//        return _foragerFactory;
        return null;
    }

    private CabbageAgent[][] _cabbages;
    private GaussianAzimuthGenerator _headingGenerator;
    private Grid _releaseBoundaryGrid;
    private EscapingAgentCatcher _catcher;
    private RandomWalkStrategy _movementStrategy;

    private static final Random RANDOM = new Random(System.currentTimeMillis());


    private ParameterMap _currentParameters;
    private long _numberOfEggs;
    public static final String PROPERTY_MAX_NUMBER_OF_EGGS = "maxNumberOfEggs";

    private long _maxNumberOfEggs;
    private EggCounter _eggCounter ;


    private String _B;

    private String _A;
    private String _L;
    private static final DecimalFormat FORMAT2D = new DecimalFormat("0.00");
    private IAdultMortalityStrategy _adultMortalityStrategy;

    private SimplePopulationFactory _foragerFactory;
    private IResourceLayout _resourceLayout;
    private IAgentFilter _butterflyFilter = ForagingAgentFilter.INSTANCE;
    private BoundaryCrossingRecorderAgent _boundaryRecorder;
    private boolean _REC_BOUNDARY = false;
    private SignalSurveyingAgent _surveyor;
}
