package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.bugsim.agent.butterfly.*;
import com.ixcode.bugsim.agent.cabbage.*;
import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.simulation.model.agent.diagnostic.*;
import com.ixcode.framework.simulation.model.agent.resource.*;
import com.ixcode.framework.simulation.model.landscape.*;
import com.ixcode.framework.swing.*;
import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class TestIntersectionViewMode extends LandscapeDisplayMode {

    private static final Logger log = Logger.getLogger(TestIntersectionViewMode.class);

    private Location startLocation;
    private LineAgent lineAgent;

    public TestIntersectionViewMode() {
    }


    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }


    @Override
    public ViewModeName getViewModeName() {
        return LandscapeViewMode.TEST_INTERSECTION;
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (lineAgent == null) {
            startLocation = view.getLocationOnLandscapeFrom(mouseEvent.getPoint());
            lineAgent = new LineAgent(startLocation);
//            view.getSimulation().addAgent(lineAgent);
//            _lineAgent.addPropertyChangeListener(_view);
//            System.out.println("Adding Line Agent at " + _startLocation);

        } else {
            Location endLocation = view.getLocationOnLandscapeFrom(mouseEvent.getPoint());
            lineAgent.setEndLocation(endLocation);
//            List resources = view.getSimulation().getLiveAgents(CabbageAgentFilter.INSTANCE);
            java.util.List intersectedCabbages = new ArrayList();
//            for (Iterator itr = resources.iterator(); itr.hasNext();){
//                CabbageAgent cabbage = (CabbageAgent)itr.next();
//
//                if (log.isDebugEnabled()) {
//                    log.debug("Testing intersection of line " + startLocation.getCoordinate() + " : "+ endLocation.getCoordinate() + " with cabbage : " + cabbage.getLocation().getCoordinate());
//                }
//
//
//                if (cabbage.intersectsLine(startLocation.getCoordinate(), endLocation.getCoordinate())) {
//
//                    RectangularCoordinate cc = cabbage.getLocation().getCoordinate();
//                    RectangularCoordinate a = startLocation.getCoordinate();
//                    RectangularCoordinate b = endLocation.getCoordinate();
//                    java.util.List intersections = Geometry.findLineCircleIntersections(cc.getDoubleX(), cc.getDoubleY(), cabbage.getRadiusDouble(), a.getDoubleX(), a.getDoubleY(), b.getDoubleX(), b.getDoubleY() );
//                    lineAgent.setPoints(intersections);
//                    intersectedCabbages.add(cabbage);
//
//                } else {
//                    cabbage.resetEggCount();
//                }
//
//
//            }

//            layOnAllIntersectedCabbages(intersectedCabbages);
//            layOnClosestCabbage(resources, startLocation, endLocation);
            lineAgent = null;
            view.getLandscape().fireAgentsChangedEvent();
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

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        view.setCursor(this.getCursor());
        if (lineAgent != null) {
            Location moveLocation = view.getLocationOnLandscapeFrom(mouseEvent.getPoint());
            lineAgent.setEndLocation(moveLocation);
            view.getLandscape().fireAgentsChangedEvent(); //@todo do something about event notification - we dont want it to happen when we do all the agents in execute timestep - maybe have it turn on and offable?
        }
    }

}
