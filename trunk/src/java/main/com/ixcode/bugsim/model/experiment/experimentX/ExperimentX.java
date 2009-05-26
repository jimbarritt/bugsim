/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experimentX;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.bugsim.model.agent.butterfly.population.IPopulationWeb;
import com.ixcode.bugsim.model.agent.butterfly.population.DiscreteGenerationPopulationWeb;
import com.ixcode.bugsim.model.agent.butterfly.population.DiscreteGeneration;
import com.ixcode.bugsim.model.agent.butterfly.population.IPopulation;
import com.ixcode.bugsim.model.agent.cabbage.layout.IResourceLayout;
import com.ixcode.bugsim.model.agent.cabbage.EggCounter;
import com.ixcode.bugsim.model.experiment.BugsimExperimentBase;
import com.ixcode.bugsim.model.experiment.experimentX.configuration.LandscapeConfiguration;
import com.ixcode.bugsim.model.experiment.experimentX.configuration.SimulationConfiguration;
import com.ixcode.bugsim.model.experiment.experimentX.configuration.ResourceConfiguration;
import com.ixcode.bugsim.model.experiment.experimentX.configuration.PopulationWebConfiguration;
import com.ixcode.bugsim.model.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.model.experiment.report.CabbageButterflyDetailReporter;
import com.ixcode.bugsim.model.experiment.report.ExperimentXSummaryReporter;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.EscapingAgentCatcher;
import com.ixcode.framework.simulation.model.timescale.ITimescale;
import com.ixcode.framework.simulation.model.timescale.DiscreteGenerationsTimescale;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Description : Generic Experiment that completely configures itself from the parameters - ultimately this will be the only experiment we need.
 */
public class ExperimentX extends BugsimExperimentBase {


    /**
     * @param simulation
     * @param plan
     * @todo make reporters parameterised aswell.
     */
    public ExperimentX(Simulation simulation, ExperimentPlan plan) {
        super(simulation, plan, new CabbageButterflyDetailReporter(DistanceUnitRegistry.SCALE_ONE_CM), new ExperimentXSummaryReporter());

        addConfiguration(new SimulationConfiguration());
        addConfiguration(new LandscapeConfiguration());
        addConfiguration(new ResourceConfiguration());
        addConfiguration(new PopulationWebConfiguration());

    }


    public void initialiseExperiment(ExperimentPlan plan) {
        super.initialiseExperiment(plan);
    }


    public void initialiseIteration(ExperimentProgress progress, ParameterMap params) {
        _currentBugsimParams = new BugsimParameterMap(params, false);
        if (BugsimMain.isDebug()) {
            ParameterMapDebug.debugParams(_currentBugsimParams.getParameterMap());
        }

        _simulation.clean();


        for (Iterator itr = _configurations.iterator(); itr.hasNext();) {
            IExperimentXConfiguration configuration = (IExperimentXConfiguration)itr.next();
            configuration.initialise(this, _currentBugsimParams);
        }

        fireIterationInitialisedEvent(_currentBugsimParams.getParameterMap(), progress);

        if (ExperimentX.log.isInfoEnabled()) {
            ExperimentX.log.info("ITR: " + progress.getCurrentIterationFormatted() + ", REP: " + progress.getCurrentReplicateFormatted() + " - " + getParameterSummary());
        }

    }

    public boolean haltSimulation(Simulation simulation) {
        return _populationWeb.haltSimulation(simulation);
    }


    public boolean isIterationComplete() {
        boolean isComplete = haltSimulation(_simulation);
        if (isComplete) {
            _simulation.tidyUp();
        }
        return isComplete;

    }

    public String getParameterSummary() {
        return super.getParameterSummary() + ", " + _resourceLayout.getParameterSummary() + ", " + _populationWeb.getParameterSummary();
    }


    public ParameterMap getCurrentParameters() {
        return _currentBugsimParams.getParameterMap();
    }
     public BugsimParameterMap getCurrentBugsimParameters() {
        return _currentBugsimParams;
    }
    public IPopulationWeb getPopulationWeb() {
        return _populationWeb;
    }

    public void setPopulationWeb(IPopulationWeb populationWeb) {
        if (_populationWeb != null) {    // @todo maybe have simulation remove all listeners when you call clean...
            _simulation.removeTimestepListener(_populationWeb);
        }
        _populationWeb = populationWeb;
        _simulation.addTimestepListener(_populationWeb);
    }

    public IResourceLayout getResourceLayout() {
        return _resourceLayout;
    }

    public void setResourceLayout(IResourceLayout resourceLayout) {
        _resourceLayout = resourceLayout;
    }

    private void addConfiguration(IExperimentXConfiguration configuration) {
        _configurations.add(configuration);
    }

    public ITimescale getTimescale() {
        return _timescale;
    }

    public void setTimescale(ITimescale timescale) {
        _timescale = timescale;
    }

    public EscapingAgentCatcher getEscapingAgentCatcher() {
        return _escapingAgentCatcher;
    }

    public void setEscapingAgentCatcher(EscapingAgentCatcher escapingAgentCatcher) {
        _escapingAgentCatcher = escapingAgentCatcher;
    }

    public void reportProgress(ExperimentProgress progress) {
        IPopulationWeb populationWeb = getPopulationWeb();
        if (populationWeb != null) {
            if (!(populationWeb instanceof DiscreteGenerationPopulationWeb)) {
                throw new IllegalStateException("Can only report progress for discrete generations!");
            }
            DiscreteGenerationPopulationWeb discreteWeb = (DiscreteGenerationPopulationWeb)populationWeb;

            DiscreteGenerationsTimescale timescale = discreteWeb.getTimescale();
            DiscreteGeneration gen = discreteWeb.getCurrentGeneration();
            IPopulation population = gen.getCurrentPopulation();

            String generationStatus;

            if (discreteWeb.getCurrentGeneration().isEndOfGeneration() && !discreteWeb.hasNextGeneration()) {
                generationStatus = "Complete";
            } else {
                generationStatus = gen.getGenerationTime() + " of " + timescale.getGenerationLimit() + " (" + gen.getGenerationTimestep() + " step of " + timescale.getTimestepLimit() + ")";
            }
            progress.addCustomProperty("Generation", generationStatus);
            if (population != null) {
                progress.addCustomProperty("Population", population.getPopulationLabel());
                progress.addCustomProperty("Foragers Alive", population.getForagersAliveFormatted());
                progress.addCustomProperty("Foragers Remaining", population.getForagersRemainingFormatted());

            } else {
                progress.addCustomProperty("Population", "NA");
                progress.addCustomProperty("Foragers Alive", "NA");
                progress.addCustomProperty("Foragers Remaining", "NA");

            }

            EggCounter eggCounter = getPopulationWeb().getForagerPopulationFactory().getEggCounter();
            progress.addCustomProperty("Egg Count", eggCounter.getTotalEggsFormatted());
        }
    }

    private static final Logger log = Logger.getLogger(ExperimentX.class);

    private BugsimParameterMap _currentBugsimParams;


    private IPopulationWeb _populationWeb;
    private IResourceLayout _resourceLayout;

    private List _configurations = new ArrayList();

    private ITimescale _timescale;
    private EscapingAgentCatcher _escapingAgentCatcher;
}
