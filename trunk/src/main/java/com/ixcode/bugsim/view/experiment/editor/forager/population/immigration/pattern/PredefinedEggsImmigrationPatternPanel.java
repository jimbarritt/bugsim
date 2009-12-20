/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.pattern;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.FixedLocationImmigrationStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.PredefinedEggsImmigrationStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:46:21 PM by jim
 */
public class PredefinedEggsImmigrationPatternPanel extends StrategyDetailsPanel {

    public PredefinedEggsImmigrationPatternPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, PredefinedEggsImmigrationStrategyDefinition.class);
        initUI(minWidth);
    }

    private void initUI(int minWidth) {

    }
}
