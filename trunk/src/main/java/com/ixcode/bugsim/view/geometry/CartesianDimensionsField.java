/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.geometry;

import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.swing.FixedWidthLabel;
import com.ixcode.framework.swing.JFrameExtension;
import com.ixcode.framework.swing.SelectTextFocusListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 *
 * @todo implement some kinda base class for these three (dimensions, coordinate and bounds
 */
public class CartesianDimensionsField extends JPanel implements DocumentListener {

    public CartesianDimensionsField(int decimalPlaces) {
        super(new BorderLayout());
        _format = DoubleMath.createFormatter(decimalPlaces);
        _decimalPlaces = decimalPlaces;

        initUI();
        setEditedValue(new CartesianDimensions(0));
    }

    protected Color getLabelTextColor(boolean enabled) {
        return enabled ? Color.BLACK : Color.GRAY;
    }

    public void setEnabled(boolean enabled) {


        _wLabel.setForeground(getLabelTextColor(enabled));
        _hLabel.setForeground(getLabelTextColor(enabled));


        _wField.setEnabled(enabled);
        _hField.setEnabled(enabled);
    }


    private void initUI() {
        _container = new JPanel();
        _container.setLayout(new BoxLayout(_container, BoxLayout.X_AXIS));

        int size = 4 + _decimalPlaces;
        _wField = new JTextField("0", size);
        _hField = new JTextField("0", size);

        _wField.setHorizontalAlignment(JTextField.TRAILING);
        _hField.setHorizontalAlignment(JTextField.TRAILING);


        _wLabel = new FixedWidthLabel("w=", RectangularCoordinateField.LABEL_WIDTH);
        _container.add(_wLabel);
        _container.add(_wField);
        _container.add(Box.createHorizontalStrut(CartesianDimensionsField.GAP));
        _hLabel = new FixedWidthLabel("h=", RectangularCoordinateField.LABEL_WIDTH);
        _container.add(_hLabel);
        _container.add(_hField);

        _wField.addFocusListener(new SelectTextFocusListener());
        _hField.addFocusListener(new SelectTextFocusListener());

        _wField.getDocument().addDocumentListener(this);
        _hField.getDocument().addDocumentListener(this);

        super.add(_container, BorderLayout.WEST);

    }

    protected double parseDouble(String text) {

        if (text == null || text.length() == 0) {
            return 0;
        } else {
            try {
                return Double.valueOf(text).doubleValue();
            } catch (NumberFormatException e) {
                throw new RuntimeException("NumberFormatException: value=" + text, e);
            }
        }
    }


    public void setEditedValue(CartesianDimensions cartesianDimensions) {
        setValueToEditors(cartesianDimensions);
    }

    protected void setValueToEditors(CartesianDimensions d) {
        _wField.setText(_format.format(d.getDoubleWidth()));
        _hField.setText(_format.format(d.getDoubleHeight()));
    }

    public Object getEditedValue() {
        return createValueFromEditors();
    }

    protected Object createValueFromEditors() {

        return new CartesianDimensions(parseDouble(_wField.getText()), parseDouble(_hField.getText()));
    }


    public static void main(String[] args) {
        JFrame test = new JFrameExtension("Test Cartesian Dimensions Field");
        CartesianDimensionsField field = new CartesianDimensionsField(2);
        JPanel p = new JPanel(new BorderLayout());
        JPanel p1 = new JPanel(new BorderLayout());
        p.add(field, BorderLayout.NORTH);
        p1.add(p, BorderLayout.WEST);
        test.getContentPane().add(p1);
        test.setSize(300, 200);

        field.setEditedValue(new CartesianDimensions(30, 40));

        field.addDocumentListener(new DocumentListener() {
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

    public void insertUpdate(DocumentEvent e) {
        for (Iterator itr = _documentListeners.iterator(); itr.hasNext();) {
            DocumentListener documentListener = (DocumentListener)itr.next();
            documentListener.insertUpdate(e);
        }
    }

    public void removeUpdate(DocumentEvent e) {
        for (Iterator itr = _documentListeners.iterator(); itr.hasNext();) {
            DocumentListener documentListener = (DocumentListener)itr.next();
            documentListener.removeUpdate(e);
        }
    }

    public void changedUpdate(DocumentEvent e) {
        for (Iterator itr = _documentListeners.iterator(); itr.hasNext();) {
            DocumentListener documentListener = (DocumentListener)itr.next();
            documentListener.changedUpdate(e);
        }
    }

    public void addDocumentListener(DocumentListener l) {
        _documentListeners.add(l);
    }

    public void removeDocumentListener(DocumentListener l) {
        _documentListeners.remove(l);
    }

    public synchronized void addFocusListener(FocusListener l) {
        super.addFocusListener(l);
        if (_wField != null && _hField != null) {
            _wField.addFocusListener(l);
            _hField.addFocusListener(l);
        }
    }

    public void reformatValue() {
        reformat(_wField);
        reformat(_hField);
    }

    protected void reformat(JTextField field) {
        String formattedValue = _format.format(parseDouble(field.getText()));
        if (!field.getText().equals(formattedValue)) {
            field.setText(formattedValue);
        }
    }


    private JTextField _wField;
    private JTextField _hField;
    private static final int GAP = 10;

    public JLabel _wLabel;
    public JLabel _hLabel;
    private DecimalFormat _format;
    private int _decimalPlaces;
    private JPanel _container;
    private List _documentListeners = new ArrayList();
}
