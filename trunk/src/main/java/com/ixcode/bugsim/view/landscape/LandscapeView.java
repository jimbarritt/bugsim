package com.ixcode.bugsim.view.landscape;

import com.ixcode.bugsim.view.grid.*;
import com.ixcode.bugsim.view.landscape.mouse.*;
import com.ixcode.bugsim.view.landscape.viewmode.*;
import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.simulation.model.*;
import com.ixcode.framework.simulation.model.landscape.*;
import com.ixcode.framework.swing.*;
import static com.ixcode.framework.swing.ViewModeName.*;
import org.apache.log4j.*;

import javax.swing.*;
import java.awt.*;
import static java.lang.Math.*;

public class LandscapeView extends JComponent {

    private static final Logger log = Logger.getLogger(LandscapeView.class);

    private final Landscape landscape;

    private ViewMode currentViewMode;

    private final LandscapeViewModeRegistry viewModes = new LandscapeViewModeRegistry();
    private final GridLineRenderer gridLineRenderer = new GridLineRenderer();
    private final BackgroundRenderer backgroundRenderer = new BackgroundRenderer();
    private final LandscapeRenderer landscapeRenderer = new LandscapeRenderer();

    private RectangularCoordinate viewOriginOverLandscape;
    private RectangularCoordinate viewCentreOverLandscape;

    private boolean fitLandscapeToView = true;

    private double scaleX = 1.0d;
    private double scaleY = 1.0d;

    private double widthOfLandscapeInView;
    private double heightOfLandscapeInView;

    public LandscapeView(Landscape landscape, StatusBar statusBar) {
        this.landscape = landscape;

        setCurrentViewMode(DISPLAY);
        setBackground(Color.white);
        addMouseMotionListener(new LandscapeMouseLocationListener(this, statusBar));

        centreViewOnLandscape();
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


    private void centreViewOnLandscape() {
        centerViewOnLandscapeCoordinate(landscape.getLogicalBounds().getCentre());
    }

    public void paintComponent(Graphics graphics) {
        paintComponentWith2DGraphics((Graphics2D) graphics);
    }

    private void paintComponentWith2DGraphics(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        backgroundRenderer.render(graphics2D, getBackground());

        scaleView(graphics2D);

        landscapeRenderer.render(this, graphics2D);

        gridLineRenderer.render(landscape, graphics2D);
    }


    private void scaleView(Graphics2D graphics2D) {
        Rectangle graphicsBounds = graphics2D.getClipBounds();
        if (fitLandscapeToView) {
            scaleX = (graphicsBounds.getWidth() / (landscape.getExtentX()));
            scaleY = (graphicsBounds.getHeight() / (landscape.getExtentY()));
        }

        widthOfLandscapeInView = (graphicsBounds.getWidth() / scaleX);
        heightOfLandscapeInView = (graphicsBounds.getHeight() / scaleY);

        graphics2D.scale(scaleX, scaleY);
    }

    public ViewModeName getCurrentViewMode() {
        return currentViewMode.getViewModeName();
    }

    public boolean isViewMode(ViewModeName modeName) {
        return currentViewMode.getViewModeName() == modeName;
    }


    public Simulation getSimulation() {
        return landscape.getSimulation();
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
        double snappedX = ceil(landscapeLocation.getDoubleX()) - 1;
        double snappedY = ceil(landscapeLocation.getDoubleY()) - 1;

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
        invalidate();
        repaint();
    }

    public double getWidthOfLandscapeInView() {
        return widthOfLandscapeInView;
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


}
