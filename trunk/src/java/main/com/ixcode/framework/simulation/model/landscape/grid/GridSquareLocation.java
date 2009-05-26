/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.grid;

import com.ixcode.framework.simulation.model.landscape.grid.Grid;

import java.io.Serializable;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class GridSquareLocation implements Serializable {


    public GridSquareLocation(Grid grid, int x, int y) {
        _x = x;
        _y = y;
        _grid = grid;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public Grid getGrid() {
        return _grid;
    }

    private int _x;
    private int _y;
    private Grid _grid;
}
