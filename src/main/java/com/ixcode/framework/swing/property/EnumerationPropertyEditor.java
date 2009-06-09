/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import org.apache.log4j.Logger;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class EnumerationPropertyEditor extends NameValuePropertyEditor {

    public EnumerationPropertyEditor(String propertyName, String labelText, EnumerationComboBox comboBox, int minWidth, JComponent extraComponent) {
        super(propertyName, labelText,new EnumerationWithLookupField(comboBox, null), minWidth-HORIZ_ADJ, extraComponent);

        getComboField().addActionListener(this);
    }
    public EnumerationPropertyEditor(String propertyName, String labelText, EnumerationComboBox comboBox, int minWidth) {
        this(propertyName, labelText, comboBox,  minWidth, (IEnumerationDescriptionLookup)null);

    }
    public EnumerationPropertyEditor(String propertyName, String labelText, EnumerationComboBox comboBox, int minWidth, IEnumerationDescriptionLookup lookup) {
        super(propertyName, labelText,new EnumerationWithLookupField(comboBox, lookup), minWidth-HORIZ_ADJ);
        getComboField().addActionListener(this);
    }



    /**
     * @todo tidy this up and create a special container which can also handle the lookup./
     * @param editingComponent
     * @return
     */
    protected String getValueFromEditingComponent(JComponent editingComponent) {
        EnumerationComboBox combo = ((EnumerationWithLookupField)editingComponent).getCombo();
        return combo.getSelectedValue();
    }

    protected void setValueToEditingComponent(JComponent editingComponent, String value) {
//        if (log.isInfoEnabled()) {
//            log.info("setEditedValue: " + super.getPropertyName() + " = " + value);
//        }

        EnumerationComboBox combo = ((EnumerationWithLookupField)editingComponent).getCombo();
        combo.setSelectedValue(value);
    }

    private EnumerationWithLookupField getComboField() {
        return (EnumerationWithLookupField)super.getEditingComponent();
    }


    private static final Logger log = Logger.getLogger(EnumerationPropertyEditor.class);

    private static final int HORIZ_ADJ = 3;
}
