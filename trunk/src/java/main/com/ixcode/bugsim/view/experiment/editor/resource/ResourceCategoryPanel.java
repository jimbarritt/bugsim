/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor.resource;

import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.model.experiment.parameter.resource.ResourceCategory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyParameterBinding;
import com.ixcode.bugsim.view.experiment.editor.resource.layout.ResourceLayoutPanel;
import com.ixcode.bugsim.view.experiment.editor.resource.layout.DefaultLayoutStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.resource.signal.ResourceSignalPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.info.PropertyBundle;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.swing.property.EnumerationComboBox;
import com.ixcode.framework.swing.property.EnumerationPropertyEditor;
import com.ixcode.framework.swing.property.IEnumerationDescriptionLookup;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ResourceCategoryPanel extends JPanel implements IParameterMapLookup {


    public ResourceCategoryPanel(IModelAdapter modelAdapter) {
        super(new BorderLayout());
        _modelAdapter = modelAdapter;
        initUI() ;
    }

    private void initUI() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container,  BoxLayout.Y_AXIS));

        JPanel resourceTypePanel = createResourceTypePanel();
        container.add(resourceTypePanel);

        _tabbedPane = createTabbedPanel();


        super.add(container, BorderLayout.NORTH);
        super.add(_tabbedPane, BorderLayout.CENTER);

    }

    private JTabbedPane createTabbedPanel() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

//        _resourceBoundaryPanel = new BoundaryPanel(_modelAdapter, )
        _resourceLayoutPanel = new ResourceLayoutPanel(_modelAdapter, this);
        _resourceSignalPanel = new ResourceSignalPanel(_modelAdapter, this, 100);

        tabbedPane.add("Layout", _resourceLayoutPanel);
        tabbedPane.add("Signal", _resourceSignalPanel);

        _resourcesLayoutBinding = new StrategyParameterBinding("ResourceLayout", _resourceLayoutPanel, DefaultLayoutStrategyFactory.INSTANCE);

        return tabbedPane;
    }


    /**
     * @todo move all this into a seperate class called "CabbageResourceTypePanel"
     * @return
     */
    private JPanel createResourceTypePanel() {
        _resourceTypeParameterGroup = new ParameterGroupPanel(_modelAdapter);

        _resourceTypeCombo = new EnumerationComboBox();
        _resourceTypeCombo.addValue("Cabbage", CabbageAgent.class.getName());
        PropertyBundle bundle = _modelAdapter.getPropertyBundle(ResourceCategory.class.getName(), ResourceCategory.P_AGENT_CLASS, Locale.UK);
        IEnumerationDescriptionLookup lookup = new IEnumerationDescriptionLookup() {
            public String getDescriptionForValue(String value) {
                return CabbageAgent.class.getName();
            }
        };
        _resourceTypeEditor = new EnumerationPropertyEditor(ResourceCategory.P_AGENT_CLASS, "Resource Type", _resourceTypeCombo, 100, lookup);
        _resourceTypeParameterGroup.addPropertyEditor(_resourceTypeEditor);

//        _resourceTypeCombo.setSelectedValue(CabbageAgent.class.getName());

        _resourceTypeEditor.setReadonly(true);

        String className = ResourceCategory.class.getName();
        _includeFinalEggCount = _resourceTypeParameterGroup.addParameterEditor(className, ResourceCategory.P_INCLUDE_FINAL_EGG_COUNT, 150);

        _eggCountIntervalE = _resourceTypeParameterGroup.addParameterEditor(className, ResourceCategory.P_EGG_COUNT_INTERVAL, 150);

        _resourceTypeParameterGroup.addPropertyEditorBinding(_includeFinalEggCount);
        _resourceTypeParameterGroup.addPropertyEditorBinding(_eggCountIntervalE);


        return _resourceTypeParameterGroup;
    }

    public void setResourceCategory(ResourceCategory resourceCategory) {
        _resourceCategory = resourceCategory;

        _resourcesLayoutBinding.bind(resourceCategory, ResourceCategory.P_RESOURCE_LAYOUT);
//        _resourceLayoutPanel.setStrategyDefinition(resourceCategory.getLayoutStrategy());
        _resourceSignalPanel.setStrategyDefinition(resourceCategory.getResourceSignal());

        _resourceTypeParameterGroup.setModel(resourceCategory);
    
    }


    public ParameterMap getParameterMap() {
        return (_resourceCategory != null) ? _resourceCategory.getParameterMap() : null;
    }


    private IModelAdapter _modelAdapter;
    private ParameterGroupPanel _resourceTypeParameterGroup;
    private EnumerationComboBox _resourceTypeCombo;
    private EnumerationPropertyEditor _resourceTypeEditor;
    private JTabbedPane _tabbedPane;
    private ResourceLayoutPanel _resourceLayoutPanel;
    private ResourceSignalPanel _resourceSignalPanel;
    private ResourceCategory _resourceCategory;
    private IPropertyValueEditor _eggCountIntervalE;
    private IPropertyValueEditor _includeFinalEggCount;
    private StrategyParameterBinding _resourcesLayoutBinding;
}
