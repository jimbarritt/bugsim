/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.javabean.TextAlignment;
import com.ixcode.framework.swing.SelectTextFocusListener;
import com.ixcode.framework.swing.layout.HorizontalStretchLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class TextFieldPropertyEditor extends NameValuePropertyEditor {

    public TextFieldPropertyEditor(String propertyName, String labelText, int minWidth) {
        super(propertyName, labelText, new JTextField(), minWidth);
    }

    public TextFieldPropertyEditor(String propertyName, String labelText, int minWidth, boolean stretchEditor) {
        super(propertyName, labelText, new JTextField(), minWidth, stretchEditor);
    }

    public TextFieldPropertyEditor(String propertyName, String labelText, String initialValue, int textFieldLength, TextAlignment textAlignment, int minWidth) {
        super(propertyName, labelText, new JTextField(textFieldLength), minWidth);
//        super.setBorder(BorderFactoryExtension.createEmptyBorder(5));
//        _propertyName = propertyName;
//
//        _labelFld = new JLabel(labelText);

        int align = (textAlignment == TextAlignment.LEFT) ? JTextField.LEFT : JTextField.RIGHT;
        JTextField textField = (JTextField)super.getEditingComponent();


        textField.setHorizontalAlignment(align);
        textField.setText(initialValue);
        textField.addFocusListener(new SelectTextFocusListener());

//        JPanel labelContainer = new JPanel(new BorderLayout());
//        labelContainer.add(Box.createHorizontalStrut(150), BorderLayout.NORTH) ;
//        labelContainer.add(_labelFld, BorderLayout.WEST);
//        super.add(labelContainer, BorderLayout.WEST);
//
//        JPanel textFieldContainer = new JPanel(new BorderLayout());
//        textFieldContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
//        textFieldContainer.add(_textField, BorderLayout.WEST);
//
//
//
//        JPanel stretcher = new JPanel(new BorderLayout());
//        stretcher.add(textFieldContainer, BorderLayout.EAST);
//        super.add(textFieldContainer, BorderLayout.CENTER);
//
        textField.getDocument().addDocumentListener(this);

    }

    protected String getValueFromEditingComponent(JComponent editingComponent) {
        return ((JTextField)editingComponent).getText();
    }

    protected void setValueToEditingComponent(JComponent editingComponent, String value) {
        ((JTextField)editingComponent).setText(value);
    }

    public boolean isDisplayOnly() {
        return false;
    }



    public JTextField getTextField() {
        return (JTextField)super.getEditingComponent();
    }

    public JComponent getDisplayComponent() {
        return this;
    }


    public synchronized void addFocusListener(FocusListener l) {
        super.addFocusListener(l);
    }


    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addEditorTest(f);

//        addHorizontalStretchLayoutTest(f);

        f.pack();
        f.show();

    }

    private static void addHorizontalStretchLayoutTest(JFrame f) {
        JPanel container = new JPanel(new BorderLayout());


        JPanel editPanel = new JPanel(new HorizontalStretchLayout());
        editPanel.add(new JTextField("Some Text"));
        container.add(editPanel, BorderLayout.CENTER);

        container.add(new JButton("Click"), BorderLayout.EAST);


        f.getContentPane().add(container);
    }

    private static void addEditorTest(JFrame f) {
        TextFieldPropertyEditor editor = new TextFieldPropertyEditor("propertyName", "Label", "0", 10, TextAlignment.RIGHT, 20);

        NameValuePropertyEditor nv = (NameValuePropertyEditor)editor;
        JPanel recalcPanel = new JPanel(new BorderLayout());
        JButton testButton = new JButton("Recalc");
        recalcPanel.add(testButton, BorderLayout.WEST);
        nv.addExtraComponent(recalcPanel);
        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (log.isInfoEnabled()) {
                    log.info("Clicked The Button!");
                }
            }
        });

        JPanel container = new JPanel(new BorderLayout());
        container.add(editor, BorderLayout.NORTH);
        f.getContentPane().add(container);
        editor.addPropertyChangeListener(new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt) {
                if (log.isInfoEnabled()) {
                    log.info("PropertyChanged!: " + evt.getPropertyName());
                }
            }
        });
    }

    public void reformatValue(String modelValue) {
        super.reformatValue(modelValue);
        if (!getTextField().getText().equals(modelValue)) {
            getTextField().setText(modelValue);
        }
    }

    private static final Logger log = Logger.getLogger(TextFieldPropertyEditor.class);

    protected static final Color DEFAULT_COLOUR = new JLabel().getForeground();


}
