/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.grid;

import java.io.Serializable;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class GridSize implements Serializable {

    public GridSize(int width, int height) {
        _width = width;
        _height = height;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    private int _width;
    private int _height;
}
