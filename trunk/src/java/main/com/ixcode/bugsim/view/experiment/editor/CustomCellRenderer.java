/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

public class CustomCellRenderer implements TableCellRenderer {


    public CustomCellRenderer() {
        _checkBox.setHorizontalAlignment(JCheckBox.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//        System.out.println("getTableCellRendererComponent <" + value + ">, <"+ isSelected + ">, <" + hasFocus + ">, row=" + row + ", drawStripes=" +_drawStripes);
        //_label = new JLabel();
        if (value != null && (value instanceof Boolean)) {
            _checkBox.setSelected(((Boolean)value).booleanValue());
            return _checkBox;
        }  else {
            return configureStandardLabel(table, hasFocus, row, isSelected, value);
        }
    }



    private Component configureStandardLabel(JTable table, boolean hasFocus, int row, boolean isSelected, Object value) {
        _label.setOpaque(true);
        _label.setFont(table.getFont());
        _label.setBorder(BorderFactory.createEmptyBorder(1, 10, 1, 1));


        if (hasFocus && _showCellFocus) {
            _label.setBorder(BorderFactory.createLineBorder(Color.blue, 1));

            _label.setBackground(_focusColor);
        }
        Color background = Color.white;
        Color foreground = Color.black;

        if (_drawStripes) {// Do stripey stripes ...
            if ((row+1) % 2 == 0) {
                background = new Color(204, 204, 255);
            }
        }

        if (isSelected) {
            _label.setBackground(_selectedBackgroundColor);
            _label.setForeground(_selectedForegroundColor);
        } else {
            _label.setBackground(background);
            _label.setForeground(foreground);
        }


        _label.setText("" + value);


        return _label;
    }

    private static final Logger log = Logger.getLogger(CustomCellRenderer.class);
    private JLabel _label = new JLabel();

    private Font _normalFont = _label.getFont();
    private Font _nullValueFont = new Font("Courier New", Font.PLAIN, 12);
    private boolean _drawStripes = true;
    private boolean _showCellFocus = false;
    private Color _selectedForegroundColor = Color.white;
    private Color _selectedBackgroundColor = Color.blue;
    private Color _backgroundColor = Color.white;
    private Color _foreGroundColor = Color.black;
    private Color _focusColor = Color.cyan;
    private JCheckBox _checkBox = new JCheckBox();
    
}