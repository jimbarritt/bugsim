/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.sensor.olfactory;

import com.ixcode.bugsim.experiment.parameter.forager.sensor.olfactory.OlfactorySensorStrategy;
import com.ixcode.bugsim.experiment.parameter.forager.sensor.olfactory.SignalSensorOlfactoryStrategyDefinition;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.swing.JFrameExtension;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.table.ModelAdapterTableModel;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 6, 2007 @ 2:14:24 PM by jim
 */
public class SignalSensorOlfactoryStrategyPanel extends StrategyDetailsPanel {

    public SignalSensorOlfactoryStrategyPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, SignalSensorOlfactoryStrategyDefinition.class);

        _modelAdapter = modelAdapter;
        initUI(minWidth);
    }

    private void initUI(int minWidth) {
        JPanel container = new JPanel(new BorderLayout());
//        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));


        _tableModel = new ModelAdapterTableModel(_modelAdapter, OlfactorySensorStrategy.class, TABLE_PROPERTY_NAMES);
        _table = new JTable(_tableModel);


        JScrollPane sp = new JScrollPane(_table);
        sp.setMinimumSize(new Dimension(10,10));
        sp.setPreferredSize(new Dimension(1000, 100));
        sp.setMaximumSize(new Dimension(1000, 200));

           JPanel tableContainer = new JPanel(new BorderLayout());

        _northPanel = new StrategyDetailsPanel(_modelAdapter, SignalSensorOlfactoryStrategyDefinition.class);

        IPropertyValueEditor signalDeltaE = _northPanel.addParameterEditor(super.getStrategyClassName(), SignalSensorOlfactoryStrategyDefinition.P_SIGNAL_DELTA_SENSITIVITY, 150);
        IPropertyValueEditor directionDeltaE = _northPanel.addParameterEditor(super.getStrategyClassName(), SignalSensorOlfactoryStrategyDefinition.P_DIRECTION_DELTA_MAX, 150);

        super.addPropertyEditorBinding(signalDeltaE);
        super.addPropertyEditorBinding(directionDeltaE);

        tableContainer.add(sp, BorderLayout.CENTER);
        JPanel buttons = createButtonPanel();
        tableContainer.add(buttons, BorderLayout.SOUTH);


        container.add(_northPanel, BorderLayout.NORTH);

        JPanel sensorSection = new JPanel(new BorderLayout());
        sensorSection.add(tableContainer, BorderLayout.CENTER);


        _olfactorySensorPanel = new OlfactorySensorPanel(_modelAdapter, minWidth);

        _table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!_isLoading) {
                    updateSensorPanel();
                }
            }
        });

          _tableUpdater = new TableUpdater(this, _tableModel, _table);



        JPanel southPanel = new JPanel(new BorderLayout());


        southPanel.add(_olfactorySensorPanel, BorderLayout.CENTER);
        JTabbedPane southTab = new JTabbedPane();
        southTab.add("Sensor", southPanel);

        sensorSection.add(southTab, BorderLayout.SOUTH);

        JTabbedPane sensorTabContainer = new JTabbedPane();
                sensorTabContainer.addTab("Sensors", sensorSection);
                container.add(sensorTabContainer, BorderLayout.CENTER);
        
        super.add(container, BorderLayout.CENTER);


    }

    private JPanel createButtonPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        _btnAdd = new JButton("Add");
        p.add(_btnAdd);
        _btnAdd.setEnabled(false);
        return p;
    }

    public void updateSensorPanel() {
        int index = _table.getSelectedRow();
        OlfactorySensorStrategy s = (OlfactorySensorStrategy)_tableModel.getRow(index);
        if (s != null) {
            _olfactorySensorPanel.setStrategyDefinition(s);
            if (_sensorStrategy != null) {
                _sensorStrategy.removePropertyChangeListener(_tableUpdater);
            }
            _sensorStrategy = s;
            _sensorStrategy.addPropertyChangeListener(_tableUpdater);
        }
    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);

        SignalSensorOlfactoryStrategyDefinition sso = (SignalSensorOlfactoryStrategyDefinition)strategyDefinition;
        _tableModel.setRows(sso.getSensorStrategies());
        _table.setRowSelectionInterval(0, 0);


    }

    private ModelAdapterTableModel _tableModel;
    private JTable _table;

    private static final String[] TABLE_PROPERTY_NAMES = new String[]{
            OlfactorySensorStrategy.P_HEADING_FROM_AGENT,
            OlfactorySensorStrategy.P_DISTANCE_FROM_AGENT,
            OlfactorySensorStrategy.P_SIGNAL_SURFACE_NAME,
            OlfactorySensorStrategy.P_MAX_SENSITIVITY,
            OlfactorySensorStrategy.P_MIN_SENSITIVITY


    };


    private static class TableUpdater implements PropertyChangeListener {
        public TableUpdater(SignalSensorOlfactoryStrategyPanel parent, ModelAdapterTableModel tableModel, JTable table) {
            _model = tableModel;
            _parent = parent;
            _parentTable = table;
        }

        public void propertyChange(PropertyChangeEvent evt) {

            int index = _parentTable.getSelectionModel().getAnchorSelectionIndex();

            _parent.setLoading(true);
            _model.fireTableChangedEvent();
            _parentTable.setRowSelectionInterval(index, index);
            _parent.setLoading(false);
        }
        private static final Logger log = Logger.getLogger(TableUpdater.class);
        private ModelAdapterTableModel _model;
        private SignalSensorOlfactoryStrategyPanel _parent;
        private JTable _parentTable;
    }
    public static void main(String[] args) {
        JFrameExtension f = new JFrameExtension("Test ");

        SignalSensorOlfactoryStrategyPanel p = new SignalSensorOlfactoryStrategyPanel(new JavaBeanModelAdapter(), 100);
        f.getContentPane().add(p);
        f.show();
    }

    public void setLoading(boolean loading) {
        _isLoading = loading;
    }

    private OlfactorySensorPanel _olfactorySensorPanel;
    private IModelAdapter _modelAdapter;

    private  OlfactorySensorStrategy _sensorStrategy;
    private TableUpdater _tableUpdater;
    private boolean _isLoading;
    private JButton _btnAdd;
    private StrategyDetailsPanel _northPanel;
}
