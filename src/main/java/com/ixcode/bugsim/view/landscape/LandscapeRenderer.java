/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.bugsim.view.agent.*;
import com.ixcode.bugsim.view.grid.*;
import com.ixcode.framework.simulation.model.agent.physical.*;
import com.ixcode.framework.simulation.model.landscape.*;
import com.ixcode.framework.simulation.model.landscape.grid.*;
import com.ixcode.framework.math.geometry.*;
import org.apache.log4j.*;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeRenderer implements LandscapeLayer {

    private static final Logger log = Logger.getLogger(LandscapeRenderer.class);
    private RenderContext renderContext = new RenderContext();

    private GridRenderer gridRenderer = new GridRenderer();
    private boolean _visible = true;

    /**
     * Copy the collections to prevent concurrent modification
     */
    public void render(LandscapeView landscapeView, Graphics2D graphics2D) {

//        renderContext.setRenderForPrint(landscapeView.getRenderForPrint());
        renderContext.setView(landscapeView);
        Landscape landscape = landscapeView.getLandscape();
//        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        renderLandscapeBoundary(graphics2D, landscape, landscapeView);

//        if (landscapeView.getRenderGrids()) {
//            renderGrids(landscapeView, graphics2D);
//        }

        renderAgentsWithoutLayers(landscape, graphics2D);


    }

    private void renderGrids(LandscapeView landscapeView, Graphics2D g) {
        List grids = new ArrayList(landscapeView.getLandscape().getGrids());
        for (Iterator itr = grids.iterator(); itr.hasNext();) {
            Grid grid = (Grid) itr.next();
            if (log.isDebugEnabled()) {
                log.debug("Rendering : " + grid.getName());
            }
            gridRenderer.render(g, grid, new GridRenderOptions(), landscapeView);

        }
    }

    private void renderLandscapeBoundary(Graphics2D g, Landscape landscape, LandscapeView landscapeView) {
        Shape boundaryBorder;
        if (landscape.isCircular()) {
            boundaryBorder = createEllipseFromBounds(landscape.getLogicalBounds(), landscapeView);
        } else {
            boundaryBorder = createRectangleFromBounds(landscape.getLogicalBounds(), landscapeView);
        }

        g.setColor(new Color(220, 220, 220));
        g.fill(boundaryBorder);
    }

    private void renderAgentsWithoutLayers(Landscape landscape, Graphics2D g) {


        List agents = new ArrayList(landscape.getAgents());
        for (Iterator itr = agents.iterator(); itr.hasNext();) {
            IPhysicalAgent agent = (IPhysicalAgent) itr.next();
            IAgentRenderer renderer = AgentRendererRegistry.INSTANCE.getRendererForAgent(agent);
            renderer.render(g, landscape, agent, renderContext);
        }


    }


    protected Shape createRectangleFromBounds(CartesianBounds b, LandscapeView view) {
        RectangularCoordinate topLeft = getScreenCoord(view, new RectangularCoordinate(b.getDoubleX(), b.getDoubleY() + b.getDoubleHeight()));

        return new Rectangle2D.Double(topLeft.getDoubleX(), topLeft.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight());

    }

    protected Ellipse2D.Double createEllipseFromBounds(CartesianBounds b, LandscapeView view) {
        RectangularCoordinate topLeft = getScreenCoord(view, new RectangularCoordinate(b.getDoubleX(), b.getDoubleY() + b.getDoubleHeight()));
        return new Ellipse2D.Double(topLeft.getDoubleX(), topLeft.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight());

    }

    protected RectangularCoordinate getScreenCoord(LandscapeView view, RectangularCoordinate coord) {
        return LandscapeToViewTranslation.getScreenCoord(view.getLandscape(), coord);
    }

    private static class RenderOperation {
        public RenderOperation(IAgentRenderer renderer, IPhysicalAgent agent) {
            _renderer = renderer;
            _agent = agent;
        }

        public void render(Graphics2D g, Landscape landscape, RenderContext renderContext) {
            _renderer.render(g, landscape, _agent, renderContext);
        }

        private IAgentRenderer _renderer;
        private IPhysicalAgent _agent;
    }

    public boolean isVisible() {
        return true;
    }

    public void setVisible(boolean isVisible) {

    }

    public void clearContext() {
        renderContext = new RenderContext();
    }


}
