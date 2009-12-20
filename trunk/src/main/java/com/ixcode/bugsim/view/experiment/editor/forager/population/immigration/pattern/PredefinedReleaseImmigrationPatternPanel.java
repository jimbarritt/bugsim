/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.pattern;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.FixedLocationImmigrationStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.PredefinedReleaseImmigrationStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:46:21 PM by jim
 */
public class PredefinedReleaseImmigrationPatternPanel extends ImmigrationPatternPanelBase {

    public PredefinedReleaseImmigrationPatternPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, PredefinedReleaseImmigrationStrategyDefinition.class, minWidth);
        
    }

    protected void initUI(int minWidth) {
        super.initUI(250);
    }
}
