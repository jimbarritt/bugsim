/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.swing.StatusBar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeMouseLocationListener implements MouseMotionListener {
    public LandscapeMouseLocationListener(LandscapeView landscapeView, StatusBar statusBar) {
        _landscapeView = landscapeView;
        _statusBar = statusBar;
    }

    public void mouseDragged(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseMoved(MouseEvent e) {
        RectangularCoordinate coord = _landscapeView.getSnappedLandscapeLocation(e.getPoint(), 1).getCoordinate();
        _statusBar.setText("Landscape Location : (" + F0.format(coord.getDoubleX()) + ", "  + F0.format(coord.getDoubleY()) + " )");
    }


    private static final NumberFormat F0 = new DecimalFormat("0");

    private LandscapeView _landscapeView;
    private StatusBar _statusBar;
}
