/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.grid;

import com.ixcode.bugsim.agent.boundary.CircularBoundaryAgent;
import com.ixcode.bugsim.agent.boundary.IBoundaryAgent;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.Intersection;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.boundary.IBoundary;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Description : ${CLASS_DESCRIPTION}
 * @todo maybe the grids should be agents ?
 */
public class Grid implements Serializable {


    /**
     * We only record crossing hte boundary in 1 direction - outside to in. could adapt to go both ways
     *
     * @param oldCoord
     * @param newCoord
     * @return
     */
    public Intersection enteredBoundary(RectangularCoordinate start, RectangularCoordinate end) {
        Intersection intersection = new Intersection();

        if ((outsideBoundary(start) || onEdgeOfBoundary(start)) && insideBoundary(end)) {
            if (!isCircular()) {
                throw new RuntimeException("No support for non circular boundaries!");
            }


            CartesianBounds bb = getBounds();
            boolean intersects = Geometry.lineInstersectsCircleDouble(start.getDoubleX(), start.getDoubleY(), end.getDoubleX(), end.getDoubleY(), bb.getDoubleCentreX(), bb.getDoubleCentreY(), bb.getRadiusOfInnerCircle());

            if (intersects) {
                List intersections = Geometry.findLineSegmentCircleIntersections(bb.getCentre().getDoubleX(), bb.getCentre().getDoubleY(),
                        bb.getRadiusOfInnerCircle(), start, end, true);

                if (intersections.size() > 0) {
                    intersection = new Intersection(start.findClosestCoordinate(intersections));
                }
            }
        }

        return intersection;

    }

    public Intersection exitedBoundary(RectangularCoordinate start, RectangularCoordinate end) {
        Intersection intersection = new Intersection();

        if ((insideBoundary(start) || onEdgeOfBoundary(start)) && outsideBoundary(end)) {
            if (!isCircular()) {
                throw new RuntimeException("No support for non circular boundaries!");
            }


            CartesianBounds bb = getBounds();
            boolean intersects = Geometry.lineInstersectsCircleDouble(start.getDoubleX(), start.getDoubleY(), end.getDoubleX(), end.getDoubleY(), bb.getDoubleCentreX(), bb.getDoubleCentreY(), bb.getRadiusOfInnerCircle());

            if (intersects) {
                List intersections = Geometry.findLineSegmentCircleIntersections(bb.getCentre().getDoubleX(), bb.getCentre().getDoubleY(),
                        bb.getRadiusOfInnerCircle(), start, end, true);

                if (intersections.size() > 0) {
                    intersection = new Intersection(start.findClosestCoordinate(intersections));
                }
            }
        }

        return intersection;

    }

    private boolean insideBoundary(RectangularCoordinate coord) {
        boolean inside;
        if (isCircular()) {
            inside = _boundary.isInside(coord);
        } else {
            inside =  _bounds.isOnEdge(coord);
        }
        return inside;
    }


    private boolean onEdgeOfBoundary(RectangularCoordinate coord) {
        boolean onEdge;
        if (isCircular()) {
            onEdge = _boundary.isOnEdge(coord);
        } else {
            onEdge =  _bounds.isOnEdge(coord);
        }
        return onEdge;
    }

    private boolean outsideBoundary(RectangularCoordinate coord) {
        boolean contains;
        if (isCircular()) {
            contains = _boundary.isOutside(coord);
        } else {
            contains =  !_bounds.isInside(coord);
        }
        return contains;
    }



    public Grid(GridSquareLocation parentLocation) {
        _parentGridSquareLocation = parentLocation;
    }

    public Grid(String name, GridSquareMap gridSquareMap) {
        this(name, gridSquareMap, false);

    }

    public Grid(String name, GridSquareMap gridSquareMap, boolean isCircular) {
        _name = name;
        _gridSquares = gridSquareMap;
        _gridSquares.setParent(this);
        _bounds = new CartesianBounds(_gridSquares.getLandscapeOrigin().getDoubleX(), _gridSquares.getLandscapeOrigin().getDoubleY(), _gridSquares.getLandscapeWidth(), _gridSquares.getLandscapeHeight());
        _isCircular = isCircular;
        if (isCircular) {
            _boundaryAgent = new CircularBoundaryAgent("myBoundary", new Location(_bounds.getCentre()), _bounds.getRadiusOfInnerCircle());
            _boundary = _boundaryAgent.getBoundary();
        } else {
            _boundaryAgent = null; // need to implement this!! rectangular boundary agent
        }


    }

