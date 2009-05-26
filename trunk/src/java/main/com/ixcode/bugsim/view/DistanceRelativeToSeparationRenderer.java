/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view;

import com.ixcode.bugsim.view.landscape.LandscapeRendererBase;
import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DistanceRelativeToSeparationRenderer extends LandscapeRendererBase  {

    public DistanceRelativeToSeparationRenderer(RectangularCoordinate centre, double s, double r) {
        _centre = centre;
        _s = s;
        _r = r;
    }

    public void render(Graphics2D g, LandscapeView view) {
        CartesianBounds centreBounds = new CartesianBounds(_centre.getDoubleX()-_r , _centre.getDoubleY()-_r, 2*_r , 2*_r);
        Ellipse2D.Double centreCabbage = super.createEllipseFromBounds(centreBounds, view);

        RectangularCoordinate tlc = new RectangularCoordinate(_centre.getDoubleX() - (2*_r + _s), _centre.getDoubleY()+ (2*_r+ _s));
        CartesianBounds tlb = new CartesianBounds(tlc.getDoubleX()-_r , tlc.getDoubleY()-_r, 2*_r , 2*_r);
        Ellipse2D.Double topLeftCabbage = super.createEllipseFromBounds(tlb, view);

        g.setColor(Color.red);
        g.fill(centreCabbage);
        g.setColor(Color.blue);
        g.fill(topLeftCabbage);

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
}
