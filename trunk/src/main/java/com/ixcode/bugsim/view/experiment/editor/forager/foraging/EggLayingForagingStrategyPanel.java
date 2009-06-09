/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.foraging;

import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.foraging.EggLayingForagingStrategyDefinition;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 6, 2007 @ 11:49:25 AM by jim
 */
public class EggLayingForagingStrategyPanel extends StrategyDetailsPanel {

    public EggLayingForagingStrategyPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, EggLayingForagingStrategyDefinition.class);
        initUI(170);
    }

    private void initUI(int minWidth) {
        IPropertyValueEditor numberOfEggsE = super.addParameterEditor(super.getStrategyClassName(), EggLayingForagingStrategyDefinition.P_NUMBER_OF_EGGS, minWidth);
        IPropertyValueEditor landOnCabbageE = super.addParameterEditor(super.getStrategyClassName(), EggLayingForagingStrategyDefinition.P_LAND_ON_RESOURCE, minWidth);
        IPropertyValueEditor layOnCabbageE = super.addParameterEditor(super.getStrategyClassName(), EggLayingForagingStrategyDefinition.P_LAY_ON_CURRENT_REOURCE, minWidth);
        IPropertyValueEditor optimiseSearchE= super.addParameterEditor(super.getStrategyClassName(), EggLayingForagingStrategyDefinition.P_OPTIMISE_SEARCH, minWidth);

        super.addPropertyEditorBinding(numberOfEggsE);
        super.addPropertyEditorBinding(landOnCabbageE);
        super.addPropertyEditorBinding(layOnCabbageE);
        super.addPropertyEditorBinding(optimiseSearchE);

    }
}
