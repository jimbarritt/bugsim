/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class PropertyInspectorButtons extends JPanel implements ActionListener {

    public PropertyInspectorButtons(PropertyInspector propertyInspector, boolean modal) {
        super(new FlowLayout(FlowLayout.TRAILING));
       setState(ButtonPanelState.CLEAN);
        _helpButton.setEnabled(false);

        _okButton.addActionListener(this);
        _applyButton.addActionListener(this);
        _helpButton.addActionListener(this);
        _cancelButton.addActionListener(this);


        if (!modal) {
            super.add(_applyButton);
        } else {
            super.add(_okButton);
        }


        super.add(_cancelButton);
        super.add(_helpButton);
        _propertyInspector = propertyInspector;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Ok")) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    _propertyInspector.doOkAction();
                }
            });
        } else if (e.getActionCommand().equals("Cancel")) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    _propertyInspector.doCancelAction();
                }
            });

        }   else if (e.getActionCommand().equals("Apply")) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    _propertyInspector.doApplyAction();
                }
            });

        }
    }

    public void setReadOnly(boolean readonly) {

//        _okButton.setEnabled(!readonly);
//        _applyButton.setEnabled(!readonly);
//        _cancelButton.setEnabled(!readonly);

    }

    public void setState(ButtonPanelState state) {
        _state = state;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setButtonsEnabled(_state);
            }


        });

    }

    private void setButtonsEnabled(ButtonPanelState state) {
        if (state == ButtonPanelState.CLEAN) {
            enableButton(_okButton, false);
            enableButton(_applyButton, false);
            enableButton(_cancelButton, false);
        } else if (state == ButtonPanelState.DIRTY) {
            enableButton(_okButton,true);
            enableButton(_applyButton,true);
            enableButton(_cancelButton,true);
        }
    }

    private void enableButton(JButton button, boolean enabled) {
        button.setEnabled(enabled);
        button.setFocusPainted(enabled);
    }


    private JButton _okButton = new JButton("Ok");
    private JButton _applyButton = new JButton("Apply");
    private JButton _cancelButton = new JButton("Cancel");
    private JButton _helpButton = new JButton("Help");


    private PropertyInspector _propertyInspector;
    private ButtonPanelState _state;
}


