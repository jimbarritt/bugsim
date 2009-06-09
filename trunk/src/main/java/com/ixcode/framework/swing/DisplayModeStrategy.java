/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DisplayModeStrategy implements IViewModeStrategy,MouseListener, MouseMotionListener {

    public DisplayModeStrategy() {

    }

    public void enterMode(Component parent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void exitMode(Component parent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    }

    public ViewMode getViewMode() {
        return ViewMode.DISPLAY;
    }

    public MouseListener getMouseListener() {
        return this;
    }

    public MouseMotionListener getMouseMotionListener() {
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
