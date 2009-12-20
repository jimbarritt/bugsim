/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.ReadOnlyPropertyEditor;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyParameterBinding;
import com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.adult.AdultMortalityPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.adult.DefaultAdultMortalityStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.larval.LarvalMortalityPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.larval.DefaultLarvalMortalityStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.ImmigrationPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.DefaultImmigrationStrategyDefinitionFactory;
import com.ixcode.bugsim.experiment.parameter.forager.population.PopulationCategory;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 4, 2007 @ 10:10:59 PM by jim
 */
public class PopulationCategoryPanel extends JPanel implements IParameterMapLookup {
    public PopulationCategoryPanel(IModelAdapter modelAdapter) {
        super(new BorderLayout());
        _modelAdapter = modelAdapter;
        initUI(120);

    }

    private void initUI(int minWidth) {
        JPanel container = new JPanel(new BorderLayout());

        _mainParameters = new ParameterGroupPanel(_modelAdapter);

        String className = PopulationCategory.class.getName();
        IPropertyValueEditor agentClassE = new ReadOnlyPropertyEditor(PopulationCategory.P_AGENT_CLASS, "Agent Class", minWidth);
        _mainParameters.addPropertyEditor(agentClassE);
        _mainParameters.addPropertyEditorBinding(agentClassE);

        container.add(_mainParameters, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        _adultMortalityPanel = new AdultMortalityPanel(_modelAdapter, this, 120);
        _larvalMortalityPanel = new LarvalMortalityPanel(_modelAdapter, this, 120);
        _immigrationPanel = new ImmigrationPanel(_modelAdapter, this, 150);

        _adultMortalityBinding = new StrategyParameterBinding("adultMortality", _adultMortalityPanel, DefaultAdultMortalityStrategyDefinitionFactory.INSTANCE);
        _larvalMortalityBinding = new StrategyParameterBinding("larvalMortality", _larvalMortalityPanel, DefaultLarvalMortalityStrategyDefinitionFactory.INSTANCE);
        _immigrationBinding = new StrategyParameterBinding("immigration", _immigrationPanel, DefaultImmigrationStrategyDefinitionFactory.INSTANCE);

        tabbedPane.addTab("Immigration", _immigrationPanel);
        tabbedPane.addTab("Adult Mortality", _adultMortalityPanel);
        tabbedPane.addTab("Larval Mortality", _larvalMortalityPanel);


        container.add(tabbedPane, BorderLayout.CENTER);

        super.add(container, BorderLayout.CENTER);
    }

    public void setPopulationCategory(PopulationCategory populationC) {
        _mainParameters.setModel(populationC);
        _populationC = populationC;

        _immigrationBinding.bind(populationC,  PopulationCategory.P_IMMIGRATION);
        _larvalMortalityBinding.bind(populationC,  PopulationCategory.P_LARVAL_MORTALITY);
        _adultMortalityBinding.bind(populationC,  PopulationCategory.P_ADULT_MORTALITY);

    }

    public ParameterMap getParameterMap() {
        return (_populationC != null) ? _populationC.getParameterMap() : null;
    }


    private ParameterGroupPanel _mainParameters;
    private PopulationCategory _populationC;
    private IModelAdapter _modelAdapter;
    private AdultMortalityPanel _adultMortalityPanel;
    private LarvalMortalityPanel _larvalMortalityPanel;
    private ImmigrationPanel _immigrationPanel;
    private StrategyParameterBinding _adultMortalityBinding;
    private StrategyParameterBinding _larvalMortalityBinding;
    private StrategyParameterBinding _immigrationBinding;
}
