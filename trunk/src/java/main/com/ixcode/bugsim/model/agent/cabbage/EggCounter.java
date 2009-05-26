/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.cabbage;

import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.framework.model.ModelBase;
import com.ixcode.framework.simulation.model.ISimulationListener;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.javabean.IntrospectionUtils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : Keeps track of the total number of eggs so we dont have to keep calculating it.
 */
public class EggCounter extends ModelBase implements PropertyChangeListener, ISimulationListener {

    /**
     * @param agentClass
     * @todo at some point we are going to have to implement an egg object which will belong to different classes of agent, forager etc....
     */
    public EggCounter(Class agentClass, long eggCountInterval) {
        _agentClass = agentClass;
        _counterId = IntrospectionUtils.getShortClassName(_agentClass);
        _eggCounts = new ArrayList();
        _eggCountInterval = eggCountInterval;
        resetCounter(0);
    }

    public void agentAdded(Simulation simulation, IAgent agent) {

    }

    public void agentDeath(Simulation simulation, IAgent agent) {

    }

    public void agentEscaped(Simulation simulation, IAgent agent) {

    }

    public void timestepCompleted(Simulation simulation) {
        //@todo put in here an egg count every time a certain interval of timesteps is completed
    }

    public void generationCompleted(int generationTime, Simulation simulation) {
        if (generationTime % _eggCountInterval == 0) {
            _eggCounts.add(createEggCount(generationTime, simulation));
        }
    }


    public EggCount createEggCount(long time, Simulation simulation) {
        EggCount count = new EggCount(time, _agentClass);
        List cabbages = simulation.getLiveAgents(CabbageAgentFilter.INSTANCE);
        count.countEggs(cabbages);
        return count;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        long eggDiff = ((Long)evt.getNewValue()).longValue() - ((Long)evt.getOldValue()).longValue();
        setTotalEggs(_totalEggs + eggDiff);

    }


    public void register(List cabbages) {
        for (Iterator itr = cabbages.iterator(); itr.hasNext();) {
            CabbageAgent cabbage = (CabbageAgent)itr.next();
            cabbage.addPropertyChangeListener(CabbageAgent.PROPERTY_EGG_COUNT, this);
            _cabbages.add(cabbage);
        }
    }

    public void resetCounter(long eggLimit) {
        setEggLimit(eggLimit);
        setTotalEggs(0);

    }
    public void setEggLimit(long eggLimit) {
        _eggLimit = eggLimit;
        setTotalEggsFormatted(createEggCountFormatted());
    }

    public void unregister() {
        for (Iterator itr = _cabbages.iterator(); itr.hasNext();) {
            CabbageAgent cabbageAgent = (CabbageAgent)itr.next();
            cabbageAgent.removePropertyChangeListener(CabbageAgent.PROPERTY_EGG_COUNT, this);
        }

    }

    public long getTotalEggs() {
        return _totalEggs;
    }

    private void setTotalEggs(long totalEggs) {
        long oldVal = _totalEggs;
        _totalEggs = totalEggs;
        super.firePropertyChangeEvent(PROPERTY_EGG_COUNT, oldVal, _totalEggs);
        setTotalEggsFormatted(createEggCountFormatted());
    }

    private String createEggCountFormatted() {
        if (_eggLimit > 0) {
            return _totalEggs + " of " + _eggLimit;
        } else {
            return "" + _totalEggs;
        }
    }


    public String getTotalEggsFormatted() {
        return _eggCountFormatted;
    }

    private void setTotalEggsFormatted(String eggCountFormatted) {
        String oldVal = _eggCountFormatted;
        _eggCountFormatted = eggCountFormatted;
        super.firePropertyChangeEvent(PROPERTY_EGG_COUNT_FORMATTED, oldVal, _eggCountFormatted);
    }

    public List getEggCounts() {
        return _eggCounts;
    }

    public String getCounterId() {
        return _counterId;
    }

    public EggDistribution createEggDistribution() {
        EggDistribution dist = new EggDistribution();
        for (Iterator itr = _cabbages.iterator(); itr.hasNext();) {
            CabbageAgent cabbageAgent = (CabbageAgent)itr.next();
            dist.addEggs(cabbageAgent);
        }
        return dist;
    }

    /**
     * Might need the simulation if we have to remove egg agents.
     * @param simulation
     */
    public void removeAllEggs(Simulation simulation) {
        for (Iterator itr = _cabbages.iterator(); itr.hasNext();) {
            CabbageAgent cabbageAgent = (CabbageAgent)itr.next();
            cabbageAgent.resetEggCount();
        }
    }



    private List _cabbages = new ArrayList();
    private List _eggCounts = new ArrayList();
    private long _totalEggs = -1; // so that the event gets fired when we start up!
    private String _eggCountFormatted;
    public static final String PROPERTY_EGG_COUNT = "totalEggs";
    public static final String PROPERTY_EGG_COUNT_FORMATTED = "totalEggsFormatted";
    private long _eggLimit;
    private Class _agentClass;
    private String _counterId;
    private long _eggCountInterval;
}
