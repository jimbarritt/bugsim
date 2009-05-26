/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.math.geometry.RectangularCoordinate;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LocationLineRenderer extends LandscapeRendererBase implements MouseMotionListener {

    public LocationLineRenderer(LandscapeView view) {
        _view = view;
    }

    public void mouseDragged(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseMoved(MouseEvent e) {
        _currentPoint = e.getPoint();
//        _view.redraw();
    }


    public void render(Graphics2D g, LandscapeView view) {
        if (_currentPoint != null) {

            RectangularCoordinate landscapePoint = super.getScreenCoord(_view, view.getLandscapeLocation(_currentPoint).getCoordinate());

            Rectangle r = view.getBounds();
            Line2D.Double xLine = new Line2D.Double(landscapePoint.getDoubleX(), 0, landscapePoint.getDoubleX(), r.getHeight());
            Line2D.Double yLine = new Line2D.Double(0, landscapePoint.getDoubleY(), r.getWidth(), landscapePoint.getDoubleY());

            g.setColor(Color.blue);
            g.setStroke(new BasicStroke(.1f));
            g.draw(xLine);
            g.draw(yLine);
        }
    }

    public boolean isVisible() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setVisible(boolean isVisible) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private Point _currentPoint;
    private LandscapeView _view;
}
