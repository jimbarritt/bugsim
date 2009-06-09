/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class StatusBar extends JPanel {

    public StatusBar() {
        setLayout(new BorderLayout());
        _message = new JLabel("Status bar");


        super.add(_message, BorderLayout.WEST);
        super.setMinimumSize(new Dimension(15, 17));
        super.setPreferredSize(new Dimension(15, 17));
        getMessageLabel().setForeground(Color.DARK_GRAY);
    }


    public String getMessage() {
        return _message.getText();
    }

    public void setText(String message) {
        _message.setText(message);
        invalidate();
        repaint();
    }

    public JLabel getMessageLabel() {
        return _message;

    }

    private JLabel _message;
}
