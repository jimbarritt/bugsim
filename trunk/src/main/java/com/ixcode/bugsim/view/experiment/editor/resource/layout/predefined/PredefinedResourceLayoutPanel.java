/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout.predefined;

import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.bugsim.view.experiment.editor.resource.layout.ResourceLayoutPanelBase;
import com.ixcode.bugsim.experiment.parameter.resource.layout.predefined.PredefinedResourceLayoutStrategy;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.swing.BorderFactoryExtension;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.CheckBoxPropertyEditor;
import com.ixcode.framework.javabean.TextAlignment;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Description : Displays the Predefined Resource Layout Strategy (where you specify each resource individually.
 * Created     : Jan 25, 2007 @ 5:15:26 PM by jim
 */
public class PredefinedResourceLayoutPanel extends ResourceLayoutPanelBase implements IParameterMapLookup  {


    public PredefinedResourceLayoutPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, PredefinedResourceLayoutStrategy.class);
        _modelAdapter = modelAdapter;
        initUI();
    }

    private void initUI() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactoryExtension.createEmptyBorder(5));


        _tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        _resourceBoundaryPanel = new PredefinedResourceBoundaryPanel(_modelAdapter, this);

        _resourceListPanel = new PredefinedResourceListPanel(_modelAdapter);

        _tabbedPane.add("Boundary", _resourceBoundaryPanel);
        _tabbedPane.add("Resource List", _resourceListPanel);

        _northPanel = new ParameterGroupPanel(_modelAdapter);
        int minWidth = 150;
        IPropertyValueEditor layoutNameE = _northPanel.addPropertyEditor(PredefinedResourceLayoutStrategy.P_LAYOUT_NAME, "Layout Name", 5, TextAlignment.LEFT, minWidth);
        IPropertyValueEditor expectedEggCountE = _northPanel.addPropertyEditor(PredefinedResourceLayoutStrategy.P_EXPECTED_EGG_COUNT, "Expected Egg Count", 5, TextAlignment.RIGHT, minWidth);
        IPropertyValueEditor searchGridResolution = _northPanel.addPropertyEditor(PredefinedResourceLayoutStrategy.P_SEARCH_GRID_RESOLUTION, "Search Grid Resolution", 5, TextAlignment.LEFT, minWidth);
        IPropertyValueEditor applyFixedRadiusE = _northPanel.addParameterEditor(PredefinedResourceLayoutStrategy.class.getName(),  PredefinedResourceLayoutStrategy.P_APPLY_FIXED_RESOURCE_RADIUS, "Apply Fixed Radius", minWidth);
        final IPropertyValueEditor fixedResourceRadiusE = _northPanel.addParameterEditor(PredefinedResourceLayoutStrategy.class.getName(),  PredefinedResourceLayoutStrategy.P_FIXED_RESOURCE_RADIUS, "Fixed Radius", minWidth);
//        fixedResourceRadiusE.setReadonly(true);

        ((CheckBoxPropertyEditor)applyFixedRadiusE).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (fixedResourceRadiusE.isReadonly()) {
                    fixedResourceRadiusE.setReadonly(false);
                } else {
                    fixedResourceRadiusE.setReadonly(true);
                }
            }
        });

        _northPanel.addPropertyEditorBinding(layoutNameE);
        _northPanel.addPropertyEditorBinding(expectedEggCountE);
        _northPanel.addPropertyEditorBinding(searchGridResolution);
        _northPanel.addPropertyEditorBinding(applyFixedRadiusE);
        _northPanel.addPropertyEditorBinding(fixedResourceRadiusE);


        _northPanel.setBorder(BorderFactory.createEmptyBorder());


        container.add(_northPanel, BorderLayout.NORTH);
        container.add(_tabbedPane, BorderLayout.CENTER);



        super.add(container, BorderLayout.CENTER);

    }


    public void setStrategyDefinition(StrategyDefinition layoutStrategystrategyDefinition) {
        _resourceBoundaryPanel.setStrategyDefinition(layoutStrategystrategyDefinition);
        _resourceListPanel.setStrategyDefinition(layoutStrategystrategyDefinition);
        _northPanel.setModel(layoutStrategystrategyDefinition);

        super.setStrategyDefinition(layoutStrategystrategyDefinition);

    }




    public ParameterMap getParameterMap() {
        return super.getStrategyDefinition() == null ? null : super.getStrategyDefinition().getParameterMap();
    }

    private static final Logger log = Logger.getLogger(PredefinedResourceLayoutPanel.class);

    private IModelAdapter _modelAdapter;

    public void setTab(int index) {
        _tabbedPane.setSelectedIndex(index);
    }

    private JTabbedPane _tabbedPane;

    private PredefinedResourceListPanel _resourceListPanel;
    private PredefinedResourceBoundaryPanel _resourceBoundaryPanel;
    private ParameterGroupPanel _northPanel;
}
