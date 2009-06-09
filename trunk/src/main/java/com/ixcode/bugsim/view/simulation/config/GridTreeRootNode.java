/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.config;

import com.ixcode.framework.simulation.model.landscape.grid.Grid;

import java.util.List;

/**
 *  Description : Container for a list of grids
 */
public class GridTreeRootNode {

    public GridTreeRootNode(String name, List grids) {
        _name = name;
        _grids = grids;
    }

    public void addGrid(Grid grid) {
        _grids.add(grid);
    }

    public List getGrids() {
        return _grids;
    }

    public String toString() {
        return _name;
    }

    public String getName() {
        return _name;
    }

    private String _name;
    private List _grids;
}
