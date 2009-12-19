/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.agent;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.diagnostic.LineAgent;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Iterator;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LineAgentRenderer extends PhysicalAgentRendererBase {

    public void render(Graphics2D g, Landscape landscape, IPhysicalAgent agent, RenderContext renderContext) {
        LineAgent lineAgent = (LineAgent)agent;

        RectangularCoordinate startPoint = super.getRenderCoordinate(lineAgent.getLocation().getCoordinate(), landscape);
        RectangularCoordinate endPoint = super.getRenderCoordinate(lineAgent.getEndLocation().getCoordinate(), landscape);

        g.setStroke(new BasicStroke(1f));
        g.setColor(Color.BLUE);
        drawEndCircle(startPoint, g);
        drawEndCircle(endPoint, g);

        Line2D.Double line = new Line2D.Double(startPoint.getDoubleX(), startPoint.getDoubleY(), endPoint.getDoubleX(), endPoint.getDoubleY());
        g.setColor(Color.DARK_GRAY);
        g.setStroke(new BasicStroke(2f));

        g.draw(line);

        for (Iterator itr = lineAgent.getPoints().iterator(); itr.hasNext();) {
            RectangularCoordinate point = (RectangularCoordinate)itr.next();
            drawEndCircle(super.getRenderCoordinate(point, landscape), g, true);
        }
    }

    private void drawEndCircle(RectangularCoordinate point, Graphics2D g) {
        drawEndCircle(point, g, false);
    }

    private void drawEndCircle(RectangularCoordinate point, Graphics2D g, boolean fill) {
        double x = point.getDoubleX();
        double y = point.getDoubleY();
        double r= .5;

        g.setStroke(new BasicStroke(.1f));
        Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, r*2, r*2) ;
        g.draw(circle);

        g.setColor(Color.gray);
        g.fill(circle);
    }
}
