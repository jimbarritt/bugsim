package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.bugsim.view.landscape.action.*;
import com.ixcode.framework.simulation.model.agent.*;
import com.ixcode.framework.simulation.model.agent.physical.*;
import com.ixcode.framework.simulation.model.landscape.*;
import com.ixcode.framework.swing.*;

import java.awt.*;
import java.awt.event.*;

public class AddAgentViewMode extends LandscapeDisplayMode {
    private AgentTypeChoiceCombo combo;

    public AddAgentViewMode(AgentTypeChoiceCombo combo) {
        this.combo = combo;//@todo make this part of LandscapeView so you can just access it from here rather than passing it
    }

    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    @Override
    public ViewModeName getViewModeName() {
        return LandscapeViewMode.ADD_AGENT;
    }



    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Location location = view.getLocationOnLandscapeSnappedFrom(mouseEvent.getPoint());
        IAgentInfo agentInfo = combo.getSelectedInfo();
        IAgentFactory factory = agentInfo.getFactory();
        IPhysicalAgent agent = factory.createAgent(location);
//        view.getSimulation().addAgent(agent);
    }


    public void mouseMoved(MouseEvent mouseEvent) {
        view.setCursor(this.getCursor());
    }

}
