/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.experiment.model.ExperimentController;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.scale.Distance;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.swing.*;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import static java.awt.Cursor.getPredefinedCursor;
import static java.awt.Cursor.CROSSHAIR_CURSOR;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeView extends JComponent implements PropertyChangeListener {
    public boolean getRenderGrids() {
        return _renderGrids;
    }

    public void setRenderGrids(boolean render) {
        boolean oldRenderGrids = _renderGrids;
        _renderGrids = render;
        firePropertyChange(PROPERTY_RENDER_GRIDS,oldRenderGrids, _renderGrids);
    }

    public ExperimentController getExperimentController() {
        return _experimentController;
    }

    public void setExperimentController(ExperimentController experimentController) {
        _experimentController = experimentController;
    }


    public static final String PROPERTY_RENDER_GRIDS ="renderGrids";
    public static final String PROPERTY_GRID_RESOLUTION = "gridResolution";
    public static final String PROPERTY_GRID_TO_SCALE = "gridToScale";

    public LandscapeView(Landscape landscape, AgentTypeChoiceCombo combo, StatusBar statusBar) {

        _agentTypeChoiceCombo = combo;
        LandscapeViewModeStrategeyRegistry registry = new LandscapeViewModeStrategeyRegistry(this, combo);

        setLandscape(landscape);
        _viewModeRegistry = registry;
        setViewMode(ViewMode.DISPLAY);
        LandscapeMouseLocationListener locationListener = new LandscapeMouseLocationListener(this, statusBar);
        super.addMouseMotionListener(locationListener);

        _locationLineRenderer = new LocationLineRenderer(this);
        addMouseMotionListener(_locationLineRenderer);
        addOverlayRenderer(_locationLineRenderer, 0);

        setBackground(new Color(255, 255, 200));
        setGridResolution(new ScaledDistance(1, DistanceUnitRegistry.metres()));
        setGridThickness(new ScaledDistance(10, DistanceUnitRegistry.centimetres()));

        setCursor(getPredefinedCursor(CROSSHAIR_CURSOR));
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

        ImageIO.write(bufferedImage, "jpg", file);
    }

    public void setLandscape(Landscape landscape) {
        if (_landscape != null) {
            _landscape.removePropertyChangeListener(this);
        }
        _landscape = landscape;
        _landscape.addPropertyChangeListener(this);
        setZoomCenter(new Point2D.Double(_landscape.getExtentX() / 2, _landscape.getExtentY() / 2));
    }


    public double getDisplayWidth() {
        return _displayWidth;
    }

    public ViewMode getViewMode() {
        return _viewModeStrategy.getViewMode();
    }

    public boolean isViewMode(ViewMode mode) {
        return _viewModeStrategy.getViewMode() == mode;
    }

    public void setGridResolution(ScaledDistance resolution) {
        double logicalResolution = _landscape.getScale().convertScaledToLogicalDistance(new Distance(resolution.getDistance(), resolution.getUnits()));
        setLogicalGridResolution((int)Math.round(logicalResolution));
        _gridResolution = resolution;

    }

    public void setLogicalGridThickness(int gridWidth) {
        _logicalGridThickness = gridWidth;
    }

    public int getLogicalGridThickness() {
        return _logicalGridThickness;
    }

    public void setGridThickness(ScaledDistance gridThickness) {
        double logicalThickness = _landscape.getScale().convertScaledToLogicalDistance(new Distance(gridThickness.getDistance(), gridThickness.getUnits()));
        setLogicalGridThickness((int)Math.round(logicalThickness));

        _gridThickness = gridThickness;
    }

    public ScaledDistance getGridThickness() {
        return _gridThickness;
    }


    public ScaledDistance getGridResolution() {
        return _gridResolution;
    }


    public void setViewMode(ViewMode mode) {
        if (_viewModeStrategy != null) {
            this.removeMouseListener(_viewModeStrategy.getMouseListener());
            this.removeMouseMotionListener(_viewModeStrategy.getMouseMotionListener());
        }

        _viewModeStrategy = _viewModeRegistry.getStrategy(mode);

        if (log.isTraceEnabled()) {
            log.trace("Setting View Mode to be " + mode + " , " + _viewModeStrategy + ", " + _viewModeStrategy.getCursor());
        }

        super.setCursor(_viewModeStrategy.getCursor());

        super.addMouseListener(_viewModeStrategy.getMouseListener());
        super.addMouseMotionListener(_viewModeStrategy.getMouseMotionListener());


        invalidate();
        repaint();
    }

    public Simulation getSimulation() {
        return _landscape.getSimulation();
    }

    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals(Landscape.PROPERTY_AGENTS) && !_listenToAgents) {
            return;
        }
        invalidate();
        repaint();
    }

    public void setDisplayWidth(double displayWidth) {
        _displayWidth = displayWidth;
    }

    /**
     * Notice we have to flip the Y coordinate over as the screen points start 0,0 top left
     * but the landscape is bottom left
     * <p/>
     * We also need to work it out from the centre rather than from the orign asw e
     * already calculate the centre.
     *
     * @param screenPoint
     * @return the location
     */
    public Location getLandscapeLocation(Point screenPoint) {
//        System.out.println("GetLandscapeLocation for : " + screenPoint + " landscapeClipX=" + getLandscapeClipSizeX() + ", landscapeCLipY=" + getLandscapeClipSizeY() + " landscapeBottomeLeft=" + getLandscapeOrigin() + ", scaleX=" + getScaleX() + ", scaleY=" + getScaleY());
        Point.Double landscapeOrigin = getLandscapeOrigin();
        double landscapeX = (screenPoint.getX() / getScaleX()) + landscapeOrigin.getX();
        double landscapeY = (getLandscapeClipSizeY() - (screenPoint.getY() / getScaleY())) + landscapeOrigin.getY();
//        System.out.println("Landscape Location : (" + landscapeX + ", " + landscapeY + ")");
        return new Location(landscapeX, landscapeY);
    }

    public Location getSnappedLandscapeLocation(Point point) {
        return getSnappedLandscapeLocation(point, _logicalGridResolution);

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


    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;


        Rectangle bounds = graphics.getClipBounds();
        double x = bounds.getWidth() / 2;
        double y = bounds.getHeight() / 2;

        g.rotate(_rotateTheta, x, y);

        Rectangle preScaleBounds = g.getClipBounds();

        setDisplayWidth(preScaleBounds.getWidth());
        setDisplayHeight(preScaleBounds.getHeight());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (_fitToScreen)
        {  // The extra adjustments are just to line it up a bit better otherwise it chops .5 of a line off left and top
            setScaleX((preScaleBounds.getWidth() / (_landscape.getExtentX() + 1)));
            setScaleY((preScaleBounds.getHeight() / (_landscape.getExtentY() + 1)));

        }

        // The -1 is for the extra we allow around the edges
        setLandscapeClipSizeX((preScaleBounds.getWidth() / getScaleX()) - 1);
        setLandscapeClipSizeY((preScaleBounds.getHeight() / getScaleY()) - 1);


        g.setPaint(super.getBackground());
        if (_renderForPrint) {
            g.setColor(Color.white);
        }
        g.fill(bounds);

        paintBackground(g, preScaleBounds);


        if (_showGrid && !_gridToScale) {
            drawGridLines(g, preScaleBounds.getWidth(), preScaleBounds.getHeight());
        }
        g.scale(_scaleX, _scaleY);

        if (_zoomCenterActive) {
            setTranslateX(-(_zoomCenter.getX() - _landscapeClipSizeX / 2));

            //ok this is a bit wierd but its because the y axis is all rotated
            double flipY = -((-_zoomCenter.getY()) - (_landscapeClipSizeY / 2));
            setTranslateY(flipY - _landscape.getExtentY());
//            System.out.println("adj = " + flipY + ", translateY = "  + translateY + ", clipsize=" + _landscapeClipSizeY + ", extent=" +extentY);

//g.translate(.4 + getTranslateX(), .4 + getTranslateY());
        } else {
//            g.translate(.4 + getTranslateX(), .4 + getTranslateY());
        }

        g.translate(.4 + getTranslateX(), .4 + getTranslateY());

        // Must be after all calculations esp translation
        double bottomX = - getTranslateX();
        double bottomY = (_landscape.getExtentY() + getTranslateY()) - getLandscapeClipSizeY();
        setLandscapeOrigin(new Point2D.Double(bottomX, bottomY));




        // @todo replace paintBackground with a special renderer.
        renderBackgrounds(g);

//        g.setPaint(new Color(100, 100, 200));
        if (_showGrid && _gridToScale) {
            drawGridLines(g, _landscape.getExtentX(), _landscape.getExtentY());
        }

        _landscapeRenderer.render(g, this);

        renderOverlays(g);

    }

    public List getBackgroundRenderers() {
        return _backgroundRenderers;
    }


    public List getOverlayRenderers() {
        return _overlayRenderers;
    }

    public void addBackgroundRenderer(ILandscapeRenderer renderer, int position) {
        int pos = (position > _backgroundRenderers.size()) ? _backgroundRenderers.size() : position;
        _backgroundRenderers.add(pos, renderer);
    }

    public void removeBackgroundRenderer(ILandscapeRenderer renderer) {
        _backgroundRenderers.remove(renderer);
    }

    public void addOverlayRenderer(ILandscapeRenderer renderer, int position) {
        int pos = (position > _overlayRenderers.size()) ? _overlayRenderers.size() : position;
        _overlayRenderers.add(pos, renderer);

    }

    public void removeOverlayRenderer(ILandscapeRenderer renderer) {
        _overlayRenderers.remove(renderer);

    }

    private void renderOverlays(Graphics2D g) {
        for (Iterator itr = _overlayRenderers.iterator(); itr.hasNext();) {
            ILandscapeRenderer renderer = (ILandscapeRenderer)itr.next();
            if (renderer.isVisible()) {
                renderer.render(g, this);
            }
        }
    }

    private void renderBackgrounds(Graphics2D g) {
        for (Iterator itr = _backgroundRenderers.iterator(); itr.hasNext();) {
            ILandscapeRenderer renderer = (ILandscapeRenderer)itr.next();
            if (renderer.isVisible()) {
                renderer.render(g, this);
            }
        }
    }

    public void loadBackgroundImageFromFileName(File file) throws FileNotFoundException {
        _rawBackgroundImage = IxImageManipulation.getBufferedImage(file, this);
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
        if (_rawBackgroundImage != null) {
            IxScaledImage scImage = IxImageManipulation.scaleImageToSize(_rawBackgroundImage, preScaleBounds.getWidth(), preScaleBounds.getHeight(), this);
            g.drawImage(scImage.getImage(), 0, 0, this);
        }
    }

    private void paintBackground_old(Graphics2D g, double width, double height) {

        try {
            if (_image == null) {
                BufferedImage unprocessed = IxImageManipulation.getBufferedImage(new File("/Users/jim/Documents/msc/field results/flight.jpg"), this);
                BufferedImage translated = new BufferedImage(unprocessed.getWidth(), unprocessed.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
                Graphics2D translateG = translated.createGraphics();
                double translateX = - (unprocessed.getWidth() * .23);
                double translateY = - (unprocessed.getHeight() * .425);
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
                _image = scaled;

            }

            g.drawImage(_image, 0, 0, this);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void drawGridLines(Graphics2D g, double extentX, double extentY) {
        double cx = extentX / 2;
        double cy = extentY / 2;


        int countx = (int)(extentX / _logicalGridResolution) + 1;
        int countXSide = countx/2; // number of gridlines from centre to edge.
        int minX = (int)(cx-((_logicalGridResolution * 0.5) + (_logicalGridResolution * (countXSide-1))));

        int county = (int)(extentY / _logicalGridResolution) + 1;
        int countYSide = county/2; // number of gridlines from centre to edge.
        int minY = (int)(cy-((_logicalGridResolution * 0.5) + (_logicalGridResolution * (countYSide-1))));


        g.setColor(new Color(200, 200, 200));
        g.setStroke(new BasicStroke(_logicalGridThickness));

        int offsetx = minX;
        int offsety = minY;

        for (int i = 0; i < countx; ++i) {
            Line2D line = new Line2D.Double((_logicalGridResolution * i) + offsetx, 0, (_logicalGridResolution * i) + offsetx, extentY);
            g.draw(line);

        }


        for (int i = 0; i < county; ++i) {
            Line2D line = new Line2D.Double(0, (_logicalGridResolution * i) + offsety, extentX, (_logicalGridResolution * i) + offsety);
            g.draw(line);

        }

    }


    public int getLogicalGridResolution() {
        return _logicalGridResolution;
    }

    public void setLogicalGridResolution(int gridResolution) {
        int oldGridRes = _logicalGridResolution;
        _logicalGridResolution = gridResolution;
        super.firePropertyChange(PROPERTY_GRID_RESOLUTION, oldGridRes, _logicalGridResolution);
    }

    public boolean isGridToScale() {
        return _gridToScale;
    }

    public void setGridToScale(boolean gridToScale) {
        boolean oldGridToScale = _gridToScale;
        _gridToScale = gridToScale;
        super.firePropertyChange(PROPERTY_GRID_TO_SCALE, oldGridToScale, _gridToScale);
    }

    public double getScaleX() {
        return _scaleX;
    }

    public void setScaleX(double scaleX) {
        double oldScaleX = _scaleX;
        _scaleX = scaleX;
        firePropertyChange(PROPERTY_SCALE_X, oldScaleX, _scaleX);
    }

    public double getScaleY() {
        return _scaleY;
    }

    public void setScaleY(double scaleY) {
        double oldScaleY = _scaleY;
        _scaleY = scaleY;
        firePropertyChange(PROPERTY_SCALE_Y, oldScaleY, _scaleY);
    }

    public double getDisplayHeight() {
        return _displayHeight;
    }

    public void setDisplayHeight(double displayHeight) {
        _displayHeight = displayHeight;
    }

    public boolean isShowGrid() {
        return _showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        boolean oldShowGrid = _showGrid;
        _showGrid = showGrid;
        firePropertyChange(PROPERTY_SHOW_GRID, oldShowGrid, _showGrid);
    }

    public void setRenderForPrint(boolean bw) {
        boolean oldBW = _renderForPrint;
        _renderForPrint = bw;
        firePropertyChange(PROPERTY_BLACK_AND_WHITE, oldBW, _renderForPrint);

    }
    public boolean getRenderForPrint() {
        return _renderForPrint;
    }


    public double getTranslateX() {
        return _translateX;
    }

    public void setTranslateX(double translateX) {
        double old = _translateX;
        _translateX = translateX;
        firePropertyChange(PROPERTY_TRANSLATE_X, old, _translateX);
    }

    public double getTranslateY() {
        return _translateY;
    }

    public void setTranslateY(double translateY) {
        double old = _translateY;
        _translateY = translateY;
        firePropertyChange(PROPERTY_TRANSLATE_Y, old, _translateY);
    }

    public Point2D getZoomCenter() {
        return _zoomCenter;
    }

    public void setZoomCenter(Point2D zoomCenter) {
        Point2D oldZoomCenter = _zoomCenter;
        _zoomCenter = zoomCenter;
        firePropertyChange(PROPERTY_ZOOM_CENTER, oldZoomCenter, _zoomCenter);
        invalidate();
        repaint();
    }

    public boolean isFitToScreen() {
        return _fitToScreen;
    }

    public void setFitToScreen(boolean fitToScreen) {
        boolean oldFitToScreen = _fitToScreen;
        _fitToScreen = fitToScreen;
        _scaleX = 1;
        _scaleY = 1;
        _translateX = 0;
        _translateY = 0;

        _zoomCenterActive = !fitToScreen;
        firePropertyChange(PROPERTY_FIT_TO_SCREEN, oldFitToScreen, _fitToScreen);
    }

    public double getZoomPercent() {
        return _zoomPercent;
    }

    public void setZoomPercent(double zoomPercent) {
        double oldZoomPercent = _zoomPercent;
        _zoomPercent = zoomPercent;
        setScaleX(_zoomPercent);
        setScaleY(_zoomPercent);
        firePropertyChange(PROPERTY_ZOOM_PERCENT, oldZoomPercent, _zoomPercent);
    }

    public double getLandscapeClipSizeX() {
        return _landscapeClipSizeX;
    }

    public double getLandscapeClipSizeY() {
        return _landscapeClipSizeY;
    }

    public Point2D.Double getLandscapeOrigin() {
        return _landscapeOrigin;
    }

    public Landscape getLandscape() {
        return _landscape;
    }

    private void setLandscapeClipSizeX(double landscapeClipSizeX) {
        double oldClipSizeX = _landscapeClipSizeX;
        _landscapeClipSizeX = landscapeClipSizeX;
        firePropertyChange(PROPERTY_CLIP_SIZE_X, oldClipSizeX, _landscapeClipSizeX);
    }

    private void setLandscapeClipSizeY(double landscapeClipSizeY) {
        _landscapeClipSizeY = landscapeClipSizeY;
    }

    private void setLandscapeOrigin(Point2D.Double landscapeOrigin) {
        Point2D.Double oldOrigin = _landscapeOrigin;
        _landscapeOrigin = landscapeOrigin;
        firePropertyChange(PROPERTY_LANDSCAPE_ORIGIN, oldOrigin, _landscapeOrigin);
    }


    public boolean isListenToAgents() {
        return _listenToAgents;
    }

    public void setListenToAgents(boolean listenToAgents) {
        _listenToAgents = listenToAgents;
    }

    public AgentTypeChoiceCombo getAgentTypeChoiceCombo() {
        return _agentTypeChoiceCombo;
    }


    private List _listeners = new ArrayList();
    private double _scaleX = 1.0d;
    private double _scaleY = 1.0d;
    private double _rotateTheta = 0;//Math.PI;
    private double _translateX = 0;
    private double _translateY = 0;

    private double _lastWidth;
    private double _lastHeight;

    private Landscape _landscape;
    private boolean _fitToScreen = true;
    private int _logicalGridResolution = 10;
    private boolean _gridToScale = true;
    private boolean _showGrid = true;

    private double _landscapeClipSizeX;
    private double _landscapeClipSizeY;

    private Point2D.Double _landscapeOrigin = new Point2D.Double();


    private boolean _zoomCenterActive = false;

    /**
     * @todo change this to Location
     */
    private Point2D _zoomCenter = new Point2D.Double(0, 0);
    private double _zoomPercent = 1;


    private static final String PROPERTY_SCALE_X = "scaleX";
    private static final String PROPERTY_SCALE_Y = "scaleY";
    private double _displayWidth;
    private double _displayHeight;
    public static final String PROPERTY_SHOW_GRID = "showGrid";
    private LandscapeRenderer _landscapeRenderer = new LandscapeRenderer();
    public static final String PROPERTY_FIT_TO_SCREEN = "fitToScreen";
    public static final String PROPERTY_ZOOM_PERCENT = "zoomPercent";
    public static final String PROPERTY_ZOOM_CENTER = "zoomCenter";
    private final String PROPERTY_CLIP_SIZE_X = "landscapeClipSizeX";
    private final String PROPERTY_LANDSCAPE_ORIGIN = "landscapeOrigin";
    private static final String PROPERTY_TRANSLATE_Y = "translateY";
    private static final String PROPERTY_TRANSLATE_X = "translateX";
    private static final String PROPERTY_BLACK_AND_WHITE = "blackAndWhite";
    private BufferedImage _image;
    private BufferedImage _rawBackgroundImage;
    private BufferedImage _displayImage;
    private LandscapeViewModeStrategeyRegistry _viewModeRegistry;
    private IViewModeStrategy _viewModeStrategy;

    private ScaledDistance _gridResolution;
    private int _logicalGridThickness;
    private ScaledDistance _gridThickness;
    private boolean _listenToAgents;

    private static final Logger log = Logger.getLogger(LandscapeView.class);
    private List _backgroundRenderers = new ArrayList();
    private List _overlayRenderers = new ArrayList();
    private AgentTypeChoiceCombo _agentTypeChoiceCombo;
    private LocationLineRenderer _locationLineRenderer;
    private boolean _renderForPrint = false;
    private boolean _renderGrids = true;
    private ExperimentController _experimentController;
}
