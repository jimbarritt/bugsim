/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor.extent;

import com.ixcode.bugsim.view.experiment.editor.boundary.BoundaryPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.ExtentStrategyBase;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
abstract class ExtentStrategyPanelBase extends StrategyDetailsPanel {


    public ExtentStrategyPanelBase(IModelAdapter modelAdapter, Class strategyClass) {
        super(modelAdapter, strategyClass);

        initUI();
    }

    private void initUI() {
        JPanel container = new JPanel();
        BoxLayout boxLayout = new BoxLayout(container, BoxLayout.Y_AXIS);
        container.setLayout(boxLayout);


        JPanel outerBoundary = createOuterBoundaryPanel();
        _outerBoundaryPanel.setChangeTypeEnabled(false);
        _boundaryTabs = new JTabbedPane();
        addBoundaryTab("Outer Boundary", outerBoundary);

        JPanel strategyPanel = createStrategyPanel();

        container.add(strategyPanel);

        container.add(_boundaryTabs);

        super.add(container, BorderLayout.NORTH);

    }

    protected void addBoundaryTab(String name, JComponent boundaryPanel) {
        _boundaryTabs.addTab(name, boundaryPanel);
    }

    private JPanel createOuterBoundaryPanel() {
        JPanel container = new JPanel(new BorderLayout());

        _outerBoundaryPanel = new BoundaryPanel("OuterLandscapeBoundary", super.getModelAdapter(), this);

        container.add(_outerBoundaryPanel, BorderLayout.NORTH);
        return container;
    }

    protected abstract JPanel createStrategyPanel();


    public BoundaryPanel getOuterBoundaryPanel() {
        return _outerBoundaryPanel;
    }


    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);

        ExtentStrategyBase es = (ExtentStrategyBase)strategyDefinition;
        _outerBoundaryPanel.setStrategyDefinition(es.getOuterBoundary());

        Parameter outerBoundaryP = es.getStrategyS().findParameter(ExtentStrategyBase.P_OUTER_BOUNDARY);
        StrategyDefinitionParameter outerBoundaryS = outerBoundaryP.getStrategyDefinitionValue();

//        Parameter dimensionP = outerBoundaryS.findParameter(RectangularBoundaryStrategy.P_DIMENSIONS);
//        if (log.isInfoEnabled()) {
//            log.info("outerBoundaryDimensions: " + dimensionP.getValue());
//        }



    }


    public void setTab(int index) {
        _boundaryTabs.setSelectedIndex(index);
    }


    private static final Logger log = Logger.getLogger(ExtentStrategyPanelBase.class);
    private BoundaryPanel _outerBoundaryPanel;

    private JTabbedPane _boundaryTabs;


}
