/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.swing.SelectTextFocusListener;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SimplePropertyEditor extends JPanel {

    public SimplePropertyEditor(String label, String propertyName, String initialValue) {
        super(new GridLayout(1, 2));
        _label = new JLabel(label);
        _textField = new JTextField(initialValue);
        _propertyName = propertyName;

        super.add(_label);
        super.add(_textField);

        _textField.addFocusListener(new SelectTextFocusListener());

    }


    public String getPropertyName() {
        return _propertyName;
    }

    public String getEditedValue() {
        return _textField.getText();
    }

    private JLabel _label;
    private JTextField _textField;

    private String _propertyName;
}
