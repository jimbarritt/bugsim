/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public abstract class LandscapeRendererBase implements ILandscapeRenderer {

    protected Shape createRectangleFromBounds(CartesianBounds b, LandscapeView view) {
        RectangularCoordinate topLeft = getScreenCoord(view, new RectangularCoordinate(b.getDoubleX(), b.getDoubleY() + b.getDoubleHeight()));

        return new Rectangle2D.Double(topLeft.getDoubleX(), topLeft.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight());

    }

    protected Ellipse2D.Double createEllipseFromBounds(CartesianBounds b, LandscapeView view) {


        RectangularCoordinate topLeft = getScreenCoord(view, new RectangularCoordinate(b.getDoubleX(), b.getDoubleY() + b.getDoubleHeight()));

        return new Ellipse2D.Double(topLeft.getDoubleX(), topLeft.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight());

    }

    protected RectangularCoordinate getScreenCoord(LandscapeView view, RectangularCoordinate coord) {
        return LandscapeView.getScreenCoord(view.getLandscape(), coord);
    }


    public boolean isVisible() {
        return _visible;
    }

    public void setVisible(boolean isVisible) {
        _visible = isVisible;
    }


    private boolean _visible = true;
}