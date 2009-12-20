/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.larval;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.experiment.parameter.forager.population.mortality.DirectLarvalMortalityStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import org.apache.log4j.Logger;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:26:48 PM by jim
 */
public class DirectLarvalMortalityPanel extends StrategyDetailsPanel {
    public DirectLarvalMortalityPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, DirectLarvalMortalityStrategyDefinition.class);
        initUI(minWidth);
    }

    private void initUI(int minWidth) {
        IPropertyValueEditor mortalityRateE = super.addParameterEditor(super.getStrategyClassName(), DirectLarvalMortalityStrategyDefinition.P_MORTALITY_RATE, minWidth);
        IPropertyValueEditor lifespanE = super.addParameterEditor(super.getStrategyClassName(), DirectLarvalMortalityStrategyDefinition.P_LARVAL_LIFESPAN, minWidth);
        super.addPropertyEditorBinding(mortalityRateE);
        super.addPropertyEditorBinding(lifespanE);
    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);
    }

    private static final Logger log = Logger.getLogger(DirectLarvalMortalityPanel.class);
}
