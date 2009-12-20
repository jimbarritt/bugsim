/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.agent.butterfly.population;

import com.ixcode.framework.simulation.model.ISimulationListener;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.AgentClassFilter;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.model.ModelBase;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 1, 2007 @ 2:15:37 PM by jim
 */
public class ForagerPopulation extends ModelBase implements IPopulation, ISimulationListener {

    /**
     * Called when we want to halt the simulation i.e. when the egg limit is reached
     * @param simulation
     */
    public void killAllAgents(Simulation simulation) {
        List agents = new ArrayList(simulation.getLiveAgents(_agentFilter));
        for (Iterator itr = agents.iterator(); itr.hasNext();) {
            IMotileAgent agent = (IMotileAgent)itr.next();
            agent.kill(simulation);
        }
    }

    public static final String P_POPULATION_LABEL = "populationLabel";
    public static final String P_FORAGERS_ALIVE_FORMATTED = "foragersAliveFormatted";
    public static final String P_MAX_NUMBER_FORAGERS = "maxNumberForagers";
    public static final String P_FORAGER_POPULATION_SIZE = "foragerPopulationSize";
    public static final String P_FORAGERS_REMAINING = "foragersRemaining";
    public static final String P_FORAGERS_ALIVE = "foragersAlive";
    public static final String P_FORAGERS_REMAINING_FORMATTED = "foragersRemainingFormatted";
    public static final String P_FORAGERS_DEAD = "foragersDead";
    public static final String P_FORAGERS_ESCAPED = "foragersEscaped";
    public static final String P_EGG_COUNT = "eggCount";
    public static final String P_LARVAE_DEAD = "larvaeDead";
    public static final String P_AGENT_CLASS = "agentClass";

    public ForagerPopulation(String populationLabel, long populationSize, AgentClassFilter agentFilter) {
        this(populationLabel, populationSize, agentFilter, 0, 0);
    }
    public ForagerPopulation(String populationLabel, long populationSize, AgentClassFilter agentFilter, long eggCount, long larvalDeadCount) {
        _populationSize = populationSize;
        _agentFilter = agentFilter;
        _populationLabel = populationLabel;
        _eggCount = eggCount;
        _laraveDead = larvalDeadCount;
        setForagersRemaining(_populationSize);
        setForagersAlive(0);
    }

    public String getForagersRemainingFormatted() {
        return _foragersRemainingFormatted;
    }

    public String getForagersAliveFormatted() {
        return _foragersAliveFormatted;
    }

    public long getForagersAlive() {
        return _foragersAlive;
    }

    public long getForagersRemaining() {
        return _foragersRemaining;
    }

    public void setForagersRemaining(long remaining) {
        _foragersRemaining = remaining;
        setForagersRemainingFormatted(createForagersRemainingString());
    }
    public long getForagersDead() {
        return _foragersDead;
    }

    public long getForagersEscaped() {
        return _foragersEscaped;
    }




    public void agentAdded(Simulation simulation, IAgent agent) {
        if (_agentFilter.acceptAgent(agent)) {
             setForagersAlive(_foragersAlive+1);
            setForagersRemaining(_foragersRemaining-1);
        }
    }

    public void agentDeath(Simulation simulation, IAgent agent) {
        if (_agentFilter.acceptAgent(agent)) {
            setForagersAlive(_foragersAlive-1);
            setForagersDead(_foragersDead+1);
        }
    }


    public void agentEscaped(Simulation simulation, IAgent agent) {
        if (_agentFilter.acceptAgent(agent))  {
            setForagersAlive(_foragersAlive-1);
            setForagersEscaped(_foragersEscaped+1);
        }
    }


    private void setForagersAlive(long alive) {
        _foragersAlive = alive;
        setForagersAliveFormatted("" + _foragersAlive);
    }



    private void setForagersDead(long dead) {
        _foragersDead = dead;
        setForagersRemainingFormatted(createForagersRemainingString());
    }
    private void setForagersEscaped(long escaped) {
        _foragersEscaped = escaped;
        setForagersRemainingFormatted(createForagersRemainingString());
    }

    private void setForagersRemainingFormatted(String foragersRemainingString) {
        Object oldVal = _foragersRemainingFormatted;
        _foragersRemainingFormatted = foragersRemainingString;
        super.firePropertyChangeEvent(P_FORAGERS_REMAINING_FORMATTED, oldVal, _foragersRemainingFormatted);
    }
    private void setForagersAliveFormatted(String foragersAliveString) {
        Object oldVal = _foragersAliveFormatted;
        _foragersAliveFormatted = foragersAliveString;
        super.firePropertyChangeEvent(P_FORAGERS_ALIVE_FORMATTED, oldVal, _foragersAliveFormatted);
    }
    private String createForagersRemainingString() {
        StringBuffer sb = new StringBuffer();
        sb.append(_foragersRemaining).append(" of ").append(_populationSize);
        sb.append(" (dead: ").append(_foragersDead);
        sb.append(", escaped: " + _foragersEscaped);
        sb.append(" )");

        if (_laraveDead > 0) {
            sb.append(" ").append(_laraveDead).append(" larvalDead");
        }
        return sb.toString();
    }

    public String getPopulationLabel() {
        return _populationLabel;
    }

    public void larvalDeath() {
        setLarvaeDead(_laraveDead+1);
    }
    public void setLarvaeDead(long dead) {
        _laraveDead = dead;
        setForagersRemainingFormatted(createForagersRemainingString());
    }

    public long getLaraveDead() {
        return _laraveDead;
    }

    public long getPopulationSize() {
        return _populationSize;
    }


    public long getEggCount() {
        return _eggCount;
    }


    public Class getAgentClass() {
        return _agentFilter.getAgentClass();
    }

    public String toString() {
        return "[Forager Population]: label=" + _populationLabel + " : alive=" + _foragersAliveFormatted + " : remaining=" + _foragersRemainingFormatted;
    }
    private long _populationSize;
    private long _foragersAlive;
    private long _foragersDead;
    private long _foragersRemaining;
    private long _foragersEscaped;
    private long _laraveDead;
    private long _eggCount;
    private String _foragersAliveFormatted;
    private String _foragersRemainingFormatted;

    private AgentClassFilter _agentFilter;

    private String _populationLabel;
    public static final String P_POPULATION_SIZE = "populationSize";
}
