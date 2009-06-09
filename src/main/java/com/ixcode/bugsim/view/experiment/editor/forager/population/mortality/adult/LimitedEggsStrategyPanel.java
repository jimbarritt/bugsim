/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.adult;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedEggsAdultMortalityStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.LimitedEggsAdultMortalityStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:22:25 PM by jim
 */
public class LimitedEggsStrategyPanel extends StrategyDetailsPanel {

    public LimitedEggsStrategyPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, LimitedEggsAdultMortalityStrategyDefinition.class);
         super.add(new JLabel("There are no specific properties for this strategy - it just goes by how many eggs the forager has(set in the egg laying strategy)"));
    }
}
