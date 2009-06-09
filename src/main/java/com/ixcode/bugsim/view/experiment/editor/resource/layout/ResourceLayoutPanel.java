/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout;

import com.ixcode.bugsim.model.experiment.parameter.resource.ResourceCategory;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.*;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.bugsim.view.experiment.editor.resource.layout.calculated.CalculatedResourceLayoutPanel;
import com.ixcode.bugsim.view.experiment.editor.resource.layout.levy.LevyWalkResourceLayoutPanel;
import com.ixcode.bugsim.view.experiment.editor.resource.layout.predefined.PredefinedResourceLayoutPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.CardLayoutPanel;
import com.ixcode.framework.swing.property.EnumerationComboBox;
import com.ixcode.framework.swing.property.EnumerationPropertyEditor;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Jan 25, 2007 @ 4:29:51 PM by jim
 */
public class ResourceLayoutPanel extends StrategyPanel {

    public ResourceLayoutPanel(IModelAdapter modelAdapter, IParameterMapLookup lookup) {
        super("ResourceLayout", modelAdapter, lookup, ResourceLayoutStrategyFactory.getRegistry(), "type", 120);

        addCards();
//        super.getLayoutContainer().add(createGeneralPanel(), BorderLayout.NORTH);

    }

    private void addCards() {
        _predefinedLayoutPanel = new PredefinedResourceLayoutPanel(super.getModelAdapter());
        _calculatedLayoutPanel = new CalculatedResourceLayoutPanel(super.getModelAdapter());
//        _levyWalkLayoutPanel = new LevyWalkResourceLayoutPanel(_modelAdapter);

        super.addStrategyDetailPanel(_predefinedLayoutPanel);
        super.addStrategyDetailPanel(_calculatedLayoutPanel);
        super.setChangeTypeEnabled(true);

    }


    private JPanel createGeneralPanel() {
        _generalPanel = new ParameterGroupPanel(super.getModelAdapter());


        _btnSaveResource = new JButton("Export ...");
        _btnPreview = new JButton("Preview ...");
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.TRAILING));
       buttons.add(_btnSaveResource);
       buttons.add(_btnPreview);

        _btnPreview.setEnabled(false);
        _btnSaveResource.setEnabled(false);


        // Could maybe put the implementing class as a lookup...
//        _layoutTypeEditor = new EnumerationPropertyEditor(ResourceCategory.P_AGENT_CLASS, "Layout Type", _layoutTypeCombo, 150, buttons);
//        _generalPanel.addPropertyEditor(_layoutTypeEditor);


        return _generalPanel;
    }


//    public void setLayoutStrategy(ResourceLayoutStrategyBase layoutStrategy) {
//        _layoutStrategy = layoutStrategy;
//
//        ResourceLayoutPanelBase layoutPanel = (ResourceLayoutPanelBase)_cardPanel.getCard(_layoutStrategy.getClass().getName());
//        layoutPanel.setStrategyDefinition(_layoutStrategy);
//        _layoutTypeCombo.setSelectedValue(_layoutStrategy.getClass().getName());
//
//    }



    private static final Logger log = Logger.getLogger(ResourceLayoutPanel.class);


    private ParameterGroupPanel _generalPanel;
    private EnumerationComboBox _layoutTypeCombo;
    private EnumerationPropertyEditor _layoutTypeEditor;
    private PredefinedResourceLayoutPanel _predefinedLayoutPanel;
    private CalculatedResourceLayoutPanel _calculatedLayoutPanel;
    private LevyWalkResourceLayoutPanel _levyWalkLayoutPanel;
    private CardLayoutPanel _cardPanel;
    private JButton _btnSaveResource;
    private JButton _btnPreview;

    private ResourceLayoutStrategyBase _layoutStrategy;
}
