/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.pattern;

import com.ixcode.bugsim.view.geometry.RectangularCoordinatePropertyEditor;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.FixedLocationImmigrationStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.CheckBoxPropertyEditor;
import com.ixcode.framework.parameter.model.StrategyDefinition;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:46:21 PM by jim
 */
public class FixedLocationImmigrationPatternPanel extends ImmigrationPatternPanelBase {

    public FixedLocationImmigrationPatternPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, FixedLocationImmigrationStrategyDefinition.class, minWidth);

    }

    protected void initUI(int minWidth) {
        super.initUI(minWidth);

        //@todo put in a check box to centre or not the location on the landscape...
        _locationE = super.addRectangularCoordinateParameterEditor(super.getStrategyClassName(), FixedLocationImmigrationStrategyDefinition.P_RELEASE_LOCATION, "Release Location", minWidth, 0);
        IPropertyValueEditor applyFixedAzimuth = super.addParameterEditor(super.getStrategyClassName(), FixedLocationImmigrationStrategyDefinition.P_APPLY_FIXED_AZIMUTH, minWidth);
        final IPropertyValueEditor fixedAzimuth = super.addParameterEditor(super.getStrategyClassName(), FixedLocationImmigrationStrategyDefinition.P_FIXED_AZIMUTH, minWidth);
        fixedAzimuth.setReadonly(true);

        ((CheckBoxPropertyEditor)applyFixedAzimuth).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (fixedAzimuth.isReadonly()) {
                    fixedAzimuth.setReadonly(false);
                } else {
                    fixedAzimuth.setReadonly(true);
                }
            }
        });




        super.addPropertyEditorBinding(_locationE);
        super.addPropertyEditorBinding(applyFixedAzimuth);
        super.addPropertyEditorBinding(fixedAzimuth);

    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);
        FixedLocationImmigrationStrategyDefinition fl = (FixedLocationImmigrationStrategyDefinition)strategyDefinition;
        if (fl.getReleaseLocationP().isDerived()) {
            _locationE.setReadonly(true);
        } else {
            _locationE.setReadonly(false);
        }
    }

    private RectangularCoordinatePropertyEditor _locationE;
}
