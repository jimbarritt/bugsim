/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.pattern;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.pattern.FixedLocationImmigrationStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.pattern.ImmigrationPatternStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.pattern.PredefinedReleaseImmigrationStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.ImmigrationStrategyBase;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.CheckBoxPropertyEditor;
import com.ixcode.framework.parameter.model.StrategyDefinition;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 5, 2007 @ 8:46:21 PM by jim
 */
public class ImmigrationPatternPanelBase extends StrategyDetailsPanel {

    public ImmigrationPatternPanelBase(IModelAdapter modelAdapter, Class strategyClass, int minWidth) {
        super(modelAdapter, strategyClass);
        initUI(minWidth);
    }

    protected void initUI(int minWidth) {
        IPropertyValueEditor numberToReleaseE = super.addParameterEditor(super.getStrategyClassName(), ImmigrationPatternStrategyBase.P_NUMBER_TO_RELEASE, minWidth);

        _deriveEggLimitE = new CheckBoxPropertyEditor("deriveEggLimit", "Derive From Layout?", minWidth);
        super.addPropertyEditor(_deriveEggLimitE);
        _eggLimitE = super.addParameterEditor(super.getStrategyClassName(), ImmigrationPatternStrategyBase.P_EGG_LIMIT, minWidth);

        IPropertyValueEditor optimiseReleaseE = super.addParameterEditor(super.getStrategyClassName(), ImmigrationPatternStrategyBase.P_OPTIMISE_RELEASE_AZIMUTH, minWidth);
        super.addPropertyEditorBinding(optimiseReleaseE);

        super.addPropertyEditorBinding(numberToReleaseE);


        super.addPropertyEditorBinding(_eggLimitE);

        _deriveEggLimitE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!_isUpdating) {
                    _eggLimitE.setReadonly(!_eggLimitE.isReadonly());
                    if (_eggLimitE.isReadonly()) {
                        deriveEggLimitFromLayout();
                    } else {
                        detatchEggLimit();
                    }
                }
            }


        });
    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);
        ImmigrationPatternStrategyBase isb = (ImmigrationPatternStrategyBase)strategyDefinition;
        _isUpdating = true;
        if (isb.getEggLimitP().isDerived()) {
            _eggLimitE.setReadonly(true);
            _deriveEggLimitE.setValue("true");
        } else {
            _eggLimitE.setReadonly(false);
            _deriveEggLimitE.setValue("false");
        }
        _isUpdating = false;
    }

    private void deriveEggLimitFromLayout() {
        ImmigrationPatternStrategyBase pris = (ImmigrationPatternStrategyBase)super.getStrategyDefinition();
        pris.deriveEggLimitFromLayout();
    }

    private void detatchEggLimit() {
        ImmigrationPatternStrategyBase pris = (ImmigrationPatternStrategyBase)super.getStrategyDefinition();
        pris.detatchEggLimitFromLayout();
    }

    private IPropertyValueEditor _eggLimitE;
    private boolean _isUpdating;
    private CheckBoxPropertyEditor _deriveEggLimitE;
}
