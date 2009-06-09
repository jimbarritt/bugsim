/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.swing.DisplayModeStrategy;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeDisplayModeStrategy extends DisplayModeStrategy {

    public LandscapeDisplayModeStrategy(LandscapeView view) {
        _view = view;
    }

    public void mousePressed(MouseEvent mouseEvent) {
        super.mouseClicked(mouseEvent);

        _startDragPoint = mouseEvent.getPoint();

        _startDragZoomCentre = _view.getZoomCenter();
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        super.mouseDragged(mouseEvent);
        if (!_view.isFitToScreen()) {
            Point currentDragPoint = mouseEvent.getPoint();
            double xIncr = _view.getLandscapeDistanceX(_startDragPoint.getX() -currentDragPoint.getX());
            double yIncr = _view.getLandscapeDistanceY(_startDragPoint.getY() - currentDragPoint.getY());
            Point2D.Double newZoomCentre = new Point2D.Double(_startDragZoomCentre.getX() + xIncr, _startDragZoomCentre.getY() - yIncr);
            _view.setZoomCenter(newZoomCentre);


        }
    }


    public String toString() {
        return "DisplayMode";
    }
    private LandscapeView _view;

    private Point2D _startDragZoomCentre;
    private static final     NumberFormat F3 = new DecimalFormat("#,##0.000");
    private Point _startDragPoint;
}
