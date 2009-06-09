/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SelectTextFocusListener implements FocusListener {

    public SelectTextFocusListener() {
    }

    public void focusGained(FocusEvent focusEvent) {
        Object source = focusEvent.getSource();
        if (source instanceof JTextField)  {
            JTextField f = (JTextField)source;
            f.setSelectionStart(0);
            f.setSelectionEnd(f.getText().length());
        }

    }

    public void focusLost(FocusEvent focusEvent) {

    }


}
