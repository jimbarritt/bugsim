package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.bugsim.view.landscape.*;
import com.ixcode.bugsim.view.landscape.action.*;
import com.ixcode.framework.simulation.model.agent.*;
import com.ixcode.framework.simulation.model.agent.physical.*;
import com.ixcode.framework.simulation.model.landscape.*;
import com.ixcode.framework.swing.*;

import java.awt.*;
import java.awt.event.*;

public class AddAgentViewMode implements ViewMode, MouseListener, MouseMotionListener {

    public AddAgentViewMode(LandscapeView view, AgentTypeChoiceCombo combo) {
        _view = view;
        _combo = combo;//@todo make this part of LandscapeView so you can just access it from here rather than passing it
    }

    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    public ViewModeName getName() {
        return LandscapeViewMode.ADD_AGENT;
    }

    public void begin(Component parent) {
        parent.setCursor(getCursor());
        parent.addMouseListener(getMouseListener());
        parent.addMouseMotionListener(getMouseMotionListener());
    }

    public void end(Component parent) {
        parent.removeMouseListener(getMouseListener());
        parent.removeMouseMotionListener(getMouseMotionListener());
    }
    public MouseListener getMouseListener() {
        return this;
    }

    public MouseMotionListener getMouseMotionListener() {
        return this;
    }


    public void mouseClicked(MouseEvent mouseEvent) {
        Location location = _view.getLocationOnLandscapeSnappedFrom(mouseEvent.getPoint());
        IAgentInfo agentInfo = _combo.getSelectedInfo();
        IAgentFactory factory = agentInfo.getFactory();
        IPhysicalAgent agent = factory.createAgent(location);
        _view.getSimulation().addAgent(agent);
    }

    public void mousePressed(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseExited(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        _view.setCursor(this.getCursor());


    }

    LandscapeView _view;
    private AgentTypeChoiceCombo _combo;
}
