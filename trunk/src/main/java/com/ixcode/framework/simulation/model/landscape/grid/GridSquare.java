/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.grid;

import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgentFilter;
import com.ixcode.framework.datatype.analysis.AnalysisValue;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class GridSquare implements Serializable {
    public GridSquare(int x, int y, RectangularCoordinate origin, double landscapeWidth, double landscapeHeight, AnalysisValue analysisValue, boolean topRightSquare) {
        this(null, x, y, origin, landscapeWidth, landscapeHeight, analysisValue, topRightSquare);
    }

    public GridSquare(Grid parent, RectangularCoordinate origin, double landscapeWidth, double landscapeHeight, AnalysisValue analysisValue, boolean topRightSquare) {
        this(parent, 0, 0, origin, landscapeWidth, landscapeHeight, analysisValue, topRightSquare);
    }

    public GridSquare(Grid parent, int xIndex, int yIndex, RectangularCoordinate origin, double landscapeWidth, double landscapeHeight, AnalysisValue analysisValue, boolean topRightSquare) {
        _xIndex = xIndex;
        _yIndex = yIndex;
        _parent = parent;
        _gridSquareLocation = new GridSquareLocation(parent, xIndex, yIndex);
        _origin = origin;
        _landscapeHeight = landscapeHeight;
        _landscapeWidth = landscapeWidth;
        _analysisValue = analysisValue;
        if (analysisValue != null) {
            _analysisValues.add(analysisValue);
        }
        _topRightSquare = topRightSquare;

        _bounds = new CartesianBounds( _origin.getDoubleX(),
                     _origin.getDoubleY(), _landscapeWidth, _landscapeHeight);


        if (parent != null) {
            initAgents(parent.getLandscape());
        }
    }

    /**
     * Adds the agents that are either in or intersecting this grid square...
     * @param landscape
     */
    private void initAgents(Landscape landscape) {

        _agentsInSquare = new ArrayList();
        if (landscape != null) {
            List agents = landscape.getSimulation().getLiveAgents(CabbageAgentFilter.INSTANCE);
            for (Iterator itr = agents.iterator(); itr.hasNext();) {
                CabbageAgent cabbage = (CabbageAgent)itr.next();
                if (containedInSquare(cabbage)) {
                    _agentsInSquare.add(cabbage);
                }
            }
        }
    }

    public List getAgentsInSquare() {
        return _agentsInSquare;
    }

    /**
     * Adds a border the width of the radius all around the square and then tests to see if the centre of the cabbage is
     * inside or on the border - this will get any what intersect it.
     * @param cabbage
     * @return
     */
    private boolean containedInSquare(CabbageAgent cabbage) {

        double radius = cabbage.getRadiusDouble();
        double x = _bounds.getDoubleX() - radius;
        double y = _bounds.getDoubleY() - radius;
        double h = _bounds.getDoubleHeight() + (radius * 2);
        double w = _bounds.getDoubleWidth() + (radius * 2);


        CartesianBounds searchBounds = new CartesianBounds(x, y, h, w);
        return searchBounds.containsPoint(cabbage.getLocation().getCoordinate());

    }

    public RectangularCoordinate getLandscapeOrigin() {
        return _origin;
    }

    public void setParent(Grid parent) {
        _parent = parent;
    }

    public GridSquareLocation getGridSquareLocation() {
        return _gridSquareLocation;
    }

    public Grid getParent() {
        return _parent;
    }

    public double getLandscapeHeight() {
        return _landscapeHeight;
    }

    public double getLandscapeWidth() {
        return _landscapeWidth;
    }

    public int getxIndex() {
        return _xIndex;
    }

    public int getyIndex() {
        return _yIndex;
    }

    public boolean hasChildGrid() {
        return _childGrid != null;
    }

    public Grid getChildGrid() {
        return _childGrid;
    }

    public void setChildGrid(Grid childGrid) {
        _childGrid = childGrid;
    }

    public Grid addChildGrid(Grid grid) {
        setChildGrid(grid);
        return grid;
    }

    public Grid addChildGrid(Landscape landscape, String name, int countX, int countY) {
        Grid g = new Grid(landscape, name, _origin, _landscapeWidth, _landscapeHeight, countX, countY, _gridSquareLocation);
        setChildGrid(g);
        return g;
    }

    public String toString() {
        return "origin: (" + _origin.getDoubleX() + ", " + _origin.getDoubleY() + "), size: (" + _landscapeWidth + ", " + _landscapeHeight + ")";
    }


    public AnalysisValue getAnalysisValue() {
        return _analysisValue;
    }

    /**
     * Returns wether a point is in this gridsquare but only includes bottom left edges,
     * unless the gridsquare is on the edge of the grid - that way you only have 1 square per point.
     * @param coord
     * @return
     */
    public boolean containsPoint(RectangularCoordinate coord) {
        double x= coord.getDoubleX();
        double y=  coord.getDoubleY();
        double x1 = _origin.getDoubleX();
        double y1 = _origin.getDoubleY();
        double width =  _landscapeWidth;
        double height= _landscapeHeight;

        boolean contained = false;
        if (_topRightSquare) {
            contained = ((x1 <= x) && (x < (x1 + width)) && (y1 <= y) && (y < (y1 + height)));
        } else {
            contained =((x1 <= x) && (x <= (x1 + width)) && (y1 <= y) && (y <= (y1 + height)));
        }
        return contained;

    }



    public List getAnalysisValues() {
        return _analysisValues;
    }

    public RectangularCoordinate getCoordRelativeToParent(RectangularCoordinate coord) {

        RectangularCoordinate parentOrign = getParent().getLandscapeOrigin();
        return new RectangularCoordinate(coord.getDoubleX() - parentOrign.getDoubleX(), coord.getDoubleY() - parentOrign.getDoubleY());
    }

    public List getAgents(IAgentFilter filter, Simulation simulation) {
        List agents = simulation.getLiveAgents(filter);
        List agentsInThisSq = new ArrayList();
        CartesianBounds b = getLandscapeBounds();

        if (_parent!= null) {
           CartesianBounds pb = _parent.getBounds();
           b = new CartesianBounds(pb.getDoubleX() + b.getDoubleX(), pb.getDoubleY() + b.getDoubleY(), b.getDoubleWidth(), b.getDoubleHeight());
        }

        for (Iterator iterator = agents.iterator(); iterator.hasNext();) {
            CabbageAgent cabbage = (CabbageAgent)iterator.next();
            if (b.isInside(cabbage.getLocation().getCoordinate())) {
                agentsInThisSq.add(cabbage);
            }
        }
        return agentsInThisSq;
    }

    /**
     * Lazy load because we rely on the parent being initialised and the parent relies on us!!
     * @return
     */
    public CartesianBounds getLandscapeBounds() {

        return _bounds;
    }

    private static final Logger log = Logger.getLogger(GridSquare.class);
    private RectangularCoordinate _origin;
    private double _landscapeHeight;
    private double _landscapeWidth;

    private int _xIndex;
    private int _yIndex;
    private Grid _parent;
    private GridSquareLocation _gridSquareLocation;
    private Grid _childGrid;
    private List _analysisValues = new ArrayList();
    private AnalysisValue _analysisValue;
    private List _agentsInSquare = new ArrayList();
    private CartesianBounds _bounds;
    private boolean _topRightSquare;
}
