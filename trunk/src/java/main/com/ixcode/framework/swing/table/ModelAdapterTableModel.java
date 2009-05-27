/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.swing.table;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.info.PropertyBundle;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.io.IOException;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Jan 25, 2007 @ 6:07:36 PM by jim
 */
public class ModelAdapterTableModel implements TableModel {

    public ModelAdapterTableModel(IModelAdapter modelAdapter, Class modelClass) {
        this(modelAdapter, modelClass, new String[]{});
    }
    public ModelAdapterTableModel(IModelAdapter modelAdapter, Class modelClass, String[] columnPropertyNames) {
        _modelAdapter = modelAdapter;
        _modelClass = modelClass;

        _columns = initColumns(columnPropertyNames);
        _rows = new ArrayList();
    }

    private List initColumns(String[] propertyNames) {
        List columns = new ArrayList();

        List propertyNameList = null;
        if (propertyNames.length == 0) {
            propertyNameList = _modelAdapter.getPropertyNames(_modelClass.getName());
        } else {
            propertyNameList = Arrays.asList(propertyNames);
        }

        for (Iterator itr = propertyNameList.iterator(); itr.hasNext();) {
            String name = (String)itr.next();
            PropertyBundle bundle = _modelAdapter.getPropertyBundle(_modelClass.getName(), name, Locale.UK);
            Class type = _modelAdapter.getPropertyType(_modelClass.getName(), name);
            ModelAdapterTableColumnModel column = new ModelAdapterTableColumnModel(name, bundle.getShortLabel(), type, bundle.getDisplayCharacterCount());
            columns.add(column);
        }

        return columns;
    }

    public int getRowCount() {
        return _rows.size();
    }

    public int getColumnCount() {
        return _columns.size();
    }

    public String getColumnName(int columnIndex) {
        return getColumnModel(columnIndex).getLabel();
    }

    private ModelAdapterTableColumnModel getColumnModel(int columnIndex) {
        return ((ModelAdapterTableColumnModel)_columns.get(columnIndex));
    }

    public Class getColumnClass(int columnIndex) {
        return String.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {        
        try {
            return _modelAdapter.getPropertyValueAsString(_rows.get(rowIndex), getColumnModel(columnIndex).getPropertyName(), Locale.UK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            _modelAdapter.setPropertyValueAsString(_rows.get(rowIndex), getColumnModel(columnIndex).getPropertyName(), (String)aValue, Locale.UK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTableModelListener(TableModelListener l) {
        _listeners.add(l);
    }

    public void removeTableModelListener(TableModelListener l) {
        _listeners.remove(l);
    }

    public void setRows(List rows) {
        _rows = rows;
        fireDataChangedEven();
    }

    private void fireDataChangedEven() {
        TableModelEvent evt = new TableModelEvent(this);
        List listeners = new ArrayList(_listeners); // Incase someone removes themselves whilst responding
        for (Iterator itr = listeners.iterator(); itr.hasNext();) {
            TableModelListener listener = (TableModelListener)itr.next();
            listener.tableChanged(evt);
        }
    }

    public Object getRow(int index) {
        if (index <0 || index>=_rows.size()) {
            return null;
        }
        return _rows.get(index);
    }

    public void fireTableChangedEvent() {
        TableModelEvent evt = new TableModelEvent(this);
        List listeners = new ArrayList(_listeners); // Incase someone removes themselves whilst responding
        for (Iterator itr = listeners.iterator(); itr.hasNext();) {
            TableModelListener listener= (TableModelListener)itr.next();
            listener.tableChanged(evt);
        }
    }

                      private static final Logger log = Logger.getLogger(ModelAdapterTableModel.class);
    private IModelAdapter _modelAdapter;
    private Class _modelClass;
    private List _columns;
    private List _rows;
    private List _listeners = new ArrayList();
}
