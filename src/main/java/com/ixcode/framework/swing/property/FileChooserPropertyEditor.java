/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class FileChooserPropertyEditor extends NameValuePropertyEditor implements DocumentListener {



    public FileChooserPropertyEditor(Class preferencesClass,  String propertyName, String labelText, int minWidth) {
        super(propertyName, labelText, new FileChooserTextField(preferencesClass, propertyName), minWidth, true);
        FileChooserTextField field = (FileChooserTextField)super.getEditingComponent();
        field.getTextField().getDocument().addDocumentListener(this);

    }

    public FileChooserTextField getFileChooserTextField() {
        return (FileChooserTextField)super.getEditingComponent();        
    }

    public void initialiseValueFromPreferences() {
        FileChooserTextField field = (FileChooserTextField)super.getEditingComponent();
        field.initialiseValueFromPreferences();

    }


    protected String getValueFromEditingComponent(JComponent editingComponent) {
        return ((FileChooserTextField)editingComponent).getFilePath();
    }

    protected void setValueToEditingComponent(JComponent editingComponent, String value) {
        ((FileChooserTextField)editingComponent).setFilePath(value);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Test Name Value Property Editor");
        FileChooserPropertyEditor e = new FileChooserPropertyEditor(FileChooserPropertyEditor.class,  "blahs", "Some Label", 150);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p = new JPanel(new BorderLayout());

        p.add(e, BorderLayout.CENTER);
        f.getContentPane().add(p);
        f.pack();
        f.show();


    }

    private void firePropertyValueChangedEvent() {
        FileChooserTextField editingComponent = (FileChooserTextField)super.getEditingComponent();
        super.firePropertyChange(super.getPropertyName(), "Old" + editingComponent.getFilePath(), editingComponent.getFilePath());
    }

    public void insertUpdate(DocumentEvent e) {
        firePropertyValueChangedEvent();
    }

    public void removeUpdate(DocumentEvent e) {
        firePropertyValueChangedEvent();
    }

    public void changedUpdate(DocumentEvent e) {
        firePropertyValueChangedEvent();
    }


}
