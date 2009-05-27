/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import com.ixcode.framework.resource.IxInvalidResourceException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class IxImageManipulation {
    public static BufferedImage getBufferedImage(File file, Component c) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        if (!file.exists()) {
            throw new IllegalArgumentException("File " + file + " does not exist!");
        }
        Image image = c.getToolkit().getImage(file.getAbsolutePath());
        waitForImage(image, c);
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(c), image.getHeight(c), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, c);
        return (bufferedImage);
    }

    public static boolean waitForImage(Image image, Component c) {
        MediaTracker tracker = new MediaTracker(c);
        tracker.addImage(image, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException ie) {
        }
        return (!tracker.isErrorAny());
    }

    /**
     * ScaleX = ScaledWidth / ImageWidth
     * ScaleY = ScaledHeight / ImageHeight
     *
     * @param rawImage
     * @param imageObserver
     * @return scaled image
     */
    public static IxScaledImage scaleImageToSize(BufferedImage rawImage, double scaledWidth, double scaledHeight, ImageObserver imageObserver) {
        double scx = scaledWidth / rawImage.getWidth();
        double scy = scaledHeight / rawImage.getHeight();
//        System.out.println("scaling scaledWidth=" + scaledWidth + ", scaledHeight=" + scaledHeight + ", scx=" + scx + ", scy=" + scy);
        return new IxScaledImage(scaleImage(rawImage,scaledWidth, scaledHeight, scx, scy, imageObserver), scx, scy);

    }

    public static BufferedImage scaleImage(BufferedImage rawImage, double scaledWith, double scaledHeight, double scx, double scy, ImageObserver imageObserver) {
        BufferedImage scaled = new BufferedImage((int)scaledWith, (int)scaledHeight, BufferedImage.TYPE_BYTE_INDEXED);
        Graphics2D scaleG = scaled.createGraphics();
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scx, scy);
        scaleG.drawImage(rawImage, scaleTransform, imageObserver);
        return scaled;
    }

    public static ImageIcon createImageIcon(String iconResourcePath) {
        URL url = ImageIcon.class.getResource(iconResourcePath);
        if (url == null) {
            throw new IxInvalidResourceException(iconResourcePath, url);
        }

        return new ImageIcon(url);
    }

    public static BufferedImage flipVertical(BufferedImage rawImage, ImageObserver imageObserver) {
        return rotate(rawImage, Math.PI, imageObserver);
    }

    public static BufferedImage rotate(BufferedImage rawImage, double radians, ImageObserver imageObserver) {
        BufferedImage rotated = new BufferedImage(rawImage.getWidth(), rawImage.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
        Graphics2D rotateG = rotated.createGraphics();
        double x = rawImage.getWidth() / 2;
        double y = rawImage.getHeight() / 2;
        AffineTransform rotateTransform = AffineTransform.getRotateInstance(radians, x, y);
        rotateG.drawImage(rawImage, rotateTransform, imageObserver);

        return rotated;
    }

    public static BufferedImage crop(BufferedImage rawImage, Rectangle2D cropBounds) {
//        System.out.println("Returning Sub image for x=" + cropBounds.getMinX() + ", " + cropBounds.getMinY() + ", " + ", " + cropBounds.getDoubleWidth() + "," + cropBounds.getDoubleHeight());
        return rawImage.getSubimage((int)cropBounds.getMinX(), (int)cropBounds.getMinY(), (int)cropBounds.getWidth(), (int)cropBounds.getHeight());
    }

    public static RenderingHints createRenderingHints() {
        HashMap hm = new HashMap();
        hm.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        hm.put(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        hm.put(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        hm.put(RenderingHints.KEY_DITHERING,RenderingHints.VALUE_DITHER_ENABLE);
        hm.put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        hm.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        hm.put(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        hm.put(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        hm.put(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);
        return new RenderingHints(hm);
    }

    public static void saveImageToFile(BufferedImage rawImage, File file) {
        if (file.exists()) {
            throw new IllegalArgumentException("File '" + file.getAbsolutePath() + "' already exists!");
        }
         try {
             FileOutputStream out = new FileOutputStream(file);

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(rawImage);
            param.setQuality(1.0f, false);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(rawImage);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static final RenderingHints RENDERING_HINTS = createRenderingHints();


}
