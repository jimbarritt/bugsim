/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.pattern;

import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.RandomReleaseImmigrationPatternStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:46:21 PM by jim
 */
public class RandomReleaseImmigrationPatternPanel extends ImmigrationPatternPanelBase {

    public RandomReleaseImmigrationPatternPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, RandomReleaseImmigrationPatternStrategyDefinition.class, minWidth);
    }

    protected void initUI(int minWidth) {
        super.initUI(minWidth);
        IPropertyValueEditor releaseInZoneE = super.addParameterEditor(super.getStrategyClassName(), RandomReleaseImmigrationPatternStrategyDefinition.P_RELEASE_IN_ZONE, minWidth);

        super.addPropertyEditorBinding(releaseInZoneE);
    }
}
