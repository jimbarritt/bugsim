/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.InitialImmigrationStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.TimedImmigrationStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:18:11 PM by jim
 */
public class TimedImmigrationStrategyPanel extends StrategyDetailsPanel {

    public TimedImmigrationStrategyPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, TimedImmigrationStrategyDefinition.class);

        initUI(minWidth);

    }

    private void initUI(int minWidth) {
        super.addParameterEditor(super.getStrategyClassName(), TimedImmigrationStrategyDefinition.P_STRATEGY_NAME, minWidth);
    }
}
