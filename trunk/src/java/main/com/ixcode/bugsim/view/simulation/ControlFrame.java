/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ControlFrame extends JFrame {

    public static final String TITLE = "Landscape Properties";

    public ControlFrame(JComponent content) throws HeadlessException {
        super(TITLE);
        JPanel panel = new JPanel(new BorderLayout());
        super.getContentPane().add(panel, BorderLayout.CENTER);
        panel.add(content, BorderLayout.NORTH);
        super.setSize(300, 550);
        super.setLocation(100, 100);
//        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


}
