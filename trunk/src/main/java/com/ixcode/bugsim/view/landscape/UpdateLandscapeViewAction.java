/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgent;
import com.ixcode.bugsim.view.agent.AgentRendererRegistry;
import com.ixcode.bugsim.view.agent.MotileAgentRenderer;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class UpdateLandscapeViewAction extends AbstractAction {

    public static final String ID = "Update Landscape Viewer";

    public UpdateLandscapeViewAction(LandscapeViewPropertiesPanel propertiesPanel, LandscapeView landscapeViewer) {
        super(ID);
        _propertiesPanel = propertiesPanel;
        _landscapeViewer = landscapeViewer;
    }

    public void actionPerformed(ActionEvent actionEvent) {
//        _landscapeViewer.setLogicalGridResolution(_propertiesPanel.getIntTextFieldValue(LandscapeView.PROPERTY_GRID_RESOLUTION));
        _landscapeViewer.setGridToScale(_propertiesPanel.getBoolTextFieldValue(LandscapeView.PROPERTY_GRID_TO_SCALE));
        _landscapeViewer.setShowGrid(_propertiesPanel.getBoolTextFieldValue(LandscapeView.PROPERTY_SHOW_GRID));
        _landscapeViewer.setFitToScreen(_propertiesPanel.getBoolTextFieldValue(LandscapeView.PROPERTY_FIT_TO_SCREEN));
        double zoomPercent = _propertiesPanel.getDoubleTextFieldValue(LandscapeView.PROPERTY_ZOOM_PERCENT);
        _landscapeViewer.setZoomPercent(zoomPercent / 100);

        _landscapeViewer.setZoomCenter(_propertiesPanel.getPoint2dDoubleTextFieldValue(LandscapeView.PROPERTY_ZOOM_CENTER));

        _landscapeViewer.setGridResolution(_propertiesPanel.getGridResolution());
        _propertiesPanel.setLogicalGridResolution(_landscapeViewer.getLogicalGridResolution());

        _landscapeViewer.setGridThickness(_propertiesPanel.getGridThickness());
        _propertiesPanel.setLogicalGridthickness(_landscapeViewer.getLogicalGridThickness());

        _landscapeViewer.invalidate();
        _landscapeViewer.repaint();

        MotileAgentRenderer renderer = (MotileAgentRenderer)AgentRendererRegistry.INSTANCE.getRendererForAgent(ButterflyAgent.AGENT_CLASS_ID);
        renderer.setDrawPaths(_propertiesPanel.isDrawPaths());

    }

    private LandscapeViewPropertiesPanel _propertiesPanel;
    private LandscapeView _landscapeViewer;
}
