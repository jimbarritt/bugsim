/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.sensor;

import com.ixcode.bugsim.experiment.parameter.forager.sensor.SensorCategory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.sensor.olfactory.OlfactoryStrategyPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.sensor.visual.VisualStrategyPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.swing.table.ModelAdapterTableModel;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 4, 2007 @ 10:09:07 PM by jim
 */
public class SensorCategoryPanel extends ParameterGroupPanel implements IParameterMapLookup  {

    public SensorCategoryPanel(IModelAdapter modelAdapter) {
        super(modelAdapter);
        initUI();
    }



    private void initUI() {
        JPanel container = new JPanel(new BorderLayout());

        _visualStrategyPanel = new VisualStrategyPanel(super.getModelAdapter(), this, 120);
        _olfactoryStrategyPanel = new OlfactoryStrategyPanel(super.getModelAdapter(), this, 120);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("Visual", _visualStrategyPanel);
        tabbedPane.add("Olfactory", _olfactoryStrategyPanel);

        container.add(tabbedPane, BorderLayout.CENTER);

        super.add(container, BorderLayout.CENTER);
    }

    public void setSensorCategory(SensorCategory sensorC) {
        super.setModel(sensorC);
        _sensorC = sensorC;

        _visualStrategyPanel.setStrategyDefinition(_sensorC.getVisualSensorStrategy());
        _olfactoryStrategyPanel.setStrategyDefinition(_sensorC.getOlfactorySensorStrategy());
    }

    public ParameterMap getParameterMap() {
        return (_sensorC!= null) ? _sensorC.getParameterMap() : null;
    }

    private ModelAdapterTableModel _tableModel;
    private VisualStrategyPanel _visualStrategyPanel;
    private SensorCategory _sensorC;
    private OlfactoryStrategyPanel _olfactoryStrategyPanel;
}
