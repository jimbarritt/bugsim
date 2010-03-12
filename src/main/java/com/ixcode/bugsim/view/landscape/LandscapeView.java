package com.ixcode.bugsim.view.landscape;

import com.ixcode.bugsim.view.grid.*;
import com.ixcode.bugsim.view.landscape.mouse.*;
import com.ixcode.bugsim.view.landscape.viewmode.*;
import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.simulation.model.landscape.*;
import com.ixcode.framework.swing.*;
import static com.ixcode.framework.swing.ViewModeName.*;
import org.apache.log4j.*;

import javax.swing.*;
import java.awt.*;

public class LandscapeView extends JComponent {

    private static final Logger log = Logger.getLogger(LandscapeView.class);

    private final Landscape landscape;

    private ViewMode currentViewMode;

    private final LandscapeViewModeRegistry viewModes = new LandscapeViewModeRegistry();
    private final BackgroundRenderer backgroundRenderer = new BackgroundRenderer();
    private final GridLineRenderer gridLineRenderer = new GridLineRenderer();
    private final LandscapeRenderer landscapeRenderer = new LandscapeRenderer();

    private final LandscapeToViewTranslation landscapeToViewTranslation;

    public LandscapeView(Landscape landscape, StatusBar statusBar) {
        this.landscape = landscape;

        setCurrentViewMode(DISPLAY);
        setBackground(Color.white);
        addMouseMotionListener(new LandscapeMouseLocationListener(this, statusBar));

        landscapeToViewTranslation = new LandscapeToViewTranslation(landscape);
        landscapeToViewTranslation.centreViewOnLandscape();
    }

    public void setCurrentViewMode(ViewModeName modeName) {
        if (!viewModes.hasMode(modeName)) {
           throw new RuntimeException("No view mode called [" + modeName + "] !");
        }

        if (currentViewMode != null) {
            currentViewMode.end(this);
        }
        currentViewMode = viewModes.getViewMode(modeName);
        currentViewMode.begin(this);

        forceRedraw();
        
        if (log.isDebugEnabled()) {
            log.debug("View Mode set to be " + modeName + " , " + currentViewMode);
        }
    }


    public void paintComponent(Graphics graphics) {
        paintComponentWith2DGraphics((Graphics2D) graphics);
    }

    private void paintComponentWith2DGraphics(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        backgroundRenderer.render(graphics2D, getBackgroundColor());

        landscapeToViewTranslation.scaleToFitInView(graphics2D.getClipBounds());
        graphics2D.scale(landscapeToViewTranslation.scaleX(), landscapeToViewTranslation.scaleY());

        landscapeRenderer.render(this, graphics2D);

        gridLineRenderer.render(landscape, graphics2D);
    }


    public boolean isViewMode(ViewModeName modeName) {
        return currentViewMode.is(modeName);
    }

    public Landscape getLandscape() {
        return landscape;
    }

    public void forceRedraw() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                invalidate();
                repaint();
            }
        });
    }


    private Color getBackgroundColor() {
        return getBackground();
    }

    public Location getLocationOnLandscapeFrom(Point screenPoint) {
        return landscapeToViewTranslation.landscapeLocationFromViewPoint(screenPoint);
    }

    public Location getLocationOnLandscapeSnappedFrom(Point point) {
        return landscapeToViewTranslation.getLocationOnLandscapeSnappedFrom(point);
    }

    public double getLandscapeDistanceX(double screenDistance) {
        return landscapeToViewTranslation.getLandscapeDistanceX(screenDistance);
    }

    public double getLandscapeDistanceY(double screenDistance) {
        return landscapeToViewTranslation.getLandscapeDistanceY(screenDistance);
    }

    public double getScreenDistanceX(double landscapeDistance) {
        return landscapeToViewTranslation.getScreenDistanceX(landscapeDistance);
    }

    public double getScreenDistanceY(double landscapeDistance) {
        return landscapeToViewTranslation.getScreenDistanceY(landscapeDistance);
    }

    public RectangularCoordinate getCenterOfViewOnLandscape() {
        return landscapeToViewTranslation.getCenterOfViewOnLandscape();
    }

    public void centerViewOnLandscapeCoordinate(RectangularCoordinate landscapeCoordinate) {
        landscapeToViewTranslation.centerViewOnLandscapeCoordinate(landscapeCoordinate);
    }

    public double getWidthOfLandscapeInView() {
        return landscapeToViewTranslation.getWidthOfLandscapeInView();
    }

}
