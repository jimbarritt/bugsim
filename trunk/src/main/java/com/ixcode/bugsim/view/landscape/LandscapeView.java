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
import static java.awt.Cursor.*;
import java.awt.geom.*;
import static java.lang.Math.*;

public class LandscapeView extends JComponent {

    private static final Logger log = Logger.getLogger(LandscapeView.class);

    private double scaleX = 1.0d;
    private double scaleY = 1.0d;
    private double translateX = 0;
    private double translateY = 0;

    private Landscape landscape;
    private boolean zoomIsFitToScreen = true;

    private double landscapeClipSizeX;
    private double landscapeClipSizeY;
    private double displayWidth;
    private double displayHeight;

    private Point2D.Double landscapeOrigin = new Point2D.Double();

    private boolean zoomCenterActive = false;
    private Point2D zoomCenter = new Point2D.Double(0, 0);
    private double zoomPercent = 1;

    private LandscapeViewModeStrategeyRegistry viewModeRegistry;
    private ViewMode viewMode;

    private GridLineRenderer gridLineRenderer = new GridLineRenderer();
    private BackgroundRenderer backgroundRenderer = new BackgroundRenderer();
    private LandscapeRenderer landscapeRenderer = new LandscapeRenderer();

    public LandscapeView(Landscape landscape, StatusBar statusBar) {
        setLandscape(landscape);
        setBackground(Color.white);
        setCursor(getPredefinedCursor(CROSSHAIR_CURSOR));

        viewModeRegistry = new LandscapeViewModeStrategeyRegistry(this);
        setViewMode(DISPLAY);

        addMouseMotionListener(new LandscapeMouseLocationListener(this, statusBar));
    }

    public void setLandscape(Landscape landscape) {
        this.landscape = landscape;
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

    public double getDisplayWidth() {
        return displayWidth;
    }

    public ViewModeName getViewMode() {
        return viewMode.getName();
    }

    public boolean isViewMode(ViewModeName modeName) {
        return viewMode.getName() == modeName;
    }

    public void setViewMode(ViewModeName modeName) {
        if (viewMode != null) {
            this.removeMouseListener(viewMode.getMouseListener());
            this.removeMouseMotionListener(viewMode.getMouseMotionListener());
        }
        viewMode = viewModeRegistry.getStrategy(modeName);

        if (log.isDebugEnabled()) {
            log.debug("Setting View Mode to be " + modeName + " , " + viewMode + ", " + viewMode.getCursor());
        }

        super.setCursor(viewMode.getCursor());
        super.addMouseListener(viewMode.getMouseListener());
        super.addMouseMotionListener(viewMode.getMouseMotionListener());
        invalidate();
        repaint();
    }

    public Simulation getSimulation() {
        return landscape.getSimulation();
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
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
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
        this.translateX = translateX;
    }

    public double getTranslateY() {
        return translateY;
    }

    public void setTranslateY(double translateY) {
        this.translateY = translateY;
    }

    public Point2D getZoomCenter() {
        return zoomCenter;
    }

    public void setZoomCenter(Point2D zoomCenter) {
        this.zoomCenter = zoomCenter;
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
    }

    public double getZoomPercent() {
        return zoomPercent;
    }

    public void setZoomPercent(double zoomPercent) {
        this.zoomPercent = zoomPercent;
        setScaleX(this.zoomPercent);
        setScaleY(this.zoomPercent);
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
        this.landscapeClipSizeX = landscapeClipSizeX;
    }

    private void setLandscapeClipSizeY(double landscapeClipSizeY) {
        this.landscapeClipSizeY = landscapeClipSizeY;
    }

    private void setLandscapeOrigin(Point2D.Double landscapeOrigin) {
        this.landscapeOrigin = landscapeOrigin;
    }
}