    public boolean isCircular() {
        return _isCircular;
    }

    public Grid(Landscape landscape,  String name, RectangularCoordinate landscapeOrigin, double landscapeWidth, double landscapeHeight, int gridSquareCountX, int gridSquareCountY, boolean isCircular) {
        this(landscape, name, landscapeOrigin, landscapeWidth, landscapeHeight, gridSquareCountX, gridSquareCountY, null, isCircular);
    }

    public Grid(Landscape landscape, String name, RectangularCoordinate landscapeOrigin, double landscapeWidth, double landscapeHeight, int gridSquareCountX, int gridSquareCountY) {
        this(landscape,  name, landscapeOrigin, landscapeWidth, landscapeHeight, gridSquareCountX, gridSquareCountY, null, false);
    }

    public Grid(Landscape landscape, String name, RectangularCoordinate landscapeOrigin, double landscapeWidth, double landscapeHeight, int gridSquareCountX, int gridSquareCountY, GridSquareLocation parentLocation) {
        this(landscape, name, landscapeOrigin, landscapeWidth, landscapeHeight, gridSquareCountX, gridSquareCountY, parentLocation, false);
    }

    public Grid(Landscape landscape, String name, RectangularCoordinate landscapeOrigin, double landscapeWidth, double landscapeHeight, int gridSquareCountX, int gridSquareCountY, GridSquareLocation parentLocation, boolean isCircular) {
        _parentGridSquareLocation = parentLocation;
        _isCircular = isCircular;
        initGrid(landscape,  name, landscapeOrigin, landscapeWidth, landscapeHeight, gridSquareCountX, gridSquareCountY);

    }

    public void initGrid(Landscape landscape, String name, RectangularCoordinate landscapeOrigin, double landscapeWidth, double landscapeHeight, int gridSquareCountX, int gridSquareCountY) {
        _landscape = landscape;
        _name = name;
        _gridSquares = new GridSquareMap(this, landscapeOrigin, landscapeWidth, landscapeHeight, gridSquareCountX, gridSquareCountY);
//        _hasChildGrids = calculateHasChildGrids(_gridSquares);
        _bounds = new CartesianBounds(_gridSquares.getLandscapeOrigin().getDoubleX(), _gridSquares.getLandscapeOrigin().getDoubleY(), _gridSquares.getLandscapeWidth(), _gridSquares.getLandscapeHeight());

    if (_isCircular) {
            _boundaryAgent = new CircularBoundaryAgent("myBoundary", new Location(_bounds.getCentre()), _bounds.getRadiusOfInnerCircle());
        _boundary = _boundaryAgent.getBoundary();
        } else {
            _boundaryAgent = null; // need to implement this!! rectangular boundary agent
        }
    }

    public boolean calculateHasChildGrids(GridSquareMap gridSquares) {
        boolean hasChildGrids = false;
        for (Iterator itr = gridSquares.asList().iterator(); itr.hasNext();) {
            GridSquare gridSquare = (GridSquare)itr.next();
            if (gridSquare.hasChildGrid()) {
                hasChildGrids = true;
                break;
            }
        }
        return hasChildGrids;
    }

    public boolean hasChildGrids() {
        return calculateHasChildGrids(_gridSquares);
    }

    public GridSquare getGridSquare(GridSquareLocation location) {
        return getGridSquare(location.getX(), location.getY());
    }

    public GridSquare getGridSquare(int x, int y) {
        return _gridSquares.getGridSquare(x, y);
    }

    public GridSize getGridSize() {
        return _gridSquares.getGridSize();
    }

    public String getName() {
        return _name;
    }

    public List getGridSquares() {
        return _gridSquares.asList();
    }

    public RectangularCoordinate getLandscapeOrigin() {
        return _gridSquares.getLandscapeOrigin();
    }

