/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.agent;

import com.ixcode.bugsim.agent.cabbage.AttractionFieldAgent;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class RadiusOfAttractionRenderer extends CabbageAgentRenderer {


    public RadiusOfAttractionRenderer() {

    }

    public void render(Graphics2D g, Landscape landscape, IPhysicalAgent agent, RenderContext renderContext) {

        AttractionFieldAgent a = (AttractionFieldAgent)agent;

        double r = a.getRadius();
        double x = agent.getLocation().getDoubleX();
        double y = landscape.getExtentY() - agent.getLocation().getDoubleY();

        Ellipse2D outerCircle = new Ellipse2D.Double(x - r, y - r, r * 2, r * 2);

        int type = AlphaComposite.SRC_OVER;
        AlphaComposite composite = AlphaComposite.getInstance(type, a.getTransparency());
        g.setComposite(composite);


        g.setColor(a.getColor());
        g.setStroke(new BasicStroke(0.5f));
        g.fill(outerCircle);
        g.setColor(Color.DARK_GRAY);
        g.draw(outerCircle);

    }


}
