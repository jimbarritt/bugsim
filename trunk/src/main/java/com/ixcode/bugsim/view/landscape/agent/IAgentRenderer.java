/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.agent;

import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;

import java.awt.*;

public interface IAgentRenderer {

    void render(Graphics2D g, Landscape landscape, IPhysicalAgent agent, RenderContext renderContext);

    /**
     * Which layer to display in - 0 is on the top, get progressively higher as you go deeper.
     * @return
     */
    Integer getLayer();
}
