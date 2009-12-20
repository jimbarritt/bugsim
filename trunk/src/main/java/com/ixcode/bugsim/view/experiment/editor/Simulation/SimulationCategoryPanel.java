/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.Simulation;

import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.bugsim.view.experiment.editor.boundary.IStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyParameterBinding;
import com.ixcode.bugsim.view.experiment.editor.strategy.DefaultAzimuthStrategyFactory;
import com.ixcode.bugsim.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.experiment.parameter.simulation.timescale.TimescaleStrategyFactory;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 28, 2007 @ 7:16:53 PM by jim
 */
public class SimulationCategoryPanel extends JPanel implements IParameterMapLookup {

    public SimulationCategoryPanel(IModelAdapter modelAdapter) {
        super(new BorderLayout());
        _modelAdapter = modelAdapter;
        initUI();
    }

    private void initUI() {
        _generalPanel = new ParameterGroupPanel(_modelAdapter, "General");

        int minWidth = 180;

        String className = SimulationCategory.class.getName();
        IPropertyValueEditor replicatesE = _generalPanel.addParameterEditor(className, SimulationCategory.P_NUMBER_OF_REPLICATES, minWidth);
        IPropertyValueEditor competitorsE = _generalPanel.addParameterEditor(className, SimulationCategory.P_ENABLE_COMPETITORS, minWidth);
        IPropertyValueEditor parasitoidsE = _generalPanel.addParameterEditor(className, SimulationCategory.P_ENABLE_PARASITOIDS, minWidth);
        IPropertyValueEditor archiveRemovedE = _generalPanel.addParameterEditor(className, SimulationCategory.P_ARCHIVE_REMOVED_AGENTS, minWidth);
        IPropertyValueEditor archiveEscapedE = _generalPanel.addParameterEditor(className, SimulationCategory.P_ARCHIVE_ESCAPED_AGENTS, minWidth);
        IPropertyValueEditor recordBoundaryE = _generalPanel.addParameterEditor(className, SimulationCategory.P_RECORD_BOUNDARY_CROSSINGS, minWidth);
        IPropertyValueEditor recordDispersalE = _generalPanel.addParameterEditor(className, SimulationCategory.P_RECORD_DISPERSAL_STATISTICS, minWidth);

        _generalPanel.addPropertyEditorBinding(replicatesE);
        _generalPanel.addPropertyEditorBinding(competitorsE);
        _generalPanel.addPropertyEditorBinding(parasitoidsE);
        _generalPanel.addPropertyEditorBinding(archiveRemovedE);
        _generalPanel.addPropertyEditorBinding(archiveEscapedE);
        _generalPanel.addPropertyEditorBinding(recordBoundaryE);
        _generalPanel.addPropertyEditorBinding(recordDispersalE);

        competitorsE.setReadonly(true);
        parasitoidsE.setReadonly(true);

        super.add(_generalPanel, BorderLayout.NORTH);

        _timescaleStrategyPanel = new TimescaleStrategyPanel(_modelAdapter, this, 120);
        _timescaleStrategyBinding = new StrategyParameterBinding("TimsecaleStrategy", _timescaleStrategyPanel, DefaultStrategyFactory.INSTANCE);


        JPanel timescaleContainer = new JPanel(new BorderLayout());
        timescaleContainer.setBorder(BorderFactory.createTitledBorder("Timescale"));
        timescaleContainer.add(_timescaleStrategyPanel, BorderLayout.CENTER);

        super.add(timescaleContainer, BorderLayout.CENTER);


    }

    public static class DefaultStrategyFactory implements IStrategyDefinitionFactory {
        public StrategyDefinition createDefaultStrategyDefinition(String className, IParameterMapLookup parameterMapLookup) {
            return TimescaleStrategyFactory.getRegistry().createDefaultStrategy(className, parameterMapLookup.getParameterMap());
        }

        public static final IStrategyDefinitionFactory INSTANCE = new DefaultStrategyFactory();
    }

    public void setSimulationCategory(SimulationCategory simulationCategory) {
        _simulationCategory = simulationCategory;
        _generalPanel.setModel(_simulationCategory);
        _timescaleStrategyBinding.bind(_simulationCategory, SimulationCategory.P_TIMESCALE);



    }

    public ParameterMap getParameterMap() {
        if (_simulationCategory != null) {
            return _simulationCategory.getParameterMap();
        } else {
            return null;
        }
    }

    private SimulationCategory _simulationCategory;

    private ParameterGroupPanel _generalPanel;
    private IModelAdapter _modelAdapter;

    private TimescaleStrategyPanel _timescaleStrategyPanel;
    private StrategyParameterBinding _timescaleStrategyBinding;
}
