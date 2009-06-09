/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.pattern;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.pattern.ImmigrationPatternStrategyFactory;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyRegistry;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:44:39 PM by jim
 */
public class ImmigrationPatternPanel extends StrategyPanel {
    public ImmigrationPatternPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, int minWidth) {
        super("ImmigrationPattern", modelAdapter, parameterMapLookup, ImmigrationPatternStrategyFactory.getRegistry(), "Type", minWidth);
        addCards(minWidth);
    }

    private void addCards(int minWidth) {
        FixedLocationImmigrationPatternPanel fixed = new FixedLocationImmigrationPatternPanel(super.getModelAdapter(), minWidth);
        RandomReleaseImmigrationPatternPanel random = new RandomReleaseImmigrationPatternPanel(super.getModelAdapter(), minWidth);
        RandomPointReleaseImmigrationPatternPanel randomPoint = new RandomPointReleaseImmigrationPatternPanel(super.getModelAdapter(), minWidth);

        super.addStrategyDetailPanel(fixed);
        super.addStrategyDetailPanel(random);
        super.addStrategyDetailPanel(randomPoint);
    }
}
