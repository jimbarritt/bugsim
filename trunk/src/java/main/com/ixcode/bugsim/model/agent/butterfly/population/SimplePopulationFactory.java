/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.butterfly.population;

import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.ForagerCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.BehaviourCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.PopulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.InitialImmigrationStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.pattern.ImmigrationPatternStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.pattern.RandomReleaseImmigrationPatternStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.SensorCategory;
import com.ixcode.bugsim.model.agent.cabbage.EggCounter;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgentFilter;
import com.ixcode.bugsim.model.agent.butterfly.immigration.pattern.IImmigrationPattern;
import com.ixcode.bugsim.model.agent.butterfly.immigration.pattern.PredefinedReleaseImmigrationPattern;
import com.ixcode.bugsim.model.agent.butterfly.*;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.model.ModelBase;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterisedStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.EscapingAgentCatcher;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.agent.physical.IAdultMortalityStrategy;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import org.apache.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 *
 * @deprecated  Replaced by PopulationWeb
 */
public class SimplePopulationFactory extends ModelBase {
    public boolean hasMoreForagers() {
        return (getForagersRemaining() > 0 || getForagersAlive() > 0);
    }

    public String getParameterSummary() {
        return " IR=" + _immmediateRerelease + ", " + _state._movementStrategy.getParameterSummary() + ", " + _state._adultMortalityStrategy.getParameterSummary() + ", " + _state._foragingStrategy.getParameterSummary() + ", MAXBF=" + _state._maxNumberForagers + ", POPBF=" + _state._foragerPopulationSize + ", RECHIST=" + _state._recordHistory;
    }

    public void setImmediateRerelease(boolean immediateRerelease) {
        _immmediateRerelease = immediateRerelease;
    }

    public boolean haltSimulation(Simulation simulation) {
        return _eggCounter.getTotalEggs() >= _maxNumberOfEggs;

    }

    public IMovementStrategy getMovementStrategy() {
        return _state._movementStrategy;
    }

