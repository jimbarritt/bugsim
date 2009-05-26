/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout.predefined;

import com.ixcode.bugsim.model.experiment.parameter.resource.layout.ResourceListFile;
import com.ixcode.bugsim.view.experiment.editor.CustomCellRenderer;
import com.ixcode.bugsim.view.experiment.editor.CustomTable;
import com.ixcode.bugsim.view.experiment.editor.ExperimentPlanTableModel;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ResourceListFileTable extends JPanel {
    public ResourceListFileTable(boolean includeDirectory) {
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


        _table.setRowHeight(30);


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


        _tableModel = new ResourceListFileTableModel(includeDirectory);

        _table.setModel(_tableModel);

        JScrollPane sp = new JScrollPane(_table);


        _table.getColumn(ResourceListFileTableModel.COL_NAME).setMinWidth(220);
        _table.getColumn(ResourceListFileTableModel.COL_NAME).setMaxWidth(300);
        _table.getColumn(ResourceListFileTableModel.COL_NAME).setPreferredWidth(220);
        _table.getColumn(ResourceListFileTableModel.COL_PREVIEW).setMinWidth(300);

        if (includeDirectory) {
            _table.getColumn(ExperimentPlanTableModel.COL_DIRECTORY).setMinWidth(200);
        }

        super.add(sp, BorderLayout.CENTER);
    }


    public void updateEditors() {
        _tableModel.setResourceListFiles(_resourceListFiles);


    }

    public void setResourceListFiles(List resourceListFiles) {
        _resourceListFiles = resourceListFiles;
        updateEditors();
    }

    public CustomTable getTable() {
        return _table;
    }


    public java.util.List getResourceListFiles() {
        return _resourceListFiles;
    }

    public ResourceListFile getSelectedResourceListFile() {
        ResourceListFile selected = null;
        if (_resourceListFiles.size() > 0) {
            selected = (ResourceListFile)_resourceListFiles.get(_table.getSelectedRow());
        }
        return selected;
    }


    private List _resourceListFiles;
    private CustomTable _table;
    private ResourceListFileTableModel _tableModel;
}
