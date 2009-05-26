/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 14, 2007 @ 7:45:48 PM by jim
 */
public class StrategyTableCellEditor extends DefaultCellEditor {

    public StrategyTableCellEditor(JTable parent, IModelAdapter modelAdapter, IParameterMapLookup lookup) {
        super(new JTextField());
        _strategyEditor = new StrategyField(parent, modelAdapter, lookup);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (log.isInfoEnabled()) {
            log.info("GetCellEditorComponent: " + value);
        }


        StrategyDefinitionParameter sp = (StrategyDefinitionParameter)value;

        _strategyEditor.setStrategy(sp);


        return _strategyEditor;
    }

    public Object getCellEditorValue() {
        return _strategyEditor.getEditedValue();
    }


    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    private static final Logger log = Logger.getLogger(StrategyTableCellEditor.class);
    private StrategyDefinitionParameter _value;
    private StrategyField _strategyEditor;
}
