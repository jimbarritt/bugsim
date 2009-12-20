/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout.levy;

import com.ixcode.bugsim.view.experiment.editor.resource.layout.ResourceLayoutPanelBase;
import com.ixcode.bugsim.experiment.parameter.resource.layout.levy.LevyWalkResourceLayoutStrategy;
import com.ixcode.framework.model.IModelAdapter;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 25, 2007 @ 5:15:49 PM by jim
 */
public class LevyWalkResourceLayoutPanel extends ResourceLayoutPanelBase {
    public LevyWalkResourceLayoutPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, LevyWalkResourceLayoutStrategy.class);
        initUI();
    }

    private void initUI() {
        super.add(new JLabel("Levy Walk Resource Layout Panel"), BorderLayout.CENTER);
    }
}
