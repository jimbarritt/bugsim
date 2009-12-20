/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.movement.azimuth;

import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.GaussianAzimuthStrategy;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.VonMisesAzimuthStrategy;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 3:04:44 PM by jim
 */
public class GaussianAzimuthStrategyPanel extends StrategyDetailsPanel  {

    public GaussianAzimuthStrategyPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, GaussianAzimuthStrategy.class);
        initUI(minWidth);
    }

    private void initUI(int minWidth) {
        JPanel container = new JPanel(new BorderLayout());

        IPropertyValueEditor angleOfTurnSD = super.addParameterEditor(super.getStrategyClassName(), GaussianAzimuthStrategy.P_ANGLE_OF_TURN_SD,"Angle Of Turn (s.d.)", minWidth);
        IPropertyValueEditor visualNoiseThresholdE = super.addParameterEditor(super.getStrategyClassName(), GaussianAzimuthStrategy.P_VISUAL_NOISE_THRESHOLD, minWidth);

        super.addPropertyEditorBinding(angleOfTurnSD);
        super.addPropertyEditorBinding(visualNoiseThresholdE);
        

        super.add(container,  BorderLayout.CENTER);
    }
}
