/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.map;

import com.ixcode.framework.swing.ViewModeName;
import com.ixcode.framework.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class EditMapOutlineMode implements ViewMode,MouseListener, MouseMotionListener {

    public EditMapOutlineMode(MapImageView view) {
        _view = view;
    }

    private Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }


    public ViewModeName getViewModeName() {
        return MapImageViewMode.EDIT_MAP_OUTLINE;
    }

    public void begin(Component parent) {
        parent.setCursor(getCursor());
        parent.addMouseListener(getMouseListener());
        parent.addMouseMotionListener(getMouseMotionListener());
    }

    public void end(Component parent) {
        parent.removeMouseListener(getMouseListener());
        parent.removeMouseMotionListener(getMouseMotionListener());
    }

    private MouseListener getMouseListener() {
        return this;
    }

    private MouseMotionListener getMouseMotionListener() {
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
