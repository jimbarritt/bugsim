/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.adult;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.bugsim.experiment.parameter.forager.population.mortality.LarvalMortalityStrategyFactory;
import com.ixcode.bugsim.experiment.parameter.forager.population.mortality.AdultMortalityStrategyFactory;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:01:33 PM by jim
 */
public class AdultMortalityPanel extends StrategyPanel {
    public AdultMortalityPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup,  int minWidth) {
        super("AdultMortalityPanel", modelAdapter, parameterMapLookup, AdultMortalityStrategyFactory.getRegistry(), "Type", minWidth);
        addCards(minWidth);
    }

    private void addCards(int minWidth) {
        LimitedEggsStrategyPanel limitedEggs = new LimitedEggsStrategyPanel(super.getModelAdapter());
        LimitedAgeStrategyPanel limitedAge = new LimitedAgeStrategyPanel(super.getModelAdapter(), minWidth);
        LimitedAgeAndEggsStrategyPanel limitedAgeAndEggs = new LimitedAgeAndEggsStrategyPanel(super.getModelAdapter(), minWidth);

        super.addStrategyDetailPanel(limitedEggs);
        super.addStrategyDetailPanel(limitedAge);
        super.addStrategyDetailPanel(limitedAgeAndEggs);

    }
}
