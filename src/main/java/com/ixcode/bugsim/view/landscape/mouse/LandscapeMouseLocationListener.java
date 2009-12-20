package com.ixcode.bugsim.view.landscape.mouse;

import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.swing.StatusBar;
import com.ixcode.bugsim.view.landscape.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class LandscapeMouseLocationListener implements MouseMotionListener {

    private LandscapeView landscapeView;
    private StatusBar statusBar;

    public LandscapeMouseLocationListener(LandscapeView landscapeView, StatusBar statusBar) {
        this.landscapeView = landscapeView;
        this.statusBar = statusBar;
    }

    public void mouseDragged(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {
        RectangularCoordinate logicalLocation = landscapeView.getLocationOnLandscapeSnappedFrom(e.getPoint()).getCoordinate();
        StringBuilder sb = new StringBuilder();
        sb.append("Logical Location : (").append(fmt(logicalLocation.getDoubleX()))
          .append(", ").append(fmt(logicalLocation.getDoubleY())).append(" )")
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
