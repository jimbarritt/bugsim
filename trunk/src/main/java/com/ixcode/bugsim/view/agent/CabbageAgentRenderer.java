/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.agent;

import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.math.geometry.RectangularCoordinate;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CabbageAgentRenderer extends PhysicalAgentRendererBase {


    public void render(Graphics2D g, Landscape landscape, IPhysicalAgent agent, RenderContext renderContext) {
        CabbageAgent cabbage = (CabbageAgent)agent;

        double r = cabbage.getRadiusDouble();

        double scaleX = renderContext.getView().getLandscapeClipSizeX();
        float radiusMagnification = 1f;

        if (scaleX <=2000) {
            radiusMagnification = 1f;
        } else if (scaleX <=5000) {
            radiusMagnification =2f;
        } else if (scaleX <= 10000) {
            radiusMagnification =10f;
        } else {
            radiusMagnification =20f;
        }
        r = r * radiusMagnification;

        double x = agent.getLocation().getDoubleX();
        double y = landscape.getExtentY() - agent.getLocation().getDoubleY();

        Ellipse2D outerCircle = new Ellipse2D.Double(x-r, y-r, r*2, r*2) ;
        double r2 = r - (r*.04);
        Ellipse2D innerCircle = new Ellipse2D.Double(x-r2, y-r2, r2*2, r2*2) ;

        if (renderContext.isRenderForPrint()) {
            g.setColor(Color.darkGray);
            g.fill(outerCircle);
            g.setColor(Color.lightGray);
            g.fill(innerCircle);
        }  else {
                g.setColor(new Color(0, 102, 0));
                g.fill(outerCircle);


            if (cabbage.getEggCount() > 0) {
                int yellow = (int)(cabbage.getEggCount()*10);
                yellow = (yellow>254) ? 255 : yellow;
                g.setColor(new Color(yellow, 255,  0));

            } else {
                g.setColor(new Color(51, 153, 102));
            }
            g.fill(innerCircle);
        }

        super.drawFilledCircle(g, new RectangularCoordinate(x, y), Color.darkGray, Color.darkGray, 2);

//        g.draw(outerCircle);

//        g.setStroke(new BasicStroke(1));
//        g.setColor(Color.green);
//        Rectangle2D bounds = outerCircle.getBounds2D();
//        g.draw(bounds);
    }
}
