/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.geometry;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.swing.FixedWidthLabel;
import com.ixcode.framework.swing.JFrameExtension;
import com.ixcode.framework.swing.SelectTextFocusListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CartesianBoundsField extends RectangularCoordinateField  {

    public CartesianBoundsField(int decimalPlaces) {
        super(decimalPlaces);
        initUI();
        setEditedValue(new CartesianBounds(0, 0, 0, 0));
    }



    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        _wLabel.setForeground(getLabelTextColor(enabled));
        _hLabel.setForeground(getLabelTextColor(enabled));


        _wField.setEnabled(enabled);
        _hField.setEnabled(enabled);
    }


    private void initUI() {

        int size = 4 + _decimalPlaces;
        _wField = new JTextField("0", size);
        _hField = new JTextField("0", size);

        _wField.setHorizontalAlignment(JTextField.TRAILING);
        _hField.setHorizontalAlignment(JTextField.TRAILING);


        _container.add(Box.createHorizontalStrut(GAP));
        _wLabel = new FixedWidthLabel("w=", LABEL_WIDTH);
        _container.add(_wLabel);
        _container.add(_wField);
        _container.add(Box.createHorizontalStrut(GAP));
        _hLabel = new FixedWidthLabel("h=", LABEL_WIDTH);
        _container.add(_hLabel);
        _container.add(_hField);

        _wField.addFocusListener(new SelectTextFocusListener());
        _hField.addFocusListener(new SelectTextFocusListener());



        _wField.getDocument().addDocumentListener(this);
        _hField.getDocument().addDocumentListener(this);

    }


    public void setEditedValue(CartesianBounds cartesianBounds) {
        setValueToEditors(cartesianBounds);
    }

    protected void setValueToEditors(CartesianBounds b) {
        super.setValueToEditors(b.getLocation());
        _wField.setText(_format.format(b.getDoubleWidth()));
        _hField.setText(_format.format(b.getDoubleHeight()));
    }

    public Object getEditedValue() {
        return createValueFromEditors();
    }

    protected Object createValueFromEditors() {
        RectangularCoordinate c = (RectangularCoordinate)super.createValueFromEditors();
        return new CartesianBounds(c.getDoubleX(), c.getDoubleY(), parseDouble(_wField.getText()), parseDouble(_hField.getText()));
    }



    public static void main(String[] args) {
        JFrame test = new JFrameExtension("Test Cartesian Bounds Field");
        CartesianBoundsField field = new CartesianBoundsField(2);
        JPanel p = new JPanel(new BorderLayout());
        p.add(field, BorderLayout.NORTH);
        test.getContentPane().add(p);
        test.setSize(300, 200);

        field.setEditedValue(new CartesianBounds(10, 20, 30, 40));

        field.addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insertUpdate: " + e);
            }

            public void removeUpdate(DocumentEvent e) {
                System.out.println("removeUpdate: " + e);
            }

            public void changedUpdate(DocumentEvent e) {
                System.out.println("changedUpdate: " + e);
            }
        });

        test.show();
    }

    public synchronized void addFocusListener(FocusListener l) {
        super.addFocusListener(l);
        if (_wField != null && _hField != null) {
            _wField.addFocusListener(l);
            _hField.addFocusListener(l);
        }
    }


    public void reformatValue() {
        super.reformatValue();
        reformat(_wField);
        reformat(_hField);
    }

    private JTextField _wField;
    private JTextField _hField;
    private static final int GAP = 10;

    public JLabel _wLabel;
    public JLabel _hLabel;
}
