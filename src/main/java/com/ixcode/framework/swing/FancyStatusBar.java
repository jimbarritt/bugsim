package com.ixcode.framework.swing;

import com.explodingpixels.macwidgets.*;

import javax.swing.*;

public class FancyStatusBar extends StatusBar {
    private final BottomBar bottomBar = new BottomBar(BottomBarSize.SMALL);
    private final JLabel emphasizedLabel = MacWidgetFactory.createEmphasizedLabel(" Status");

    public FancyStatusBar() {
        bottomBar.addComponentToLeft(emphasizedLabel);
    }

    public JComponent getComponent() {
        return bottomBar.getComponent();
    }

    @Override
    public String getMessage() {
        return emphasizedLabel.getText();
    }

    @Override
    public void setText(String message) {
        emphasizedLabel.setText(message);
        emphasizedLabel.invalidate();
        emphasizedLabel.repaint();
    }

    @Override
    public JLabel getMessageLabel() {
        return emphasizedLabel;
    }
}
