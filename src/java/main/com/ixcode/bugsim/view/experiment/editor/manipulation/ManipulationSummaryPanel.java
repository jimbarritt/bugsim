/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.parameter.view.tree.ParameterSelectionDialog;
import com.ixcode.framework.swing.table.ModelAdapterTableModel;
import com.ixcode.bugsim.view.experiment.editor.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 8, 2007 @ 4:32:02 PM by jim
 */
public class ManipulationSummaryPanel extends JPanel implements TableModelListener {

    public ManipulationSummaryPanel(IModelAdapter modelAdapter, IParameterMapLookup lookup) {
        super(new BorderLayout());
        _modelAdapter = modelAdapter;
        _parameterMapLookup = lookup;
        initUI(modelAdapter);

    }

    private void initUI(IModelAdapter modelAdapter) {
        JPanel container = new JPanel(new BorderLayout());

        _tableModel = new ModelAdapterTableModel(modelAdapter, ParameterManipulationSequence.class, TABLE_PROPERTY_NAMES);
        _table = new CustomTable();
        _table.setModel(_tableModel);
        _table.setCellRenderer(new CustomCellRenderer());


        _table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        _table.getColumnModel().getColumn(0).setMinWidth(300);
        _table.getColumnModel().getColumn(0).setWidth(300);
        _table.getColumnModel().getColumn(1).setMinWidth(450);
        _table.getColumnModel().getColumn(1).setWidth(450);

        _table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                updateSelectedSequence();
            }
        });

        JScrollPane sp = new JScrollPane(_table);
        sp.setMinimumSize(new Dimension(10, 10));
        int height = 200;
        sp.setPreferredSize(new Dimension(1000, height));
        sp.setMaximumSize(new Dimension(1000, height));
        sp.setSize(400, height);

        JPanel buttons = createButtonPanel();

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttons, BorderLayout.EAST);

        _label = new JLabel("Manipulations");
        _label.setForeground(Color.gray);
        southPanel.add(_label, BorderLayout.WEST);

        JPanel tableContainer = new JPanel(new BorderLayout());

        tableContainer.add(sp, BorderLayout.CENTER);
        tableContainer.add(southPanel, BorderLayout.SOUTH);

        container.add(tableContainer, BorderLayout.NORTH);


        super.add(container, BorderLayout.CENTER);
    }

    private JPanel createButtonPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        _btnAddParam = new JButton("Add Parameter");
        p.add(_btnAddParam);

        _btnAddParam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addParameterToSequence();
            }


        });

        _btnAdd = new JButton("Add Sequence");
        p.add(_btnAdd);

        _btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSequence();
            }
        });

        _btnRemove = new JButton("Remove Sequence");
        p.add(_btnRemove);

        _btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSequence();
            }
        });
        return p;
    }

    public void addSequence() {
        if (_parameterSelect != null) {
            _parameterSelect.selectParameter();
            if (!_parameterSelect.isCancelled()) {
                if (log.isDebugEnabled()) {
                    log.debug("Adding a manipulation for parameter: " + _parameterSelect.getSelectedParameter().getFullyQualifiedName());
                }
                Parameter manipulatedP = _parameterSelect.getSelectedParameter();

                _manipulations.addManipulationSequence(manipulatedP, manipulatedP.getValue());
                setParameterManipulations(_manipulations);
            }
        }
    }

    private void addParameterToSequence() {
        if (_parameterSelect != null) {
            _parameterSelect.selectParameter();
            if (!_parameterSelect.isCancelled()) {
                if (log.isDebugEnabled()) {
                    log.debug("Adding a parameter to maniuplation: " + _parameterSelect.getSelectedParameter().getFullyQualifiedName());
                }
                Parameter manipulatedP = _parameterSelect.getSelectedParameter();

                int[] iRows = _table.getSelectedRows();

                for (int i = 0; i < iRows.length; ++i) {
                    ParameterManipulationSequence oldSequence = _manipulations.getManipulationSequence(iRows[i]);
                    ParameterManipulationSequence newSequence = addAnotherParameter(manipulatedP, oldSequence);
                    _manipulations.replaceManipulationSequence(oldSequence, newSequence);
                }
                setParameterManipulations(_manipulations);

            }
        }
    }

    private ParameterManipulationSequence addAnotherParameter(Parameter manipulatedP, ParameterManipulationSequence oldSequence) {
        List manips = oldSequence.getManipulations();
        List newManips = new ArrayList();
        for (Iterator itr = manips.iterator(); itr.hasNext();) {
            IParameterManipulation manipulation = (IParameterManipulation)itr.next();
            if (manipulation instanceof ParameterManipulation) {
                MultipleParameterManipulation mpm = new MultipleParameterManipulation();
                mpm.addParameterManipulation(manipulation);
                mpm.addParameterManipulation(new ParameterManipulation(manipulatedP, manipulatedP.getValue()));
                newManips.add(mpm);
            } else if (manipulation instanceof MultipleParameterManipulation) {
                MultipleParameterManipulation mpm = (MultipleParameterManipulation)manipulation;
                mpm.addParameterManipulation(new ParameterManipulation(manipulatedP, manipulatedP.getValue()));
                newManips.add(mpm);
            } else {
                throw new IllegalStateException("Unknown manipulation Type: " + manipulation.getClass().getName());
            }
        }
        return new ParameterManipulationSequence(oldSequence.getParameterMap(), newManips);

    }


    public void removeSequence() {
        if (_table.getSelectedRow() > -1) {
            int[] iRows = _table.getSelectedRows();
            _manipulations.removeManipulationSequences(iRows);
            setParameterManipulations(_manipulations);
        }
    }


    public void setParameterManipulations(ParameterManipulations manipulations) {
        _manipulations = manipulations;
        _tableModel.setRows(manipulations.getManipulationSequenceObjects());
        _label.setText("Contains: " + _manipulations.getManipulationSequenceObjects().size() + " sequences.");
        if (_manipulations.getManipulationSequenceObjects().size() > 0) {
            _table.getSelectionModel().setSelectionInterval(0, 0);
        }
        if (_parameterSelect == null || (manipulations.getParameterMap() != _parameterSelect.getParameterMap())) {
            _parameterSelect = new ParameterSelectionDialog((JFrame)this.getRootPane().getParent(), manipulations.getParameterMap());
        } else if (_selectedSequence != null) {
            _parameterSelect.refresh(_selectedSequence.getParameterMap());
        }

        if (_manipulations.getManipulationSequenceObjects().size() == 0) {
            updateSelectedSequence();
        }
    }

    public ParameterManipulationSequence getSelectedSequence() {
        return _selectedSequence;
    }

    public void setSelectedSequence(ParameterManipulationSequence sequence) {
        Object oldVal = _selectedSequence;
        _selectedSequence = sequence;
        if (sequence == null) {
            super.firePropertyChange(P_SELECTED_SEQUENCE, "OLD_VAL", _selectedSequence);  // force it to fire
        } else {
            super.firePropertyChange(P_SELECTED_SEQUENCE, oldVal, _selectedSequence);
        }


    }

    public void updateSelectedSequence() {

        if (!_updating) {
            if (log.isDebugEnabled()) {
                log.debug("updateSelectedSequence");
            }
            _updating = true;
            int index = _table.getSelectionModel().getAnchorSelectionIndex();
            if (index == -1 || index >= _manipulations.getManipulationSequenceObjects().size() || _manipulations.getManipulationSequenceObjects().size() == 0)
            {
                setSelectedSequence(null);
            } else {
                setSelectedSequence((ParameterManipulationSequence)_manipulations.getManipulationSequenceObjects().get(index));
                _table.getSelectionModel().setSelectionInterval(index, index);

            }
            _updating = false;
        }
    }

    public IModelAdapter getModelAdapter() {
        return _modelAdapter;
    }

    public IParameterMapLookup getParameterMapLookup() {
        return _parameterMapLookup;
    }

    private ParameterManipulations _manipulations;
    private ModelAdapterTableModel _tableModel;
    private static final String[] TABLE_PROPERTY_NAMES = new String[]{
            ParameterManipulationSequence.P_PARAMETER_SUMMARY,
            ParameterManipulationSequence.P_SUMMARY
    };

    /**
     * Will be fired when the sequence details change in the other panel...
     *
     * @param e
     */
    public void tableChanged(TableModelEvent e) {
        if (log.isDebugEnabled()) {
            log.debug("TableModelChanged! firing summary model changed!");
        }

        _updating = true;
        if (_selectedSequence != null) {
            _selectedSequence.refreshSummaries();
        }
        _tableModel.fireTableChangedEvent();
        _updating = false;
    }

    private static final Logger log = Logger.getLogger(ManipulationSummaryPanel.class);
    private JButton _btnAdd;
    private CustomTable _table;
    private JLabel _label;
    public static final String P_SELECTED_SEQUENCE = "selectedSequence";
    private ParameterManipulationSequence _selectedSequence;
    private ParameterSelectionDialog _parameterSelect;
    private JButton _btnRemove;
    private boolean _updating;
    private IModelAdapter _modelAdapter;
    private IParameterMapLookup _parameterMapLookup;
    private JButton _btnAddParam;
}
