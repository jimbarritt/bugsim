/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.swing.Point2DFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class PropertyPanel extends JPanel {

    public PropertyPanel(String title) {
        super.setLayout(new BoxLayout(this,3));
        _editorPanel = new JPanel();

        JPanel north = new JPanel(new BorderLayout());
        north.add(new JLabel(title), BorderLayout.NORTH);
        super.add(north);
        super.add(_editorPanel);


    }

    protected void setPanelAction(Action action) {
        _panelButton.setAction(action);
        if (_south == null) {
            _south = new JPanel(new BorderLayout());
            _south.add(_panelButton, BorderLayout.SOUTH);
            super.add(_south);
        }

    }

    protected void addPropertyEditor(String label, String propertyName,  String initialValue) {
        SimplePropertyEditor editor = new SimplePropertyEditor(label, propertyName, initialValue);
        _editorPanel.add(editor);
        _propertyEditors.put(propertyName, editor);
        _editorPanel.setLayout(new GridLayout(_propertyEditors.size(), 1));
    }

    public IPropertyValueEditor addReadOnlyPropertyEditor(String label, String propertyName, String initialValue) {
        ReadOnlyPropertyEditor editor = new ReadOnlyPropertyEditor(propertyName,label, 150);
        editor.setValue(initialValue);
        _editorPanel.add(editor.getDisplayComponent());
        _propertyEditors.put(propertyName, editor);
        _editorPanel.setLayout(new GridLayout(_propertyEditors.size(), 1));
        return editor;
    }

    public String getTextFieldValue(String propertyName) {
        SimplePropertyEditor editor = (SimplePropertyEditor)_propertyEditors.get(propertyName);
        return editor.getEditedValue();
    }

    public int getIntTextFieldValue(String propertyName) {
        return Integer.parseInt(getTextFieldValue(propertyName));
    }
     public boolean getBoolTextFieldValue(String propertyName) {
         return Boolean.valueOf(getTextFieldValue(propertyName)).booleanValue();
    }


    public double getDoubleTextFieldValue(String propertyName) {
        return Double.parseDouble(getTextFieldValue(propertyName));
    }

    public Point2D getPoint2dDoubleTextFieldValue(String propertyName) {
        return Point2DFormatter.Double.parse(getTextFieldValue(propertyName));
    }

    public String getPoint2dDoubleAsString(Point2D point2D) {
        return Point2DFormatter.Double.format(point2D);
    }


    public JPanel getEditorPanel() {
        return _editorPanel;
    }



    private Map _propertyEditors = new HashMap();
    private JPanel _editorPanel;
    private JButton _panelButton= new JButton("No Panel Action Set!");
    private JPanel _south;
}
