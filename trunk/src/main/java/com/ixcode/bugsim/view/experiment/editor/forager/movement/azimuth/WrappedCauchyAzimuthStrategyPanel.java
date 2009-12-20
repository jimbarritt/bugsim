/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.movement.azimuth;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.WrappedCauchyAzimuthStrategy;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 3:04:44 PM by jim
 */
public class WrappedCauchyAzimuthStrategyPanel extends StrategyDetailsPanel  {

    public WrappedCauchyAzimuthStrategyPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, WrappedCauchyAzimuthStrategy.class);
        initUI(minWidth);
    }

    private void initUI(int minWidth) {
        IPropertyValueEditor angleOfTurnE = super.addParameterEditor(super.getStrategyClassName(), WrappedCauchyAzimuthStrategy.P_ANGLE_OF_TURN_RHO, "Angle Of Turn (rho)", minWidth);
        IPropertyValueEditor resolutionE = super.addParameterEditor(super.getStrategyClassName(), WrappedCauchyAzimuthStrategy.P_RESOLUTION_N, minWidth);
        IPropertyValueEditor maxRHOE = super.addParameterEditor(super.getStrategyClassName(), WrappedCauchyAzimuthStrategy.P_MAX_RHO, minWidth);
        IPropertyValueEditor visualNoiseThresholdE = super.addParameterEditor(super.getStrategyClassName(), WrappedCauchyAzimuthStrategy.P_VISUAL_NOISE_THRESHOLD, minWidth);


        super.addPropertyEditorBinding(angleOfTurnE);
        super.addPropertyEditorBinding(resolutionE);
        super.addPropertyEditorBinding(maxRHOE);
        super.addPropertyEditorBinding(visualNoiseThresholdE);

    }
}
