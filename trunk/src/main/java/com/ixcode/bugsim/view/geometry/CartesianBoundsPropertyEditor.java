/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.geometry;

import com.ixcode.framework.javabean.format.JavaBeanParseException;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.CartesianBoundsFormat;
import com.ixcode.framework.swing.property.NameValuePropertyEditor;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CartesianBoundsPropertyEditor extends NameValuePropertyEditor {

    public CartesianBoundsPropertyEditor(String propertyName, String labelText, int minWidth, int decimalPlaces) {
        super(propertyName, labelText, createEditingComponent(decimalPlaces), minWidth);
        getCBoundsField().addDocumentListener(this);
    }

    private static JComponent createEditingComponent(int decimalPlaces) {
        return new CartesianBoundsField(decimalPlaces);
    }

    protected String getValueFromEditingComponent(JComponent editingComponent) {
        return _boundsFormat.format((CartesianBounds)getCBoundsField().getEditedValue());
    }

    private CartesianBoundsField getCBoundsField() {
        return (CartesianBoundsField)super.getEditingComponent();
    }

    protected void setValueToEditingComponent(JComponent editingComponent, String value) {
        try {
            CartesianBounds b = (CartesianBounds)_boundsFormat.parse(value);
            getCBoundsField().setEditedValue(b);
        } catch (JavaBeanParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void reformatValue(String modelValue) {
        super.reformatValue(modelValue);
        getCBoundsField().reformatValue();
    }


    CartesianBoundsFormat _boundsFormat = new CartesianBoundsFormat();

}
