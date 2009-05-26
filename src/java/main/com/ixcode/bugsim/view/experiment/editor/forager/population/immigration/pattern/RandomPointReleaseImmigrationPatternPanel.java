/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.pattern;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.CheckBoxPropertyEditor;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.pattern.FixedLocationImmigrationStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.pattern.RandomPointReleaseImmigrationStrategyDefinition;
import com.ixcode.bugsim.view.landscape.geometry.RectangularCoordinatePropertyEditor;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:46:21 PM by jim
 */
public class RandomPointReleaseImmigrationPatternPanel extends ImmigrationPatternPanelBase {

    public RandomPointReleaseImmigrationPatternPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, RandomPointReleaseImmigrationStrategyDefinition.class, minWidth);

    }

    protected void initUI(int minWidth) {
        super.initUI(minWidth);

        IPropertyValueEditor limitToResourcePatchE= super.addParameterEditor(super.getStrategyClassName(), RandomPointReleaseImmigrationStrategyDefinition.P_LIMIT_TO_RESOURCE_PATCH, minWidth);
        IPropertyValueEditor avoidAgentsE= super.addParameterEditor(super.getStrategyClassName(), RandomPointReleaseImmigrationStrategyDefinition.P_AVOID_AGENTS, minWidth);
        IPropertyValueEditor avoidRadiusE= super.addParameterEditor(super.getStrategyClassName(), RandomPointReleaseImmigrationStrategyDefinition.P_AVOIDING_RADIUS, minWidth);
        IPropertyValueEditor applyFixedAzimuthE = super.addParameterEditor(super.getStrategyClassName(), RandomPointReleaseImmigrationStrategyDefinition.P_APPLY_FIXED_AZIMUTH, minWidth);
        final IPropertyValueEditor fixedAzimuthE = super.addParameterEditor(super.getStrategyClassName(),RandomPointReleaseImmigrationStrategyDefinition.P_FIXED_AZIMUTH, minWidth);
        fixedAzimuthE.setReadonly(true);

        ((CheckBoxPropertyEditor)applyFixedAzimuthE).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (fixedAzimuthE.isReadonly()) {
                    fixedAzimuthE.setReadonly(false);
                } else {
                    fixedAzimuthE.setReadonly(true);
                }
            }
        });




        super.addPropertyEditorBinding(limitToResourcePatchE);
        super.addPropertyEditorBinding(avoidAgentsE);
        super.addPropertyEditorBinding(avoidRadiusE);
        super.addPropertyEditorBinding(applyFixedAzimuthE);
        super.addPropertyEditorBinding(fixedAzimuthE);

    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);


    }


}
