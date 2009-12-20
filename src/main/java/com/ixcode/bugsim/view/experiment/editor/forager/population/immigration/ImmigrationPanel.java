/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.ImmigrationStrategyFactory;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:01:33 PM by jim
 */
public class ImmigrationPanel extends StrategyPanel {
    public ImmigrationPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, int minWidth) {
        super("ImmigrationPanel", modelAdapter, parameterMapLookup, ImmigrationStrategyFactory.getRegistry(), "Type", minWidth);
        addCards(minWidth);
        super.setChangeTypeEnabled(false);
    }

    private void addCards(int minWidth) {
        InitialImmigrationStrategyPanel initialImmigration = new InitialImmigrationStrategyPanel(super.getModelAdapter(), minWidth);
        ScheduledImmigrationStrategyPanel scheduledImmigration = new ScheduledImmigrationStrategyPanel(super.getModelAdapter());
        TimedImmigrationStrategyPanel timedImmigration = new TimedImmigrationStrategyPanel(super.getModelAdapter(), minWidth);


        super.addStrategyDetailPanel(initialImmigration);
        super.addStrategyDetailPanel(scheduledImmigration);
        super.addStrategyDetailPanel(timedImmigration);

    }
}
