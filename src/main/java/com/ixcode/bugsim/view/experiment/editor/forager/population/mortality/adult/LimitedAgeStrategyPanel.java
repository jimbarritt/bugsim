/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.adult;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedEggsAdultMortalityStrategy;
import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedAgeAdultMortalityStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.LimitedAgeAdultMortalityStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:22:25 PM by jim
 */
public class LimitedAgeStrategyPanel extends StrategyDetailsPanel {

    protected LimitedAgeStrategyPanel(IModelAdapter modelAdapter, Class strategyClass, int minWidth) {
        super(modelAdapter, strategyClass);
        initUI(minWidth);
    }

    public LimitedAgeStrategyPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, LimitedAgeAdultMortalityStrategyDefinition.class);
        initUI(minWidth);

    }

    private void initUI(int minWidth) {
        IPropertyValueEditor maxAgeE = super.addParameterEditor(super.getStrategyClassName(), LimitedAgeAdultMortalityStrategyDefinition.P_MAX_AGE, minWidth);


        super.addPropertyEditorBinding(maxAgeE);
    }
}
