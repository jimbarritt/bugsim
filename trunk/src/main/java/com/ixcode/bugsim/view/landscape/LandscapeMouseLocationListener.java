/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.swing.StatusBar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeMouseLocationListener implements MouseMotionListener {

    private LandscapeView landscapeView;
    private StatusBar statusBar;

    public LandscapeMouseLocationListener(LandscapeView landscapeView, StatusBar statusBar) {
        this.landscapeView = landscapeView;
        this.statusBar = statusBar;
    }

    public void mouseDragged(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseMoved(MouseEvent e) {
        RectangularCoordinate coord = landscapeView.getSnappedLandscapeLocation(e.getPoint(), 1).getCoordinate();
        StringBuilder sb = new StringBuilder();
        sb.append("Logical Location : (").append(fmt(coord.getDoubleX()))
          .append(", ").append(fmt(coord.getDoubleY())).append(" )")
          .append(" Screen location: (").append(e.getPoint().x).append(", ").append(e.getPoint().y).append(")")
          .append(" Scale: ").append(landscapeView.getLandscape().getScale())
          .append(" Logical Extent: (").append(formatLogicalDimensions()).append(")")
          .append(" Scaled Extent: (").append(landscapeView.getLandscape().getScaledHeight()).append(", ").append(landscapeView.getLandscape().getScaledWidth()).append(")");

        statusBar.setText(sb.toString());
    }

    private String formatLogicalDimensions() {
        CartesianBounds logicalBounds = landscapeView.getLandscape().getLogicalBounds();
        return new StringBuilder()
                  .append(fmt(logicalBounds.getDoubleWidth()))
                  .append(", ")
                  .append(fmt(logicalBounds.getDoubleHeight()))
                  .toString();
    }

    private static String fmt(double number) {
        return F0.format(number);
    }

    private static final NumberFormat F0 = new DecimalFormat("0");


}
