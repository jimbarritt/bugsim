package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.simulation.model.landscape.*;

import java.io.*;
import java.awt.*;

public class LandscapeToViewModel implements Serializable {
    
    private RectangularCoordinate viewOriginOverLandscape;
    private RectangularCoordinate viewCentreOverLandscape;
    private boolean fitLandscapeToView = true;
    private double scaleX = 1.0d;
    private double scaleY = 1.0d;
    private double widthOfLandscapeInView;
    private double heightOfLandscapeInView;
    private Landscape landscape;

    public LandscapeToViewModel(LandscapeView landscapeView) {
        this.landscape = landscapeView.getLandscape();
    }

    public void centreViewOnLandscape() {
        centerViewOnLandscapeCoordinate(landscape.getLogicalBounds().getCentre());
    }

    public void scaleView(Graphics2D graphics2D) {
        Rectangle graphicsBounds = graphics2D.getClipBounds();
        if (fitLandscapeToView) {
            scaleX = (graphicsBounds.getWidth() / (landscape.getExtentX()));
            scaleY = (graphicsBounds.getHeight() / (landscape.getExtentY()));
        }

        widthOfLandscapeInView = (graphicsBounds.getWidth() / scaleX);
        heightOfLandscapeInView = (graphicsBounds.getHeight() / scaleY);

        graphics2D.scale(scaleX, scaleY);
    }

    public static RectangularCoordinate getScreenCoord(Landscape landscape, RectangularCoordinate landscapeCoord) {
        double x = landscapeCoord.getDoubleX();
        double y = landscape.getExtentY() - landscapeCoord.getDoubleY();
        return new RectangularCoordinate(x, y);

    }

    /**
     * Notice we have to flip the Y coordinate over as the screen points start 0,0 top left but the landscape is bottom
     * left
     * <p/>
     * We also need to work it out from the centre rather than from the orign asw e already calculate the centre.
     *
     * @return the location
     */
    public Location getLocationOnLandscapeFrom(Point screenPoint) {
        double landscapeX = (screenPoint.getX() / scaleX) + viewOriginOverLandscape.getDoubleX();
        double landscapeY = (heightOfLandscapeInView - (screenPoint.getY() / scaleY)) + viewOriginOverLandscape.getDoubleY();
        return new Location(landscapeX, landscapeY);
    }

    public Location getLocationOnLandscapeSnappedFrom(Point point) {
        Location landscapeLocation = getLocationOnLandscapeFrom(point);
        double snappedX = Math.ceil(landscapeLocation.getDoubleX()) - 1;
        double snappedY = Math.ceil(landscapeLocation.getDoubleY()) - 1;

        snappedX = (snappedX < 0) ? 0 : snappedX;
        snappedY = (snappedY < 0) ? 0 : snappedY;
        return new Location(snappedX, snappedY);
    }

    public double getLandscapeDistanceX(double screenDistance) {
        return screenDistance / scaleX;
    }

    public double getLandscapeDistanceY(double screenDistance) {
        return screenDistance / scaleX;
    }

    public double getScreenDistanceX(double landscapeDistance) {
        return landscapeDistance * scaleX;
    }

    public double getScreenDistanceY(double landscapeDistance) {
        return landscapeDistance * scaleY;
    }

    public RectangularCoordinate getCenterOfViewOnLandscape() {
        return viewCentreOverLandscape;
    }

    public void centerViewOnLandscapeCoordinate(RectangularCoordinate landscapeCoordinate) {
        viewCentreOverLandscape = landscapeCoordinate;
        viewOriginOverLandscape = new RectangularCoordinate(0d, 0d);
    }

    public double getWidthOfLandscapeInView() {
        return widthOfLandscapeInView;
    }
}