    public IPopulation getPopulation() {
        return null;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(EscapingAgentCatcher.PROPERTY_NUMBER_OF_ESCAPED_AGENTS)) {
            setForagersRemainingFormatted(createForagersRemainingString());
        }
    }

    public void setVisionStrategy(IVisionStrategy strategy) {
        _state._visionStrategy = strategy;
    }

    public IVisionStrategy getVisionStrategy() {
        return _state._visionStrategy;
    }

    public void setOlfactionStrategy(IOlfactionStrategy olfactionStrategy) {
        _state._olfactionStrategy = olfactionStrategy;
    }

    public IOlfactionStrategy getOlfactionStrategy() {
        return _state._olfactionStrategy;
    }

    public void initialise(SimulationCategory simulationCategory, ForagerCategory foragerCategory, Simulation simulation, ParameterMap params) {
        clean();

        Map initObjects = SimulationCategory.createSimulationStrategyInitialisation(simulation);
        Landscape landscape = simulation.getLandscape();

        // Lifecycle:
        initialisePopulation(foragerCategory, initObjects, landscape, simulation);

        // Behaviour:
        initialiseBehaviour(foragerCategory, initObjects, params);

        setForagerPopulationSize(1); // This will be set by the timescale strategy , eventually

        createForagerPopulation(simulation);


    }

    private void initialiseBehaviour(ForagerCategory foragerCategory, Map initObjects, ParameterMap params) {
        BehaviourCategory behaviourC = foragerCategory.getBehaviourCategory();

        IMovementStrategy movementStrategy = (IMovementStrategy)behaviourC.getMovementStrategy().instantiateImplementedStrategy(initObjects);
        setMovementStrategy(movementStrategy);

        setRecordHistory(behaviourC.isRecordLifeHistory());

        IForagingStrategy foragingStrategy = (IForagingStrategy)behaviourC.getForagingStrategy().instantiateImplementedStrategy(initObjects);
        setForagingStrategy(foragingStrategy);

        SensorCategory sensorCategory = foragerCategory.getSensorCategory();
        IVisionStrategy visionStrategy = (IVisionStrategy)sensorCategory.getVisualSensorStrategy().instantiateImplementedStrategy(initObjects);
        setVisionStrategy(visionStrategy);

        IOlfactionStrategy olfactionStrategy = (IOlfactionStrategy)sensorCategory.getOlfactorySensorStrategy().instantiateImplementedStrategy(initObjects);
        setOlfactionStrategy(olfactionStrategy);


        initialiseMotivations(params, initObjects);
    }

    private void initialiseMotivations(ParameterMap params, Map initObjects) {
//        List motivationStrategyPs = getMotivationStrategies(params);
        List motivationStrategyPs = new ArrayList();         // Not using these at the moment.
        List motivationStrategies = new ArrayList();
        for (Iterator itr = motivationStrategyPs.iterator(); itr.hasNext();) {
            StrategyDefinitionParameter strategyP = (StrategyDefinitionParameter)itr.next();
            IMotivationStrategy motivationS = (IMotivationStrategy)ParameterisedStrategyFactory.createParameterisedStrategy(strategyP, params, initObjects);
            motivationStrategies.add(motivationS);
        }
        setMotivationStrategies(motivationStrategies);
    }

    private void initialisePopulation(ForagerCategory foragerCategory, Map initObjects, Landscape landscape, Simulation simulation) {
        PopulationCategory populationC = foragerCategory.getPopulationCategory();

        setImmediateRerelease(false);


        InitialImmigrationStrategyDefinition immigrationStrategy = (InitialImmigrationStrategyDefinition)populationC.getImmigrationStrategy();
        IImmigrationPattern immigrationPattern = (IImmigrationPattern)immigrationStrategy.getImmigrationPattern().instantiateImplementedStrategy(initObjects);
        setImmigrationPattern(immigrationPattern);


        CartesianBounds escapeBounds = landscape.getLogicalBounds();
        boolean isCircular = landscape.isCircular();
        if (immigrationPattern instanceof PredefinedReleaseImmigrationPattern)
        { //This bit is horrible but dont have time for it DHTFI!!
            Grid zeroGrid = simulation.getLandscape().getGrid(Landscape.ZERO_BOUNDARY_GRID);
            escapeBounds = zeroGrid.getBounds();
            isCircular = zeroGrid.isCircular();
        }


        setEscapingAgentCatcher(new EscapingAgentCatcher(simulation, escapeBounds, isCircular, ForagingAgentFilter.INSTANCE));

        IAdultMortalityStrategy adultMortalityStrategy = (IAdultMortalityStrategy)populationC.getAdultMortality().instantiateImplementedStrategy(initObjects);
        setMortalityStrategy(adultMortalityStrategy);

        // Have to do this after escaping agent catcher is initialised...
        ImmigrationPatternStrategyBase psb = immigrationStrategy.getImmigrationPattern();

        setMaxNumberForagers(psb.getNumberToRelease());
        setForagersRemaining(getMaxNumberForagers());

        _maxNumberOfEggs = Long.MAX_VALUE;
        if (psb instanceof RandomReleaseImmigrationPatternStrategyDefinition) {
            RandomReleaseImmigrationPatternStrategyDefinition rrs = (RandomReleaseImmigrationPatternStrategyDefinition)psb;
            _maxNumberOfEggs = rrs.getEggLimit();
        }

        _eggCounter.resetCounter(_maxNumberOfEggs);
        _eggCounter.register(simulation.getLiveAgents(CabbageAgentFilter.INSTANCE));

    }

    public EggCounter getEggCounter() {
        return _eggCounter;
    }


    public static final String P_FORAGERS_ALIVE_FORMATTED = "foragersAliveFormatted";
    public static final String P_MAX_NUMBER_FORAGERS = "maxNumberForagers";
    public static final String P_FORAGER_POPULATION_SIZE = "foragerPopulationSize";
    public static final String P_FORAGERS_REMAINING = "foragersRemaining";
    public static final String P_FORAGERS_ALIVE = "foragersAlive";
    public static final String P_FORAGERS_REMAINING_FORMATTED = "foragersRemainingFormatted";
    public static final String P_FORAGERS_DEAD = "foragersDead";
    public static final String P_FORAGERS_ESCAPED = "foragersEscaped";


    public void setImmigrationPattern(IImmigrationPattern immigrationPattern) {
        _state._immigrationPattern = immigrationPattern;
    }

    public IImmigrationPattern getImmigrationPattern() {
        return _state._immigrationPattern;
    }

    public void setForagingStrategy(IForagingStrategy foragingStrategy) {
        _state._foragingStrategy = foragingStrategy;
    }

    public void setMotivationStrategies(List motivationStrategies) {
        _state._motivationStrategies = motivationStrategies;
    }

    public void setMovementStrategy(IMovementStrategy movementStrategy) {
        _state._movementStrategy = movementStrategy;
    }

    public void setMortalityStrategy(IAdultMortalityStrategy adultMortalityStrategy) {
        _state._adultMortalityStrategy = adultMortalityStrategy;
    }


    public long getMaxNumberForagers() {
        return _state._maxNumberForagers;
    }

    public void setMaxNumberForagers(long maxNumberForagers) {
        long oldVal = _state._maxNumberForagers;
        _state._maxNumberForagers = maxNumberForagers;
        super.firePropertyChangeEvent(P_MAX_NUMBER_FORAGERS, oldVal, _state._maxNumberForagers);
    }


    public void setForagersRemainingFormatted(String foragersRemainingFormatted) {
        String oldVal = _state._foragersRemainingFormatted;
        _state._foragersRemainingFormatted = foragersRemainingFormatted;
        super.firePropertyChangeEvent(P_FORAGERS_REMAINING_FORMATTED, oldVal, _state._foragersRemainingFormatted);
    }

    public String getForagersRemainingFormatted() {
        return _state._foragersRemainingFormatted;
    }

    public long getForagerPopulationSize() {
        return _state._foragerPopulationSize;
    }

    public void setForagerPopulationSize(long foragerPopulationSize) {
        long oldVal = _state._foragerPopulationSize;
        _state._foragerPopulationSize = foragerPopulationSize;
        super.firePropertyChangeEvent(P_FORAGER_POPULATION_SIZE, oldVal, _state._foragerPopulationSize);
    }


    public long getForagersRemaining() {
        return _state._foragersRemaining;
    }

    public void setForagersRemaining(long butterfliesRemaining) {
        Object oldVal = new Long(_state._foragersRemaining);
        _state._foragersRemaining = butterfliesRemaining;
        super.firePropertyChangeEvent(P_FORAGERS_REMAINING, oldVal, new Long(_state._foragersRemaining));
        setForagersRemainingFormatted(createForagersRemainingString());

    }

    private String createForagersRemainingString() {
        StringBuffer sb = new StringBuffer();
        sb.append(_state._foragersRemaining).append(" of ").append(_state._maxNumberForagers);
        sb.append(" (dead: ").append(getForagersDead());
        sb.append(", escaped: " + getForagersEscaped());
        sb.append(" )");
        return sb.toString();
    }

    public long getForagersDead() {
        return _state._maxNumberForagers - _state._foragersRemaining - _state._foragersAlive - _state._catcher.getNumberOfEscapedAgents();
    }

    public long getForagersEscaped() {
        return _state._catcher.getNumberOfEscapedAgents();
    }


    public long getForagersAlive() {
        return _state._foragersAlive;
    }

    public void setForagersAlive(long foragersAlive) {
        if (foragersAlive < -1) {
            throw new RuntimeException("Foragers less htan 1!");
        }
        Object oldVal = new Long(_state._foragersAlive);
        _state._foragersAlive = foragersAlive;
        super.firePropertyChangeEvent(P_FORAGERS_ALIVE, oldVal, new Long(_state._foragersAlive));
        setForagersAliveFormatted(_state._foragersAlive + " of " + _state._foragerPopulationSize);
        setForagersRemainingFormatted(createForagersRemainingString());
    }

    public String getForagersAliveFormatted() {
        return _state._foragersAliveFormatted;
    }

    private void setForagersAliveFormatted(String formatted) {
        String oldVal = _state._foragersAliveFormatted;
        _state._foragersAliveFormatted = formatted;
        super.firePropertyChangeEvent(P_FORAGERS_ALIVE_FORMATTED, oldVal, _state._foragersAliveFormatted);
    }


    public boolean isRecordHistory() {
        return _state._recordHistory;
    }

    public void setRecordHistory(boolean recordHistory) {
        _state._recordHistory = recordHistory;
    }

    public void createForagerPopulation(Simulation simulation) {


        long numberToCreate = (_state._foragersRemaining < _state._foragerPopulationSize) ? _state._foragersRemaining : _state._foragerPopulationSize;

        for (int i = 0; i < numberToCreate; ++i) {
            ButterflyAgent butterfly = createForager(_state._catcher);
            simulation.addAgent(butterfly);
        }


    }


    public ButterflyAgent createForager(EscapingAgentCatcher catcher) {
        double initialMoveLength = _state._immigrationPattern.nextReleaseMoveLength(_state._movementStrategy);
        RectangularCoordinate coord = _state._immigrationPattern.nextReleaseLocation();
        double heading = _state._immigrationPattern.nextReleaseAzimuth(coord, initialMoveLength);
        double releaseI = _state._immigrationPattern.nextReleaseI();


        ButterflyAgent butterfly = new ButterflyAgent(new Location(coord), heading, releaseI, 2, _state._movementStrategy, _state._foragingStrategy,
                _state._adultMortalityStrategy, _state._recordHistory, ForagingAgentBehaviour.RELEASE,
                _state._visionStrategy, _state._olfactionStrategy, _state._motivationStrategies);



        return butterfly;
    }

    public void setEscapingAgentCatcher(EscapingAgentCatcher escapingAgentCatcher) {
        if (_state._catcher != null) {
            _state._catcher.stopListening();
//            _state._catcher.removePropertyChangeListener(EscapingAgentCatcher.PROPERTY_NUMBER_OF_ESCAPED_AGENTS, this);
        }
        _state._catcher = escapingAgentCatcher;
        if (escapingAgentCatcher != null) {
//            _state._catcher.addPropertyChangeListener(EscapingAgentCatcher.PROPERTY_NUMBER_OF_ESCAPED_AGENTS, this);
        }
    }


    public void foragerBirth(Simulation simulation, IAgent agent) {
        setForagersAlive(getForagersAlive() + 1);
        setForagersRemaining(getForagersRemaining() - 1);
        if (log.isDebugEnabled()) {
            log.debug("[foragerBirth] alive: " + agent.getId() + ", Now Alive: " + getForagersAlive() + ", remaining: " + getForagersRemaining());
        }
    }

    public void foragerDeath(Simulation simulation, IAgent agent) {
        setForagersAlive(getForagersAlive() - 1);
        if (log.isDebugEnabled()) {
            log.debug("[foragerDeath] " + agent.getId() + ", Now Alive: " + getForagersAlive() + ", remaining: " + getForagersRemaining());
        }

        if (_immmediateRerelease) {
            immediateForagerDeath(simulation);
        } else {
            populationDeath(simulation);
        }


    }

    public void foragerEscaped(Simulation simulation, IAgent agent) {
        setForagersAlive(_state._foragersAlive - 1);
        if (log.isDebugEnabled()) {
            log.debug("[foragerEscaped] " + agent.getId() + ", Now Alive: " + getForagersAlive() + ", remaining: " + getForagersRemaining());
        }

        if (_immmediateRerelease) {
            immediateForagerDeath(simulation);
        } else {
            populationDeath(simulation);
        }

    }


    /**
     * Waits until the population is dead before creating another population
     *
     * @param simulation
     */
    private void populationDeath(Simulation simulation) {
        if ((_state._foragersAlive == 0) && (_state._foragersRemaining > 0)) {
            long numberToCreate = (_state._foragersRemaining < _state._foragerPopulationSize) ? _state._foragersRemaining : _state._foragerPopulationSize;


            for (int i = 0; i < numberToCreate; ++i) {
                ButterflyAgent butterfly = createForager(_state._catcher);
                simulation.addAgentNextTimestep((IPhysicalAgent)butterfly);
            }


        }
    }

    /**
     * Release immediately upon the death of the previous forager (sequential)
     *
     * @param simulation
     */
    private void immediateForagerDeath(Simulation simulation) {
        if (_state._foragersRemaining > 0 && _state._foragersAlive < _state._foragerPopulationSize) {

            ButterflyAgent butterfly = createForager(_state._catcher);
            simulation.addAgentNextTimestep(butterfly);
            setForagersAlive(_state._foragersAlive + 1);

        }
    }

    public void clean() {
        setEscapingAgentCatcher(null);
        _state = new ForagerFactoryState();
    }

    public void agentBirth(Simulation simulation, IAgent agent) {
        if (_foragerFilter.acceptAgent(agent)) {
            foragerBirth(simulation, agent);
        }
    }

    public void agentDeath(Simulation simulation, IAgent agent) {
        if (_foragerFilter.acceptAgent(agent)) {
            foragerDeath(simulation, agent);
        }
    }

    public void timestepCompleted(Simulation simulation) {

    }

    /**
     * @param simulation
     * @param agent
     * @todo what if its not just butterflies!!
     */
    public void agentEscaped(Simulation simulation, IAgent agent) {
        if (_foragerFilter.acceptAgent(agent)) {
            foragerEscaped(simulation, agent);
        }
    }


    private static class ForagerFactoryState {

        private IImmigrationPattern _immigrationPattern;
        private IMovementStrategy _movementStrategy;
        private IAdultMortalityStrategy _adultMortalityStrategy;
        private IForagingStrategy _foragingStrategy;

        private long _maxNumberForagers;
        private long _foragersRemaining;

        private long _foragersAlive;
        private long _foragerPopulationSize;


        private String _foragersAliveFormatted;
        private String _foragersRemainingFormatted;


        private boolean _recordHistory;


        private EscapingAgentCatcher _catcher;
        public IVisionStrategy _visionStrategy;
        public IOlfactionStrategy _olfactionStrategy;
        public List _motivationStrategies;
    }

    private ForagerFactoryState _state = new ForagerFactoryState();
    private boolean _immmediateRerelease = true;

    private static final Logger log = Logger.getLogger(SimplePopulationFactory.class);

    private long _maxNumberOfEggs;
    private EggCounter _eggCounter ;
    private IAgentFilter _foragerFilter = ForagingAgentFilter.INSTANCE;

}
