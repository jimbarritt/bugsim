/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.bugsim.view.grid.*;
import com.ixcode.bugsim.view.landscape.action.*;
import com.ixcode.bugsim.view.landscape.mouse.*;
import com.ixcode.bugsim.view.landscape.viewmode.*;
import com.ixcode.framework.experiment.model.*;
import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.simulation.model.*;
import com.ixcode.framework.simulation.model.landscape.*;
import com.ixcode.framework.swing.*;
import org.apache.log4j.*;

import javax.swing.*;
import java.awt.*;
import static java.awt.Cursor.*;
import java.awt.geom.*;
import java.beans.*;
import static java.lang.Math.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeView extends JComponent implements PropertyChangeListener {
    private static final String PROPERTY_SCALE_X = "scaleX";
    private static final String PROPERTY_SCALE_Y = "scaleY";
    public static final String PROPERTY_FIT_TO_SCREEN = "fitToScreen";
    public static final String PROPERTY_ZOOM_PERCENT = "zoomPercent";
    public static final String PROPERTY_ZOOM_CENTER = "zoomCenter";
    private final String PROPERTY_CLIP_SIZE_X = "landscapeClipSizeX";
    private final String PROPERTY_LANDSCAPE_ORIGIN = "landscapeOrigin";
    private static final String PROPERTY_TRANSLATE_Y = "translateY";
    private static final String PROPERTY_TRANSLATE_X = "translateX";

    private double scaleX = 1.0d;
    private double scaleY = 1.0d;
    private double translateX = 0;
    private double translateY = 0;

    private Landscape landscape;
    private boolean zoomIsFitToScreen = true;

    private double landscapeClipSizeX;
    private double landscapeClipSizeY;

    private Point2D.Double landscapeOrigin = new Point2D.Double();

    private boolean zoomCenterActive = false;

    private Point2D zoomCenter = new Point2D.Double(0, 0);
    private double zoomPercent = 1;

    private double displayWidth;
    private double displayHeight;

    private LandscapeRenderer landscapeRenderer = new LandscapeRenderer();

    private LandscapeViewModeStrategeyRegistry viewModeRegistry;
    private ViewModeStrategy viewModeStrategy;

   private boolean listenToAgents;

    private static final Logger log = Logger.getLogger(LandscapeView.class);
    private AgentTypeChoiceCombo agentTypeChoiceCombo;
    private ExperimentController experimentController;
    private GridLineRenderer gridLineRenderer = new GridLineRenderer();
    private BackgroundRenderer backgroundRenderer = new BackgroundRenderer();

    public LandscapeView(Landscape landscape, AgentTypeChoiceCombo combo, StatusBar statusBar) {
        agentTypeChoiceCombo = combo;
        LandscapeViewModeStrategeyRegistry registry = new LandscapeViewModeStrategeyRegistry(this, combo);

        setLandscape(landscape);
        viewModeRegistry = registry;
        setViewMode(ViewMode.DISPLAY);
        LandscapeMouseLocationListener locationListener = new LandscapeMouseLocationListener(this, statusBar);
        addMouseMotionListener(locationListener);

        setBackground(Color.white);
        
        setCursor(getPredefinedCursor(CROSSHAIR_CURSOR));
    }

    public void setLandscape(Landscape landscape) {
        if (this.landscape != null) {
            this.landscape.removePropertyChangeListener(this);
        }
        this.landscape = landscape;
        this.landscape.addPropertyChangeListener(this);
        setZoomCenter(new Point2D.Double(this.landscape.getExtentX() / 2, this.landscape.getExtentY() / 2));
    }

    public void paintComponent(Graphics graphics) {
        paintComponentWith2DGraphics((Graphics2D) graphics);
    }

    private void paintComponentWith2DGraphics(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        setDisplaySize(graphics2D.getClipBounds());

        backgroundRenderer.render(graphics2D, getBackground());

        scaleView(graphics2D);

        poisitionView(graphics2D);

        landscapeRenderer.render(this, graphics2D);

        gridLineRenderer.render(landscape, graphics2D);
    }


    private void setDisplaySize(Rectangle bounds) {
        setDisplayWidth(bounds.getWidth());
        setDisplayHeight(bounds.getHeight());
    }

    private void poisitionView(Graphics2D graphics2D) {
        if (zoomCenterActive) {
            setTranslateX(-(zoomCenter.getX() - landscapeClipSizeX / 2));

            //ok this is a bit wierd but its because the y axis is all rotated
            double flipY = -((-zoomCenter.getY()) - (landscapeClipSizeY / 2));
            setTranslateY(flipY - landscape.getExtentY());
        }

        float offset = 0f;
        graphics2D.translate(offset + getTranslateX(), offset + getTranslateY());

        // Must be after all calculations esp translation
        double bottomX = -getTranslateX();
        double bottomY = (landscape.getExtentY() + getTranslateY()) - getLandscapeClipSizeY();
        setLandscapeOrigin(new Point2D.Double(bottomX, bottomY));
    }

    private void scaleView(Graphics2D graphics2D) {
        Rectangle graphicsBounds = graphics2D.getClipBounds();
        if (zoomIsFitToScreen) {
            setScaleX((graphicsBounds.getWidth() / (landscape.getExtentX())));
            setScaleY((graphicsBounds.getHeight() / (landscape.getExtentY())));
        }

        setLandscapeClipSizeX((graphicsBounds.getWidth() / getScaleX()));
        setLandscapeClipSizeY((graphicsBounds.getHeight() / getScaleY()));

        graphics2D.scale(scaleX, scaleY);
    }

    public ExperimentController getExperimentController() {
        return experimentController;
    }

    public void setExperimentController(ExperimentController experimentController) {
        this.experimentController = experimentController;
    }


    public double getDisplayWidth() {
        return displayWidth;
    }

    public ViewMode getViewMode() {
        return viewModeStrategy.getViewMode();
    }

    public boolean isViewMode(ViewMode mode) {
        return viewModeStrategy.getViewMode() == mode;
    }

    public void setViewMode(ViewMode mode) {
        if (viewModeStrategy != null) {
            this.removeMouseListener(viewModeStrategy.getMouseListener());
            this.removeMouseMotionListener(viewModeStrategy.getMouseMotionListener());
        }
        viewModeStrategy = viewModeRegistry.getStrategy(mode);

        if (log.isDebugEnabled()) {
            log.debug("Setting View Mode to be " + mode + " , " + viewModeStrategy + ", " + viewModeStrategy.getCursor());
        }

        super.setCursor(viewModeStrategy.getCursor());
        super.addMouseListener(viewModeStrategy.getMouseListener());
        super.addMouseMotionListener(viewModeStrategy.getMouseMotionListener());
        invalidate();
        repaint();
    }

    public Simulation getSimulation() {
        return landscape.getSimulation();
    }

    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals(Landscape.PROPERTY_AGENTS) && !listenToAgents) {
            return;
        }
        invalidate();
        repaint();
    }

    public void setDisplayWidth(double displayWidth) {
        this.displayWidth = displayWidth;
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
    public Location getLandscapeLocation(Point screenPoint) {
        Point.Double landscapeOrigin = getLandscapeOrigin();
        double landscapeX = (screenPoint.getX() / getScaleX()) + landscapeOrigin.getX();
        double landscapeY = (getLandscapeClipSizeY() - (screenPoint.getY() / getScaleY())) + landscapeOrigin.getY();
        return new Location(landscapeX, landscapeY);
    }

    public Location getSnappedLandscapeLocation(Point point) {
        Location landscapeLocation = getLandscapeLocation(point);
        double snappedX = ceil(landscapeLocation.getDoubleX()) - 1;
        double snappedY = ceil(landscapeLocation.getDoubleY()) - 1;

        snappedX = (snappedX < 0) ? 0 : snappedX;
        snappedY = (snappedY < 0) ? 0 : snappedY;
        return new Location(snappedX, snappedY);
    }

    public double getLandscapeDistanceX(double screenDistance) {
        return screenDistance / getScaleX();
    }

    public double getLandscapeDistanceY(double screenDistance) {
        return screenDistance / getScaleX();
    }

    public double getScreenDistanceX(double landscapeDistance) {
        return landscapeDistance * getScaleX();
    }

    public double getScreenDistanceY(double landscapeDistance) {
        return landscapeDistance * getScaleY();
    }

    protected void paintOuterBorder(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        Rectangle shape = g.getClipBounds();
        g.setColor(Color.white);
        g.fillRect(shape.x, shape.y, shape.width, shape.height);

        Rectangle test = new Rectangle(0, 0, shape.width, shape.height);
        g.setColor(Color.blue);
        int strokeWidth = 4;
        int halfStrokeWidth = strokeWidth / 2;
        g.setStroke(new BasicStroke(strokeWidth));
        g.drawRect(test.x + halfStrokeWidth, test.y + halfStrokeWidth, test.width - strokeWidth, test.height - strokeWidth);
    }


    public void redraw() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                invalidate();
                repaint();
            }
        });


    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        double oldScaleX = this.scaleX;
        this.scaleX = scaleX;
        firePropertyChange(PROPERTY_SCALE_X, oldScaleX, this.scaleX);
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        double oldScaleY = this.scaleY;
        this.scaleY = scaleY;
        firePropertyChange(PROPERTY_SCALE_Y, oldScaleY, this.scaleY);
    }

    public double getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(double displayHeight) {
        this.displayHeight = displayHeight;
    }

    public double getTranslateX() {
        return translateX;
    }

    public void setTranslateX(double translateX) {
        double old = this.translateX;
        this.translateX = translateX;
        firePropertyChange(PROPERTY_TRANSLATE_X, old, this.translateX);
    }

    public double getTranslateY() {
        return translateY;
    }

    public void setTranslateY(double translateY) {
        double old = this.translateY;
        this.translateY = translateY;
        firePropertyChange(PROPERTY_TRANSLATE_Y, old, this.translateY);
    }

    public Point2D getZoomCenter() {
        return zoomCenter;
    }

    public void setZoomCenter(Point2D zoomCenter) {
        Point2D oldZoomCenter = this.zoomCenter;
        this.zoomCenter = zoomCenter;
        firePropertyChange(PROPERTY_ZOOM_CENTER, oldZoomCenter, this.zoomCenter);
        invalidate();
        repaint();
    }

    public boolean isZoomIsFitToScreen() {
        return zoomIsFitToScreen;
    }

    public void setZoomIsFitToScreen(boolean zoomIsFitToScreen) {
        boolean oldFitToScreen = this.zoomIsFitToScreen;
        this.zoomIsFitToScreen = zoomIsFitToScreen;
        scaleX = 1;
        scaleY = 1;
        translateX = 0;
        translateY = 0;

        zoomCenterActive = !zoomIsFitToScreen;
        firePropertyChange(PROPERTY_FIT_TO_SCREEN, oldFitToScreen, this.zoomIsFitToScreen);
    }

    public double getZoomPercent() {
        return zoomPercent;
    }

    public void setZoomPercent(double zoomPercent) {
        double oldZoomPercent = this.zoomPercent;
        this.zoomPercent = zoomPercent;
        setScaleX(this.zoomPercent);
        setScaleY(this.zoomPercent);
        firePropertyChange(PROPERTY_ZOOM_PERCENT, oldZoomPercent, this.zoomPercent);
    }

    public double getLandscapeClipSizeX() {
        return landscapeClipSizeX;
    }

    public double getLandscapeClipSizeY() {
        return landscapeClipSizeY;
    }

    public Point2D.Double getLandscapeOrigin() {
        return landscapeOrigin;
    }

    public Landscape getLandscape() {
        return landscape;
    }

    private void setLandscapeClipSizeX(double landscapeClipSizeX) {
        double oldClipSizeX = this.landscapeClipSizeX;
        this.landscapeClipSizeX = landscapeClipSizeX;
        firePropertyChange(PROPERTY_CLIP_SIZE_X, oldClipSizeX, this.landscapeClipSizeX);
    }

    private void setLandscapeClipSizeY(double landscapeClipSizeY) {
        this.landscapeClipSizeY = landscapeClipSizeY;
    }

    private void setLandscapeOrigin(Point2D.Double landscapeOrigin) {
        Point2D.Double oldOrigin = this.landscapeOrigin;
        this.landscapeOrigin = landscapeOrigin;
        firePropertyChange(PROPERTY_LANDSCAPE_ORIGIN, oldOrigin, this.landscapeOrigin);
    }


    public boolean isListenToAgents() {
        return listenToAgents;
    }

    public void setListenToAgents(boolean listenToAgents) {
        this.listenToAgents = listenToAgents;
    }

    public AgentTypeChoiceCombo getAgentTypeChoiceCombo() {
        return agentTypeChoiceCombo;
    }


}
