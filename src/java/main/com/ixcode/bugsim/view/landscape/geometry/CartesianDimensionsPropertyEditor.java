/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.landscape.geometry;

import com.ixcode.framework.javabean.format.JavaBeanParseException;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.CartesianDimensionsFormat;
import com.ixcode.framework.swing.property.NameValuePropertyEditor;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CartesianDimensionsPropertyEditor extends NameValuePropertyEditor {

    public CartesianDimensionsPropertyEditor(String propertyName, String labelText, int minWidth, int decimalPlaces) {
        super(propertyName, labelText, CartesianDimensionsPropertyEditor.createEditingComponent(decimalPlaces), minWidth);
        getCDimensionsField().addDocumentListener(this);
    }

    private static JComponent createEditingComponent(int decimalPlaces) {
        return new CartesianDimensionsField(decimalPlaces);
    }

    protected String getValueFromEditingComponent(JComponent editingComponent) {
        return _dimensionsFormat.format((CartesianDimensions)getCDimensionsField().getEditedValue());
    }

    private CartesianDimensionsField getCDimensionsField() {
        return (CartesianDimensionsField)super.getEditingComponent();
    }

    protected void setValueToEditingComponent(JComponent editingComponent, String value) {
        try {
            CartesianDimensions d = (CartesianDimensions)_dimensionsFormat.parse(value);
            getCDimensionsField().setEditedValue(d);
        } catch (JavaBeanParseException e) {
            throw new RuntimeException(e);
        }
    }


    public void reformatValue(String modelValue) {
        super.reformatValue(modelValue);
        getCDimensionsField().reformatValue();
    }

    CartesianDimensionsFormat _dimensionsFormat = new CartesianDimensionsFormat();

}
