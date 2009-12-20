package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.framework.swing.DisplayMode;
import com.ixcode.framework.math.geometry.*;
import com.ixcode.bugsim.view.landscape.*;

import java.awt.*;
import java.awt.event.*;

public class LandscapeDisplayMode extends DisplayMode {
    private RectangularCoordinate startDragZoomCentre;
    private Point startDragPoint;
    protected LandscapeView view;

    public LandscapeDisplayMode() {
    }

    @Override
    public void begin(Component parent) {
        view = convertParentToLandscapeView(parent);
        super.begin(parent);
    }

    @Override
    public void end(Component parent) {
        super.end(parent);
        view = null;
    }

    private static LandscapeView convertParentToLandscapeView(Component parent) {
        if (!(parent instanceof LandscapeView)) {
            throw new RuntimeException("Cannot use the LandscapeDisplaMode with a component of type: " + parent.getClass());
        }
        return (LandscapeView) parent;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        super.mouseClicked(mouseEvent);
        startDragPoint = mouseEvent.getPoint();
        startDragZoomCentre = view.getCenterOfViewOnLandscape();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        super.mouseDragged(mouseEvent);

//            Point currentDragPoint = mouseEvent.getPoint();
//            double xIncr = view.getLandscapeDistanceX(startDragPoint.getX() - currentDragPoint.getX());
//            double yIncr = view.getLandscapeDistanceY(startDragPoint.getY() - currentDragPoint.getY());
//            RectangularCoordinate newZoomCentre = new RectangularCoordinate(startDragZoomCentre.getDoubleX() + xIncr, startDragZoomCentre.getDoubleY() - yIncr);
//            view.centerViewOnLandscapeCoordinate(newZoomCentre);
//
    }


    public String toString() {
        return "DisplayMode";
    }



}
