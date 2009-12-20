/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.movement;

import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.RandomWalkMovementStrategy;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.SensoryRandomWalkMovementStrategy;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.movement.azimuth.AzimuthGeneratorPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.movement.movelength.MoveLengthGeneratorPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.DefaultAzimuthStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyParameterBinding;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

import javax.swing.*;
import java.awt.*;

/**
 * @todo work out how to make a base class to share with Randomnwalk panel
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 2:37:14 PM by jim
 */
public class SensoryRandomWalkStrategyPanel extends StrategyDetailsPanel {

    public SensoryRandomWalkStrategyPanel(IModelAdapter modelAdapter ) {
        super(modelAdapter, SensoryRandomWalkMovementStrategy.class);
        initUI(130);
    }

    private void initUI(int minWidth) {
        JPanel container = new JPanel(new BorderLayout());
        ParameterGroupPanel general = new ParameterGroupPanel(super.getModelAdapter());

        IPropertyValueEditor visionEnabledE = general.addParameterEditor(super.getStrategyClassName(), SensoryRandomWalkMovementStrategy.P_VISION_ENABLED, minWidth);
        IPropertyValueEditor olfactionEnabledE = general.addParameterEditor(super.getStrategyClassName(), SensoryRandomWalkMovementStrategy.P_OLFACTION_ENABLED, minWidth);
        IPropertyValueEditor refactoryE = general.addParameterEditor(super.getStrategyClassName(), SensoryRandomWalkMovementStrategy.P_REFACTORY_PERIOD, "Refactory Period (timesteps)", minWidth);
                

        super.addPropertyEditorBinding(refactoryE);
        super.addPropertyEditorBinding(visionEnabledE);
        super.addPropertyEditorBinding(olfactionEnabledE);
        container.add(general, BorderLayout.NORTH) ;


        _azimuthPanel = new AzimuthGeneratorPanel(super.getModelAdapter(), this, minWidth);
        _moveLengthPanel = new MoveLengthGeneratorPanel(super.getModelAdapter(), this, minWidth);

        _azimuthBinding = new StrategyParameterBinding("AzimuthStrategy", _azimuthPanel, DefaultAzimuthStrategyFactory.INSTANCE);

        JTabbedPane tabbedPane= new JTabbedPane();

        tabbedPane.add("Azimuth Generator", _azimuthPanel);
        tabbedPane.add("MoveLength Generator", _moveLengthPanel);

        container.add(tabbedPane, BorderLayout.CENTER);


        super.add(container,  BorderLayout.CENTER);
    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);

        RandomWalkMovementStrategy rw = (RandomWalkMovementStrategy)strategyDefinition;
//        _azimuthPanel.setStrategyDefinition(rw.getAzimuthGenerator());
        _azimuthBinding.bind(rw, RandomWalkMovementStrategy.P_AZIMUTH_GENERATOR);
        _moveLengthPanel.setStrategyDefinition(rw.getMoveLengthGenerator());
    }


    private AzimuthGeneratorPanel _azimuthPanel;
    private MoveLengthGeneratorPanel _moveLengthPanel;
    private StrategyParameterBinding _azimuthBinding;
}
