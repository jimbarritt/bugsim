/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager;

import com.ixcode.bugsim.model.experiment.parameter.forager.ForagerCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.BehaviourCategory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.foraging.ForagingStrategyPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.population.PopulationCategoryPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.motivation.MotivationStrategyPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.movement.MovementStrategyPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.sensor.SensorCategoryPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.swing.property.CheckBoxPropertyEditor;

import javax.swing.*;
import java.awt.*;

/**
 * Description : Properties for the forager
 * Created     : Feb 4, 2007 @ 8:48:03 PM by jim
 */
public class ForagerCategoryPanel extends ParameterGroupPanel implements IParameterMapLookup {


    public ForagerCategoryPanel(IModelAdapter modelAdapter) {
        super(modelAdapter);
        initUI();
    }

    private void initUI() {
        JPanel container = new JPanel(new BorderLayout());
        _tabbedPane = new JTabbedPane();

        _movementPanel = new MovementStrategyPanel(getModelAdapter(), this);
        _foragingPanel = new ForagingStrategyPanel(getModelAdapter(), this);
        _sensorPanel = new SensorCategoryPanel(getModelAdapter());
        _populationPanel = new PopulationCategoryPanel(getModelAdapter());
        _motivationPanel = new MotivationStrategyPanel(getModelAdapter());

        _tabbedPane.add("Movement", _movementPanel);
        _tabbedPane.add("Sensors", _sensorPanel);
        _tabbedPane.add("Foraging", _foragingPanel);
        _tabbedPane.add("Population", _populationPanel);
//        _tabbedPane.add("Motivation", _motivationPanel);


        _behaviourPanel = new ParameterGroupPanel(super.getModelAdapter());
        CheckBoxPropertyEditor recordLifeHistoryEditor = new CheckBoxPropertyEditor(BehaviourCategory.P_RECORD_LIFE_HISTORY, "Record Life History", 100);
        _behaviourPanel.addPropertyEditor(recordLifeHistoryEditor);

        _behaviourPanel.addPropertyEditorBinding(recordLifeHistoryEditor);

        container.add(_behaviourPanel, BorderLayout.NORTH);

        container.add(_tabbedPane, BorderLayout.CENTER);
        super.add(container, BorderLayout.CENTER);


    }


    public void setForagerCategory(ForagerCategory foragerC) {
        super.setModel(foragerC);
        _movementPanel.setStrategyDefinition(foragerC.getBehaviourCategory().getMovementStrategy());
        _foragingPanel.setStrategyDefinition(foragerC.getBehaviourCategory().getForagingStrategy());
        _sensorPanel.setSensorCategory(foragerC.getSensorCategory());
        _populationPanel.setPopulationCategory(foragerC.getPopulationCategory());
        _behaviourPanel.setModel(foragerC.getBehaviourCategory());
    }


    public ParameterMap getParameterMap() {
        return null;
    }


    private JTabbedPane _tabbedPane;
    private MovementStrategyPanel _movementPanel;
    private SensorCategoryPanel _sensorPanel;
    private PopulationCategoryPanel _populationPanel;
    private MotivationStrategyPanel _motivationPanel;
    private ParameterGroupPanel _behaviourPanel;
    private ForagingStrategyPanel _foragingPanel;
}
