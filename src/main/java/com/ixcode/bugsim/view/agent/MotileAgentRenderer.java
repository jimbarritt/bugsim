/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.agent;

import com.ixcode.bugsim.agent.butterfly.FieldOfViewVisualStrategy;
import com.ixcode.bugsim.agent.butterfly.IForagingStrategy;
import com.ixcode.bugsim.agent.butterfly.EggLayingForagingStrategy;
import com.ixcode.bugsim.view.landscape.*;
import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.ICoordinatePath;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.motile.IVisualAgent;
import com.ixcode.framework.simulation.model.agent.motile.MotileAgentBase;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.SensoryRandomWalkStrategy;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.*;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class MotileAgentRenderer extends PhysicalAgentRendererBase {

    public void render(Graphics2D g, Landscape landscape, IPhysicalAgent agent, RenderContext renderContext) {

        MotileAgentBase bug = (MotileAgentBase)agent;
        double r = bug.getRadius();

        RectangularCoordinate c = LandscapeToViewModel.getScreenCoord(landscape, agent.getLocation().getCoordinate());

        String contextId = "MotileAgentRenderer." + agent.getId();


        if (_drawPaths) {
            drawLocationPath(contextId, g, bug.getLocationPathAsCoordinatePath(), landscape, renderContext);
            drawBoundaryIntersections(g, bug.getBoundaryIntersections(), landscape, renderContext);
        }


        drawBody(c.getDoubleX(), r, c.getDoubleY(), g, bug, renderContext);


    }


    protected void drawBody(double x, double r, double y, Graphics2D g, IMotileAgent agent, RenderContext renderContext) {
        Ellipse2D outerCircle = new Ellipse2D.Double(x - r, y - r, r * 2, r * 2);

        double r2 = r - (r * .2);
        Ellipse2D innerCircle = new Ellipse2D.Double(x - r2, y - r2, r2 * 2, r2 * 2);


        boolean drawSensors = true;
        IMovementStrategy movement = agent.getMovementStrategy();
        if (movement instanceof SensoryRandomWalkStrategy) {
            SensoryRandomWalkStrategy srws = (SensoryRandomWalkStrategy)movement;
            drawSensors = srws.isVisionEnabled();
        }
        if (drawSensors && agent instanceof IVisualAgent) {
            IVisualAgent visualA = (IVisualAgent)agent;
            drawFieldOfVision(g, visualA, r2, x, y, renderContext.getView().getLandscape());
        }

        IForagingStrategy foraging = agent.getForagingStrategy();
        if (foraging instanceof EggLayingForagingStrategy) {
            EggLayingForagingStrategy eggLaying = (EggLayingForagingStrategy)foraging;
            if (!renderContext.isRenderForPrint()) {
                highlightPotentialIntersections(g, eggLaying.getPotentialResources(), renderContext.getView().getLandscape(), renderContext.getView());
            }
        }

//        g.setStroke(new BasicStroke(.5f));

        g.setColor(getBodyOutlineColor(renderContext));
        g.fill(outerCircle);


        g.setColor(getBodyColor(renderContext));
        g.fill(innerCircle);

        Point2D point1 = calculatePointAtTheta(180 - agent.getAzimuth(), r2, g, x, y);
        Point2D point2 = calculatePointAtTheta(45 - agent.getAzimuth(), r2, g, x, y);
        Point2D point3 = calculatePointAtTheta(315 - agent.getAzimuth(), r2, g, x, y);

        GeneralPath triangle = new GeneralPath();
        triangle.moveTo((float)point1.getX(), (float)point1.getY());
        triangle.lineTo((float)point2.getX(), (float)point2.getY());
        triangle.lineTo((float)point3.getX(), (float)point3.getY());
        triangle.lineTo((float)point1.getX(), (float)point1.getY());


        g.setColor(getDirectionTriangleColor(renderContext));
        g.fill(triangle);

        // Need to take 180 - heading because everything is upside down in the drawing coordinate space...
        drawDirectionLine(180 - agent.getAzimuth(), r2, g, x, y, renderContext);

        drawNextMoveRadius(agent, g, renderContext.getView().getLandscape());

    }


    private void drawNextMoveRadius(IMotileAgent agent, Graphics2D g, Landscape landscape) {
        IMovementStrategy movement = agent.getMovementStrategy();
        if (movement instanceof SensoryRandomWalkStrategy) {
            SensoryRandomWalkStrategy srw = (SensoryRandomWalkStrategy)movement;
            double movelength = srw.getMoveLengthGenerator().getMoveLength();
            RectangularCoordinate screenLocation = super.getRenderCoordinate(agent.getLocation().getCoordinate(), landscape);
            super.drawCircle(g, screenLocation, Color.blue, movelength);
        }
    }

    private void drawFieldOfVision(Graphics2D g, IVisualAgent visualAgent, double radius, double x, double y, Landscape landscape) {


        Point2D point1 = calculatePointAtTheta(180 - visualAgent.getAzimuth(), radius, g, x, y);

        if (!(visualAgent.getVisionStrategy() instanceof FieldOfViewVisualStrategy)) {
            throw new IllegalStateException("Cannot render a vision strategy of type: " + visualAgent.getVisionStrategy().getClass().getName());
        }
        FieldOfViewVisualStrategy strategy = (FieldOfViewVisualStrategy)visualAgent.getVisionStrategy();
        double range = strategy.getVisualFieldDepth();
        double r2 = range / 2;
        Composite before = g.getComposite();
        int type = AlphaComposite.SRC_OVER;
        AlphaComposite composite = AlphaComposite.getInstance(type, .25f);
        g.setComposite(composite);

        double fieldWidth = strategy.getVisualFieldWidth();
        double fieldDepth = strategy.getVisualFieldDepth();
        double fd2 = fieldDepth * 2;
        double fw2 = fieldWidth / 2;
        double azimuthEnd = AzimuthCoordinate.applyAzimuthChange(visualAgent.getAzimuth(), fw2);
        double end = AzimuthCoordinate.convertAzimuthToPolarAngle(azimuthEnd);

//            if (log.isInfoEnabled()) {
//                log.info("AgentHeading: " + visualAgent.getHeading() + ", azimuthEnd: " + azimuthEnd +  ", polarEnd: " + end+ ", fieldWidth: " + fieldWidth);
//            }
        Arc2D pie = new Arc2D.Double(x - fieldDepth, y - fieldDepth, fd2, fd2, end, fieldWidth, Arc2D.PIE);
        g.setColor(Color.blue);
        g.fill(pie);
        g.setComposite(before);
        //        Rectangle2D border = new Rectangle2D.Double(x - fd2, y - fd2, fieldDepth, fieldDepth);
        //        g.setColor(Color.black);
        //        g.draw(border);


        highlightAgentsInFieldOfView(g, strategy.getVisibleAgents(), landscape);
    }

    private void highlightPotentialIntersections(Graphics2D g, Collection potentialIntersections, Landscape landscape, LandscapeView landscapeView) {
        for (Iterator itr = potentialIntersections.iterator(); itr.hasNext();) {
            IPhysicalAgent agent = (IPhysicalAgent)itr.next();
            RectangularCoordinate screenCoord = super.getRenderCoordinate(agent.getLocation().getCoordinate(), landscape);
            Composite before = g.getComposite();
            int type = AlphaComposite.SRC_OVER;
            AlphaComposite composite = AlphaComposite.getInstance(type, .8f);
            g.setComposite(composite);

            double scaleX = landscapeView.getWidthOfLandscapeInView();
            float radiusMagnification = 1f;

            if (scaleX <= 400) {
                radiusMagnification = 1f;
            } else if (scaleX <= 2000) {
                radiusMagnification = 1f;
            } else if (scaleX <= 10000) {
                radiusMagnification = 10f;
            } else {
                radiusMagnification = 20f;
            }

            double r = 2*radiusMagnification;
            super.drawFilledCircle(g, screenCoord,r , Color.cyan);
            g.setComposite(before);
        }
    }

    private void highlightAgentsInFieldOfView(Graphics2D g, List agentsInVisualField, Landscape landscape) {
        for (Iterator itr = agentsInVisualField.iterator(); itr.hasNext();) {
            IPhysicalAgent agent = (IPhysicalAgent)itr.next();
            RectangularCoordinate screenCoord = super.getRenderCoordinate(agent.getLocation().getCoordinate(), landscape);
            Composite before = g.getComposite();
            int type = AlphaComposite.SRC_OVER;
            AlphaComposite composite = AlphaComposite.getInstance(type, .8f);
            g.setComposite(composite);

            super.drawFilledCircle(g, screenCoord, 3, Color.red);
            g.setComposite(before);
        }
    }


    private Color getDirectionTriangleColor(RenderContext renderContext) {
        return renderContext.isRenderForPrint() ? Color.lightGray : Color.red;
    }

    private Color getBodyColor(RenderContext renderContext) {
        return renderContext.isRenderForPrint() ? Color.white : PURPLE;
    }

    private static final Color PURPLE = new Color(100, 100, 200);

    private Color getBodyOutlineColor(RenderContext renderContext) {
        return Color.DARK_GRAY;
    }


    private void drawBoundaryIntersections(Graphics2D g, List boundaryIntersections, Landscape landscape, RenderContext renderContext) {
        for (Iterator itr = boundaryIntersections.iterator(); itr.hasNext();) {
            RectangularCoordinate coordinate = (RectangularCoordinate)itr.next();
            RectangularCoordinate lscpCoord = LandscapeToViewModel.getScreenCoord(landscape, coordinate);

            Ellipse2D.Double ixCircle = new Ellipse2D.Double(lscpCoord.getDoubleX() - 1, lscpCoord.getDoubleY() - 1, 2, 2);
            g.setColor(Color.blue);
            g.setStroke(new BasicStroke(.125f));
            g.draw(ixCircle);
        }

    }

    private void drawLocationPath(String contextId, Graphics2D g, ICoordinatePath coordPath, Landscape landscape, RenderContext ctx) {

        GeneralPath path = generatePath(ctx, contextId, g, coordPath, landscape);
        g.setColor(getPathColor(ctx));

        double scaleX = ctx.getView().getWidthOfLandscapeInView();
        float strokeWidth = 6f;

        if (scaleX <= 400) {
            strokeWidth = 0.5f;
        }else    if (scaleX <= 2000) {
            strokeWidth = 1f;

        } else if (scaleX <= 5000) {
            strokeWidth = 12f;
        } else if (scaleX <= 10000) {
            strokeWidth = 24f;
        } else {
            strokeWidth = 48f;
        }

        g.setStroke(new BasicStroke(strokeWidth));
        g.draw(path);
    }

    private Color getPathColor(RenderContext ctx) {
        return ctx.isRenderForPrint() ? Color.black : Color.blue;
    }

    private GeneralPath generatePath(RenderContext ctx, String contextId, Graphics2D g, ICoordinatePath coordPath, Landscape landscape) {
//        if (!ctx.hasAttribute(contextId + "_locationPath")) {
//            ctx.setAttribute(contextId + "_locationPath", new GeneralPath());
//        }
//        if (!ctx.hasAttribute(contextId + "_lastRenderedIndex")) {
//            ctx.setAttribute(contextId + "_lastRenderedIndex", new Integer(0));
//        }
//        int lastRenderedIndex = ((Integer)ctx.getAttribute(contextId + "_lastRenderedIndex")).intValue();

        int lastRenderedIndex = 0;
//        GeneralPath path = (GeneralPath)ctx.getAttribute(contextId + "_locationPath");
        GeneralPath path = new GeneralPath();
        g.setColor(new Color(100, 100, 255));
        g.setStroke(new BasicStroke(.25f));


        for (int i = lastRenderedIndex; i < coordPath.getCoordinates().size(); ++i) {
            RectangularCoordinate coord = (RectangularCoordinate)coordPath.getCoordinates().get(i);
            if (i == 0) {
                path.moveTo((float)coord.getDoubleX(), (float)(landscape.getExtentY() - coord.getDoubleY()));
            } else {
                path.lineTo((float)coord.getDoubleX(), (float)(landscape.getExtentY() - coord.getDoubleY()));
            }

        }
        lastRenderedIndex = coordPath.getCoordinates().size() - 1;
//        System.out.println("Context id " + contextId + " lastRendered " + lastRenderedIndex);
//        ctx.setAttribute(contextId + "_lastRenderedIndex", new Integer(lastRenderedIndex));
        return path;
    }

    private Point2D drawDirectionLine(double theta, double r, Graphics2D g, double x, double y, RenderContext renderContext) {
        //Now draw a line indicating the direction :
        Point2D endPoint = calculatePointAtTheta(theta, r, g, x, y);

        g.setColor(getDirectionLineColor(renderContext));
        g.setStroke(new BasicStroke((float)(.25)));
        Line2D line = new Line2D.Double(x, y, endPoint.getX(), endPoint.getY());
        g.draw(line);
        return endPoint;
    }

    private Color getDirectionLineColor(RenderContext renderContext) {
        return renderContext.isRenderForPrint() ? Color.DARK_GRAY : Color.lightGray;
    }


    public void setDrawPaths(boolean drawPaths) {
        _drawPaths = drawPaths;
    }

    private boolean _drawPaths;
    private static final Logger log = Logger.getLogger(MotileAgentRenderer.class);
}
