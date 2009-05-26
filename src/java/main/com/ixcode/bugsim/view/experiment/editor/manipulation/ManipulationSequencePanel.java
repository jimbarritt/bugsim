/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import com.ixcode.framework.parameter.model.*;
import com.ixcode.bugsim.view.experiment.editor.CustomTable;
import com.ixcode.bugsim.view.experiment.editor.CustomCellRenderer;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 8, 2007 @ 6:07:14 PM by jim
 */
public class ManipulationSequencePanel extends JPanel {

    public ManipulationSequencePanel(ManipulationSummaryPanel summaryPanel) {
        super(new BorderLayout());
        initUI();
        _summaryPanel = summaryPanel;
    }

    private void initUI() {
        _table = new CustomTable();
        JScrollPane sp = new JScrollPane(_table);
        sp.setMinimumSize(new Dimension(100, 10));
        sp.setMaximumSize(new Dimension(100, 300));
        sp.setPreferredSize(new Dimension(100, 300));
        super.add(sp, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        super.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {
        JPanel container = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        _addRowBtn = new JButton("Add");
        _insertRowBtn = new JButton("Insert");
        _removeRowBtn = new JButton("Remove");

        container.add(_addRowBtn);
        container.add(_insertRowBtn);
        container.add(_removeRowBtn);

        _addRowBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                        addRow();


            }
        });
        _insertRowBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                        insertRow();

            }
        });
        _removeRowBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                        removeRow();

            }
        });

        return container;
    }

    private void addRow() {
        IParameterManipulation newManipulation = _sequence.createTemplateManipulation();

        _sequence.addManipulation(newManipulation);
        setManipulationSequence(_sequence);
    }

    private void insertRow() {
        IParameterManipulation newManipulation = _sequence.createTemplateManipulation();

        int index = _table.getSelectedRow();
        _sequence.addManipulation(index,  newManipulation);
        setManipulationSequence(_sequence);
    }
    private void removeRow() {
        int iRow = _table.getSelectedRow();
        if (iRow == -1) {
            warnUser("Please select a row to remove first...", "Remove Row");
        } else {
            int[] selectedRows = _table.getSelectedRows();
            _sequence.removeManipulations(selectedRows);
            setManipulationSequence(_sequence);
        }

    }

    private void warnUser(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }


    public void setManipulationSequence(ParameterManipulationSequence sequence) {
        _sequence = sequence;

        TableModel previous = _table.getModel();
        if (previous != null) {
            previous.removeTableModelListener(_summaryPanel);
        }

        if (_sequence != null) {

            _table.setModel(new ManipulationSequenceTableModel(_sequence));
            _table.setColumnWidth(0, 50);
            _table.setCellRenderer(new CustomCellRenderer());
            _table.setDefaultEditor(StrategyDefinitionParameter.class, new StrategyTableCellEditor(_table, _summaryPanel.getModelAdapter(), _summaryPanel.getParameterMapLookup()));

            _table.getModel().addTableModelListener(_summaryPanel);
            ((ManipulationSequenceTableModel)_table.getModel()).fireTableModelChanged();
            _table.setVisible(true);
        } else {
            _table.setVisible(false);
        }

    }

    private static final Logger log = Logger.getLogger(ManipulationSequencePanel.class);

    private CustomTable _table;

    private ParameterManipulationSequence _sequence;
    private ManipulationSummaryPanel _summaryPanel;
    private JButton _addRowBtn;
    private JButton _removeRowBtn;

    private JButton _insertRowBtn;
}
