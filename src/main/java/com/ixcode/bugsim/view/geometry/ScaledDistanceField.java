/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.geometry;

import com.ixcode.framework.javabean.format.JavaBeanParseException;
import com.ixcode.framework.math.scale.IDistanceUnit;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.math.scale.ScaledDistanceFormat;
import com.ixcode.framework.swing.SelectTextFocusListener;
import com.ixcode.framework.swing.layout.HorizontalStretchLayout;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ScaledDistanceField extends JPanel {

    public ScaledDistanceField() {
        this(false);
    }

    public ScaledDistanceField(boolean includeLogical) {
        super(new BorderLayout());
        _unitCombo = new DistanceUnitComboBox(includeLogical);


        JPanel valueContainer = new JPanel(new HorizontalStretchLayout());
        _valueField.setHorizontalAlignment(JTextField.RIGHT);
        _valueField.addFocusListener(new SelectTextFocusListener());
        valueContainer.add(_valueField);
        super.add(valueContainer, BorderLayout.CENTER);


        JPanel unitcontainer = new JPanel(new BorderLayout());
        unitcontainer.add(Box.createHorizontalStrut(10), BorderLayout.CENTER);
        unitcontainer.add(_unitCombo, BorderLayout.EAST);
        super.add(unitcontainer, BorderLayout.EAST);

    }

    public void setValue(String value) {
        try {
            ScaledDistance scaledDistance = (ScaledDistance)_format.parse(value);
            _valueField.setText(scaledDistance.getDistanceAsString());


            _unitCombo.setSelectedUnitSymbol(scaledDistance.getUnitsAsString());
        } catch (JavaBeanParseException e) {
            throw new RuntimeException(e);
        }

    }

    public String getValue() {
        return ScaledDistance.parse(_valueField.getText(),  _unitCombo.getSelectedUnitSymbol()).toString();
    }

    public void setValue(IDistanceUnit defaultUnit) {
        _unitCombo.setSelectedUnitSymbol(defaultUnit);
    }

    private JTextField _valueField = new JTextField(5);
    private DistanceUnitComboBox _unitCombo;


    private ScaledDistanceFormat _format = new ScaledDistanceFormat();
}
