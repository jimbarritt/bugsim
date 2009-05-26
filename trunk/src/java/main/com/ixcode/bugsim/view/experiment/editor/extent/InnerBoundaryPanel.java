/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.extent;

import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.boundary.BoundaryPanel;
import com.ixcode.bugsim.view.experiment.editor.boundary.DefaultBoundaryStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyParameterBinding;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.DerivedParameter;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.DistancedExtentStrategy;
import com.ixcode.framework.swing.property.ReadOnlyPropertyEditor;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Jan 28, 2007 @ 6:45:27 PM by jim
 */
public class InnerBoundaryPanel extends StrategyDetailsPanel {

    public InnerBoundaryPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup) {
        super(modelAdapter, DistancedExtentStrategy.class);
        initUI(parameterMapLookup);
    }

    private void initUI(IParameterMapLookup parameterMapLookup) {
        JPanel container = new JPanel(new BorderLayout());

        _innerBoundaryDerivedParameterEditor = new ReadOnlyPropertyEditor(P_DERIVED_BOUNDARY_NAME, "Derived from", 100);

        _boundaryPanel = new BoundaryPanel("InnerLandscapeBoundary", super.getModelAdapter(), parameterMapLookup);
        _boundaryPanel.setChangeTypeEnabled(false);


        _boundaryBinding = new StrategyParameterBinding("innerBoundaryPanel", _boundaryPanel, DefaultBoundaryStrategyDefinitionFactory.INSTANCE);

        container.add(_boundaryPanel, BorderLayout.NORTH);
        container.add(_innerBoundaryDerivedParameterEditor, BorderLayout.SOUTH);
        super.add(container, BorderLayout.CENTER);
    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);
        DistancedExtentStrategy ds = (DistancedExtentStrategy)strategyDefinition;


        _boundaryBinding.bind(ds, DistancedExtentStrategy.P_INNER_BOUNDARY);

        updateReadOnlyState();



    }

    private void updateReadOnlyState() {
        DistancedExtentStrategy ds = (DistancedExtentStrategy)getStrategyDefinition();
        Parameter innerBoundaryP = ds.getInnerBoundaryP();
        if (innerBoundaryP instanceof DerivedParameter) {
            _innerBoundaryDerivedParameterEditor.setValue(innerBoundaryP.getStrategyDefinitionValue().getFullyQualifiedName());
            _innerBoundaryDerivedParameterEditor.setVisible(true);
            _boundaryPanel.setParametersReadOnly(true);
        } else {
            _innerBoundaryDerivedParameterEditor.setVisible(false);
            _boundaryPanel.setParametersReadOnly(false);
        }
    }


    public BoundaryPanel getBoundaryPanel() {
        return _boundaryPanel;
    }




    private static final Logger log = Logger.getLogger(InnerBoundaryPanel.class);


    private BoundaryPanel _boundaryPanel;
    private ReadOnlyPropertyEditor _innerBoundaryDerivedParameterEditor;
    private static final String P_DERIVED_BOUNDARY_NAME = "derivedBoundaryName";

    private StrategyParameterBinding _boundaryBinding;
}
