/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.foraging;

import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.foraging.ForagingStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 4, 2007 @ 8:56:27 PM by jim
 */
public class ForagingStrategyPanel extends StrategyPanel {
    public ForagingStrategyPanel(IModelAdapter modelAdapter, IParameterMapLookup paramMapLookup) {
        super("ForagingStrategy", modelAdapter, paramMapLookup, ForagingStrategyFactory.getRegistry(), "Type", 100);
        addCards();
        super.setChangeTypeEnabled(false);
    }

    private void addCards() {
        EggLayingForagingStrategyPanel eggLayingPanel = new EggLayingForagingStrategyPanel(super.getModelAdapter());
        super.addStrategyDetailPanel(eggLayingPanel);
    }


}
