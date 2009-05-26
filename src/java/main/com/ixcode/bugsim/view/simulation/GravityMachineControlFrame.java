/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation;

import com.ixcode.framework.swing.JFrameExtension;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class GravityMachineControlFrame extends JFrameExtension {

    public static final String TITLE = "Gravity Machine control panel";

    public GravityMachineControlFrame(JComponent content) throws HeadlessException {
        super(TITLE, content, new JPanel().getBackground(), BorderLayout.NORTH, false);
        super.setSize(300, 700);
        super.setLocation(400, 0);
    }
}
