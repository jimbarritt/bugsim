/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.butterfly;

import com.ixcode.bugsim.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.agent.cabbage.CabbageAgentFilter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.IVisualAgent;
import com.ixcode.framework.simulation.model.agent.motile.MotileAgentBase;
import com.ixcode.framework.simulation.model.agent.motile.IOlfactoryAgent;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.Move;
import com.ixcode.framework.simulation.model.agent.physical.IAdultMortalityStrategy;
import com.ixcode.framework.simulation.model.agent.physical.AgentBehaviour;
import com.ixcode.framework.simulation.model.agent.resource.IResourceAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ButterflyAgent extends MotileAgentBase implements IVisualAgent, IOlfactoryAgent,  IMotivationalAgent {
    public ForagingAgentBehaviour getBehaviour() {
        return _behaviour;
    }


    public IMovementStrategy getMovementStrategy() {
        return _movementStrategy;
    }

    public IOlfactionStrategy getOlfactionStrategy() {
        return _olfactionStrategy;
    }

    public IForagingStrategy getForagingStrategy() {
        return _foragingStrategy;
    }


    public static final String AGENT_CLASS_ID = "ButterflyAgent";

    public ButterflyAgent(RectangularCoordinate location, double azimuth, ForagerAgentStrategies strategies, boolean recordHistory, AgentBehaviour behaviour) {
        super(AGENT_CLASS_ID, new Location(location), 2, azimuth,  recordHistory);

        _movementStrategy = strategies.getMovementStrategy();
        _numberOfEggs = strategies.getForagingStrategy().getInitialNumberOfEggs();
        _behaviour = (ForagingAgentBehaviour)behaviour;


        _initialHeading = azimuth;
        _I = 0;

        _adultMortalityStrategy = strategies.getMortalityStrategy();
        _foragingStrategy = strategies.getForagingStrategy();

        _visionStrategy = strategies.getVisionStrategy();
        _olfactionStrategy = strategies.getOlfactionStrategy();

        setMotivationStrategies(strategies.getMotivationStrategies());

        updateStateHistory();
    }

    public ButterflyAgent(Location location, double initialDirection, double I, double radius, IMovementStrategy movementStrategy,
                          IForagingStrategy foragingStrategy, IAdultMortalityStrategy adultMortalityStrategy,
                          boolean recordHistory, ForagingAgentBehaviour initialBehaviour,
                          IVisionStrategy visionStrategy, IOlfactionStrategy olfactionStrategy,
                          List motivationStrategies) {

        super(AGENT_CLASS_ID, location, radius, initialDirection,  recordHistory);

        _movementStrategy = movementStrategy;
        _numberOfEggs = foragingStrategy.getInitialNumberOfEggs();
        _behaviour = initialBehaviour;


        _initialHeading = initialDirection;
        _I = I;

        _adultMortalityStrategy = adultMortalityStrategy;
        _foragingStrategy = foragingStrategy;

        _visionStrategy = visionStrategy;
        _olfactionStrategy = olfactionStrategy;

        setMotivationStrategies(motivationStrategies);

        updateStateHistory();
    }

    public void setMotivationStrategies(List motivationStrategies) {
        for (Iterator itr = motivationStrategies.iterator(); itr.hasNext();) {
            IMotivationStrategy strategy = (IMotivationStrategy)itr.next();
            _motivationStrategyMap.put(strategy.getName(), strategy);
        }
    }

    public IMotivationStrategy getMotivationStrategy(String name) {
        return (IMotivationStrategy)_motivationStrategyMap.get(name);
    }

    public void registerSimulation(Simulation simulation) {
        super.registerSimulation(simulation);
        _visionStrategy.update(simulation, this.getLocation().getCoordinate(), super.getAzimuth(), CabbageAgentFilter.INSTANCE);

    }

    public void executeTimeStep(Simulation simulation) {
        if (log.isDebugEnabled()) {
            log.debug("Begin Timestep");
        }

        if (isAlive() && isInSimulation()) {

            setBehaviour(ForagingAgentBehaviour.SEARCHING);


            Move move = _movementStrategy.calculateNextLocation(simulation, this);
            Location next = move.getLocation();

            double azimuth = super.getLocation().getCoordinate().calculateAzimuthTo(next.getCoordinate());
            if (Double.isNaN(azimuth)) {  // Staying put!
                azimuth = super.getAzimuth();
            }

            _visionStrategy.update(simulation, next.getCoordinate(),azimuth, CabbageAgentFilter.INSTANCE);


            next = _foragingStrategy.forage(this, next, simulation);

            incrAge(); // It will be this age after the move so anyone listening to it will want this to be correct


            super.moveTo(move, next);


            updateStateHistory();


            if (!_adultMortalityStrategy.isAlive(this)) {
                kill(simulation);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("End Timestep");
        }

    }

    public IVisionStrategy getVisionStrategy() {
        return _visionStrategy;
    }


    public void tidyUp(Simulation simulation) {
        _behaviour = isEscaped() ? ForagingAgentBehaviour.ESCAPED : (isAlive()) ? ForagingAgentBehaviour.SEARCHING : ForagingAgentBehaviour.DEAD;
        updateStateHistory();
    }

    public void setCurrentCabbage(CabbageAgent currentCabbage) {
        _currentCabbage = currentCabbage;
    }

    public CabbageAgent getCurrentCabbage() {
        return _currentCabbage;
    }

    public void setBehaviour(ForagingAgentBehaviour behaviour) {
        _behaviour = behaviour;
        super.behaviourOccured(behaviour);
    }


    public void layEgg(IResourceAgent resource, Simulation simulation) {
        if (_numberOfEggs > 0) {
            setBehaviour(ForagingAgentBehaviour.OVIPOSITING);
            resource.addEgg();
            setCurrentCabbage((CabbageAgent)resource);
            _numberOfEggs--;
        }
    }


    public List getStateHistory() {
        return _stateHistory;
    }

    private void updateStateHistory() {
        if (super.getIsRecordHistory()) {
            ButterflyAgentState state = createState();
            _stateHistory.add(state);
        }
    }

    public ButterflyAgentState getLastState() {
        return (ButterflyAgentState)_stateHistory.get(_stateHistory.size() - 1);
    }

    private ButterflyAgentState createState() {
        return new ButterflyAgentState(getId(), getAge(), getLocation(), _behaviour, super.getAzimuth(), _numberOfEggs, _currentCabbage, super.getLastMove());
    }


    public String toString() {
        return "Butterfly" + super.getLocation() + ", heading=" + D2.format(getAzimuth()) + ", isAlive=" + isAlive() + ",  age=" + getAge() + ", id=" + getId() + ", behaviour=" + _behaviour + ", movement=" + _movementStrategy;

    }

    public long getEggCount() {
        return _numberOfEggs;
    }


    public double getInitialAzimuth() {
        return _initialHeading;
    }


    public double getReleaseI() {
        return _I;
    }

    private long _numberOfEggs;
    private IMovementStrategy _movementStrategy;
    private ForagingAgentBehaviour _behaviour;
    private List _stateHistory = new ArrayList();
    private static final Logger log = Logger.getLogger(ButterflyAgent.class);


    private double _initialHeading;
    private static final DecimalFormat D2 = new DecimalFormat("0.00");


    private CabbageAgent _currentCabbage;
    private IAdultMortalityStrategy _adultMortalityStrategy;
    private IForagingStrategy _foragingStrategy;


    private double _I;


    private static final DecimalFormat F2 = new DecimalFormat("0.00");
    private IVisionStrategy _visionStrategy;
    private IOlfactionStrategy _olfactionStrategy;
    private Map _motivationStrategyMap = new HashMap();


}
