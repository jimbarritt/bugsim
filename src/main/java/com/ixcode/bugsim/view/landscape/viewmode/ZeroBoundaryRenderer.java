/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.bugsim.view.landscape.*;
import com.ixcode.framework.math.geometry.*;

import java.awt.*;
import java.awt.geom.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ZeroBoundaryRenderer implements LandscapeLayer {
    private boolean _visible = true;

    public void render(LandscapeView view, Graphics2D graphics2D) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected Shape createRectangleFromBounds(CartesianBounds b, LandscapeView view) {
        RectangularCoordinate topLeft = getScreenCoord(view, new RectangularCoordinate(b.getDoubleX(), b.getDoubleY() + b.getDoubleHeight()));

        return new Rectangle2D.Double(topLeft.getDoubleX(), topLeft.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight());

    }

    protected Ellipse2D.Double createEllipseFromBounds(CartesianBounds b, LandscapeView view) {
        RectangularCoordinate topLeft = getScreenCoord(view, new RectangularCoordinate(b.getDoubleX(), b.getDoubleY() + b.getDoubleHeight()));
        return new Ellipse2D.Double(topLeft.getDoubleX(), topLeft.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight());

    }

    protected RectangularCoordinate getScreenCoord(LandscapeView view, RectangularCoordinate coord) {
        return LandscapeToViewModel.getScreenCoord(view.getLandscape(), coord);
    }

    public boolean isVisible() {
        return _visible;
    }

    public void setVisible(boolean isVisible) {
        _visible = isVisible;
    }
}
