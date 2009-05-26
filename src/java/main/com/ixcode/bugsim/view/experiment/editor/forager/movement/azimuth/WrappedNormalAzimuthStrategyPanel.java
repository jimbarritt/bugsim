/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.movement.azimuth;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth.GaussianAzimuthStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth.WrappedNormalAzimuthStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth.VonMisesAzimuthStrategy;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 3:04:44 PM by jim
 */
public class WrappedNormalAzimuthStrategyPanel extends StrategyDetailsPanel  {

    public WrappedNormalAzimuthStrategyPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, WrappedNormalAzimuthStrategy.class);
        initUI(minWidth);
    }

    private void initUI(int minWidth) {
        JPanel container = new JPanel(new BorderLayout());

        IPropertyValueEditor angleOfTurnE = super.addParameterEditor(super.getStrategyClassName(), WrappedNormalAzimuthStrategy.P_ANGLE_OF_TURN_SD, "Angle Of Turn (s.d.)", minWidth);
        IPropertyValueEditor resolutionE = super.addParameterEditor(super.getStrategyClassName(), WrappedNormalAzimuthStrategy.P_RESOLUTION_N, minWidth);
        IPropertyValueEditor toleranceE= super.addParameterEditor(super.getStrategyClassName(), WrappedNormalAzimuthStrategy.P_TOLERANCE, minWidth);
        IPropertyValueEditor visNoisethreshE = super.addParameterEditor(super.getStrategyClassName(), WrappedNormalAzimuthStrategy.P_VISUAL_NOISE_THRESHOLD, minWidth);


        super.addPropertyEditorBinding(angleOfTurnE);
        super.addPropertyEditorBinding(resolutionE);
        super.addPropertyEditorBinding(toleranceE);
        super.addPropertyEditorBinding(visNoisethreshE);

        super.add(container,  BorderLayout.CENTER);
    }
}
