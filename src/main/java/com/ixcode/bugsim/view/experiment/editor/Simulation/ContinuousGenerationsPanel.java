/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.Simulation;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.model.experiment.parameter.simulation.timescale.ContinuousTimescaleStrategyDefinition;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 28, 2007 @ 7:25:19 PM by jim
 */
public class ContinuousGenerationsPanel extends StrategyDetailsPanel {
    protected ContinuousGenerationsPanel(IModelAdapter modelAdapter, Class strategyClass) {
        super(modelAdapter, strategyClass);
    }
    public ContinuousGenerationsPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, ContinuousTimescaleStrategyDefinition.class);
        initUI(minWidth);
    }

    protected void initUI(int minWidth) {
        IPropertyValueEditor timestepE = super.addParameterEditor(super.getStrategyClassName(), ContinuousTimescaleStrategyDefinition.P_TIMESTEP_LIMIT, minWidth);
        super.addPropertyEditorBinding(timestepE);
    }
}
