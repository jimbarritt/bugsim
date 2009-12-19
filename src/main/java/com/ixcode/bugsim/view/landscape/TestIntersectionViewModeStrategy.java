/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgentFilter;
import com.ixcode.bugsim.model.agent.butterfly.EggLayingForagingStrategy;
import com.ixcode.bugsim.model.agent.butterfly.ResourceIntersection;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.diagnostic.LineAgent;
import com.ixcode.framework.simulation.model.agent.resource.IResourceAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.swing.ViewModeStrategy;
import com.ixcode.framework.swing.*;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class TestIntersectionViewModeStrategy implements ViewModeStrategy, MouseListener, MouseMotionListener {

    public TestIntersectionViewModeStrategy(LandscapeView view) {
        _view = view;

    }

    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    public void enterMode(Component parent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void exitMode(Component parent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public ViewMode getViewMode() {
        return LandscapeViewMode.TEST_INTERSECTION;
    }

    public MouseListener getMouseListener() {
        return this;
    }

    public MouseMotionListener getMouseMotionListener() {
        return this;
    }


    public void mouseClicked(MouseEvent mouseEvent) {
        if (_lineAgent == null) {
            _startLocation = _view.getLandscapeLocation(mouseEvent.getPoint());
            _lineAgent = new LineAgent(_startLocation);
            _view.getSimulation().addAgent(_lineAgent);
            _lineAgent.addPropertyChangeListener(_view);
//            System.out.println("Adding Line Agent at " + _startLocation);

        } else {
            Location endLocation = _view.getLandscapeLocation(mouseEvent.getPoint());
            _lineAgent.setEndLocation(endLocation);
            List resources = _view.getSimulation().getLiveAgents(CabbageAgentFilter.INSTANCE);
            java.util.List intersectedCabbages = new ArrayList();
            for (Iterator itr = resources.iterator(); itr.hasNext();){
                CabbageAgent cabbage = (CabbageAgent)itr.next();

                if (log.isDebugEnabled()) {
                    log.debug("Testing intersection of line " + _startLocation.getCoordinate() + " : "+ endLocation.getCoordinate() + " with cabbage : " + cabbage.getLocation().getCoordinate());
                }


                if (cabbage.intersectsLine(_startLocation.getCoordinate(), endLocation.getCoordinate())) {

                    RectangularCoordinate cc = cabbage.getLocation().getCoordinate();
                    RectangularCoordinate a = _startLocation.getCoordinate();
                    RectangularCoordinate b = endLocation.getCoordinate();
                    java.util.List intersections = Geometry.findLineCircleIntersections(cc.getDoubleX(), cc.getDoubleY(), cabbage.getRadiusDouble(), a.getDoubleX(), a.getDoubleY(), b.getDoubleX(), b.getDoubleY() );
                    _lineAgent.setPoints(intersections);
                    intersectedCabbages.add(cabbage);

                } else {
                    cabbage.resetEggCount();
                }


            }

//            layOnAllIntersectedCabbages(intersectedCabbages);
            layOnClosestCabbage(resources, _startLocation, endLocation);
            _lineAgent = null;
            _view.getLandscape().fireAgentsChangedEvent();
        }
    }

    private void layOnClosestCabbage(List resources, Location start, Location end) {
        ResourceIntersection closest = EggLayingForagingStrategy.findClosestIntersectedResource(start, end,resources, (IResourceAgent)resources.get(0));
        if (closest != null)  {
            closest.getResource().addEgg();
        }
    }

    private void layOnAllIntersectedCabbages(java.util.List intersectedCabbages) {
        for (Iterator itr = intersectedCabbages.iterator(); itr.hasNext();) {
            CabbageAgent cabbageAgent = (CabbageAgent)itr.next();
            cabbageAgent.addEgg();
        }
    }

    public void mousePressed(MouseEvent mouseEvent) {

    }

    public void mouseReleased(MouseEvent mouseEvent) {


    }

    public void mouseEntered(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseExited(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseDragged(MouseEvent mouseEvent) {

    }

    public void mouseMoved(MouseEvent mouseEvent) {
        _view.setCursor(this.getCursor());
        if (_lineAgent != null) {
            Location moveLocation = _view.getLandscapeLocation(mouseEvent.getPoint());
            _lineAgent.setEndLocation(moveLocation);
            _view.getLandscape().fireAgentsChangedEvent(); //@todo do something about event notification - we dont want it to happen when we do all the agents in execute timestep - maybe have it turn on and offable?
        }
    }

    LandscapeView _view;

    private Location _startLocation;
    private LineAgent _lineAgent;
    private static final Logger log = Logger.getLogger(TestIntersectionViewModeStrategy.class);
}
