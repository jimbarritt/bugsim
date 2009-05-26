/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.zoomcontrol;

import com.ixcode.bugsim.view.landscape.LandscapeView;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ZoomGlassPane extends JComponent implements PropertyChangeListener {

    public ZoomGlassPane(LandscapeView mainView, LandscapeView zoomView) {
        _mainView = mainView;
        _zoomView = zoomView;
        _mainView.addPropertyChangeListener(this);
        ZoomGlassPaneDisplayModeStrategy strategy = new ZoomGlassPaneDisplayModeStrategy(this, _mainView, _zoomView);
        super.addMouseListener(strategy);
        super.addMouseMotionListener(strategy);

    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D)graphics;

        Point2D.Double origin = _mainView.getLandscapeOrigin();
        double x = _zoomView.getScreenDistanceX(origin.getX());
        double y = _zoomView.getScreenDistanceY((_mainView.getLandscape().getExtentY() - origin.getY()) - _mainView.getLandscapeClipSizeY()  );
        double width = _zoomView.getScreenDistanceX(_mainView.getLandscapeClipSizeX());
        double height = _zoomView.getScreenDistanceY(_mainView.getLandscapeClipSizeY());
        _rectangle = new Rectangle.Double(x, y, width, height);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.red);
        g.setStroke(new BasicStroke(3));

//        System.out.println("Origin: " + origin + ", rectangle: " + rectangle);
        g.draw(_rectangle);

    }

    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals(LandscapeView.PROPERTY_GRID_RESOLUTION)) {
            _zoomView.setLogicalGridResolution(_mainView.getLogicalGridResolution());
        }
//        System.out.println("PropertyChanged!:" + evt);
        redraw();

    }

    private void redraw() {
        super.invalidate();
        super.repaint();
    }

    public Rectangle2D.Double getZoomRectangle() {
        return _rectangle;
    }


    private LandscapeView _mainView;
    private LandscapeView _zoomView;
    private Rectangle2D.Double _rectangle;
}
