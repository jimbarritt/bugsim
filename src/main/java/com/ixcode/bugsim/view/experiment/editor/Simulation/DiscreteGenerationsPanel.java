/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.Simulation;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.experiment.parameter.simulation.timescale.ContinuousTimescaleStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.simulation.timescale.DiscreteTimescaleStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 28, 2007 @ 7:25:19 PM by jim
 */
public class DiscreteGenerationsPanel extends ContinuousGenerationsPanel {

    public DiscreteGenerationsPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, DiscreteTimescaleStrategyDefinition.class);
        initUI(minWidth);
    }

    protected void initUI(int minWidth) {
        super.initUI(minWidth);
        IPropertyValueEditor generationE =  super.addParameterEditor(super.getStrategyClassName(), DiscreteTimescaleStrategyDefinition.P_GENERATION_LIMIT, minWidth);
        super.addPropertyEditorBinding(generationE);
    }
}
