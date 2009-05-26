/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout.predefined;

import com.ixcode.bugsim.model.experiment.parameter.resource.layout.predefined.PredefinedResourceLayoutStrategy;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.ResourceLayoutStrategyBase;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.bugsim.view.experiment.editor.boundary.BoundaryPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyParameterBinding;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.swing.property.CheckBoxPropertyEditor;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Jan 28, 2007 @ 4:59:38 PM by jim
 */
public class PredefinedResourceBoundaryPanel extends StrategyDetailsPanel implements ICentredBoundaryContainer {
    public PredefinedResourceBoundaryPanel(IModelAdapter modelAdapter, IParameterMapLookup paramMapLookup) {
        super(modelAdapter, PredefinedResourceLayoutStrategy.class);

        initUI(paramMapLookup);
    }


    private void initUI(IParameterMapLookup paramMapLookup) {

        JPanel boundaryPanel = new JPanel();
        boundaryPanel.setLayout(new BoxLayout(boundaryPanel, BoxLayout.Y_AXIS));

        _resourceBoundaryPanel = new BoundaryPanel("PredefinedResourceBoundary", super.getModelAdapter(), paramMapLookup);

        _strategyBinding = new StrategyParameterBinding("predefinedResourceLayoutBoundary", _resourceBoundaryPanel, new CentredBoundaryStrategyFactory(this));

        _boundaryParameterGroup = new ParameterGroupPanel(super.getModelAdapter());

        

        _centredOnLandscapeEditor = new CheckBoxPropertyEditor("centered", "Landscape Centred", 200);
        _centredOnLandscapeEditor.setValue("true");
        _centredOnLandscapeEditor.setReadonly(true);
        _boundaryParameterGroup.addPropertyEditor(_centredOnLandscapeEditor);
        _centredOnLandscapeEditor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleCentredOnLandscape();
            }
        });

        _calculatedFromResourcesEditor = new CheckBoxPropertyEditor("calculated", "Calculated From resources", 200);
        _calculatedFromResourcesEditor.setValue("true");
        _boundaryParameterGroup.addPropertyEditor(_calculatedFromResourcesEditor);
        _calculatedFromResourcesEditor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleCalculatedFromResources();
            }
        });
        _distanceEditor = _boundaryParameterGroup.addParameterEditor(PredefinedResourceLayoutStrategy.class.getName(), PredefinedResourceLayoutStrategy.P_BOUNDARY_DISTANCE, "Border", 140);

        // We dont yet implement this - need to implement a whole new derivation calculation which ive had enough of for the time being!!
        _calculatedFromResourcesEditor.setValue("false");
        toggleCalculatedFromResources();
        _calculatedFromResourcesEditor.setReadonly(true);


        boundaryPanel.add(_boundaryParameterGroup);
        JPanel boundaryContainer = new JPanel(new BorderLayout());
        boundaryContainer.add(boundaryPanel, BorderLayout.NORTH);
        boundaryPanel.add(_resourceBoundaryPanel);

        super.add(boundaryContainer, BorderLayout.CENTER);
    }


    private void toggleCalculatedFromResources() {
        if (_distanceEditor.getDisplayComponent().isVisible()) {
            _distanceEditor.getDisplayComponent().setVisible(false);
        } else {
            _distanceEditor.getDisplayComponent().setVisible(true);
        }
    }

    private void toggleCentredOnLandscape() {
        if (log.isInfoEnabled()) {
            log.info("Toggling Centre on Landscape Property.");
        }
    }


    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);

        _strategyBinding.unbind();
        PredefinedResourceLayoutStrategy predefinedStrategy = (PredefinedResourceLayoutStrategy)strategyDefinition;
        _boundaryParameterGroup.setModel(strategyDefinition);

        _strategyBinding.bind(predefinedStrategy, PredefinedResourceLayoutStrategy.P_LAYOUT_BOUNDARY);

    }

    public ResourceLayoutStrategyBase getResourceLayoutStrategy() {
        return (ResourceLayoutStrategyBase)super.getStrategyDefinition();
    }


    private static final Logger log = Logger.getLogger(PredefinedResourceBoundaryPanel.class);

    private ParameterGroupPanel _boundaryParameterGroup;
    private IPropertyValueEditor _distanceEditor;
    private JButton _recalcButton;
    private CheckBoxPropertyEditor _centredOnLandscapeEditor;
    private CheckBoxPropertyEditor _calculatedFromResourcesEditor;

    private BoundaryPanel _resourceBoundaryPanel;
    private StrategyParameterBinding _strategyBinding;
}