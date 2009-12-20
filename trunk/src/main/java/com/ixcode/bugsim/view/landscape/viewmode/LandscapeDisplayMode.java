/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.framework.swing.DisplayMode;
import com.ixcode.bugsim.view.landscape.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeDisplayMode extends DisplayMode {
    private Point2D startDragZoomCentre;
    private Point startDragPoint;
    private LandscapeView view;

    public LandscapeDisplayMode(LandscapeView view) {
        this.view = view;
    }

    public void mousePressed(MouseEvent mouseEvent) {
        super.mouseClicked(mouseEvent);
        startDragPoint = mouseEvent.getPoint();
        startDragZoomCentre = view.getZoomCenter();
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        super.mouseDragged(mouseEvent);
        if (!view.isZoomIsFitToScreen()) {
            Point currentDragPoint = mouseEvent.getPoint();
            double xIncr = view.getLandscapeDistanceX(startDragPoint.getX() - currentDragPoint.getX());
            double yIncr = view.getLandscapeDistanceY(startDragPoint.getY() - currentDragPoint.getY());
            Point2D.Double newZoomCentre = new Point2D.Double(startDragZoomCentre.getX() + xIncr, startDragZoomCentre.getY() - yIncr);
            view.setZoomCenter(newZoomCentre);
        }
    }


    public String toString() {
        return "DisplayMode";
    }



}
