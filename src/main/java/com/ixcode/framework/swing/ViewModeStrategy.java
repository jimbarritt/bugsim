/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface ViewModeStrategy {

    Cursor getCursor();

    ViewMode getViewMode();

    MouseListener getMouseListener();

    MouseMotionListener getMouseMotionListener();

    void enterMode(Component parent);

    void exitMode(Component parent);

}
