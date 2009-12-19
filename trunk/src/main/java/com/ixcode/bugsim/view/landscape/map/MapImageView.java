/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.map;

import com.ixcode.framework.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Description : Deals with everything to do with manipulating an image to allow us to use it as a map.
 */
public class MapImageView extends JComponent {

    public MapImageView() {
        _viewModeRegistry = new MapImageViewModeStrategeyRegistry(this);
        setViewMode(ViewMode.DISPLAY);

    }


    protected void paintComponent(Graphics graphics) {
        if (_rawImage == null) {
            return;
        }

        Graphics2D g = (Graphics2D)graphics;
        Rectangle preScaleBounds = g.getClipBounds();


        if (isFitToScreen()) {
            IxScaledImage scImage = IxImageManipulation.scaleImageToSize(_rawImage, preScaleBounds.getWidth(), preScaleBounds.getHeight(), this);
            _displayImage = scImage.getImage();
            setScaleX(scImage.getScaleX());
            setScaleY(scImage.getScaleY());
        }

        if (_displayImage != null) {
            g.drawImage(_displayImage, 0, 0, this);
        }


        if (_mapOutline != null) {
            _mapOutline.render(g, getScaleX(), getScaleY());
        }


    }

    private void paintImage(Graphics2D g) throws FileNotFoundException {
        if (_displayImage == null) {
            BufferedImage unprocessed = IxImageManipulation.getBufferedImage(new File("/Users/jim/Documents/msc/field results/flight.jpg"), this);
            BufferedImage translated = new BufferedImage(unprocessed.getWidth(), unprocessed.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
            Graphics2D translateG = translated.createGraphics();
            double translateX = - (unprocessed.getWidth() * .23);
            double translateY = - (unprocessed.getHeight() * .425);
            AffineTransform translateTransform = AffineTransform.getTranslateInstance(translateX, translateY);
            translateG.drawImage(unprocessed, translateTransform, this);
//            System.out.println("Translated : x=" + translateX + ", y=" + translateY);

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
            _displayImage = scaled;

        }

        g.drawImage(_displayImage, 0, 0, this);
    }

    public void loadImageFromFileName(File file) throws IOException {
        _rawImage = IxImageManipulation.getBufferedImage(file, this);
        redraw();
    }

    public boolean isFitToScreen() {
        return _fitToScreen;
    }

    public void setFitToScreen(boolean fitToScreen) {
        _fitToScreen = fitToScreen;
    }


    public static void main(String[] args) {
        MapImageView map = new MapImageView();
//            map.loadImageFromFileName("/Users/jim/Documents/msc/field results/flight.jpg");

        MapImageToolbar toolbar = new MapImageToolbar(map);

        JPanel content = new JPanel(new BorderLayout());
        content.add(toolbar, BorderLayout.NORTH);
        content.add(map, BorderLayout.CENTER);

        JFrameExtension testFrame = new JFrameExtension("ScaledDistance Image Map", content, false);
        testFrame.setSize(500, 700);


        testFrame.show();

    }

    public boolean isMode(ViewMode mode) {
        return _viewModeStrategy.getViewMode() == mode;
    }

    public void setViewMode(ViewMode mode) {
        if (_viewModeStrategy != null) {
            this.removeMouseListener(_viewModeStrategy.getMouseListener());
            this.removeMouseMotionListener(_viewModeStrategy.getMouseMotionListener());
        }

        _viewModeStrategy = _viewModeRegistry.getStrategy(mode);

        this.setCursor(_viewModeStrategy.getCursor());
        this.addMouseListener(_viewModeStrategy.getMouseListener());
        this.addMouseMotionListener(_viewModeStrategy.getMouseMotionListener());


        invalidate();
        repaint();

    }

    public void resetMapOutline() {
        _mapOutline = new MapOutline();
    }

    public MapOutline getMapOutline() {
        return _mapOutline;
    }

    public void flipRawImageVertical() {
        _rawImage = IxImageManipulation.flipVertical(_rawImage, this);
        redraw();
    }

    public void rotateRawImage(double radians) {
        _rawImage = IxImageManipulation.rotate(_rawImage, radians, this);
        redraw();
    }

    public void cropRawImage(Rectangle2D cropBounds) {
        _rawImage = IxImageManipulation.crop(_rawImage, cropBounds);
        redraw();
    }


    public double getScaleY() {
        return _scaleY;
    }

    public void setScaleY(double scaleY) {
        _scaleY = scaleY;
    }

    public double getScaleX() {
        return _scaleX;
    }

    public void setScaleX(double scaleX) {
        _scaleX = scaleX;
    }

    public void redraw() {
        invalidate();
        repaint();
    }

    public void rotateMap(double radians) {
        rotateRawImage(radians);
        _mapOutline.rotate(radians, _rawImage.getWidth() / 2, _rawImage.getHeight() / 2);
    }

    public void saveRawImageToFile(File file) {

        IxImageManipulation.saveImageToFile(_rawImage, file);

    }


    private double _scaleX;
    private double _scaleY;
    private BufferedImage _rawImage;
    private BufferedImage _displayImage;
    private File _imageFile;
    private boolean _fitToScreen = true;

    private ViewModeStrategyRegistry _viewModeRegistry;
    private ViewModeStrategy _viewModeStrategy;
    private MapOutline _mapOutline;

}
