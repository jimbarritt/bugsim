/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.agent;

import com.ixcode.bugsim.agent.boundary.BoundaryAgentIntersection;
import com.ixcode.bugsim.agent.matchstick.MatchstickAgent;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class MatchstickAgentRenderer extends PhysicalAgentRendererBase {


    public void render(Graphics2D g, Landscape landscape, IPhysicalAgent agent, RenderContext renderContext) {
        MatchstickAgent matchstick = (MatchstickAgent)agent;

       


        RectangularCoordinate start = super.getRenderCoordinate(matchstick.getStartCoord(), landscape);
        RectangularCoordinate end = super.getRenderCoordinate(matchstick.getEndCoord(), landscape);

        Line2D.Double line = new Line2D.Double(start.getDoubleX(), start.getDoubleY(), end.getDoubleX(), end.getDoubleY());

        g.setStroke(new BasicStroke(.2f));
        g.setColor(Color.blue);
        g.draw(line);

        super.drawFilledCircle(g, start, Color.blue, Color.blue, .5);
        super.drawFilledCircle(g, end, Color.blue, Color.blue, .5);
        super.drawFilledCircle(g, super.getRenderCoordinate(matchstick.getLocation().getCoordinate(), landscape), Color.green, Color.green, 1);

        drawIntersections(g, matchstick, landscape);



    }

    /**
     * @todo move this to an overlaid component on top of everything
     * @param g
     * @param matchstick
     * @param landscape
     */
    private void drawIntersections(Graphics2D g, MatchstickAgent matchstick, Landscape landscape) {
        List intersections = matchstick.getIntersections();



        for (Iterator itr = intersections.iterator(); itr.hasNext();) {
            BoundaryAgentIntersection ix = (BoundaryAgentIntersection)itr.next();
            super.drawFilledCircle(g, super.getRenderCoordinate(ix.getCoord(), landscape), Color.red, Color.red, 0.25);
        }
    }


}
