package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.simulation.model.landscape.*;

import java.io.*;
import java.awt.*;

public class LandscapeToViewTranslation implements Serializable {

    private RectangularCoordinate viewOriginOverLandscape;
    private RectangularCoordinate viewCentreOverLandscape;
    private boolean fitLandscapeToView = true;
    private double scaleX = 1.0d;
    private double scaleY = 1.0d;
    private double widthOfLandscapeInView;
    private double heightOfLandscapeInView;
    private Landscape landscape;

    public LandscapeToViewTranslation(Landscape landscape) {
        this.landscape = landscape;
        centreViewOnLandscape();
    }

    public void centreViewOnLandscape() {
        centerViewOnLandscapeCoordinate(landscape.getLogicalBounds().getCentre());
    }

    public void scaleToFitInView(Rectangle viewClipBounds) {
        scaleX = (viewClipBounds.getWidth() / (landscape.getExtentX()));
        scaleY = (viewClipBounds.getHeight() / (landscape.getExtentY()));
        
        widthOfLandscapeInView = (viewClipBounds.getWidth() / scaleX);
        heightOfLandscapeInView = (viewClipBounds.getHeight() / scaleY);
    }

    public void centerViewOnLandscapeCoordinate(RectangularCoordinate landscapeCoordinate) {
        viewCentreOverLandscape = landscapeCoordinate;
        viewOriginOverLandscape = new RectangularCoordinate(0d, 0d);
    }    

    public double scaleX() {
        return scaleX;
    }

    public double scaleY() {
        return scaleY;
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
    public Location landscapeLocationFromViewPoint(Point screenPoint) {
        double landscapeX = (screenPoint.getX() / scaleX) + viewOriginOverLandscape.getDoubleX();
        double landscapeY = (heightOfLandscapeInView - (screenPoint.getY() / scaleY)) + viewOriginOverLandscape.getDoubleY();
        return new Location(landscapeX, landscapeY);
    }

    public Location getLocationOnLandscapeSnappedFrom(Point point) {
        Location landscapeLocation = landscapeLocationFromViewPoint(point);
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


    public double getWidthOfLandscapeInView() {
        return widthOfLandscapeInView;
    }


}