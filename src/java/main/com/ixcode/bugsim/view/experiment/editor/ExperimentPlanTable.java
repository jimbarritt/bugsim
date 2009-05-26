/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor;

import com.ixcode.framework.experiment.model.ExperimentPlanFile;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentPlanTable extends JPanel {
    public ExperimentPlanTable(boolean includeDirectory) {
        super(new BorderLayout());
        initUI(includeDirectory);
    }



    private void initUI(boolean includeDirectory) {


        _table = new CustomTable();

//        _table.setFocusCycleRoot(false);
//        _table.setFocusTraversalKeysEnabled(true);

        _table.setShowHorizontalLines(false);
        _table.setShowVerticalLines(true);
        _table.setCellSelectionEnabled(false);
        _table.setRowSelectionAllowed(true);
        _table.setColumnSelectionAllowed(false);
        _table.setGridColor(Color.lightGray);


        _table.setRowHeight(25);


        _table.setDefaultRenderer(Object.class, new CustomCellRenderer());
        _table.setDefaultEditor(Object.class,  new TableCellEditor(){
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public Object getCellEditorValue() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public boolean isCellEditable(EventObject anEvent) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public boolean shouldSelectCell(EventObject anEvent) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public boolean stopCellEditing() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public void cancelCellEditing() {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void addCellEditorListener(CellEditorListener l) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void removeCellEditorListener(CellEditorListener l) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });


        _tableModel = new ExperimentPlanTableModel(includeDirectory);

        _table.setModel(_tableModel);

        JScrollPane sp = new JScrollPane(_table);


        _table.getColumn(ExperimentPlanTableModel.COL_NAME).setMinWidth(220);
        _table.getColumn(ExperimentPlanTableModel.COL_NAME).setMaxWidth(300);
        _table.getColumn(ExperimentPlanTableModel.COL_NAME).setPreferredWidth(220);
        _table.getColumn(ExperimentPlanTableModel.COL_DESCRIPTION).setMinWidth(300);

        if (includeDirectory) {
            _table.getColumn(ExperimentPlanTableModel.COL_DIRECTORY).setMinWidth(200);
        }

        super.add(sp, BorderLayout.CENTER);
    }


    public void updateEditors() {
        _tableModel.setPlanFiles(_planFiles);


    }

    public void setPlanFiles(List planFiles) {
        _planFiles = planFiles;
        updateEditors();
    }

    public CustomTable getTable() {
        return _table;
    }


    public List getPlanFiles() {
        return _planFiles;
    }

    public ExperimentPlanFile getSelectedPlan() {
        return (ExperimentPlanFile)_planFiles.get(_table.getSelectedRow());
    }


    private List _planFiles;
    private CustomTable _table;
    private ExperimentPlanTableModel _tableModel;
}
