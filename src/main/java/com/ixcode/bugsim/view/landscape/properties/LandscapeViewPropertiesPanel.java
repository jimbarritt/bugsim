/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.properties;

import com.ixcode.bugsim.view.geometry.ScaledDistancePropertyEditor;
import com.ixcode.bugsim.view.landscape.action.*;
import com.ixcode.bugsim.view.landscape.*;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.PropertyPanel;


/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeViewPropertiesPanel extends PropertyPanel {


    public LandscapeViewPropertiesPanel(LandscapeView landscapeViewer) {
        super("Landscape Viewer Properties");
//        _logicalGridResolution = super.addReadOnlyPropertyEditor("Logical Grid Resoultion", "logicalGridResolution", "" + landscapeViewer.getLogicalGridResolution());
//        _gridResolution = new ScaledDistancePropertyEditor(LandscapeView.PROPERTY_GRID_RESOLUTION, "Grid Resolution", 150);
//        _gridResolution.setValue(landscapeViewer.getGridResolution().toString());

        super.add(_gridResolution);
//        _logicalGridThickness = super.addReadOnlyPropertyEditor("Logical Grid Thickness", "logicalGridThickness", "" + landscapeViewer.getLogicalGridThickness());
        _gridThickness = new ScaledDistancePropertyEditor("gridLinethickness", "GridLine Thickness", 150);
//        _gridThickness.setValue(landscapeViewer.getGridThickness().toString());
        super.add(_gridThickness);

//        super.addPropertyEditor("Show Grid ?", LandscapeView.PROPERTY_SHOW_GRID, "" + landscapeViewer.isShowGrid());
//        super.addPropertyEditor("Grid to scale ?", LandscapeView.PROPERTY_GRID_TO_SCALE,"" + landscapeViewer.isGridToScale());
        super.addPropertyEditor("Fit to Screen ?", LandscapeView.PROPERTY_FIT_TO_SCREEN,"" + landscapeViewer.isZoomIsFitToScreen());
        super.addPropertyEditor("Zoom %", LandscapeView.PROPERTY_ZOOM_PERCENT,"" + landscapeViewer.getZoomPercent() * 100);
        super.addPropertyEditor("Zoom Center", LandscapeView.PROPERTY_ZOOM_CENTER,"" + super.getPoint2dDoubleAsString(landscapeViewer.getZoomCenter()));

        // @todo implement different rendering options for each agent type.
        super.addPropertyEditor("Draw Paths ?", "drawPaths", "true");


        super.setPanelAction(new UpdateLandscapeViewAction(this, landscapeViewer));

    }

    public ScaledDistance getGridResolution() {
        return ScaledDistance.parse(_gridResolution.getValue());
    }

    public void setLogicalGridResolution(int logicalGridResolution) {
        _logicalGridResolution.setValue("" + logicalGridResolution);
    }

    public boolean isDrawPaths() {
        return super.getBoolTextFieldValue("drawPaths");
    }

    public ScaledDistance getGridThickness() {
        return ScaledDistance.parse(_gridThickness.getValue());
    }

    public void setLogicalGridthickness(int logicalGridThickness) {
        _logicalGridThickness.setValue("" + logicalGridThickness);
    }


    private ScaledDistancePropertyEditor _gridThickness;
    private ScaledDistancePropertyEditor _gridResolution;
    private IPropertyValueEditor _logicalGridResolution;
    private IPropertyValueEditor _logicalGridThickness;
}
