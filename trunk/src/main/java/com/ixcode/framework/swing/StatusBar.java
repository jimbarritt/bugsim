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
        messageLabel = new JLabel("Status bar");


        super.add(messageLabel, BorderLayout.WEST);
        super.setMinimumSize(new Dimension(15, 17));
        super.setPreferredSize(new Dimension(15, 17));
        messageLabel.setForeground(Color.DARK_GRAY);
    }


    public String getMessage() {
        return messageLabel.getText();
    }

    public void setText(String message) {
        messageLabel.setText(message);
        invalidate();
        repaint();
    }

    public JLabel getMessageLabel() {
        return messageLabel;

    }

    private JLabel messageLabel;
}
