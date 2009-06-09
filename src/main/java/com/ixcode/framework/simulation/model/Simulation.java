/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model;

import com.ixcode.bugsim.view.landscape.AgentInfoRegistry;
import com.ixcode.framework.math.random.RandomNumberGenerator;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.information.GravitationalCalculator;
import com.ixcode.framework.simulation.model.landscape.information.GravitationalCalculatorFactory;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Description : Provides the overall control for the simulation
 */
public class Simulation implements Serializable {


    public Simulation() {
        this(GravitationalCalculatorFactory.createNullGravityMachine());

    }

    public Simulation(GravitationalCalculator gravitationalCalculator) {
        _gravitationalCalculator = gravitationalCalculator;
    }


    /**
     * @param landscape
     * @todo work out wethere we can have multiple landscapes or not..
     */
    public void setLandscape(Landscape landscape) {
        _landscape = landscape;
        landscape.setSimulation(this);
    }

    public void executeTimeStep(ISimulationDisruptor disruptor) {

        fireBeforeTimestepExecutedEvent(); // do it here incase someone wants to add some agents next timestep...

        List deadAgentsToRemove = new ArrayList(_deadAgents);
        List escapedAgentsToRemove = new ArrayList(_escapedAgents);
        List agentsToAdd = new ArrayList(_pendingAgents);


        _pendingAgents = new ArrayList();
        _deadAgents = new ArrayList();
        _escapedAgents = new ArrayList();
        for (Iterator itr = _liveAgents.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();


            agent.executeTimeStep(this);

            if (disruptor.haltSimulation(this)) {
                deadAgentsToRemove.addAll(_deadAgents); // Add any that died mid-timestep - dont care about ones to be added as we dont report on them
                escapedAgentsToRemove.addAll(_escapedAgents); // same for escaped ones
                _deadAgents = new ArrayList();
                _escapedAgents = new ArrayList();
                break;
            }
        }

        executeEndOfTimestepActions();
        removeAgentsFromLiveList(deadAgentsToRemove, escapedAgentsToRemove);

        addNewAgents(agentsToAdd); //mean that new agents get added at the end so you see them appear for 1 timestep before they move.

        if ((_currentTimestep % 1) == 0) {
            _landscape.fireAgentsChangedEvent();
        }
        fireTimestepCompletedEvent();
        _currentTimestep++;


    }

    private void fireBeforeTimestepExecutedEvent() {
        for (Iterator itr = _timestepListeners.iterator(); itr.hasNext();) {
            ITimestepListener listener = (ITimestepListener)itr.next();
            listener.beforeTimestepExecuted(this);
        }
    }
    private void fireTimestepCompletedEvent() {
        for (Iterator itr = _timestepListeners.iterator(); itr.hasNext();) {
            ITimestepListener listener = (ITimestepListener)itr.next();
            if (log.isDebugEnabled()) {
                log.debug("Firing Timestep Complete to: " + listener);
            }
            listener.timestepCompleted(this);
        }
    }

    private void executeEndOfTimestepActions() {
        for (Iterator itr = _endTimestepActions.iterator(); itr.hasNext();) {
            ISimulationAction action = (ISimulationAction)itr.next();
            action.executeAction(this);
        }
        _endTimestepActions = new ArrayList();
    }


    /**
     * Call this when the experiment is run - i.e. you dont want to execute anymore timesteps.
     */
    public void tidyUp() {
        removeAgentsFromLiveList(_deadAgents, _escapedAgents);
        addNewAgents(_pendingAgents); //mean that new agents get added at the end so you see them appear for 1 timestep before they move.
        _deadAgents = new ArrayList();
        _escapedAgents = new ArrayList();
        _pendingAgents = new ArrayList();
        _landscape.fireAgentsChangedEvent();
    }

    private void removeAgentsFromLiveList(List deadAgentsToRemove, List escapingAgentsToRemove) {
        for (Iterator itr = deadAgentsToRemove.iterator(); itr.hasNext();) {
            IPhysicalAgent agent = (IPhysicalAgent)itr.next();
            _liveAgents.remove(agent);
            _landscape.unregisterAgent(agent);

            if (escapingAgentsToRemove.contains(agent)) {
                escapingAgentsToRemove.remove(agent); // If you die you cannot escape!
            }

            agent.tidyUp(this);
            if (agent instanceof IPhysicalAgent) {
                fireAgentDeathEvent((IPhysicalAgent)agent);
            }
            if (agent.hasListeners()) {
//                log.warn("!!!!!  DeadAgents: Not all listeners were removed from agent : " + agent);
                agent.removeAllListeners();
            }

        }
        archiveRemovedAgents(deadAgentsToRemove);
        removeEscapedAgentsFromLiveList(escapingAgentsToRemove);
    }

