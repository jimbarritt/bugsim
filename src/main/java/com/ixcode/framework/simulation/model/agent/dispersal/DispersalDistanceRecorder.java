/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.dispersal;

import com.ixcode.framework.math.stats.DescriptiveStatistics;
import com.ixcode.framework.simulation.model.ISimulationListener;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.AgentBase;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.motile.MotileAgentBase;
import org.apache.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class DispersalDistanceRecorder extends AgentBase implements PropertyChangeListener, ISimulationListener {
    public long[] getAges() {
        return AGES_TO_RECORD;
    }

    /**
     * These were generated in R so that we get an even spread accross the log scale...
     */
    private static final long[] AGES_TO_RECORD = new long[]{1 ,  2 ,  3 ,  4 ,  5 ,  6 ,  7 ,  8 ,  9 ,  10 ,  13 ,  17 ,  21 ,  27 ,  35 ,  45 ,  58 ,  74 ,  95 ,  122 ,  157 ,  202 ,  260 ,  334 ,  429 ,  551 ,  707 ,  909 ,  1168 ,  1500};

    public static final String AGENT_CLASS_ID = "DDRecorder";

    public DispersalDistanceRecorder(Simulation simulation, IAgentFilter filter) {
        super(AGENT_CLASS_ID);
        initAgesToRecord(AGES_TO_RECORD);
        _agentFilter = filter;

        listenToAgents(simulation.getLiveAgents(_agentFilter));
        simulation.addSimulationListener(this);

    }

    public void timestepCompleted(Simulation simulation) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void agentAdded(Simulation simulation, IAgent agent) {
        if (_agentFilter.acceptAgent(agent)) {
            agent.addPropertyChangeListener(MotileAgentBase.PROPERTY_LOCATION,this);
        }
    }

    public void agentDeath(Simulation simulation, IAgent agent) {
        if (_agentFilter.acceptAgent(agent)) {
            agent.removePropertyChangeListener(MotileAgentBase.PROPERTY_LOCATION, this);
        }
    }

    public void agentEscaped(Simulation simulation, IAgent agent) {
        if (_agentFilter.acceptAgent(agent)) {
            agent.removePropertyChangeListener(MotileAgentBase.PROPERTY_LOCATION, this);
        }
    }

    public void iterationCompleted(Simulation simulation) {

    }

    private void listenToAgents(List agents) {
        for (Iterator itr = agents.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            agent.addPropertyChangeListener(this);
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        MotileAgentBase agent = (MotileAgentBase)evt.getSource();
        Long age =new Long(agent.getAge());
        if (_displacements.containsKey(age)) {
            recordAgentDisplacement(agent, age);
        }
    }

    private void recordAgentDisplacement(MotileAgentBase agent, Long age) {
        List displacements = (List)_displacements.get(age);
        displacements.add(new Double(Math.pow(agent.calculateDisplacementDistance(), 2)));
    }

    private void initAgesToRecord(long[] timestepsToRecord) {
        for (int i = 0; i < timestepsToRecord.length; ++i) {
            _displacements.put(new Long(timestepsToRecord[i]), new ArrayList());
        }
    }

    public void executeTimeStep(Simulation simulation) {

    }

    public void executeAction(Simulation simulation) {
        Long timestep = new Long(simulation.getExecutedTimesteps()+1);
        recordAgentDisplacements(simulation, timestep);

    }

    private void recordAgentDisplacements(Simulation simulation, Long timestep) {
        List agents = simulation.getAllAgents(_agentFilter);
        if (log.isInfoEnabled()) {
            log.info("Recording agent displacements at timestep: " + timestep + " for " + agents.size() + " agents.");
        }
        double displacements[] = new double[agents.size()];

        int iAgent = 0;
        for (Iterator itr = agents.iterator(); itr.hasNext();) {
            IAgent agent = (IAgent)itr.next();
            if (agent instanceof MotileAgentBase) {
                MotileAgentBase motileAgent = (MotileAgentBase)agent;
                displacements[iAgent] = Math.pow(motileAgent.calculateDisplacementDistance(), 2);
            }
            ++iAgent;
        }

        DescriptiveStatistics stats = new DescriptiveStatistics(displacements);

        _statistics.put(timestep, stats);
    }


    public void calculateStatistics() {
        for (int iAge =0;iAge<AGES_TO_RECORD.length;++iAge) {
            Long age = new Long(AGES_TO_RECORD[iAge]);
            List displacements = (List)_displacements.get(age);
            double disps[] = toDoubleArray(displacements);
            DescriptiveStatistics stats = new DescriptiveStatistics(disps);
            _statistics.put(age, stats);
        }
    }

    private double[] toDoubleArray(List displacements) {
        double[] disps = new double[displacements.size()];
        int iBf=0;
        for (Iterator iDisp = displacements.iterator(); iDisp.hasNext();) {
            Double d = (Double)iDisp.next();
            disps[iBf] = d.doubleValue();
            iBf++;
        }
        return disps;
    }

    public Map getStatistics() {
        return _statistics;
    }

    public String toString() {
        return "DispersalDistanceRecorder: timestepsToRecord - " + AGES_TO_RECORD;
    }

    private Map  _displacements = new HashMap();

    private IAgentFilter _agentFilter;
    private static final Logger log = Logger.getLogger(DispersalDistanceRecorder.class);
    private Map _statistics = new HashMap();
}
