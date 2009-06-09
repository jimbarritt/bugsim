/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.larval;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.DirectLarvalMortalityStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.CompetitiveLarvalMortalityStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:26:48 PM by jim
 */
public class CompetitiveLarvalMortalityPanel extends StrategyDetailsPanel {
    public CompetitiveLarvalMortalityPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, CompetitiveLarvalMortalityStrategyDefinition.class);
    }
}
