/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.datatype.IxDataTypeUtils;
import com.ixcode.framework.javabean.IntrospectionUtils;
import com.ixcode.framework.javabean.TextAlignment;
import com.ixcode.framework.swing.BorderFactoryExtension;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

/**
 * Description : Contains a collection of PropertyEditors
 * @todo factor out all the property editor creation to factories ...
 */
public class PropertyGroupPanel extends JPanel implements PropertyChangeListener {

    public PropertyGroupPanel() {
        this(null);
    }

    public PropertyGroupPanel(String title) {
        super.setLayout(new BorderLayout());

        _layoutPanel = new JPanel();
        _layoutPanel.setLayout(new BoxLayout(_layoutPanel, BoxLayout.PAGE_AXIS));
        super.add(_layoutPanel, BorderLayout.NORTH);

        Border titleBorder;
        if (title != null) {
            titleBorder = BorderFactory.createTitledBorder(title);

        } else {
            titleBorder= BorderFactoryExtension.createEmptyBorder(0);
        }

        Border emptyBorder = BorderFactoryExtension.createEmptyBorder(0);

         _layoutPanel.setBorder(BorderFactory.createCompoundBorder(emptyBorder, titleBorder));
    }

     public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        super.firePropertyChange(propertyChangeEvent.getPropertyName(), propertyChangeEvent.getOldValue(), propertyChangeEvent.getNewValue());
    }

    public void addButton(Action action) {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(new JButton(action), BorderLayout.EAST);
        buttonPanel.setBorder(BorderFactoryExtension.createEmptyBorder(5));
        _layoutPanel.add(buttonPanel);
    }

    public void addPropertyEditor(String name, String labelText, Class propertyType, String initialValue, int minWidth) {
        int textFieldLength = (String.class.isAssignableFrom(propertyType))? initialValue.length() : getLengthFromType(propertyType);
        addPropertyEditor(name, labelText, textFieldLength, TextAlignment.LEFT, minWidth);
        setTextValue(name, initialValue);

    }

    public IPropertyValueEditor addPropertyEditor(String name, Class propertyType, String labelText, int fieldLength, TextAlignment textAlignment, int minWidth) {
        IPropertyValueEditor propertyEditor = null;
        if (IntrospectionUtils.isBoolean(propertyType)) {
            propertyEditor = new CheckBoxPropertyEditor(name, labelText, minWidth);
        } else {
            propertyEditor = new TextFieldPropertyEditor(name, labelText, "", fieldLength, textAlignment, minWidth);
            _textFields.put(name, ((TextFieldPropertyEditor)propertyEditor).getTextField());
        }
        _layoutPanel.add(propertyEditor.getDisplayComponent());
        addPropertyEditor(propertyEditor);
        return propertyEditor;
    }

    public TextFieldPropertyEditor addPropertyEditor(String name, String labelText, int fieldLength, TextAlignment textAlignment, int minWidth) {
        TextFieldPropertyEditor propertyEditor = new TextFieldPropertyEditor(name, labelText, "", fieldLength, textAlignment, minWidth);
        _layoutPanel.add(propertyEditor);
        _textFields.put(name, propertyEditor.getTextField());
        addPropertyEditor(propertyEditor);
        return propertyEditor;
    }


    public IPropertyValueEditor addPropertyEditor(IPropertyValueEditor editor) {
        _propertyEditors.add(editor);
        _layoutPanel.add(editor.getDisplayComponent());
//        editor.addPropertyChangeListener(this);
        return editor;
    }


    public JPanel getLayoutPanel() {
        return _layoutPanel;
    }
    public String getTextValue(String name) {
        return ((JTextField)_textFields.get(name)).getText();

    }

    public void setTextValue(String name, String value) {
        ((JTextField)_textFields.get(name)).setText(value);
    }

    protected int getLengthFromType(Class type) {
        int length = 15;
        if (IxDataTypeUtils.isNumeric(type)) {
            length = 15;
        }
        return length;
    }


    protected void addLabel(JLabel label) {

        JPanel labelPanel = new JPanel(new BorderLayout());

//        label.setBackground(Color.lightGray);
//        label.setOpaque(true);
        labelPanel.add(label, BorderLayout.NORTH);


        _layoutPanel.add(labelPanel);
    }

    public void addCombo(JComboBox functionCombo) {
        _layoutPanel.add(functionCombo);
    }

    public void addComponent(JComponent component) {
        _layoutPanel.add(component);
    }

    public List getPropertyValueEditors() {
        return _propertyEditors;
    }

    public void setReadOnly(boolean readOnly)  {
        for (Iterator itr = _propertyEditors.iterator(); itr.hasNext();) {
            IPropertyValueEditor editor = (IPropertyValueEditor)itr.next();
            editor.setReadonly(readOnly);
        }
    }

    public boolean isLoading() {
        return _loading;
    }

    public void setLoadingOn() {
        _loading = true;
    }

    public void setLoadingOff() {
        _loading = false;
    }

    private boolean _loading;

    private Map _textFields = new HashMap();
    private JPanel _layoutPanel;

    private List _propertyEditors = new ArrayList();
    public static final String EDITED_PROPERTY_EVENT_PREFIX = "EditedProperty.";
}
