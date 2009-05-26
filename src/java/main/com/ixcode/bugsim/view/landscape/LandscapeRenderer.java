/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.bugsim.view.landscape.agent.AgentRendererRegistry;
import com.ixcode.bugsim.view.landscape.agent.IAgentRenderer;
import com.ixcode.bugsim.view.landscape.agent.RenderContext;
import com.ixcode.bugsim.view.landscape.grid.GridRenderOptions;
import com.ixcode.bugsim.view.landscape.grid.GridRenderer;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;

import java.awt.*;
import java.util.*;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeRenderer extends LandscapeRendererBase {

    /**
     * Copy the collections to prevent concurrent modification
     *
     * @param g
     * @param landscapeView
     */
    public void render(Graphics2D g, LandscapeView landscapeView) {

        _renderContext.setRenderForPrint(landscapeView.getRenderForPrint());
        _renderContext.setView(landscapeView);
        Landscape landscape = landscapeView.getLandscape();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        renderLandscapeBoundary(g, landscape, landscapeView);

        if (landscapeView.getRenderGrids()) {
            renderGrids(landscapeView, g);
        }

        renderAgentsWithoutLayers(landscape, g);


    }

    private void renderGrids(LandscapeView landscapeView, Graphics2D g) {
        List grids = new ArrayList(landscapeView.getLandscape().getGrids());
        for (Iterator itr = grids.iterator(); itr.hasNext();) {
            Grid grid = (Grid)itr.next();
            if (log.isDebugEnabled()) {
                log.debug("Rendering : " + grid.getName());
            }
            _gridRenderer.render(g, grid, new GridRenderOptions(), landscapeView);

        }
    }

    private void renderLandscapeBoundary(Graphics2D g, Landscape landscape, LandscapeView landscapeView) {
        Shape boundaryShape;
        if (landscape.isCircular()) {
            boundaryShape = super.createEllipseFromBounds(landscape.getLogicalBounds(), landscapeView);
        } else {
            boundaryShape = super.createRectangleFromBounds(landscape.getLogicalBounds(), landscapeView);
        }

        double scaleX = landscapeView.getLandscapeClipSizeX();
        float strokeWidth = 6f;

        if (scaleX <=400) {
            strokeWidth = 0.5f;
        } else if (scaleX <=2000) {
            strokeWidth=12f;
        } else if (scaleX <= 10000) {
            strokeWidth=24f;
        } else {
            strokeWidth=48f;
        }

        g.setStroke(new BasicStroke(strokeWidth));
        g.setColor(Color.blue);
        g.draw(boundaryShape);
    }

    private void renderAgentsWithoutLayers(Landscape landscape, Graphics2D g) {


        List agents = new ArrayList(landscape.getAgents());
        for (Iterator itr = agents.iterator(); itr.hasNext();) {
            IPhysicalAgent agent = (IPhysicalAgent)itr.next();
            IAgentRenderer renderer = AgentRendererRegistry.INSTANCE.getRendererForAgent(agent);
            renderer.render(g, landscape, agent, _renderContext);
        }


    }

    /**
     * This doesnt seem to work at all - sunddenly everything is non anti aliased and with lots of agents it hangs up!
     *
     * @param landscapeView
     * @param g
     */
    private void renderAgentsWithLayers(LandscapeView landscapeView, Graphics2D g) {

        List layers = new ArrayList();
        layers.add(new Integer(0));
        Map renderOperations = new HashMap();
        renderOperations.put(new Integer(0), new ArrayList());

        List agents = new ArrayList(landscapeView.getLandscape().getAgents());
        for (Iterator itr = agents.iterator(); itr.hasNext();) {
            IPhysicalAgent agent = (IPhysicalAgent)itr.next();
            IAgentRenderer renderer = AgentRendererRegistry.INSTANCE.getRendererForAgent(agent);
            Integer layer = renderer.getLayer();
            layers.add(layer);
            if (!renderOperations.containsKey(layer)) {
                renderOperations.put(layer, new ArrayList());
            }
            List operations = (List)renderOperations.get(layer);
            operations.add(new RenderOperation(renderer, agent));
        }

        Collections.sort(layers);

        for (ListIterator itr = layers.listIterator(layers.size()); itr.hasPrevious();) {
            Integer layer = (Integer)itr.previous();
            List operations = (List)renderOperations.get(layer);
            for (Iterator itrOp = operations.iterator(); itrOp.hasNext();) {
                RenderOperation op = (RenderOperation)itrOp.next();
                op.render(g, landscapeView.getLandscape(), _renderContext);
            }
        }


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
        _renderContext = new RenderContext();
    }

    private static final Logger log = Logger.getLogger(LandscapeRenderer.class);
    private RenderContext _renderContext = new RenderContext();

    private GridRenderer _gridRenderer = new GridRenderer();
}
