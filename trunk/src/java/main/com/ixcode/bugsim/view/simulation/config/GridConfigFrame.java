/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.config;

import com.ixcode.framework.simulation.model.landscape.grid.GridFactory;
import com.ixcode.framework.swing.JFrameExtension;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class GridConfigFrame extends JFrameExtension {

    public GridConfigFrame(List grids) throws HeadlessException {
        super("Configure Grids");
        JPanel content = new JPanel(new BorderLayout());

        _gridTree = new GridTree("Grids", grids);
        content.add(new GridConfigToolbar(this, _gridTree, grids), BorderLayout.NORTH);

        JScrollPane sp =new JScrollPane(_gridTree);
        sp.setBorder(BorderFactory.createEmptyBorder());


        content.add(sp,BorderLayout.CENTER);

        content.setMinimumSize(new Dimension(200, 300));
        content.setPreferredSize(new Dimension(200, 300));
        super.getContentPane().add(content);
        super.pack();


    }

    public static void main(String[] args) {
        java.util.List grids = new ArrayList();
        grids.add(GridFactory.createTestGrid("Experiment 1"));
        grids.add(GridFactory.createTestGrid("Experiment 2"));
        grids.add(GridFactory.createTestGrid("Experiment 3"));
        grids.add(GridFactory.createTestGrid("Experiment 4"));


        GridConfigFrame frame = new GridConfigFrame(grids);
        frame.show();
    }

    private GridTree _gridTree;
}
