/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout.predefined;

import com.ixcode.bugsim.model.experiment.parameter.landscape.LandscapeCategory;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.ResourceListFile;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.swing.action.ActionBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 7, 2007 @ 6:40:03 PM by jim
 */
public class ImportResourceListAction extends ActionBase {

    public ImportResourceListAction(PredefinedResourceListPanel parent) {
        super("Import...");
        _parent = parent;
    }

    public void actionPerformed(ActionEvent e) {
        ImportResourceListDialog importD;
        Container parent = _parent.getTopLevelAncestor();
        if (parent instanceof JFrame) {
            importD = new ImportResourceListDialog((JFrame)parent);
        } else {
            importD = new ImportResourceListDialog((JDialog)parent);            
        }

            importD.showOpenPlanDialog();

            if (!importD.isCancelled()) {
                ResourceListFile resourceListFile = importD.getSelectedResourceListFile();

                ScaledDistance logicalScale = LandscapeCategory.findLandscapeScale(_parent.getParameterMap());

                _parent.importResources(resourceListFile, importD.getRadius(), importD.getInputUnits(), logicalScale);
//                _parent.setLoadDir(resourceListFile.getParentFile().getAbsolutePath());
            }

//        JFileChooser fileChooser = new JFileChooser(".");
//        fileChooser.setDialogTitle("Import resources ...");
//
//        fileChooser.setCurrentDirectory(new File(_parent.getLoadDir()));
//        fileChooser.setFileFilter(RESOURCE_FILE_FILTER);
//        int result = fileChooser.showOpenDialog(_parent);
//
//        if ((result == JFileChooser.APPROVE_OPTION) && fileChooser.getSelectedFile() != null) {
//            File file = fileChooser.getSelectedFile();
//            _parent.loadResources(file);
//            _parent.setLoadDir(file.getParentFile().getAbsolutePath());
//
//        }

    }


    private PredefinedResourceListPanel _parent;
}
