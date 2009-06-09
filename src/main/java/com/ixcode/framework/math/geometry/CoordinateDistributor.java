/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import com.ixcode.framework.math.DoubleMath;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : Given an area and a number of points to plot, will distribute the points in different fashions.
 */
public class CoordinateDistributor {


    /**
     * Works out a square layout for the points and distributes them accross the square.
     * It calculates how many "suqares" to make by finding the closest sqrt which can contain the
     * number of points you requested and then starts from top left moving right and then down a line until you have all the points
     * requested.
     * <p/>
     * e.g. extentX=10, extentY=10, numberOfCoords=16
     * <p/>
     * <p/>
     * 10  |* * * * * * * * * * *
     *     |*   *   *   *   *   *
     * 8   |* * X * X * X * X * *
     *     |*   *   *   *   *   *
     * 6   |* * X * X * X * X * *
     *     |*   *   *   *   *   *
     * 4   |* * X * X * X * X * *
     *     |*   *   *   *   *   *
     * 2   |* * X * X * X * X * *
     *     |*   *   *   *   *   *
     *     |* * * * * * * * * * *
     *     |---------------------
     * (0,0)  2   4   6   8   10
     * <p/>
     * Another option would be to distribute them all on the outside so (make number of points 36 to make it easier to draw:) :
     * <p/>
     * 10  |X * X * X * X * X * X
     *     |*   *   *   *   *   *
     * 8   |X * X * X * X * X * X
     *     |*   *   *   *   *   *
     * 6   |X * X * X * X * X * X
     *     |*   *   *   *   *   *
     * 4   |X * X * X * X * X * X
     *     |*   *   *   *   *   *
     * 2   |X * X * X * X * X * X
     *     |*   *   *   *   *   *
     *     |X * X * X * X * X * X
     *     |---------------------
     * (0,0)  2   4   6   8   10
     *
     * @param extentX
     * @param extentY
     * @param numberOfCoords
     * @return List of Coordinates
     */
    public List distributePointsInSquare(double extentX, double extentY, int numberOfCoords, DistributionType type) {
        if (type == DistributionType.OUTER) {
            throw new UnsupportedOperationException("Dont currently support creating an OUTER distribution");
        }

        List coords = new ArrayList();

        int numberOfSquares = DoubleMath.inclusiveSqrt(numberOfCoords) + 1;

        int squareSize = (int)(extentX / numberOfSquares);
        for (int iX = 1; iX < numberOfSquares; ++iX) {  // start at one because we dont want the first line (0,0)
            double x = iX * squareSize;
            for (int iY = 1; iY < numberOfSquares; ++iY) {
                double y = iY * squareSize;
                RectangularCoordinate coord = new RectangularCoordinate(x, y);
                coords.add(coord);
                if (coords.size() >= numberOfCoords) {
                    break;
                }
            }
            if (coords.size() >= numberOfCoords) {
                break;
            }
        }

        return coords;
    }

    /**
     * Creates a set of coordinates which are in a rectangle and where each point is equally separated from every other one.
     *
     * @param xCount
     * @param yCount
     * @param interPointDistance
     * @return the list, numbered from top left to bottom right assumes origin is bottom left
     */
    public List distributePointsBySeparation(int xCount, int yCount, double interPointDistance, boolean includeBorder) {
        List coords = new ArrayList();

        CartesianBounds b = calculateBoundsForSeparation(xCount, yCount, interPointDistance, includeBorder);

        double extentY = b.getDoubleHeight();

        double border = interPointDistance / 2;
        double currentX = border;
        double currentY = extentY - (border);

        for (int y = 0; y < yCount; ++y) {
            for (int x = 0; x < xCount; ++x) {
                RectangularCoordinate coord = new RectangularCoordinate(currentX, currentY);
                coords.add(coord);

                currentX += interPointDistance;
            }
            currentX = border;
            currentY -= interPointDistance;
        }

        return coords;
    }

    /**                                      
     * Tells you how big the rectangle containing the points is.
     * Allows for a border surrounding the points which is half of the interpoint distance if you specify that
     *
     * @param xCount
     * @param yCount
     * @param interPointDistance
     * @return
     */
    public CartesianBounds calculateBoundsForSeparation(int xCount, int yCount, double interPointDistance, boolean includeBorder) {
        double border = (includeBorder) ? interPointDistance  : 0;
        double extentX = ((double)(xCount - 1) * interPointDistance) + border;
        double extentY = ((double)(yCount - 1) * interPointDistance) + border;
        return new CartesianBounds(0, 0, extentX, extentY);
    }

    /**
     * Tells you what the separation should be to fill a certain rectangle.
     * <p/>
     * This is one dimensional - usually this is done in a square so edge sep is the same for x and y.
     */
    public double calculateSeparationForBounds(int pointCount, double length, boolean includeBorder) {
        double divisor = (includeBorder) ? pointCount : pointCount - 1;
        return length / divisor;        
    }
}
