/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.agent;

import com.ixcode.bugsim.model.agent.boundary.LinearBoundaryAgent;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.boundary.LinearBoundary;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LinearBoundaryAgentRenderer extends PhysicalAgentRendererBase {

    private static final Integer LAYER_ONE = new Integer(1);

    public Integer getLayer() {
        return LAYER_ONE;
    }

    public void render(Graphics2D g, Landscape landscape, IPhysicalAgent agent, RenderContext renderContext) {
        LinearBoundaryAgent boundaryAgent = (LinearBoundaryAgent)agent;
        LinearBoundary boundary = boundaryAgent.getLinearBoundary();


        RectangularCoordinate start = super.getRenderCoordinate(boundary.getStartLocation(), landscape);
        RectangularCoordinate end = super.getRenderCoordinate(boundary.getEndLocation(), landscape);

        Line2D.Double line = new Line2D.Double(start.getDoubleX(), start.getDoubleY(), end.getDoubleX(), end.getDoubleY());

        g.setStroke(new BasicStroke(.1f));
        g.setColor(Color.darkGray);
        g.draw(line);


        super.drawFilledSquare(g, start, 2, Color.darkGray);
        super.drawFilledSquare(g, end, 2, Color.darkGray);


    }




}
