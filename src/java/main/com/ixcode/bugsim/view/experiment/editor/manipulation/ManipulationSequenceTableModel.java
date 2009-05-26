/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.javabean.IntrospectionUtils;
import com.ixcode.framework.javabean.convert.IValueConverter;

import javax.swing.table.TableModel;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;

import java.util.*;

import org.apache.log4j.Logger;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 4, 2007 @ 12:05:37 PM by jim
 */
public class ManipulationSequenceTableModel implements TableModel {

    public ManipulationSequenceTableModel(ParameterManipulationSequence sequence) {
        setSequence(sequence);
    }

    public void setSequence(ParameterManipulationSequence sequence) {
        _sequence = sequence;

        int colIndex = 0;
        _columnNames.add("Id");
        _columnTypes.add(String.class);
        _columnIndexMap.put("Id", new Integer(colIndex));
        colIndex++;

        List parameters = sequence.getParameters();
        for (Iterator itr = parameters.iterator(); itr.hasNext();) {
            Parameter param = (Parameter)itr.next();
            _columnNames.add(param.getName());
            _columnIndexMap.put(param.getName(), new Integer(colIndex));
            Class type = (param.getValue() == null) ?  Object.class  : param.getValue().getClass();
            _columnTypes.add(type);
            colIndex++;
        }




        int sequenceId = 1;
        for (Iterator itr = sequence.getManipulations().iterator(); itr.hasNext();) {
            IParameterManipulation manipulation = (IParameterManipulation)itr.next();
            Object[] row = new Object[_columnNames.size()];
            row[0] = new Integer(sequenceId);
            setRowValue(manipulation, row);
            _rows.add(row);
            sequenceId++;
        }
        fireTableModelChanged();
    }

    public void fireTableModelChanged() {
        TableModelEvent evt = new TableModelEvent(this);
        List listeners = new ArrayList(_modelListeners); // Incase someone removes themselves whilst responding
        for (Iterator itr = listeners.iterator(); itr.hasNext();) {
            TableModelListener listener = (TableModelListener)itr.next();
            listener.tableChanged(evt);
        }
    }

    private void setRowValue(IParameterManipulation manipulation, Object[] row) {
        int colIndex;
        if (manipulation instanceof MultipleParameterManipulation) {
            MultipleParameterManipulation mp = (MultipleParameterManipulation)manipulation;
            for (Iterator itr = mp.getManipulations().iterator(); itr.hasNext();) {
                IParameterManipulation parameterManipulation = (IParameterManipulation)itr.next();
                setRowValue(parameterManipulation, row);
            }
        } else {
            ParameterManipulation pm = (ParameterManipulation)manipulation;
            colIndex = getColumnIndex(pm.getParameter().getName());
            row[colIndex] = pm.getValueToSet();
        }
    }

    private int getColumnIndex(String name) {
        if (!_columnIndexMap.containsKey(name)) {
            throw new IllegalArgumentException("Could not find column called '" + name + "'");
        }
        return ((Integer)_columnIndexMap.get(name)).intValue();
    }

    public int getRowCount() {
        return _rows.size();
    }

    public int getColumnCount() {
        return _columnNames.size();
    }

    public String getColumnName(int columnIndex) {
        return (String)_columnNames.get(columnIndex);
    }

    public Class getColumnClass(int columnIndex) {
        return (Class)_columnTypes.get(columnIndex);
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex != 0);// && !(StrategyDefinitionParameter.class.isAssignableFrom(getColumnClass(columnIndex))));
    }

    Object[] getRow(int rowIndex) {
        return  (Object[])_rows.get(rowIndex);
    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getRow(rowIndex)[columnIndex];
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        getRow(rowIndex)[columnIndex] = aValue;
        if (columnIndex >0) {
            ParameterManipulation manip = findParameterManipulation(rowIndex, columnIndex);
            if (manip==null) {
                throw new IllegalStateException("Cannot find parameter manipulation at column: " + columnIndex);
            }
            Class type = manip.getParameter().getParameterType();
            IValueConverter converter = IntrospectionUtils.getValueConverter(type);
            if (converter == null) {
                if (log.isInfoEnabled()) {

                    log.info("Setting value: '" + aValue + "'");
                }
                manip.setValueToSet(aValue);
//                log.warn("Could not find a converter for type '" + type + "' - not setting value");
            }   else {
                Object convertedValue = converter.convert(aValue);
                manip.setValueToSet(convertedValue);
            }

        }

        fireCellValueChanged(rowIndex, columnIndex);
    }

    private void fireCellValueChanged(int rowIndex, int columnIndex) {
        TableModelEvent evt = new TableModelEvent(this, rowIndex, rowIndex, columnIndex);
        List listeners = new ArrayList(_modelListeners);// Incase someone removes themselves whilst responding
        for (Iterator itr = listeners.iterator(); itr.hasNext();) {
            TableModelListener listener = (TableModelListener)itr.next();
            listener.tableChanged(evt);
        }
    }


    private ParameterManipulation findParameterManipulation(int rowIndex, int columnIndex) {
        String paramName = getColumnName(columnIndex);
        IParameterManipulation imanip = (IParameterManipulation)_sequence.getManipulations().get(rowIndex);
        return findParameterManipulation(paramName, imanip);
    }

    private ParameterManipulation findParameterManipulation(String name, IParameterManipulation imanip) {
        ParameterManipulation found = null;
        if (imanip instanceof MultipleParameterManipulation) {
            MultipleParameterManipulation mp = (MultipleParameterManipulation)imanip;
            for (Iterator itr = mp.getManipulations().iterator(); itr.hasNext();) {
                IParameterManipulation manipulation = (IParameterManipulation)itr.next();
                found = findParameterManipulation(name, manipulation);
                if (found != null) {
                    break;
                }
            }
        }   else if (((ParameterManipulation)imanip).getParameter().getName().equals(name)){
            found = (ParameterManipulation)imanip;

        }
        return found;
    }

    public void addTableModelListener(TableModelListener l) {
        _modelListeners.add(l);
    }

    public void removeTableModelListener(TableModelListener l) {
        _modelListeners.remove(l);
    }

    private static final Logger log = Logger.getLogger(ManipulationSequenceTableModel.class);
    private ParameterManipulationSequence _sequence;
    private List _columnNames = new ArrayList();
    private Map _columnIndexMap = new HashMap();
    private List _rows = new ArrayList();
    private List _modelListeners = new ArrayList();
    private List _columnTypes = new ArrayList();
}
