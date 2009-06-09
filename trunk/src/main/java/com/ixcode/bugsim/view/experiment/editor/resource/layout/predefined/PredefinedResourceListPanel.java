/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout.predefined;

import com.ixcode.bugsim.model.experiment.input.CabbageLoader;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.predefined.CabbageInitialisationParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.predefined.PredefinedResourceLayoutStrategy;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.math.scale.IDistanceUnit;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.swing.property.ReadOnlyPropertyEditor;
import com.ixcode.framework.swing.table.ModelAdapterTableModel;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Jan 28, 2007 @ 4:43:24 PM by jim
 */
public class PredefinedResourceListPanel extends StrategyDetailsPanel {

    public PredefinedResourceListPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, PredefinedResourceLayoutStrategy.class);

        initUI();

    }

    private void initUI() {
        JPanel container = new JPanel(new BorderLayout());
        JPanel titlePanel = createTitlePanel();

        container.add(titlePanel, BorderLayout.NORTH);

        _tableModel = new ModelAdapterTableModel(super.getModelAdapter(), CabbageInitialisationParameters.class);
        _table = new JTable(_tableModel);
        JScrollPane sp = new JScrollPane(_table);

        int height = 150;
        sp.setMinimumSize(new Dimension(10, 10));
           sp.setPreferredSize(new Dimension(1000, height));
           sp.setMaximumSize(new Dimension(1000, height));


        container.add(sp, BorderLayout.CENTER);

        JPanel buttonPanel = createResourcesButtons();
        super.add(buttonPanel, BorderLayout.SOUTH);

        super.add(container, BorderLayout.CENTER);
    }
    private JPanel createTitlePanel() {
        _titleParameterGroup = new ParameterGroupPanel(super.getModelAdapter());

        _initialisationTypeField = new ReadOnlyPropertyEditor("initialisationType", "Initialisation Type", 150);
        _initialisationTypeField.setValue(CabbageInitialisationParameters.class.getName().substring(CabbageInitialisationParameters.class.getName().lastIndexOf(".") + 1));
        _titleParameterGroup.addPropertyEditor(_initialisationTypeField);


        return _titleParameterGroup;
    }

    private JPanel createResourcesButtons() {
        JPanel container = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        _btnAddResource = new JButton("Add...");
        _btnRemoveResource = new JButton("Remove");
        _btnClearResources = new JButton(new ClearResourceListAction(this));
        _btnLoadResource = new JButton(new ImportResourceListAction(this));
        buttons.add(_btnLoadResource);
        buttons.add(_btnClearResources);

        buttons.add(_btnAddResource);
        buttons.add(_btnRemoveResource);

        _btnAddResource.setEnabled(false);
        _btnRemoveResource.setEnabled(false);

        _resourceCountField = new ReadOnlyPropertyEditor("resourceCount", "Number Of Resources", 150);
        _resourceCountField .setValue("0");
//        _titleParameterGroup.addPropertyEditor(_resourceCountField);

        container.add(buttons, BorderLayout.EAST);
        container.add(_resourceCountField, BorderLayout.WEST);

        return container;

    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);
        _tableModel.setRows(((PredefinedResourceLayoutStrategy)super.getStrategyDefinition()).getResources());
        _resourceCountField.setValue(_tableModel.getRowCount());
    }



    public void importResources(File file, double radius, IDistanceUnit inputUnits, ScaledDistance logicalScale) {
//        if (log.isInfoEnabled()) {
//            log.info("Loading resources from file '" + file.getAbsolutePath() + "'");
//        }

        if (!file.exists()) {
            throw new RuntimeException("Could not find cabbage parameter file '" + file.getAbsolutePath() + "'");
        }

        try {
            CabbageLoader loader = new CabbageLoader();
            List resources =  loader.loadCabbageInitialisationParameters(file.getAbsolutePath(), radius, inputUnits, logicalScale);
            _tableModel.setRows(resources);
            _resourceCountField.setValue(_tableModel.getRowCount());
            ((PredefinedResourceLayoutStrategy)super.getStrategyDefinition()).setResources(resources);
        } catch (IOException e) {
            throw new RuntimeException("IOException loading cabbage initialisation parameters: " + e.getMessage(), e);
        }
    }

    public void clearResourceList() {
        List resources = new ArrayList();
        _tableModel.setRows(resources);
        ((PredefinedResourceLayoutStrategy)super.getStrategyDefinition()).setResources(resources);
        _resourceCountField.setValue(_tableModel.getRowCount());
    }


    private static final Logger log = Logger.getLogger(PredefinedResourceListPanel.class);
    private ParameterGroupPanel _titleParameterGroup;
    private ReadOnlyPropertyEditor _initialisationTypeField;

    private ReadOnlyPropertyEditor _resourceCountField;
    private JButton _btnAddResource;
    private JButton _btnRemoveResource;
    private JButton _btnLoadResource;





    private ModelAdapterTableModel _tableModel;
    private JTable _table;


    private JButton _btnClearResources;
}
