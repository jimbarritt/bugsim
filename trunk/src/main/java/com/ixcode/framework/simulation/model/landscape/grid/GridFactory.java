/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.grid;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.landscape.Landscape;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class GridFactory {
    public static Grid createGrid(Landscape landscape, String name, double landscapeX, double landscapeY, double landscapeWidth, double landscapeHeight, int gridSquareCountX, int gridSquareCountY) {
        return new Grid( landscape, name, new RectangularCoordinate(landscapeX, landscapeY), landscapeWidth, landscapeHeight, gridSquareCountX, gridSquareCountY);
    }

    public static Grid createGrid(Landscape landscape, String name, double landscapeX, double landscapeY, double landscapeWidth, double landscapeHeight, int gridSquareCountX, int gridSquareCountY, boolean isCircular) {
        return new Grid(landscape, name, new RectangularCoordinate(landscapeX, landscapeY), landscapeWidth, landscapeHeight, gridSquareCountX, gridSquareCountY, isCircular);
    }

    public static Grid createChildGrid(Landscape landscape, String name, Grid parentGrid, int x, int y, int gridSquareCountX, int gridSquarecountY) {
        GridSquare gridSquare = parentGrid.getGridSquare(x, y);
        return gridSquare.addChildGrid(landscape, name, gridSquareCountX, gridSquarecountY);
    }

    public static Grid createTestGrid(String name) {

        Grid grid = createGrid(null, name, 0, 0, 3600, 3600, 2, 2);
        Grid child4 = createTreatments(grid);
        createBlocks(child4);

        return grid;

    }

    public static Grid createTreatments(Grid grid) {
        Grid child1 = createChildGrid(null, "Treatment 1", grid, 0, 0, 2, 2);
        createBlocks(child1);
        Grid child2 = createChildGrid(null, "Treatment 2", grid, 0, 1, 2, 2);
        createBlocks(child2);
        Grid child3 = createChildGrid(null, "Treatment 3", grid, 1, 0, 2, 2);
        createBlocks(child3);
        Grid child4 = createChildGrid(null,"Treatment 4", grid, 1, 1, 2, 2);
        return child4;
    }

    public static void createBlocks(Grid grid) {

        createChildGrid(null, "Block A", grid, 0, 0, 9, 9);
        createChildGrid(null, "Block B", grid, 0, 1, 9, 9);
        createChildGrid(null, "Block C", grid, 1, 0, 9, 9);
        createChildGrid(null, "Block D", grid, 1, 1, 9, 9);

    }

    /**
     * @todo go through whole grid construction process and create bigdecmail / cartesian bounds version
     * @param gridName
     * @param b
     * @param rowCount
     * @param colCount
     * @return
     */
    public static Grid createGrid(Landscape landscape, String gridName, CartesianBounds b, int rowCount, int colCount) {
        return new Grid(landscape,  gridName, b.getOrigin(),  b.getWidth().doubleValue(), b.getHeight().doubleValue(), rowCount, colCount);
    }

    public static Grid createGrid(Landscape landscape, String gridName, CartesianBounds b, int rowCount, int colCount, boolean isCircular) {
        return new Grid(landscape, gridName, b.getOrigin(),  b.getWidth().doubleValue(), b.getHeight().doubleValue(), rowCount, colCount, isCircular);
    }
}
