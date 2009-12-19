/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.agent;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.landscape.Landscape;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public abstract class PhysicalAgentRendererBase implements IAgentRenderer {

    private static Integer ZERO = new Integer(0);

    public Integer getLayer() {
        return ZERO;

    }

    /**
     * Because the screen is upside down (i.e. 0, 0, is top left instead of bottom left) we have to adjust the y
     *
     * @param agentCoordinate
     * @return
     */
    public RectangularCoordinate getRenderCoordinate(RectangularCoordinate agentCoordinate, Landscape landscape) {
        double x = agentCoordinate.getDoubleX();
        double y = landscape.getExtentY() - agentCoordinate.getDoubleY();
        return new RectangularCoordinate(x, y);
    }

    public void drawFilledCircle(Graphics2D g, RectangularCoordinate center, double size, Color fillColor) {
        drawFilledCircle(g, center, fillColor, fillColor, size);

    }
    protected void drawFilledCircle(Graphics2D g, RectangularCoordinate center, Color fillColor, Color borderColor, double r) {
        double x = center.getDoubleX();
        double y = center.getDoubleY();


        Ellipse2D circle = new Ellipse2D.Double(x - r, y - r, r * 2, r * 2);
//        g.setColor(borderColor);
//        g.draw(circle);

        g.setColor(fillColor);
        g.fill(circle);
    }

    protected void drawCircle(Graphics2D g, RectangularCoordinate center, Color borderColor, double r) {
        double x = center.getDoubleX();
        double y = center.getDoubleY();


        Ellipse2D circle = new Ellipse2D.Double(x - r, y - r, r * 2, r * 2);
        g.setColor(borderColor);
        g.draw(circle);


    }

    protected void drawFilledCircle(Graphics2D g, Point2D center, Color fillColor, double r) {
        double x = center.getX();
        double y = center.getY();

        Ellipse2D circle = new Ellipse2D.Double(x - r, y - r, r * 2, r * 2);

        g.setColor(fillColor);
        g.fill(circle);
    }

    public void drawFilledSquare(Graphics2D g, RectangularCoordinate center, double size, Color fillColor) {
        double x = center.getDoubleX();
        double y = center.getDoubleY();

        Rectangle2D square = new Rectangle2D.Double(x - size, y - size, size * 2, size * 2);

        g.setColor(fillColor);
        g.fill(square);

    }

    protected Point2D calculatePointAtTheta(double theta, double r, Graphics2D g, double x, double y) {
        double thetaRadians = Math.toRadians(theta);
        double xIncr = Math.sin(thetaRadians) * r;
        double yIncr = Math.cos(thetaRadians) * r;


        g.setColor(new Color(200, 200, 200));
        g.setStroke(new BasicStroke(1));
        Point2D endPoint = new Point2D.Double(x + xIncr, y + yIncr);
        return endPoint;
    }

}
