/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.map;

import com.ixcode.framework.swing.ViewModeStrategy;
import com.ixcode.framework.swing.ViewMode;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class EditMapOutlineModeStrategy implements ViewModeStrategy,MouseListener, MouseMotionListener {

    public EditMapOutlineModeStrategy(MapImageView view) {
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
        return MapImageViewMode.EDIT_MAP_OUTLINE;
    }

    public MouseListener getMouseListener() {
        return this;
    }

    public MouseMotionListener getMouseMotionListener() {
        return this;
    }


    public void mouseClicked(MouseEvent mouseEvent) {
        double sx = _view.getScaleX();
        double sy = _view.getScaleY();
        double screenX = mouseEvent.getPoint().getX();
        double screenY = mouseEvent.getPoint().getY();
        Point2D.Double rawPoint = new Point2D.Double(screenX / sx, screenY / sy);

        _view.getMapOutline().addPoint(rawPoint);
//        System.out.println("Added Point at " + rawPoint + " mouse at " + mouseEvent.getPoint());
        _view.redraw();
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    MapImageView _view;
}
