/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.experiment.model.*;
import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.math.scale.*;
import com.ixcode.framework.simulation.model.*;
import com.ixcode.framework.simulation.model.landscape.*;
import com.ixcode.framework.swing.*;
import org.apache.log4j.*;

import static javax.imageio.ImageIO.*;
import javax.swing.*;
import java.awt.*;
import static java.awt.Cursor.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.beans.*;
import java.io.*;
import java.util.*;
import java.util.List;

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
    private static final String PROPERTY_BLACK_AND_WHITE = "blackAndWhite";
    public static final String PROPERTY_RENDER_GRIDS = "renderGrids";
    public static final String PROPERTY_GRID_RESOLUTION = "gridResolution";
    public static final String PROPERTY_GRID_TO_SCALE = "gridToScale";

    private double scaleX = 1.0d;
    private double scaleY = 1.0d;
    private double rotateTheta = 0;//Math.PI;
    private double translateX = 0;
    private double translateY = 0;

    private Landscape landscape;
    private boolean fitToScreen = true;
    private int logicalGridResolution = 10;
    private boolean gridToScale = true;
    private boolean showGrid = true;

    private double landscapeClipSizeX;
    private double landscapeClipSizeY;

    private Point2D.Double landscapeOrigin = new Point2D.Double();

    private boolean zoomCenterActive = false;

    private Point2D zoomCenter = new Point2D.Double(0, 0);
    private double zoomPercent = 1;

    private double displayWidth;
    private double displayHeight;
    public static final String PROPERTY_SHOW_GRID = "showGrid";
    private LandscapeRenderer landscapeRenderer = new LandscapeRenderer();

    private BufferedImage image;
    private BufferedImage rawBackgroundImage;
    private LandscapeViewModeStrategeyRegistry viewModeRegistry;
    private IViewModeStrategy viewModeStrategy;

    private ScaledDistance gridResolution;
    private int logicalGridThickness;
    private ScaledDistance gridThickness;
    private boolean listenToAgents;

    private static final Logger log = Logger.getLogger(LandscapeView.class);
    private List backgroundRenderers = new ArrayList();
    private List overlayRenderers = new ArrayList();
    private AgentTypeChoiceCombo agentTypeChoiceCombo;
    private boolean renderForPrint = false;
    private boolean renderGrids = true;
    private ExperimentController experimentController;

    public LandscapeView(Landscape landscape, AgentTypeChoiceCombo combo, StatusBar statusBar) {
        agentTypeChoiceCombo = combo;
        LandscapeViewModeStrategeyRegistry registry = new LandscapeViewModeStrategeyRegistry(this, combo);

        setLandscape(landscape);
        viewModeRegistry = registry;
        setViewMode(ViewMode.DISPLAY);
        LandscapeMouseLocationListener locationListener = new LandscapeMouseLocationListener(this, statusBar);
        addMouseMotionListener(locationListener);

        LocationLineRenderer locationLineRenderer = new LocationLineRenderer(this);
        addOverlayRenderer(locationLineRenderer, 0);

        setBackground(Color.white);
        setGridResolution(new ScaledDistance(1, DistanceUnitRegistry.metres()));
        setGridThickness(new ScaledDistance(10, DistanceUnitRegistry.centimetres()));

        setCursor(getPredefinedCursor(CROSSHAIR_CURSOR));
    }

    public void paintComponent(Graphics graphics) {

        Graphics2D g = (Graphics2D) graphics;

        Rectangle bounds = graphics.getClipBounds();
        double x = bounds.getWidth() / 2;
        double y = bounds.getHeight() / 2;

        g.rotate(rotateTheta, x, y);

        Rectangle preScaleBounds = g.getClipBounds();

        setDisplayWidth(preScaleBounds.getWidth());
        setDisplayHeight(preScaleBounds.getHeight());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (fitToScreen) {
            setScaleX((preScaleBounds.getWidth() / (landscape.getExtentX())));
            setScaleY((preScaleBounds.getHeight() / (landscape.getExtentY())));
        }

        setLandscapeClipSizeX((preScaleBounds.getWidth() / getScaleX()));
        setLandscapeClipSizeY((preScaleBounds.getHeight() / getScaleY()));

        g.setPaint(getBackground());
        g.fill(bounds);

        paintBackground(g, preScaleBounds);

        if (showGrid && !gridToScale) {
            drawGridLines(g, preScaleBounds.getWidth(), preScaleBounds.getHeight());
        }
        g.scale(scaleX, scaleY);

        if (zoomCenterActive) {
            setTranslateX(-(zoomCenter.getX() - landscapeClipSizeX / 2));

            //ok this is a bit wierd but its because the y axis is all rotated
            double flipY = -((-zoomCenter.getY()) - (landscapeClipSizeY / 2));
            setTranslateY(flipY - landscape.getExtentY());
        }

        float offset = 0f;
        g.translate(offset + getTranslateX(), offset + getTranslateY());

        // Must be after all calculations esp translation
        double bottomX = -getTranslateX();
        double bottomY = (landscape.getExtentY() + getTranslateY()) - getLandscapeClipSizeY();
        setLandscapeOrigin(new Point2D.Double(bottomX, bottomY));

        renderBackgrounds(g);

        landscapeRenderer.render(g, this);

        if (showGrid && gridToScale) {
            drawGridLines(g, landscape.getExtentX(), landscape.getExtentY());
        }

        renderOverlays(g);

    }


    public boolean getRenderGrids() {
        return renderGrids;
    }

    public void setRenderGrids(boolean render) {
        boolean oldRenderGrids = renderGrids;
        renderGrids = render;
        firePropertyChange(PROPERTY_RENDER_GRIDS, oldRenderGrids, renderGrids);
    }

    public ExperimentController getExperimentController() {
        return experimentController;
    }

    public void setExperimentController(ExperimentController experimentController) {
        this.experimentController = experimentController;
    }

    public static RectangularCoordinate getScreenCoord(Landscape landscape, RectangularCoordinate landscapeCoord) {
        double x = landscapeCoord.getDoubleX();
        double y = landscape.getExtentY() - landscapeCoord.getDoubleY();
        return new RectangularCoordinate(x, y);

    }


    public void saveViewAsJPeg(File file) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("Saving landscape image as: " + file.getAbsolutePath());
        }
        int width = getWidth();
        int height = getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        paint(g2d);
        g2d.dispose();
        write(bufferedImage, "jpg", file);
    }

    public void setLandscape(Landscape landscape) {
        if (this.landscape != null) {
            this.landscape.removePropertyChangeListener(this);
        }
        this.landscape = landscape;
        this.landscape.addPropertyChangeListener(this);
        setZoomCenter(new Point2D.Double(this.landscape.getExtentX() / 2, this.landscape.getExtentY() / 2));
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

    public void setGridResolution(ScaledDistance resolution) {
        double logicalResolution = landscape.getScale().convertScaledToLogicalDistance(new Distance(resolution.getDistance(), resolution.getUnits()));
        setLogicalGridResolution((int) Math.round(logicalResolution));
        gridResolution = resolution;
    }

    public void setLogicalGridThickness(int gridWidth) {
        logicalGridThickness = gridWidth;
    }

    public int getLogicalGridThickness() {
        return logicalGridThickness;
    }

    public void setGridThickness(ScaledDistance gridThickness) {
        double logicalThickness = landscape.getScale().convertScaledToLogicalDistance(new Distance(gridThickness.getDistance(), gridThickness.getUnits()));
        setLogicalGridThickness((int) Math.round(logicalThickness));
        this.gridThickness = gridThickness;
    }

    public ScaledDistance getGridThickness() {
        return gridThickness;
    }


    public ScaledDistance getGridResolution() {
        return gridResolution;
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
        return getSnappedLandscapeLocation(point, logicalGridResolution);
    }

    public Location getSnappedLandscapeLocation(Point point, int resolution) {
        Location rawLocation = getLandscapeLocation(point);
        double snappedX = Math.round(rawLocation.getDoubleX() / resolution) * resolution;
        double snappedY = Math.round(rawLocation.getDoubleY() / resolution) * resolution;
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


    public List getBackgroundRenderers() {
        return backgroundRenderers;
    }


    public List getOverlayRenderers() {
        return overlayRenderers;
    }

    public void addBackgroundRenderer(ILandscapeRenderer renderer, int position) {
        int pos = (position > backgroundRenderers.size()) ? backgroundRenderers.size() : position;
        backgroundRenderers.add(pos, renderer);
    }

    public void removeBackgroundRenderer(ILandscapeRenderer renderer) {
        backgroundRenderers.remove(renderer);
    }

    public void addOverlayRenderer(ILandscapeRenderer renderer, int position) {
        int pos = (position > overlayRenderers.size()) ? overlayRenderers.size() : position;
        overlayRenderers.add(pos, renderer);

    }

    public void removeOverlayRenderer(ILandscapeRenderer renderer) {
        overlayRenderers.remove(renderer);

    }

    private void renderOverlays(Graphics2D g) {
        for (Iterator itr = overlayRenderers.iterator(); itr.hasNext();) {
            ILandscapeRenderer renderer = (ILandscapeRenderer) itr.next();
            if (renderer.isVisible()) {
                renderer.render(g, this);
            }
        }
    }

    private void renderBackgrounds(Graphics2D g) {
        for (Iterator itr = backgroundRenderers.iterator(); itr.hasNext();) {
            ILandscapeRenderer renderer = (ILandscapeRenderer) itr.next();
            if (renderer.isVisible()) {
                renderer.render(g, this);
            }
        }
    }

    public void loadBackgroundImageFromFileName(File file) throws FileNotFoundException {
        rawBackgroundImage = IxImageManipulation.getBufferedImage(file, this);
        redraw();
    }

    public void redraw() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                invalidate();
                repaint();
            }
        });


    }

    private void paintBackground(Graphics2D g, Rectangle preScaleBounds) {
        if (rawBackgroundImage != null) {
            IxScaledImage scImage = IxImageManipulation.scaleImageToSize(rawBackgroundImage, preScaleBounds.getWidth(), preScaleBounds.getHeight(), this);
            g.drawImage(scImage.getImage(), 0, 0, this);
        }
    }

    private void paintBackground_old(Graphics2D g, double width, double height) {

        try {
            if (image == null) {
                BufferedImage unprocessed = IxImageManipulation.getBufferedImage(new File("/Users/jim/Documents/msc/field results/flight.jpg"), this);
                BufferedImage translated = new BufferedImage(unprocessed.getWidth(), unprocessed.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
                Graphics2D translateG = translated.createGraphics();
                double translateX = -(unprocessed.getWidth() * .23);
                double translateY = -(unprocessed.getHeight() * .425);
                AffineTransform translateTransform = AffineTransform.getTranslateInstance(translateX, translateY);
                translateG.drawImage(unprocessed, translateTransform, this);
//                System.out.println("Translated : x=" + translateX + ", y=" + translateY);

                BufferedImage rotated = new BufferedImage(translated.getWidth(), translated.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
                Graphics2D rotateG = rotated.createGraphics();
                AffineTransform rotateTransform = AffineTransform.getRotateInstance(.025);
                rotateG.drawImage(translated, rotateTransform, this);

                BufferedImage scaled = new BufferedImage(rotated.getWidth(), rotated.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
                Graphics2D scaleG = scaled.createGraphics();
                double scx = .75;
                double scy = .75;
                AffineTransform scaleTransform = AffineTransform.getScaleInstance(scx, scy);
                scaleG.drawImage(rotated, scaleTransform, this);
                image = scaled;

            }

            g.drawImage(image, 0, 0, this);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void drawGridLines(Graphics2D g, double extentX, double extentY) {
        double cx = extentX / 2;
        double cy = extentY / 2;


        int countx = (int) (extentX / logicalGridResolution) + 1;
        int countXSide = countx / 2; // number of gridlines from centre to edge.
        int minX = (int) (cx - ((logicalGridResolution * 0.5) + (logicalGridResolution * (countXSide - 1))));

        int county = (int) (extentY / logicalGridResolution) + 1;
        int countYSide = county / 2; // number of gridlines from centre to edge.
        int minY = (int) (cy - ((logicalGridResolution * 0.5) + (logicalGridResolution * (countYSide - 1))));
       
        g.setColor(Color.lightGray);
        g.setStroke(new BasicStroke(0.025f));//logicalGridThickness

        int offsetx = minX;
        int offsety = minY;

        for (int i = 0; i < countx; ++i) {
            Line2D line = new Line2D.Double((logicalGridResolution * i) + offsetx, 0, (logicalGridResolution * i) + offsetx, extentY);
            g.draw(line);
        }


        for (int i = 0; i < county; ++i) {
            Line2D line = new Line2D.Double(0, (logicalGridResolution * i) + offsety, extentX, (logicalGridResolution * i) + offsety);
            g.draw(line);
        }

    }


    public int getLogicalGridResolution() {
        return logicalGridResolution;
    }

    public void setLogicalGridResolution(int gridResolution) {
        int oldGridRes = logicalGridResolution;
        logicalGridResolution = gridResolution;
        super.firePropertyChange(PROPERTY_GRID_RESOLUTION, oldGridRes, logicalGridResolution);
    }

    public boolean isGridToScale() {
        return gridToScale;
    }

    public void setGridToScale(boolean gridToScale) {
        boolean oldGridToScale = this.gridToScale;
        this.gridToScale = gridToScale;
        super.firePropertyChange(PROPERTY_GRID_TO_SCALE, oldGridToScale, this.gridToScale);
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

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        boolean oldShowGrid = this.showGrid;
        this.showGrid = showGrid;
        firePropertyChange(PROPERTY_SHOW_GRID, oldShowGrid, this.showGrid);
    }

    public void setRenderForPrint(boolean bw) {
        boolean oldBW = renderForPrint;
        renderForPrint = bw;
        firePropertyChange(PROPERTY_BLACK_AND_WHITE, oldBW, renderForPrint);

    }

    public boolean getRenderForPrint() {
        return renderForPrint;
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

    public boolean isFitToScreen() {
        return fitToScreen;
    }

    public void setFitToScreen(boolean fitToScreen) {
        boolean oldFitToScreen = this.fitToScreen;
        this.fitToScreen = fitToScreen;
        scaleX = 1;
        scaleY = 1;
        translateX = 0;
        translateY = 0;

        zoomCenterActive = !fitToScreen;
        firePropertyChange(PROPERTY_FIT_TO_SCREEN, oldFitToScreen, this.fitToScreen);
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
