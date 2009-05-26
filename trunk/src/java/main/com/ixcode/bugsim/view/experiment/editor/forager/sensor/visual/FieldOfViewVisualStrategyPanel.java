/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.sensor.visual;

import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.visual.FieldOfViewVisualStrategyDefinition;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 6, 2007 @ 2:14:24 PM by jim
 */
public class FieldOfViewVisualStrategyPanel extends StrategyDetailsPanel {

    public FieldOfViewVisualStrategyPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, FieldOfViewVisualStrategyDefinition.class);
        initUI(minWidth);
    }

    private void initUI(int minWidth) {
        IPropertyValueEditor fieldDepthE = super.addParameterEditor(super.getStrategyClassName(), FieldOfViewVisualStrategyDefinition.P_FIELD_DEPTH, minWidth);
        IPropertyValueEditor fieldWidthE = super.addParameterEditor(super.getStrategyClassName(), FieldOfViewVisualStrategyDefinition.P_FIELD_WIDTH, minWidth);
        IPropertyValueEditor signalThresholdE = super.addParameterEditor(super.getStrategyClassName(), FieldOfViewVisualStrategyDefinition.P_SIGNAL_THRESHOLD, minWidth);
        IPropertyValueEditor gammaE = super.addParameterEditor(super.getStrategyClassName(), FieldOfViewVisualStrategyDefinition.P_LUMINOSITY_GAMMA, minWidth);

        super.addPropertyEditorBinding(fieldDepthE);
        super.addPropertyEditorBinding(fieldWidthE);
        super.addPropertyEditorBinding(signalThresholdE);
        super.addPropertyEditorBinding(gammaE);
    }
}
