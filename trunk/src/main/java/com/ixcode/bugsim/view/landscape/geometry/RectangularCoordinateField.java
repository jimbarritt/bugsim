/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.geometry;

import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.swing.FixedWidthLabel;
import com.ixcode.framework.swing.SelectTextFocusListener;
import org.apache.log4j.Logger;

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
 */
public class RectangularCoordinateField extends JPanel implements DocumentListener {

    public RectangularCoordinateField(int decimalPlaces) {
        super(new BorderLayout());
        _format = DoubleMath.createFormatter(decimalPlaces);
        _decimalPlaces = decimalPlaces;
        initBaseUI();
        setEditedValue(new RectangularCoordinate(0, 0));
    }

    protected Color getLabelTextColor(boolean enabled) {
        return enabled ?  Color.BLACK :Color.GRAY ;
    }
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        _xLabel.setForeground(getLabelTextColor(enabled));
        _yLabel.setForeground(getLabelTextColor(enabled));

        _xField.setEnabled(enabled);
        _yField.setEnabled(enabled);
    }

    private void initBaseUI() {
        _container = new JPanel();
        _container.setLayout(new BoxLayout(_container, BoxLayout.X_AXIS));

        int size = 4 + _decimalPlaces;
        _xField = new JTextField("0", size);
        _yField = new JTextField("0", size);



        _xField.setHorizontalAlignment(JTextField.TRAILING);
        _yField.setHorizontalAlignment(JTextField.TRAILING);

        _xLabel = new FixedWidthLabel("x=", LABEL_WIDTH);
        _container.add(_xLabel);
        _container.add(_xField);
        _container.add(Box.createHorizontalStrut(GAP));
        _yLabel = new FixedWidthLabel("y=", LABEL_WIDTH);
        _container.add(_yLabel);
        _container.add(_yField);


        _xField.addFocusListener(new SelectTextFocusListener());
        _yField.addFocusListener(new SelectTextFocusListener());



        _xField.getDocument().addDocumentListener(this);
        _yField.getDocument().addDocumentListener(this);

        super.add(_container, BorderLayout.WEST);
    }


    protected void setValueToEditors(RectangularCoordinate c) {
        _xField.setText(_format.format(c.getDoubleX()));
        _yField.setText(_format.format(c.getDoubleY()));

    }

    public void setEditedValue(RectangularCoordinate coordinate) {
        setValueToEditors(coordinate);
    }


    public Object getEditedValue() {
        return createValueFromEditors();
    }

    protected Object createValueFromEditors() {
        return new RectangularCoordinate(parseDouble(_xField.getText()), parseDouble(_yField.getText()));
    }

    protected double parseDouble(String text) {
        
        if (text == null || text.length() ==0) {
            return 0;
        } else {
            try {
                return Double.valueOf(text).doubleValue();
            } catch (NumberFormatException e) {
                throw new RuntimeException("NumberFormatException for value: " + text , e);
            }
        }
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
        if (_xField != null && _yField != null) {
            _xField.addFocusListener(l);
            _yField.addFocusListener(l);
        }
    }

    public void reformatValue() {
        reformat(_xField);
        reformat(_yField);
    }

    protected void reformat(JTextField field) {
        String formattedValue = _format.format(parseDouble(field.getText()));
        if (!field.getText().equals(formattedValue)) {
            field.setText(formattedValue);
        }
    }


    private static final Logger log = Logger.getLogger(RectangularCoordinateField.class);
    private List _documentListeners = new ArrayList();
    protected JTextField _xField;
    protected JTextField _yField;
    protected DecimalFormat _format;
    protected static final int GAP = 10;
    protected int _decimalPlaces;
    protected JPanel _container;
    public JLabel _xLabel;
    public JLabel _yLabel;
    public static final int LABEL_WIDTH = 25;
}
