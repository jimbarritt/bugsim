/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.sensor.olfactory;

import com.ixcode.bugsim.experiment.parameter.forager.sensor.olfactory.OlfactorySensorStrategy;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.javabean.TextAlignment;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.TextFieldPropertyEditor;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 6, 2007 @ 3:41:42 PM by jim
 */
public class OlfactorySensorPanel extends StrategyDetailsPanel {

    public OlfactorySensorPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, OlfactorySensorStrategy.class);
        initUI(minWidth);
    }

    private void initUI(int minWidth) {
        IPropertyValueEditor distanceFromAgentE = super.addParameterEditor(super.getStrategyClassName(), OlfactorySensorStrategy.P_DISTANCE_FROM_AGENT, minWidth);
        IPropertyValueEditor headingFromAgentE = super.addParameterEditor(super.getStrategyClassName(), OlfactorySensorStrategy.P_HEADING_FROM_AGENT, minWidth);
        // @todo implement a 'browse' button to select the name from a list - OR have a combo box that is updated from the parameters..
        IPropertyValueEditor signalSurfaceNameE = new TextFieldPropertyEditor(OlfactorySensorStrategy.P_SIGNAL_SURFACE_NAME, "Signal Surface Name", "", 20, TextAlignment.LEFT, minWidth) ;
        super.addPropertyEditor(signalSurfaceNameE);
        IPropertyValueEditor minSensitivityE = super.addParameterEditor(super.getStrategyClassName(), OlfactorySensorStrategy.P_MIN_SENSITIVITY, minWidth);
        IPropertyValueEditor maxSensitivityE = super.addParameterEditor(super.getStrategyClassName(), OlfactorySensorStrategy.P_MAX_SENSITIVITY, minWidth);

        super.addPropertyEditorBinding(distanceFromAgentE);
        super.addPropertyEditorBinding(headingFromAgentE);
        super.addPropertyEditorBinding(signalSurfaceNameE);
        super.addPropertyEditorBinding(minSensitivityE);
        super.addPropertyEditorBinding(maxSensitivityE);
    }
}
