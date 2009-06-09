/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.config;

import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquare;
import com.ixcode.framework.swing.TreeModelBase;

import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class GridTreeModel extends TreeModelBase {
    public GridTreeModel(String name, List root) {
        super(new GridTreeRootNode(name, root));


    }


    protected Object getParent(Object child) {
        Object parent = null;
        if (child instanceof GridTreeRootNode) {
            parent = null; // nothing to do here!
        } else if (child instanceof Grid) {
            Grid g = (Grid)child;
            if (g.getParentGridSquareLocation() != null) {
                parent = ((Grid)child).getParentGridSquareLocation().getGrid();
            } else {
                parent = super.getRoot();
            }
        } else if (child instanceof GridSquare) {
            parent = ((GridSquare)child).getParent();
        }
        return parent;
    }

    public Object getChild(Object parent, int index) {
        Object child = null;
        if (parent instanceof GridTreeRootNode) {
            child = ((GridTreeRootNode)parent).getGrids().get(index);
        } else if ((parent instanceof Grid) && (((Grid)parent).hasChildGrids())) {
            child = ((Grid)parent).getGridSquares().get(index);
        } else if (parent instanceof GridSquare) {
            GridSquare sq = (GridSquare)parent;
            if (sq.hasChildGrid() && sq.getChildGrid().hasChildGrids()) {
                child = sq.getChildGrid().getGridSquares().get(index);
            }
        }
        return child;
    }

    public int getChildCount(Object parent) {
        int childCount = 0;
        if (parent instanceof GridTreeRootNode) {
            childCount = ((GridTreeRootNode)parent).getGrids().size();
        } else if ((parent instanceof Grid) && (((Grid)parent).hasChildGrids())) {
            childCount = ((Grid)parent).getGridSquares().size();
        } else if (parent instanceof GridSquare) {
            GridSquare sq = (GridSquare)parent;
            if (sq.hasChildGrid() && sq.getChildGrid().hasChildGrids()) {
                childCount = sq.getChildGrid().getGridSquares().size();
            }
        }
        return childCount;
    }

    public boolean isLeaf(Object node) {
        return (getChildCount(node) == 0);
    }

    public int getIndexOfChild(Object parent, Object child) {
        int childIndex = -1;
        if (parent instanceof GridTreeRootNode) {
            childIndex = ((GridTreeRootNode)parent).getGrids().indexOf(child);
        } else if (parent instanceof Grid) {
            childIndex = ((Grid)parent).getGridSquares().indexOf(child);
        } else if (parent instanceof GridSquare) {
            GridSquare sq = (GridSquare)parent;
            childIndex = sq.hasChildGrid() ? sq.getChildGrid().getGridSquares().indexOf(child) : -1;
        }
        return childIndex;
    }


}
