/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.motivation;

import com.ixcode.framework.model.IModelAdapter;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 4, 2007 @ 10:11:21 PM by jim
 */
public class MotivationStrategyPanel extends JPanel {
    public MotivationStrategyPanel(IModelAdapter modelAdapter) {
        super(new BorderLayout());
        initUI();
    }

    private void initUI() {
        JPanel container = new JPanel(new BorderLayout());
        container.add(new JLabel("Motivation Panel"), BorderLayout.NORTH);
    }


}
