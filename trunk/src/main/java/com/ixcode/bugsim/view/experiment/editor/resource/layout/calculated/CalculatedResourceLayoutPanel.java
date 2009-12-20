/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout.calculated;

import com.ixcode.bugsim.view.experiment.editor.resource.layout.ResourceLayoutPanelBase;
import com.ixcode.bugsim.view.experiment.editor.boundary.BoundaryPanel;
import com.ixcode.bugsim.view.experiment.editor.boundary.DefaultBoundaryStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyParameterBinding;
import com.ixcode.bugsim.experiment.parameter.resource.layout.calculated.CalculatedResourceLayoutStrategy;
import com.ixcode.bugsim.experiment.parameter.resource.layout.calculated.CalculatedResourceLayoutType;
import com.ixcode.bugsim.experiment.parameter.resource.layout.predefined.PredefinedResourceLayoutStrategy;
import com.ixcode.bugsim.experiment.parameter.resource.ResourceCategory;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.EnumerationComboBox;
import com.ixcode.framework.swing.property.EnumerationPropertyEditor;
import com.ixcode.framework.swing.property.CheckBoxPropertyEditor;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.javabean.TextAlignment;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Jan 25, 2007 @ 5:15:37 PM by jim
 */
public class CalculatedResourceLayoutPanel extends ResourceLayoutPanelBase {
    public CalculatedResourceLayoutPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, CalculatedResourceLayoutStrategy.class);
        initUI(150);
    }

    private void initUI(int minWidth) {
        IPropertyValueEditor resourceRadiusE = super.addParameterEditor(super.getStrategyClassName(), CalculatedResourceLayoutStrategy.P_RESOURCE_RADIUS, minWidth);
        IPropertyValueEditor interEdgeSepearationE = super.addParameterEditor(super.getStrategyClassName(), CalculatedResourceLayoutStrategy.P_INTER_EDGE_SEPARATION, minWidth);

        IPropertyValueEditor layoutNameE = super.addParameterEditor(super.getStrategyClassName(), CalculatedResourceLayoutStrategy.P_LAYOUT_NAME, "Layout Name", minWidth);
        layoutNameE.setReadonly(true);
        IPropertyValueEditor expectedEggCountE = super.addParameterEditor(super.getStrategyClassName(), CalculatedResourceLayoutStrategy.P_EXPECTED_EGG_COUNT, "Expected Egg Count", minWidth);


        EnumerationComboBox layoutTypeCombo = new EnumerationComboBox();
        layoutTypeCombo.addValue("Corner Centre Only", CalculatedResourceLayoutType.CORNER_CENTRE.getName());
        layoutTypeCombo.addValue("Corner Centre And Edge", CalculatedResourceLayoutType.CORNER_EDGE_CENTRE.getName());
        IPropertyValueEditor layoutTypeE = new EnumerationPropertyEditor(CalculatedResourceLayoutStrategy.P_CALCULATED_LAYOUT_TYPE, "Layout Type", layoutTypeCombo, minWidth);



        super.addPropertyEditor(layoutTypeE);

        IPropertyValueEditor derivedDimensionE= super.addParameterEditor(super.getStrategyClassName(), CalculatedResourceLayoutStrategy.P_BOUNDARY_DIMENSION_DERIVED, "Derived Dimension", minWidth);


        super.addPropertyEditorBinding(layoutNameE);
        super.addPropertyEditorBinding(expectedEggCountE);

        super.addPropertyEditorBinding(resourceRadiusE);
        super.addPropertyEditorBinding(interEdgeSepearationE);
        super.addPropertyEditorBinding(layoutTypeE);
        super.addPropertyEditorBinding(derivedDimensionE);

        _resourceBoundaryPanel = new BoundaryPanel("CalculatedResourceBoundary", super.getModelAdapter(), this);
        _resourceBoundaryPanel.setChangeTypeEnabled(false);
        _resourceBoundaryBinding = new StrategyParameterBinding("CalculatedResourceBoundary", _resourceBoundaryPanel, DefaultBoundaryStrategyDefinitionFactory.INSTANCE);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Boundary", _resourceBoundaryPanel);
        super.getLayoutPanel().add(tabbedPane);


        ((JCheckBox)derivedDimensionE.getEditingComponent()).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable(){
                    public void run() {
                        _resourceBoundaryPanel.updateDerivedStatus();
                    }
                });

            }
        });

    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);

        _resourceBoundaryBinding.bind(strategyDefinition, CalculatedResourceLayoutStrategy.P_LAYOUT_BOUNDARY);
    }

    private BoundaryPanel _resourceBoundaryPanel;
    private StrategyParameterBinding _resourceBoundaryBinding;
}
