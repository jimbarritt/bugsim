/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor.extent;

import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.info.PropertyBundle;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.DistancedExtentStrategy;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.Locale;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
class DistancedExtentStrategyPanel extends ExtentStrategyPanelBase {
    public DistancedExtentStrategyPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, DistancedExtentStrategy.class);
    }

    protected JPanel createStrategyPanel() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        ParameterGroupPanel extentStrategyParameterPanel = new ParameterGroupPanel(super.getModelAdapter());

        PropertyBundle bundle = getModelAdapter().getPropertyBundle(DistancedExtentStrategy.class.getName(), DistancedExtentStrategy.P_DISTANCE, Locale.UK);
        Class propertyType = getModelAdapter().getPropertyType(DistancedExtentStrategy.class.getName(), DistancedExtentStrategy.P_DISTANCE);
        IPropertyValueEditor distanceEditor = extentStrategyParameterPanel.addPropertyEditor(DistancedExtentStrategy.P_DISTANCE, propertyType, bundle.getLabel(), bundle.getDisplayCharacterCount(), bundle.getTextAlignment(), 100);

        super.addPropertyEditorBinding(distanceEditor);

        _innerBoundaryPanel = new InnerBoundaryPanel(super.getModelAdapter(), this);

        container.add(extentStrategyParameterPanel);

        super.addBoundaryTab("Inner Boundary", _innerBoundaryPanel);

        return container;
    }


    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        DistancedExtentStrategy ds = (DistancedExtentStrategy)strategyDefinition;


        super.setStrategyDefinition(strategyDefinition);


        _innerBoundaryPanel.setStrategyDefinition(ds);
    }


    private static final Logger log = Logger.getLogger(DistancedExtentStrategyPanel.class);

    private InnerBoundaryPanel _innerBoundaryPanel;


}
