/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.movement;

import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.RandomWalkMovementStrategy;
import com.ixcode.bugsim.view.experiment.editor.forager.movement.azimuth.AzimuthGeneratorPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.movement.movelength.MoveLengthGeneratorPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.DefaultAzimuthStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyParameterBinding;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyDefinition;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 2:37:14 PM by jim
 */
public class RandomWalkStrategyPanel extends StrategyDetailsPanel {

    public RandomWalkStrategyPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, RandomWalkMovementStrategy.class);
        initUI();
    }

    private void initUI() {
        JPanel container = new JPanel(new BorderLayout());

        _azimuthPanel = new AzimuthGeneratorPanel(super.getModelAdapter(), this, 120);
        _moveLengthPanel = new MoveLengthGeneratorPanel(super.getModelAdapter(), this, 120);

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
