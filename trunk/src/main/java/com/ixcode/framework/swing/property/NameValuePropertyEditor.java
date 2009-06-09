/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.swing.BorderFactoryExtension;
import com.ixcode.framework.swing.layout.HorizontalStretchLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : Implements a simple layout with a label on the left and some component on the right
 * you decide what the component on the right is in the subclass.
 */
public abstract class NameValuePropertyEditor extends JPanel implements IPropertyValueEditor, DocumentListener , ActionListener {

    public NameValuePropertyEditor(String propertyName, String labelText, JComponent editingComponent, int minWidth) {
        this(propertyName, labelText, editingComponent, minWidth, false);
    }

    public NameValuePropertyEditor(String propertyName, String labelText, JComponent editingComponent, int minWidth, JComponent extraComponent) {
        this(propertyName, labelText, editingComponent, minWidth, false, extraComponent);
    }

    public NameValuePropertyEditor(String propertyName, String labelText, JComponent editingComponent, int minWidth, boolean stretchEditor) {
        this(propertyName, labelText, editingComponent, minWidth, stretchEditor, null);
    }

    public NameValuePropertyEditor(String propertyName, String labelText, JComponent editingComponent, int minWidth, boolean stretchEditor, JComponent extraComponent) {
        super(new BorderLayout());
        super.setBorder(BorderFactoryExtension.createEmptyBorder(5));
        _propertyName = propertyName;
        _editingComponent = editingComponent;

        _labelFld = new JLabel(labelText);
        JPanel labelContainer = new JPanel(new BorderLayout());
        labelContainer.add(Box.createHorizontalStrut(minWidth), BorderLayout.NORTH);
        labelContainer.add(_labelFld, BorderLayout.CENTER);
        super.add(labelContainer, BorderLayout.WEST);


        _editingComponentContainer = new JPanel(new BorderLayout());

        _editingComponentContainer.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        // When adding the editor we want to stretch it horizontally but NOT vertically...
        JPanel editorContainer = new JPanel(new HorizontalStretchLayout());
        editorContainer.add(_editingComponent);

        String editorPosition = stretchEditor ? BorderLayout.CENTER: BorderLayout.WEST;


        _editingComponentContainer.add(editorContainer,  editorPosition);

        if (extraComponent != null) {
            _editingComponentContainer.add(extraComponent, BorderLayout.EAST);
        }
//        editingComponentContainer.setBackground(Color.red);
        super.add(_editingComponentContainer, BorderLayout.CENTER);
        super.add(Box.createVerticalStrut(25), BorderLayout.EAST);


    }

    public void addExtraComponent(JComponent compo) {

        _editingComponentContainer.add(compo, BorderLayout.EAST);

    }

    /**
     * gives you the opportunity to reformat a value if you have special formatting - occurs after the user has typed
     * something in - maybe not in your format.
     */
    public void reformatValue(String modelValue) {

    }

    public void setForeground(Color fg) {
        super.setForeground(fg);
        if (_editingComponent != null) {
            _editingComponent.setForeground(fg);
        }
    }

    public boolean isDisplayOnly() {
        return _displayOnly;
    }

    public void setDisplayOnly(boolean displayOnly) {
        _displayOnly = displayOnly;
    }

    public void addPropertyValueEditorListener(IPropertyValueEditorListener l) {
        _listeners.add(l);
    }

    protected void fireEditedValueChanged() {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            IPropertyValueEditorListener listener = (IPropertyValueEditorListener)itr.next();
            listener.editedValueChanged(_propertyName, getValue());
        }
    }


    public void insertUpdate(DocumentEvent documentEvent) {
        fireEditedValueChanged();
    }


    public void removeUpdate(DocumentEvent documentEvent) {
        fireEditedValueChanged();
    }

    public void changedUpdate(DocumentEvent documentEvent) {

        fireEditedValueChanged();
    }

    public void actionPerformed(ActionEvent e) {
       fireEditedValueChanged();
    }

    public String getPropertyName() {
        return _propertyName;
    }

    public JComponent getDisplayComponent() {
        return this;
    }

    public String getValue() {
        return getValueFromEditingComponent(_editingComponent);
    }

    public void setValue(String value) {
        setValueToEditingComponent(_editingComponent, value);
    }

    protected abstract String getValueFromEditingComponent(JComponent editingComponent);

    protected abstract void setValueToEditingComponent(JComponent editingComponent, String value);

    public void setReadonly(boolean readonly) {
        _editingComponent.setEnabled(!readonly);
        _labelFld.setForeground(readonly ? Color.GRAY : Color.BLACK);
        _readOnly = readonly;
    }

    public boolean isReadonly() {
        return _readOnly;
    }

    public synchronized void addFocusListener(FocusListener l) {
        super.addFocusListener(l);
        if (_editingComponent != null) {
            _editingComponent.addFocusListener(l);
        }
    }


    public JComponent getEditingComponent() {
        return _editingComponent;

    }


    public void setValue(int intValue) {
        setValue("" + intValue);
    }

    public void setValue(long longValue) {
        setValue("" + longValue);
    }

    protected JLabel getLabelFld() {
        return _labelFld;
    }



    private String _propertyName;
    private JComponent _editingComponent;

    private boolean _displayOnly;
    private JLabel _labelFld;
    private JPanel _editingComponentContainer;
    private boolean _readOnly = false;
    private List _listeners = new ArrayList();
}
