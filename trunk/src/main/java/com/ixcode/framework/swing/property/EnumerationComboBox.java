/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class EnumerationComboBox extends JComboBox {

    public EnumerationComboBox() {

    }

    public void addValue(String displayName, String value) {
        super.addItem(displayName);
        _values.add(value);
    }

    public String getSelectedValue() {
        if (super.getSelectedIndex() <0 || super.getSelectedIndex() > _values.size()-1){
            return "NOT_SET";
        }
        return (String)_values.get(super.getSelectedIndex());
    }

    public void setSelectedValue(String value) {
        super.setSelectedIndex(_values.indexOf(value));
    }


    private List _values = new ArrayList();
}
