/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.config;

import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquare;
import com.ixcode.framework.swing.TreeCellRendererBase;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class GridTreeCellRenderer extends TreeCellRendererBase {


    public GridTreeCellRenderer() {

    }

    protected String getLabelText(Object node) {
        String labelText = node.toString();
        if (node instanceof GridTreeRootNode) {
            labelText = getLabelText((GridTreeRootNode)node);
        } else if (node instanceof Grid) {
            labelText = getLabelText((Grid)node);
        } else if (node instanceof GridSquare) {
            labelText = getLabelText((GridSquare)node);
        }  else {
            System.out.println("GridTreeCellRenderer - Unkown node type!! :" + node.getClass().getName());
        }
        return labelText;
    }

    private String getLabelText(GridTreeRootNode rootNode) {
        return rootNode.getName();
    }

    private String getLabelText(Grid grid) {
        return grid.getName();
    }

    private String getLabelText(GridSquare gridSquare) {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        sb.append(gridSquare.getxIndex()).append(", ");
        sb.append(gridSquare.getyIndex());
        sb.append(") ");
        if (gridSquare.hasChildGrid()) {
            sb.append(gridSquare.getChildGrid().getName());
        }
        return sb.toString();
    }


//    private static final Icon GRID_ICON = createImageIcon("/icons/root.png");
//    private static final Icon ROOT_ICON = createImageIcon("/icons/group.gif");



}
