/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.swing;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 26, 2007 @ 4:55:52 PM by jim
 */
public class FixedWidthLabel extends JLabel {

    public FixedWidthLabel(String text, Icon icon, int horizontalAlignment, int width) {
        super(text, icon, horizontalAlignment);
        initWidth(width);
    }

    private void initWidth(int width) {
        setPreferredSize(new Dimension(width, getMinimumSize().height));
    }

    public FixedWidthLabel(String text, int horizontalAlignment, int width) {
        super(text, horizontalAlignment);
        initWidth(width);
    }

    public FixedWidthLabel(String text, int width) {
        super(text);
        initWidth(width);
    }

    public FixedWidthLabel(Icon image, int horizontalAlignment, int width) {
        super(image, horizontalAlignment);
        initWidth(width);
    }

    public FixedWidthLabel(Icon image, int width) {
        super(image);
        initWidth(width);
    }

    public FixedWidthLabel(int width) {
        initWidth(width);
    }

    public FixedWidthLabel() {
    }
}
