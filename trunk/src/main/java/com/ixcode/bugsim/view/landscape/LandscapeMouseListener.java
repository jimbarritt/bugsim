/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeMouseListener implements MouseListener, MouseMotionListener {

    public LandscapeMouseListener(LandscapeView viewer) {
        _viewer = viewer;
        _viewer.addMouseListener(this);
        _viewer.addMouseMotionListener(this);

    }

    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();


    }

    public void mousePressed(MouseEvent mouseEvent) {
        Location location = _viewer.getLandscapeLocation(mouseEvent.getPoint());
//        _agent = new ButterflyAgent(location, 0, 1, 5, 23, new Random());
        _viewer.getLandscape().registerAgent(_agent);
    }

    public void mouseReleased(MouseEvent mouseEvent) {

    }

    public void mouseEntered(MouseEvent mouseEvent) {

    }

    public void mouseExited(MouseEvent mouseEvent) {

    }

    public void mouseDragged(MouseEvent mouseEvent) {
        if (_agent != null) {
            Location location = _viewer.getLandscapeLocation(mouseEvent.getPoint());
            _agent.moveTo(null, location);
        }
    }

    public void mouseMoved(MouseEvent mouseEvent) {

    }

    private LandscapeView _viewer;
    private IMotileAgent _agent;
}
