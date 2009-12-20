/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.framework.swing.DisplayMode;
import com.ixcode.framework.math.geometry.*;
import com.ixcode.bugsim.view.landscape.*;

import java.awt.*;
import java.awt.event.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeDisplayMode extends DisplayMode {
    private RectangularCoordinate startDragZoomCentre;
    private Point startDragPoint;
    private LandscapeView view;

    public LandscapeDisplayMode(LandscapeView view) {
        this.view = view;
    }

    public void mousePressed(MouseEvent mouseEvent) {
        super.mouseClicked(mouseEvent);
        startDragPoint = mouseEvent.getPoint();
        startDragZoomCentre = view.getCenterOfViewOnLandscape();
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        super.mouseDragged(mouseEvent);
        if (!view.isFitLandscapeToView()) {
            Point currentDragPoint = mouseEvent.getPoint();
            double xIncr = view.getLandscapeDistanceX(startDragPoint.getX() - currentDragPoint.getX());
            double yIncr = view.getLandscapeDistanceY(startDragPoint.getY() - currentDragPoint.getY());
            RectangularCoordinate newZoomCentre = new RectangularCoordinate(startDragZoomCentre.getDoubleX() + xIncr, startDragZoomCentre.getDoubleY() - yIncr);
            view.centerViewOnLandscapeCoordinate(newZoomCentre);
        }
    }


    public String toString() {
        return "DisplayMode";
    }



}
