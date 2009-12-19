/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentFilter;
import com.ixcode.bugsim.view.agent.motile.SignalBasedAgentInspector;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class InspectButterflyAgentAction extends AbstractAction {

    public static final String ID = "Inspect Butterfly Agent";

    public InspectButterflyAgentAction(LandscapeView view) {
        super(ID);
        _view = view;
        _landscape = view.getLandscape();



    }

    public void actionPerformed(ActionEvent actionEvent) {
        _agent = findNextAgent();
        inspectAgent();
    }

    private void inspectAgent() {
        JFrame agentFrame = new JFrame("Inspect agent [" + _agent.getId() + "]");
        SignalBasedAgentInspector inspector = new SignalBasedAgentInspector(_view);
        inspector.setAgent(_agent);
        agentFrame.getContentPane().add(inspector);
        agentFrame.pack();
        agentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        agentFrame.show();

    }

    private IMotileAgent findNextAgent() {
        IAgentFilter filter = ForagingAgentFilter.INSTANCE;
        List agents = _landscape.getSimulation().getLiveAgents(filter);

        if (_agentIndex>=agents.size()) {
            _agentIndex = 0;
        }
        IMotileAgent agent = (IMotileAgent)agents.get(_agentIndex);
        ++_agentIndex;
        return agent;
    }


    public void setAgent(IMotileAgent agent) {
        _agent = agent;
    }

    private LandscapeView _view;
    private IMotileAgent _agent;
    private Landscape _landscape;
    private int _agentIndex;


}
