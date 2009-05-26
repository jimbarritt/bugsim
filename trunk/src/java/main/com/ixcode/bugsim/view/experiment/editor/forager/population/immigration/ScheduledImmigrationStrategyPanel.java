/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.InitialImmigrationStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.ScheduledImmigrationStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:18:11 PM by jim
 */
public class ScheduledImmigrationStrategyPanel extends StrategyDetailsPanel {

    public ScheduledImmigrationStrategyPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, ScheduledImmigrationStrategyDefinition.class);


    }
}
