/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DebugMouseListener implements MouseListener, MouseMotionListener {

    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println("Mouse Clicked!" + mouseEvent);
    }

    public void mousePressed(MouseEvent mouseEvent) {
        System.out.println("Mouse Pressed!" + mouseEvent);
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        System.out.println("Mouse Released!" + mouseEvent);
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        System.out.println("Mouse Entered!" + mouseEvent);
    }

    public void mouseExited(MouseEvent mouseEvent) {
        System.out.println("Mouse Exited!" + mouseEvent);
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        System.out.println("Mouse Dragged!" + mouseEvent);
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        System.out.println("Mouse Moved!" + mouseEvent);
    }
}
