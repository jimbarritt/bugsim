/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.swing.BorderFactoryExtension;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Description : Implements a simple layout with a label on the left and some component on the right
 * you decide what the component on the right is in the subclass.
 */
public  class CheckBoxPropertyEditor extends NameValuePropertyEditor {

    public CheckBoxPropertyEditor(String propertyName, String labelText) {
        super(propertyName, "", new JCheckBox(labelText), 0);
        initListener();
    }

    /**
     * For some reason the check box editor is about 5 pixels too far to the right ... so we take 5 off for now - try to work it out later!
     * @param propertyName
     * @param labelText
     * @param minWidth
     */
    public CheckBoxPropertyEditor(String propertyName, String labelText, int minWidth) {
        super(propertyName, labelText, new JCheckBox(""), minWidth-HORIZ_ADJ);
        initListener();
    }

    public CheckBoxPropertyEditor(String propertyName, String labelText, int minWidth, boolean stretchEditor) {
        super(propertyName, labelText, new JCheckBox(""), minWidth-HORIZ_ADJ, stretchEditor);
        initListener();
    }

    private void initListener() {
        getCheckBox().addActionListener(this);
//      getCheckBox().addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//               super.fire
//            }
//        });
    }

    protected String getValueFromEditingComponent(JComponent editingComponent) {
        return "" + ((JCheckBox)editingComponent).isSelected();
    }

    protected void setValueToEditingComponent(JComponent editingComponent, String value) {
        ((JCheckBox)editingComponent).setSelected((new Boolean(value)).booleanValue());
    }

//    private void firePropertyValueChangedEvent() {
//        super.firePropertyChange(EDITED_PROPERTY + "." + super.getPropertyName(), "Old" + getValueFromEditingComponent(this.getEditingComponent()), getValueFromEditingComponent(this.getEditingComponent()));
//    }

    public void setForeground(Color fg) {
        super.setForeground(fg);
        if (getCheckBox() != null) {
            getCheckBox().setForeground(fg);
        }
    }

    public JComponent getDisplayComponent() {
        return this;
    }

    public String getValue() {
        return "" + getCheckBox().isSelected();
    }



    public void setValue(String value) {
        boolean b = Boolean.valueOf(value).booleanValue();
        getCheckBox().setSelected(b);
    }



//    public void setReadonly(boolean readonly) {
//        getCheckBox().setEnabled(!readonly);
//    }


    protected JCheckBox getCheckBox() {
        return (JCheckBox)super.getEditingComponent();
    }

    public String toString() {
        return "CheckBoxPropertyEditor";
    }

    public void addActionListener(ActionListener actionListener) {
        getCheckBox().addActionListener(actionListener);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//        addSimpleCheckBoxTest(f);
        CheckBoxPropertyEditor checkBoxEditor = new CheckBoxPropertyEditor("test", "Some Long Label", 10);
        checkBoxEditor.getCheckBox().setOpaque(false);

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.blue);
        p.add(checkBoxEditor, BorderLayout.CENTER);

        f.getContentPane().add(p);
        f.show();
    }

    private static void addSimpleCheckBoxTest(JFrame f) {
        JPanel p = new JPanel(new BorderLayout());
        JCheckBox checkBox = new JCheckBox("");
        checkBox.setOpaque(false);
        p.add(checkBox, BorderLayout.CENTER);

        p.setBackground(Color.blue);

        JPanel p2 = new JPanel(new BorderLayout());
        p2.setBorder(BorderFactoryExtension.createEmptyBorder(10));
        p2.add(p, BorderLayout.CENTER);

        f.getContentPane().add(p2);
    }

    private static final Logger log = Logger.getLogger(CheckBoxPropertyEditor.class);

    public static final int HORIZ_ADJ = 4;
}
