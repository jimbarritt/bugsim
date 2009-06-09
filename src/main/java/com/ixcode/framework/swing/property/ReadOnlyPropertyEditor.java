/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.javabean.TextAlignment;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ReadOnlyPropertyEditor extends NameValuePropertyEditor   {

    public ReadOnlyPropertyEditor(String propertyName, String labelText, int minWidth) {
        this(propertyName, labelText, minWidth, null);

    }
    public ReadOnlyPropertyEditor(String propertyName, String labelText, int minWidth,  JComponent extraComponent) {
        super(propertyName, labelText, new JLabel(), minWidth, extraComponent);
        super.setDisplayOnly(true);
        super.getEditingComponent().setForeground(Color.GRAY);
    }

    protected String getValueFromEditingComponent(JComponent editingComponent) {
        return((JLabel)editingComponent).getText();
    }



    protected void setValueToEditingComponent(JComponent editingComponent, String value) {
        ((JLabel)editingComponent).setText(value);
    }

    public void setTextAlignment(TextAlignment align) {
        if (align == TextAlignment.LEFT) {
            ((JLabel)super.getEditingComponent()).setHorizontalAlignment(JLabel.LEADING);
        } else if (align == TextAlignment.RIGHT) {
            ((JLabel)super.getEditingComponent()).setHorizontalAlignment(JLabel.LEADING);
        }

    }


    public void setLabel(String s) {
        super.getLabelFld().setText(s);
    }
}
