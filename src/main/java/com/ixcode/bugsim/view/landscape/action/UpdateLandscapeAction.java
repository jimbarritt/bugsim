/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.action;

import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.bugsim.view.landscape.properties.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class UpdateLandscapeAction extends AbstractAction {

    public static final String ID = "Update Landscape";
    public UpdateLandscapeAction(LandscapePropertiesPanel propertiesPanel, Landscape landscape) {
        super(ID);
        _propertiesPanel = propertiesPanel;
        _landscape = landscape;
    }

    public void actionPerformed(ActionEvent actionEvent) {

//        _landscape.setExtentX(_propertiesPanel.getExtentX());
//        _landscape.setExtentY(_propertiesPanel.getExtentY());
        _landscape.setScale(_propertiesPanel.getScale());
        _landscape.setScaledWidth(_propertiesPanel.getLandscapeWidth());
        _landscape.setScaledHeight(_propertiesPanel.getLandscapeHeight());
        _propertiesPanel.setExtentX(_landscape.getExtentX());
        _propertiesPanel.setExtentY(_landscape.getExtentY());
    }

    private LandscapePropertiesPanel _propertiesPanel;
    private Landscape _landscape;
}
