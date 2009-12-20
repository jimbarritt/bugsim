/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.signal;

import com.ixcode.bugsim.experiment.parameter.resource.signal.MultipleSurfaceSignalStrategy;
import com.ixcode.bugsim.experiment.parameter.resource.signal.surface.FunctionalSignalSurfaceStrategy;
import com.ixcode.bugsim.view.experiment.editor.resource.signal.surface.SignalSurfacePanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.swing.table.ModelAdapterTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 6, 2007 @ 6:08:57 PM by jim
 */
public class MultipleSurfaceSignalStrategyPanel extends StrategyDetailsPanel {

    public MultipleSurfaceSignalStrategyPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, MultipleSurfaceSignalStrategy.class);
        initUI(minWidth);
    }

    private void initUI(int minWidth) {
        JPanel container = new JPanel(new BorderLayout());



        _tableModel = new ModelAdapterTableModel(super.getModelAdapter(), FunctionalSignalSurfaceStrategy.class, TABLE_PROPERTY_NAMES);
        _table = new JTable(_tableModel);


        JScrollPane sp = new JScrollPane(_table);
        sp.setMinimumSize(new Dimension(10, 10));
        sp.setPreferredSize(new Dimension(1000, 100));
        sp.setMaximumSize(new Dimension(1000, 100));

        JPanel buttons = createButtonPanel();

        JPanel tableContainer = new JPanel(new BorderLayout());

        tableContainer.add(sp, BorderLayout.CENTER);
        tableContainer.add(buttons, BorderLayout.SOUTH);

        container.add(tableContainer, BorderLayout.CENTER);


        _surfacePanel = new SignalSurfacePanel(super.getModelAdapter(), this, minWidth);

        _table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!_isLoading) {
                    updateSurfacePanel();
                }

            }


        });

//                 _tableUpdater = new TableUpdater(this, _tableModel, _table);



        JPanel southPanel = new JPanel(new BorderLayout());

        JTabbedPane southTab = new JTabbedPane();


        southTab.add("Surface", _surfacePanel);

        southPanel.add(southTab, BorderLayout.CENTER);

        container.add(southPanel, BorderLayout.SOUTH);
        super.add(container, BorderLayout.CENTER);


    }

    private JPanel createButtonPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        _btnAdd = new JButton("Add");
        p.add(_btnAdd);
        _btnAdd.setEnabled(false);
        return p;
    }

    public void updateSurfacePanel() {
        int index = _table.getSelectedRow();
        FunctionalSignalSurfaceStrategy s = (FunctionalSignalSurfaceStrategy)_tableModel.getRow(index);
        _surfacePanel.setStrategyDefinition(s);
//        if (_surfaceStrategy != null) {
//            _surfaceStrategy.removePropertyChangeListener(_tableUpdater);
//        }
//        _sensorStrategy = s;
//        _sensorStrategy.addPropertyChangeListener(_tableUpdater);
    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);

        MultipleSurfaceSignalStrategy sso = (MultipleSurfaceSignalStrategy)strategyDefinition;
        _tableModel.setRows(sso.getSignalSurfaces());
        _table.setRowSelectionInterval(0, 0);
    }

    private static final String[] TABLE_PROPERTY_NAMES = new String[]{
            FunctionalSignalSurfaceStrategy.P_SURFACE_NAME,
            FunctionalSignalSurfaceStrategy.P_INCLUDE_SURVEY
    };

    private ModelAdapterTableModel _tableModel;
    private JTable _table;
    private SignalSurfacePanel _surfacePanel;
    private boolean _isLoading;
    private JButton _btnAdd;
}
