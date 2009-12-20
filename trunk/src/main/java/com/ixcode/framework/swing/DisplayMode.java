/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;


import java.awt.*;
import static java.awt.Cursor.getPredefinedCursor;
import static java.awt.Cursor.CROSSHAIR_CURSOR;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class DisplayMode implements ViewMode, MouseListener, MouseMotionListener {

    public DisplayMode() {

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


    private Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    public ViewModeName getViewModeName() {
        return ViewModeName.DISPLAY;
    }

    private MouseListener getMouseListener() {
        return this;
    }

    private MouseMotionListener getMouseMotionListener() {
        return this;
    }


    public void mouseClicked(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
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


}
