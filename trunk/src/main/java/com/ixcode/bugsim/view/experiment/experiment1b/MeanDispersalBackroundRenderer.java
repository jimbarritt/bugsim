/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.experiment1b;

import com.ixcode.bugsim.view.landscape.LandscapeRendererBase;
import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.framework.math.geometry.CartesianBounds;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class MeanDispersalBackroundRenderer extends LandscapeRendererBase {

    public void render(Graphics2D g, LandscapeView view) {
        CartesianBounds b = view.getLandscape().getLogicalBounds();
        CartesianBounds cb = b.centre(new CartesianBounds(0, 0, 20, 20));

        Ellipse2D.Double centreCircle = super.createEllipseFromBounds(cb, view);

        g.setStroke(new BasicStroke(0.5f));

        g.setColor(Color.blue);
        g.draw(centreCircle);
    }



    public boolean isVisible() {
        return true;
    }

    public void setVisible(boolean isVisible) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
