/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.action;

import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.agent.IAgentInfo;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.ISimulationListener;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.ITimestepListener;
import com.ixcode.bugsim.view.landscape.*;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentFilter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CenterOnNextAgentAction extends AbstractAction implements ISimulationListener, ITimestepListener {

    public static final String ID = "Center On Agent";
    public CenterOnNextAgentAction(LandscapeView view) {
        super(ID);
        _view = view;
        _landscape = view.getLandscape();
        _typeCombo = view.getAgentTypeChoiceCombo();

         _landscape.getSimulation().addSimulationListener(this);
        _landscape.getSimulation().addTimestepListener(this);


    }

    public void agentAdded(Simulation simulation, IAgent agent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void agentDeath(Simulation simulation, IAgent agent) {
        _agent =  null;
    }

    public void agentEscaped(Simulation simulation, IAgent agent) {
        _agent = null;
    }

    public void iterationCompleted(Simulation simulation) {

    }

    public void timestepCompleted(Simulation simulation) {
        if (_agent == null && _typeCombo.getSelectedInfo().getAgentFilter() instanceof ForagingAgentFilter) {
            _agent = findNextAgent();
        }
        centreOnAgent();
    }

    public void beforeTimestepExecuted(Simulation simulation) {

    }

    public void actionPerformed(ActionEvent actionEvent) {
        _agent = findNextAgent();
        centreOnAgent();
    }

    private void centreOnAgent() {

        if (_agent != null) {
            _view.setZoomCenter(new Point2D.Double(_agent.getLocation().getDoubleX(), _agent.getLocation().getDoubleY()));
        }
    }

    private IPhysicalAgent findNextAgent() {
        IAgentInfo info = _typeCombo.getSelectedInfo();
        IAgentFilter filter = info.getAgentFilter();
        List agents = _landscape.getSimulation().getLiveAgents(filter);

        if (_agentIndex>=agents.size()) {
            _agentIndex = 0;
        }
        IPhysicalAgent agent = null;
        if (agents.size() > 0) {
            agent = (IPhysicalAgent)agents.get(_agentIndex);
            ++_agentIndex;
        }
        return agent;
    }


    public void setAgent(IPhysicalAgent agent) {
        _agent = agent;
    }

    private LandscapeView _view;
    private IPhysicalAgent _agent;
    private Landscape _landscape;
    private int _agentIndex;
    private AgentTypeChoiceCombo _typeCombo;
}
