/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.grid;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.DoubleMath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class GridSquareMap extends GridSquare implements Serializable {

    public GridSquareMap(RectangularCoordinate origin, double landscapeWidth, double landscapeHeight, GridSquare[][] gridSquares) {
        super(0, 0, origin, landscapeWidth, landscapeHeight, null, false);
        _gridSquares = gridSquares;
        _gridSquareCountY = gridSquares.length;
        _gridSquareCountX = countGridSquaresX(_gridSquares);
        _gridSize = new GridSize(_gridSquareCountX, _gridSquareCountY);

        updateGridSquareList(_gridSquares);


    }

    private void updateGridSquareList(GridSquare[][] gridSquares) {
        for (int x=0;x<gridSquares.length;++x) {
            for (int y=0;y<gridSquares[x].length;++y) {
                _gridSquaresList.add(gridSquares[x][y]);
            }
        }
    }

    private int countGridSquaresX(GridSquare[][] gridSquares) {
        int countX = 0;
        for (int y=0;y<gridSquares.length;++y) {
            if (gridSquares[y].length > countX) {
                countX = gridSquares[y].length;
            }
        }
        return countX;
    }

    public GridSquareMap(Grid parent, RectangularCoordinate origin, double landscapeWidth, double landscapeHeight, int gridSquareCountX, int gridSquareCountY) {
        super(parent, origin, landscapeWidth, landscapeHeight, null, false);
        _gridSquareCountX = gridSquareCountX;
        _gridSquareCountY = gridSquareCountY;
        _gridSize = new GridSize(_gridSquareCountX, _gridSquareCountY);

        _gridSquares = initGridSquares(parent, origin, landscapeWidth, landscapeHeight, _gridSquareCountX, _gridSquareCountY, _gridSquaresList);
//        System.out.println("GridSquareMap<init>: " + origin);
    }

    private static GridSquare[][] initGridSquares(Grid parent, RectangularCoordinate origin, double landscapeWidth, double landscapeHeight, int gridSquareCountX, int gridSquareCountY, List gridSquareList) {
        if (log.isInfoEnabled()) {
            log.info("Initialising grid '" + parent.getName() + "'");
        }
        GridSquare[][] gridSquares = new GridSquare[gridSquareCountX][gridSquareCountY];

        double gridSquareWidth = landscapeWidth / gridSquareCountX;
        double gridSquareHeight = landscapeHeight / gridSquareCountY;

        for (int x = 0; x < gridSquareCountX; ++x) {
            for (int y = 0; y < gridSquareCountY; ++y) {
                double currentX = origin.getDoubleX() + (gridSquareWidth * (x));
                double currentY = origin.getDoubleY() + (gridSquareWidth * (y));

                boolean topRight = (x==gridSquareCountX-1 ||(y==gridSquareCountY+1));

                RectangularCoordinate gridSquareOrigin = new RectangularCoordinate(currentX, currentY);
                GridSquare gridSquare = new GridSquare(parent, x, y, gridSquareOrigin, gridSquareWidth, gridSquareHeight, null, topRight);
                gridSquares[x][y] = gridSquare;
                gridSquareList.add(gridSquare);
            }
        }
        return gridSquares;
    }

    public GridSquare getGridSquare(int x, int y) {
        return _gridSquares[x][y];
    }


    public int getGridSquareCountX() {
        return _gridSquareCountX;
    }

    public int getGridSquareCountY() {
        return _gridSquareCountY;
    }

    public GridSize getGridSize() {
        return _gridSize;
    }

    public List asList() {
        return _gridSquaresList;
    }

    public void setParent(Grid parent) {
        super.setParent(parent);
        for (int x=0;x<_gridSquares.length;++x) {
            for (int y=0;y<_gridSquares[x].length;++y) {
                _gridSquares[x][y].setParent(parent);
            }
        }
    }

    public List getContainingGridSquares(RectangularCoordinate coord) {
        RectangularCoordinate relativeCoord = getCoordRelativeToParent(coord);
        List containingGridSquares = new ArrayList();
        for (Iterator itr = _gridSquaresList.iterator(); itr.hasNext();) {
            GridSquare gridSquare = (GridSquare)itr.next();
            if (gridSquare.containsPoint(relativeCoord)) {
                containingGridSquares.add(gridSquare);
            }
        }
        return containingGridSquares;
    }

    public List getContainingGridSquares(RectangularCoordinate start, RectangularCoordinate end) {
        double xmin = (start.getDoubleX() < end.getDoubleX()) ? start.getDoubleX() : end.getDoubleX();
        double xmax = (start.getDoubleX() >= end.getDoubleX()) ? start.getDoubleX() : end.getDoubleX();
        double ymin = (start.getDoubleY() < end.getDoubleY()) ? start.getDoubleY() : end.getDoubleY();
        double ymax = (start.getDoubleY() >= end.getDoubleY()) ? start.getDoubleY() : end.getDoubleY();

        boolean addSquare = false;
        boolean adding = false;
        List xIndexes = new ArrayList();
        for (int ix=0;ix<_gridSquareCountX;++ix) {
            GridSquare square = _gridSquares[ix][0];
            CartesianBounds sb = square.getLandscapeBounds();
            double sbxMin = sb.getDoubleX();
            double sbxMax = sb.getDoubleX() + sb.getDoubleWidth();
            if (!adding && sbxMax > xmin) {
                addSquare = true;
                adding = true;
            }
            if (sbxMin > xmax) {
                addSquare = false;
            }
            if (addSquare) {
                xIndexes.add(new Integer(ix));
            }


        }

        addSquare = false;
        adding = false;
        List yIndexes = new ArrayList();
        for (int iy=0;iy<_gridSquareCountY;++iy) {
            GridSquare square = _gridSquares[0][iy];
            CartesianBounds sb = square.getLandscapeBounds();
            double sbyMin = sb.getDoubleY();
            double sbyMax = sb.getDoubleY() + sb.getDoubleHeight();
            if (!adding && sbyMax > ymin) {
                addSquare = true;
                adding = true;
            }
            if (sbyMin > ymax) {
                addSquare = false;
            }
            if (addSquare) {
                yIndexes.add(new Integer(iy));
            }

        }

        List containingSquares = new ArrayList();
        for (Iterator itrX = xIndexes.iterator(); itrX.hasNext();) {
            Integer xInt = (Integer)itrX.next();

            for (Iterator itrY = yIndexes.iterator(); itrY.hasNext();) {
                Integer yInt = (Integer)itrY.next();

                containingSquares.add(_gridSquares[xInt.intValue()][yInt.intValue()]);
            }
        }

        return containingSquares;
    }


    private static final Logger log = Logger.getLogger(GridSquareMap.class);

    private int _gridSquareCountX;
    private int _gridSquareCountY;
    private GridSquare[][] _gridSquares;

    private GridSize _gridSize;
    private List _gridSquaresList = new ArrayList();
}