    private void removeEscapedAgentsFromLiveList(List agentsToRemove) {
        for (Iterator itr = agentsToRemove.iterator(); itr.hasNext();) {
            IPhysicalAgent agent = (IPhysicalAgent)itr.next();
            _liveAgents.remove(agent);
            _landscape.unregisterAgent(agent);
            agent.tidyUp(this);
            if (agent instanceof IPhysicalAgent) {
                IPhysicalAgent pa = (IPhysicalAgent)agent;
                if (pa.isAlive()) {
                    fireAgentEscapedEvent((IPhysicalAgent)agent);
                }
            }
            if (agent.hasListeners()) {
//                log.warn("!!!!! EscapedAgents:  Not all listeners were removed from agent : " + agent);
                agent.removeAllListeners();
            }

        }
        archiveRemovedAgents(agentsToRemove);

    }

    private void archiveRemovedAgents(List agentsToRemove) {
        if (_archiveRemovedAgents) {
            List agentsToArchive;
            if (_agentArchiveFilter != null) {
                agentsToArchive = filterAgentList(agentsToRemove, _agentArchiveFilter);
            } else {
                agentsToArchive = agentsToRemove;
            }

            _archivedAgents.addAll(agentsToArchive);
        }
    }


    public void setAgentArchiveFilter(IAgentFilter agentArchiveFilter) {
        _agentArchiveFilter = agentArchiveFilter;
    }

    private void addNewAgents(List agentsToAdd) {
        for (Iterator itr = agentsToAdd.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            if (agent instanceof IPhysicalAgent) {
                addAgent((IPhysicalAgent)agent);
            } else {
                addAgent(agent);
            }
        }
    }


    public void registerAgentDeath(IPhysicalAgent agent) {
        _deadAgents.add(agent);
    }


    public void registerAgentEscaped(IPhysicalAgent agent) {
        _escapedAgents.add(agent);
    }

