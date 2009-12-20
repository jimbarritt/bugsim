/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.zoomcontrol;

import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.framework.swing.DisplayMode;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ZoomGlassPaneDisplayMode extends DisplayMode {

    public static final double MIN_SIZE = 100;

    public ZoomGlassPaneDisplayMode(ZoomGlassPane glassPane, LandscapeView mainView, LandscapeView zoomView) {
        _glassPane = glassPane;
        _mainView = mainView;
        _zoomView = zoomView;
    }

    public void mousePressed(MouseEvent mouseEvent) {
        Rectangle2D.Double zr = _glassPane.getZoomRectangle();


//        double x = zr.getDoubleX()<(MIN_SIZE /2)  ? (MIN_SIZE/2): zr.getDoubleX();
//        double y = zr.getDoubleY()<(MIN_SIZE /2)  ? (MIN_SIZE/2): zr.getDoubleY();
//        Rectangle2D.Double minTarget = new Rectangle2D.Double(x, y, MIN_SIZE, MIN_SIZE) ;
//        Rectangle2D.Double hitTarget = (zr.getDoubleHeight() < MIN_SIZE) ? minTarget : zr;
         Rectangle2D.Double hitTarget = zr;
        if (!_mainView.isZoomIsFitToScreen() && hitTarget.contains(mouseEvent.getPoint())) {
            _startDragPoint = mouseEvent.getPoint();
            _startDragZoomCentre = _mainView.getZoomCenter();
        }
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        _startDragPoint = null;
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        if (_startDragPoint != null) {
            Point2D currentDragPoint = mouseEvent.getPoint();
            double xIncr = _zoomView.getLandscapeDistanceX(_startDragPoint.getX() - currentDragPoint.getX());
            double yIncr = _zoomView.getLandscapeDistanceY(_startDragPoint.getY() - currentDragPoint.getY());
            Point2D.Double newZoomCentre = new Point2D.Double(_startDragZoomCentre.getX() - xIncr, _startDragZoomCentre.getY() + yIncr);
            _mainView.setZoomCenter(newZoomCentre);

        }
    }

    private ZoomGlassPane _glassPane;
    private Point2D _startDragPoint;
    private LandscapeView _mainView;
    private LandscapeView _zoomView;
    private Point2D _startDragZoomCentre;
}
