/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.movement.azimuth;

import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.VonMisesAzimuthStrategy;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 3:04:44 PM by jim
 */
public class VonMisesAzimuthStrategyPanel extends StrategyDetailsPanel  {

    public VonMisesAzimuthStrategyPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, VonMisesAzimuthStrategy.class);
        initUI(minWidth);
    }

    private void initUI(int minWidth) {
        IPropertyValueEditor useCainE = super.addParameterEditor(super.getStrategyClassName(), VonMisesAzimuthStrategy.P_USE_CAIN_METHOD, minWidth);

        IPropertyValueEditor angleOfTurnE = super.addParameterEditor(super.getStrategyClassName(), VonMisesAzimuthStrategy.P_ANGLE_OF_TURN_K, "Angle Of Turn (kappa)", minWidth);
        IPropertyValueEditor resolutionE = super.addParameterEditor(super.getStrategyClassName(), VonMisesAzimuthStrategy.P_RESOLUTION_N, minWidth);
        IPropertyValueEditor maxKE = super.addParameterEditor(super.getStrategyClassName(), VonMisesAzimuthStrategy.P_MAX_K, minWidth);
        IPropertyValueEditor visualNoiseThresholdE = super.addParameterEditor(super.getStrategyClassName(), VonMisesAzimuthStrategy.P_VISUAL_NOISE_THRESHOLD, minWidth);

        super.addPropertyEditorBinding(useCainE);
        super.addPropertyEditorBinding(angleOfTurnE);
        super.addPropertyEditorBinding(resolutionE);
        super.addPropertyEditorBinding(maxKE);
        super.addPropertyEditorBinding(visualNoiseThresholdE);
      
    }
}
