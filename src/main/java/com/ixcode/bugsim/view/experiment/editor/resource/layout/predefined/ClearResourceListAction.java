/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout.predefined;

import com.ixcode.framework.swing.action.ActionBase;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.bugsim.experiment.parameter.resource.layout.ResourceListFile;
import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeCategory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 7, 2007 @ 6:40:03 PM by jim
 */
public class ClearResourceListAction extends ActionBase {

    public ClearResourceListAction(PredefinedResourceListPanel parent) {
        super("Clear");
        _parent = parent;
    }

    public void actionPerformed(ActionEvent e) {

        _parent.clearResourceList();
    }


    private PredefinedResourceListPanel _parent;
}
