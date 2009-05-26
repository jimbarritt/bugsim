/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.adult;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedEggsAdultMortalityStrategy;
import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedAgeAndEggsAdultMortalityStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.LimitedAgeAndEggsAdultMortalityStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:22:25 PM by jim
 */
public class LimitedAgeAndEggsStrategyPanel extends LimitedAgeStrategyPanel {

    public LimitedAgeAndEggsStrategyPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, LimitedAgeAndEggsAdultMortalityStrategyDefinition.class, minWidth);

    }
}
