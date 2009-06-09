/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import java.awt.image.BufferedImage;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class IxScaledImage {

    public IxScaledImage(BufferedImage image, double scaleX, double scaleY) {
        _image = image;
        _scaleX = scaleX;
        _scaleY = scaleY;
    }

    public BufferedImage getImage() {
        return _image;
    }

    public double getScaleX() {
        return _scaleX;
    }

    public double getScaleY() {
        return _scaleY;
    }

    private BufferedImage _image;
    private double _scaleX;
    private double _scaleY;
}
