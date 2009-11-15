/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.agent;

import com.ixcode.bugsim.model.agent.boundary.CircularBoundaryAgent;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CircularBoundaryAgentRenderer extends PhysicalAgentRendererBase {

    private static final Integer LAYER_ONE = new Integer(1);

    public Integer getLayer() {
        return CircularBoundaryAgentRenderer.LAYER_ONE;
    }

    public void render(Graphics2D g, Landscape landscape, IPhysicalAgent agent, RenderContext renderContext) {
        CircularBoundaryAgent boundaryAgent = (CircularBoundaryAgent)agent;

        RectangularCoordinate centre = getRenderCoordinate(boundaryAgent.getLocation().getCoordinate(), landscape);

        double r= boundaryAgent.getCircularBoundary().getRadius();


        double x = centre.getDoubleX() - r;
        double y = centre.getDoubleY() - r;

        Ellipse2D.Double boundaryCircle = new Ellipse2D.Double(x, y, r*2, r*2);

        g.setStroke(new BasicStroke(.1f));
        g.setColor(Color.darkGray);
        g.draw(boundaryCircle);


        super.drawFilledCircle(g, centre, 1, Color.darkGray);


    }




}
