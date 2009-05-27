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
public class EnumerationWithLookupField extends JPanel {

    public EnumerationWithLookupField(EnumerationComboBox combo, IEnumerationDescriptionLookup lookup) {
        super(new BorderLayout());
        _combo = combo;
        _lookup = lookup;
        _lookupLabel = new JLabel();
        _lookupLabel.setForeground(Color.GRAY);

        initUI();

    }

    private void initUI() {
        super.add(_combo, BorderLayout.WEST);
        if (_lookup != null) {
            super.add(_lookupLabel, BorderLayout.CENTER);

            _combo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    updateLabel();
                }
            });
        }
    }

    private void updateLabel() {
        _lookupLabel.setText("   " + _lookup.getDescriptionForValue(_combo.getSelectedValue()));
    }

    public EnumerationComboBox getCombo() {
        return _combo;
    }

    public IEnumerationDescriptionLookup getLookup() {
        return _lookup;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        _combo.setEnabled(enabled);
    }

    public void addActionListener(ActionListener actionListener) {
        _combo.addActionListener(actionListener);
    }

    private EnumerationComboBox _combo;
    private IEnumerationDescriptionLookup _lookup;
    private JLabel _lookupLabel;
}
