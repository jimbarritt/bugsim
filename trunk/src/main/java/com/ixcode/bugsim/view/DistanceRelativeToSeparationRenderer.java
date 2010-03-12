/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view;

import com.ixcode.bugsim.view.landscape.*;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.*;
import java.text.DecimalFormat;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DistanceRelativeToSeparationRenderer extends LandscapeRenderer {
    private boolean _visible = true;

    public DistanceRelativeToSeparationRenderer(RectangularCoordinate centre, double s, double r) {
        _centre = centre;
        _s = s;
        _r = r;
    }

    public void render(LandscapeView view, Graphics2D graphics2D) {
        CartesianBounds centreBounds = new CartesianBounds(_centre.getDoubleX()-_r , _centre.getDoubleY()-_r, 2*_r , 2*_r);
        Ellipse2D.Double centreCabbage = super.createEllipseFromBounds(centreBounds, view);

        RectangularCoordinate tlc = new RectangularCoordinate(_centre.getDoubleX() - (2*_r + _s), _centre.getDoubleY()+ (2*_r+ _s));
        CartesianBounds tlb = new CartesianBounds(tlc.getDoubleX()-_r , tlc.getDoubleY()-_r, 2*_r , 2*_r);
        Ellipse2D.Double topLeftCabbage = super.createEllipseFromBounds(tlb, view);

        graphics2D.setColor(Color.red);
        graphics2D.fill(centreCabbage);
        graphics2D.setColor(Color.blue);
        graphics2D.fill(topLeftCabbage);

        if (log.isInfoEnabled()) {
            double dc = _centre.calculateDistanceTo(tlc);
            log.info("TopLeft: " + F.format(tlc));
            log.info("centre : " + _centre);
            log.info("Distance between centres : " + F.format(dc));
            log.info("Distance between edges   : " + F.format(dc-(2*_r)));
        }


    }

    DecimalFormat F = DoubleMath.DECIMAL_FORMAT;
    private RectangularCoordinate _centre;
    private double _s;
    private double _r;
    private static final Logger log = Logger.getLogger(DistanceRelativeToSeparationRenderer.class);

    protected Shape createRectangleFromBounds(CartesianBounds b, LandscapeView view) {
        RectangularCoordinate topLeft = getScreenCoord(view, new RectangularCoordinate(b.getDoubleX(), b.getDoubleY() + b.getDoubleHeight()));

        return new Rectangle2D.Double(topLeft.getDoubleX(), topLeft.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight());

    }

    protected Ellipse2D.Double createEllipseFromBounds(CartesianBounds b, LandscapeView view) {
        RectangularCoordinate topLeft = getScreenCoord(view, new RectangularCoordinate(b.getDoubleX(), b.getDoubleY() + b.getDoubleHeight()));
        return new Ellipse2D.Double(topLeft.getDoubleX(), topLeft.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight());

    }

    protected RectangularCoordinate getScreenCoord(LandscapeView view, RectangularCoordinate coord) {
        return LandscapeToViewTranslation.getScreenCoord(view.getLandscape(), coord);
    }

    public boolean isVisible() {
        return _visible;
    }

    public void setVisible(boolean isVisible) {
        _visible = isVisible;
    }
}
