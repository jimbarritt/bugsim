/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.config;

import javax.swing.*;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class GridConfigToolbar extends JToolBar {

    public GridConfigToolbar(JFrame owner, GridTree tree, List grids) {
        super.add(new AddGridAction(owner, tree, grids));
    }
}
