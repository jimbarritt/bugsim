/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

/**
 * Description : Gives you some basic rendering capabilites to ease the pain of JTrees!
 */
public abstract class TreeCellRendererBase extends DefaultTreeCellRenderer {

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel label = (JLabel)super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        label.setText(getLabelText(value));

        Icon icon = getIcon(value);
        if (icon != null) {
            label.setIcon(icon);
        }
        return label;
    }

    protected abstract String getLabelText(Object node);


    protected Icon getIcon(Object node) {
        return null;
    }


    public static ImageIcon createImageIcon(String iconResourcePath) {
        URL url = ImageIcon.class.getResource(iconResourcePath);
        if (url == null) {
            throw new RuntimeException("Could not load icon from classpath: " + iconResourcePath);
        }

        return new ImageIcon(url);
    }
}
