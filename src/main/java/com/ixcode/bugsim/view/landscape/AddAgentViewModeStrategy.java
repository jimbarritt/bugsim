/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.simulation.model.agent.IAgentFactory;
import com.ixcode.framework.simulation.model.agent.IAgentInfo;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.swing.ViewModeStrategy;
import com.ixcode.framework.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class AddAgentViewModeStrategy implements ViewModeStrategy, MouseListener, MouseMotionListener {

    public AddAgentViewModeStrategy(LandscapeView view, AgentTypeChoiceCombo combo) {
        _view = view;
        _combo = combo;//@todo make this part of LandscapeView so you can just access it from here rather than passing it
    }

    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    public void enterMode(Component parent) {
       
    }

    public void exitMode(Component parent) {

    }

    public ViewMode getViewMode() {
        return LandscapeViewMode.ADD_AGENT;
    }

    public MouseListener getMouseListener() {
        return this;
    }

    public MouseMotionListener getMouseMotionListener() {
        return this;
    }


    public void mouseClicked(MouseEvent mouseEvent) {
        Location location = _view.getSnappedLandscapeLocation(mouseEvent.getPoint());
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