    public double getLandscapeWidth() {
        return _gridSquares.getLandscapeWidth();
    }

    public double getLandscapeHeight() {
        return _gridSquares.getLandscapeHeight();
    }

    public GridSquareLocation getParentGridSquareLocation() {
        return _parentGridSquareLocation;
    }

    public boolean isRootGrid() {
        return (_parentGridSquareLocation == null);
    }


    public void setGridSize(GridSize gridSize) {
        _gridSize = gridSize;

    }

    public String toString() {
        return _name + " : " + _gridSquares;
    }

    public boolean isNonUniformGrid() {
        return _nonUniformGrid;
    }

    public void setNonUniformGrid(boolean nonUniformGrid) {
        _nonUniformGrid = nonUniformGrid;
    }

    public List getContainingGridSquares(RectangularCoordinate start, RectangularCoordinate end) {
        return _gridSquares.getContainingGridSquares(start, end);

    }

    public List getContainingGridSquares(RectangularCoordinate coord) {
        List containingGridSquares = new ArrayList();
        List gridSquares = _gridSquares.getContainingGridSquares(coord);
        containingGridSquares.addAll(gridSquares);
        for (Iterator itr = gridSquares.iterator(); itr.hasNext();) {
            GridSquare gridSquare = (GridSquare)itr.next();
            if (gridSquare.hasChildGrid()) {
                List children = gridSquare.getChildGrid().getContainingGridSquares(coord);
                containingGridSquares.addAll(children);
            }
        }

        return containingGridSquares;
    }

    public CartesianBounds getBounds() {
        return _bounds;
    }

    public Landscape getLandscape() {
        return _landscape;
    }

    public RectangularCoordinate generateRandomLocation(Random random, boolean avoidAgents, double avoidingRadius) {
        if (_isCircular) {
            return generateCircularRandomLocation(random, avoidAgents,  avoidingRadius);
        } else {
            return generateRectangularRandomLocation(random,  avoidAgents,  avoidingRadius);
        }
    }

    /**
     * Not using the avoiding radius just yet but its there incase its important later....
     * at the moment all intersections are done on lines so as long as the point is not inside the resource its good.
     * @param random
     * @param avoidAgents
     * @param avoidingRadius
     * @return
     */
    private RectangularCoordinate generateRectangularRandomLocation(Random random, boolean avoidAgents, double avoidingRadius) {
        boolean clearOfAgent = false;
        RectangularCoordinate location = null;

        if (avoidAgents) {
            while(!clearOfAgent) {
                location = Geometry.generateUniformRandomCoordinate(random, _bounds);
                clearOfAgent = isLocationClearOfAgent(location);
            }
        } else {
            location = Geometry.generateUniformRandomCoordinate(random, _bounds);
        }
        return location;
    }

    private boolean isLocationClearOfAgent(RectangularCoordinate location) {
        boolean clear=true;
        List agents = _landscape.getAgents();
        for (Iterator itr = agents.iterator(); itr.hasNext();) {
            IPhysicalAgent agent = (IPhysicalAgent)itr.next();
            if (agent.containsPoint(location)) {
                clear=false;
                break;
            }
        }
        return clear;
    }

    private RectangularCoordinate generateCircularRandomLocation(Random random, boolean avoidAgents, double avoidingRadius) {
        throw new IllegalArgumentException("Method not yet implemented") ;
    }



    public boolean containsPoint(RectangularCoordinate coord, boolean includeEdge) {
        boolean contained= false;

        
        if (includeEdge) {
            contained = _boundary.isInside(coord) || _boundary.isOnEdge(coord);
        } else {
            contained = _boundary.isInside(coord);
        }

        return contained;

    }


    private String _name;
    private GridSquareMap _gridSquares;
    private GridSquareLocation _parentGridSquareLocation;
    private boolean _hasChildGrids;
    private GridSize _gridSize;
    private boolean _nonUniformGrid = false;
    private CartesianBounds _bounds;
    private boolean _isCircular;

    private static final Logger log = Logger.getLogger(Grid.class);
    private IBoundaryAgent _boundaryAgent;
    private IBoundary _boundary;
    private Landscape _landscape;
}