    private void fireAgentDeathEvent(IPhysicalAgent agent) {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            ISimulationListener listener = (ISimulationListener)itr.next();
            listener.agentDeath(this, agent);
        }
    }

    private void fireAgentEscapedEvent(IPhysicalAgent agent) {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            ISimulationListener listener = (ISimulationListener)itr.next();
            listener.agentEscaped(this, agent);
        }
    }

    private void fireAgentBirthEvent(IPhysicalAgent agent) {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            ISimulationListener listener = (ISimulationListener)itr.next();
            listener.agentAdded(this, agent);
        }
    }


    public void reset() {
        cleanAllAgents();
        _currentTimestep = 0;
    }

    public void addAgent(IAgent agent) {
        _liveAgents.add(agent);

        agent.registerSimulation(this);
        if (agent instanceof IPhysicalAgent) {
            _landscape.registerAgent((IPhysicalAgent)agent);
            fireAgentBirthEvent((IPhysicalAgent)agent);
        }

    }


    public void addAgents(List agents) {
        for (Iterator itr = agents.iterator(); itr.hasNext();) {
            IPhysicalAgent agent = (IPhysicalAgent)itr.next();
            addAgent(agent);
        }
    }

    public GravitationalCalculator getGravityMachine() {
        return _gravitationalCalculator;
    }

    public void removeLiveAgent(IPhysicalAgent agent) {
        _liveAgents.remove(agent);
        _landscape.unregisterAgent(agent);
        agent.removeAllListeners();
    }

    public List getLiveAgents() {
        return _liveAgents;
    }

    public List getLiveAgents(IAgentFilter filter) {
        List filteredList = new ArrayList();
        for (Iterator itr = _liveAgents.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            if (filter.acceptAgent(agent)) {
                filteredList.add(agent);
            }
        }
        return filteredList;
    }

    private static List filterAgentList(List candidates, IAgentFilter filter) {
        List filteredList = new ArrayList();
        for (Iterator itr = candidates.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            if (filter.acceptAgent(agent)) {
                filteredList.add(agent);
            }
        }
        return filteredList;
    }

    public List getDeadAgents(IAgentFilter filter) {
        List filteredList = new ArrayList();
        for (Iterator itr = _deadAgents.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            if (filter.acceptAgent(agent)) {
                filteredList.add(agent);
            }
        }
        return filteredList;
    }

    public List getEscapedAgents(IAgentFilter filter) {
        List filteredList = new ArrayList();
        for (Iterator itr = _escapedAgents.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            if (filter.acceptAgent(agent)) {
                filteredList.add(agent);
            }
        }
        return filteredList;
    }

    public List getAllAgents(IAgentFilter filter) {
        List allAgents = getLiveAgents(filter);
        allAgents.addAll(getHistoricalAgents(filter));
        return allAgents;
    }

    public List getHistoricalAgents(IAgentFilter filter) {
        return filterAgentList(_archivedAgents, filter);
    }

    private void cleanAllAgents() {

        cleanLiveAgents();
        cleanDeadAgents();
        cleanEscapedAgents();
        cleanPendingAgents();
        cleanArchivedAgents();

    }

    private void cleanArchivedAgents() {
        for (Iterator itr = _archivedAgents.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            agent.tidyUp(this);
        }

        _archivedAgents = new ArrayList();
    }

    private void cleanDeadAgents() {
        for (Iterator itr = _deadAgents.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            agent.tidyUp(this);
        }

        _deadAgents = new ArrayList();
    }

    private void cleanEscapedAgents() {
        for (Iterator itr = _escapedAgents.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            agent.tidyUp(this);
        }
        _escapedAgents = new ArrayList();

    }

    private void cleanLiveAgents() {
        for (Iterator itr = _liveAgents.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            agent.tidyUp(this);
        }
        _liveAgents = new ArrayList();
    }


    private void cleanPendingAgents() {
        for (Iterator itr = _pendingAgents.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            agent.tidyUp(this);
        }
        _pendingAgents = new ArrayList();
    }

    public AgentInfoRegistry getAgentRegistry() {
        return _agentInfoRegistry;
    }

    public Landscape getLandscape() {
        return _landscape;
    }


    /**
     * Allows you to name this simulation, e.g. "Random Walk Experiment"
     *
     * @return
     */
    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }


    public long getExecutedTimesteps() {
        return _currentTimestep;
    }


    public void addSimulationListener(ISimulationListener listener) {
        _listeners.add(listener);
    }

    public void removeSimulationListener(ISimulationListener listener) {
        _listeners.remove(listener);
    }

    public void addTimestepListener(ITimestepListener listener) {
        _timestepListeners.add(listener);
    }

    public void removeTimestepListener(ITimestepListener listener) {
        _timestepListeners.remove(listener);
    }

    public void addAgentNextTimestep(IAgent agent) {
        _pendingAgents.add(agent);
    }

    public void resetTimestep() {
        _currentTimestep = 0;
    }

    public void clean() {

        cleanAllAgents();
        resetTimestep();
        if (_landscape != null) {
            _landscape.clean();
        }
    }


    /**
     * Currently use the same random number generator for each iteration / initialisation
     *
     * @todo investigate the effect of re-initialising each time - should be nothing but you never can tell.
     */
    public void initialiseRandom() {
        if (_random == null) {
            _seed = System.currentTimeMillis();
            _random = new Random(_seed);
            if (log.isInfoEnabled()) {
                log.info("Initialised Random Number Generator - Random Seed is: " + _seed);
            }
        }

        if (_randomNumberGenerator == null) {
            _distributionSeed = System.currentTimeMillis();
            _randomNumberGenerator = new RandomNumberGenerator(_distributionSeed);
            if (log.isInfoEnabled()) {
                log.info("Initialised Distribution Random Number Generator - Random Seed is: " + _distributionSeed);
            }
        }
    }

    public boolean isArchiveRemovedAgents() {
        return _archiveRemovedAgents;
    }

    public void setArchiveRemovedAgents(boolean archiveRemovedAgents) {
        _archiveRemovedAgents = archiveRemovedAgents;
    }

    public Random getRandom() {
        return _random;
    }


    public void exeuteAtEndOfTimestep(ISimulationAction simulationAction) {
        _endTimestepActions.add(simulationAction);
    }

    public String getParameterSummary() {
        return "ARCHIVE=" + _archiveRemovedAgents + ", SEED=" + _seed;
    }

    public long getCurrentTimestep() {
        return _currentTimestep;
    }

    /**
     * this is another random number generator that does special distributions.
     * At somepoint we shouuld choose one or the other.
     *
     * @return
     */
    public RandomNumberGenerator getRandomNumberGenerator() {
        return _randomNumberGenerator;
    }

    public String toString() {
        return "title=" + _title + ", randomSeed=" + _seed + ", liveAgents=" + _liveAgents.size() + ", deadAgents=" + _deadAgents.size() + ", escapedAgents=" + _escapedAgents.size() + ", pendingAgents=" + _pendingAgents.size() + ", archivedAgents=" + _archivedAgents.size();
    }

    public void setEscapingAgentCatcher(EscapingAgentCatcher escapingAgentCatcher) {
        _escapingAgentCatcher = escapingAgentCatcher;
    }

    public EscapingAgentCatcher getEscapingAgentCatcher() {
        return _escapingAgentCatcher;
    }

    public boolean hasEscapingAgentCatcher() {
        return _escapingAgentCatcher != null;
    }


    private Landscape _landscape;
    private AgentInfoRegistry _agentInfoRegistry = new AgentInfoRegistry();
    private GravitationalCalculator _gravitationalCalculator;

    private boolean _archiveRemovedAgents;
    private IAgentFilter _agentArchiveFilter;

    private List _liveAgents = new ArrayList();
    private List _deadAgents = new ArrayList();
    private List _escapedAgents = new ArrayList();
    private List _pendingAgents = new ArrayList();
    private List _archivedAgents = new ArrayList();


    private List _listeners = new ArrayList();
    private List _timestepListeners = new ArrayList();

    private long _currentTimestep;
    private List _endTimestepActions = new ArrayList();

    private String _title = "Untitled Simulation";
    public static final String PROPERTY_TITLE = "title";
    public static final String PROPERTY_EXECUTED_TIMESTEPS = "executedTimesteps";

    private Random _random;
    private static final Logger log = Logger.getLogger(Simulation.class);
    private long _seed;

    private long _distributionSeed;
    private RandomNumberGenerator _randomNumberGenerator;


    private EscapingAgentCatcher _escapingAgentCatcher;
}
