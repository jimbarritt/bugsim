/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.scale.Centimetres;
import com.ixcode.framework.math.scale.Distance;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.model.ModelBase;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridFactory;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSurface;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryBase;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.landscape.LandscapeCategory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class Landscape extends ModelBase implements PropertyChangeListener {
    public boolean hasGrid(String gridName) {
        return _gridMap.containsKey(gridName);
    }

    public boolean hasInformationSurface(String informationSurfaceName) {
        return _informationSurfacesMap.containsKey(informationSurfaceName);
    }

    public  void initialise(LandscapeCategory landscapeC) {


        BoundaryStrategyBase boundaryS = landscapeC.getExtent().getOuterBoundary();

        Map initObjects = StrategyDefinition.createInitialisationObjects(false);
        BoundaryBase boundary = boundaryS.createBoundary(initObjects);

        CartesianBounds b = boundary.getBounds();
        BoundaryShape shape = boundary.getShape();


        boolean isCircular = (shape == BoundaryShape.CIRCULAR);

        setLogicalBounds(b);
        setCircular(isCircular);

        CartesianBounds releaseb = landscapeC.getReleaseBoundary().getBounds();

        boolean isReleaseCircular = landscapeC.getReleaseBoundary().getBoundaryShape().isCircular();

        CartesianBounds zeroGridBounds = releaseb.calculateInnerBounds(landscapeC.getReleaseDistance());

        _releaseBoundaryGrid = GridFactory.createGrid(null, RELEASE_GRID, releaseb, 1, 1, isReleaseCircular);
        _zeroBoundaryGrid = GridFactory.createGrid(null, ZERO_BOUNDARY_GRID, zeroGridBounds, 1, 1, isReleaseCircular);


        addGrid(_releaseBoundaryGrid);
        addGrid(_zeroBoundaryGrid);


    }

    public static final String PROPERTY_EXTENT_X = "extentX";
        public static final String PROPERTY_EXTENT_Y = "extentY";
        public static final String PROPERTY_AGENTS = "agents";

    public CartesianBounds getLogicalBounds() {
        return _logicalBounds;
    }

    public void setLogicalBounds(CartesianBounds b) {
        _logicalBounds = b;
        ScaledDistance scale = getScale();
        Distance w = scale.convertLogicalToScaledDistance(b.getDoubleWidth());
        setScaledWidth(new ScaledDistance(w.getValue(), w.getUnits()));
        Distance h = scale.convertLogicalToScaledDistance(b.getDoubleHeight());
        setScaledHeight(new ScaledDistance(h.getValue(), h.getUnits()));

    }

    public Landscape(Simulation simulation) {
        setScale(new ScaledDistance(1, DistanceUnitRegistry.INSTANCE.resolveUnit(Centimetres.class.getName())));
        _simulation = simulation;
        
    }



    public Landscape(Simulation simulation, ScaledDistance width, ScaledDistance height) {
        setScale(new ScaledDistance(1, DistanceUnitRegistry.INSTANCE.resolveUnit(Centimetres.class.getName())));
        setScaledWidth(width);
        setScaledHeight(height);
        _simulation = simulation;
        _logicalBounds = new CartesianBounds(0, 0, getExtentX(), getExtentY());
    }
    public Landscape(ScaledDistance width, ScaledDistance height) {
        this(null, width,height);
    }
    public List getGrids() {
        return _grids;
    }

    public Grid getGrid(String name) {
        Grid foundGrid = null;
        for (Iterator itr = _grids.iterator(); itr.hasNext();) {
            Grid grid = (Grid)itr.next();
            if (grid.getName().equals(name)) {
                foundGrid = grid;
                break;
            }

        }
        return foundGrid;
    }

    private void removeAllGrids() {
        _gridMap = new HashMap();
        _grids = new ArrayList();
    }

    public void addAllGrids(List grids) {
        _grids.addAll(grids);
    }

    public Simulation getSimulation() {
        return _simulation;
    }

    public void addGrid(Grid grid) {
        _grids.add(grid);
        _gridMap.put(grid.getName(), grid);
    }


    public void addSignalSurface(ISignalSurface surface) {
        _informationSurfaces.add(surface);
        _informationSurfacesMap.put(surface.getName(), surface);
    }

    public ISignalSurface getInformationSurface(String name) {
        return (ISignalSurface)_informationSurfacesMap.get(name);
    }

    public int getExtentX() {
        return _extentX;
    }

    public void setExtentX(int extentX) {
        int oldValue = _extentX;
        _extentX = extentX;
        _logicalBounds = new CartesianBounds(0, 0, _extentX, _extentY);
        Distance d = _scale.convertLogicalToScaledDistance((double)extentX);
        _scaledWidth = new ScaledDistance(d.getValue(), d.getUnits());
        super.firePropertyChangeEvent(PROPERTY_EXTENT_X, new Integer(oldValue), new Integer(_extentX));
    }

    public int getExtentY() {
        return _extentY;
    }

    public void setExtentY(int extentY) {
        int oldValue = _extentY;
        _extentY = extentY;
        _logicalBounds = new CartesianBounds(0, 0, _extentX, _extentY);
        Distance d = _scale.convertLogicalToScaledDistance((double)extentY);
        _scaledHeight = new ScaledDistance(d.getValue(), d.getUnits());
        super.firePropertyChangeEvent(PROPERTY_EXTENT_X, new Integer(oldValue), new Integer(_extentY));
    }

    public List getAgents() {
        return Collections.unmodifiableList(_agents);
    }




    public void registerAgent(IPhysicalAgent agent) {
        _agents.add(agent);
        fireAgentsChangedEvent();
    }

    public void fireAgentsChangedEvent() {
        firePropertyChangeEvent(PROPERTY_AGENTS, _agents, null);        
    }


    public void unregisterAgent(IPhysicalAgent agent) {
        _agents.remove(agent);
//        agent.removePropertyChangeListener(this);
        firePropertyChangeEvent(PROPERTY_AGENTS, _agents, agent);

    }

    public void propertyChange(PropertyChangeEvent e) {
        if (e.getSource() instanceof IPhysicalAgent) {
//            propagatePropertyChangeEvent(e);
        }
    }

    public ScaledDistance getScale() {
        return _scale;
    }

    

    public void setScale(ScaledDistance scale) {
        _scale = scale;
    }

    public void setScaledWidth(ScaledDistance scaledWidth) {
        _scaledWidth = scaledWidth;
        double logicalWidth = _scale.convertScaledToLogicalDistance(new Distance(scaledWidth.getDistance(), _scaledWidth.getUnits()));
        setExtentX((int)Math.round(logicalWidth));
    }

    public void setScaledHeight(ScaledDistance scaledHeight) {
        _scaledHeight = scaledHeight;
        double logicalHeight = _scale.convertScaledToLogicalDistance(new Distance(scaledHeight.getDistance(), _scaledWidth.getUnits()));
        setExtentY((int)Math.round(logicalHeight));
    }


    public ScaledDistance getScaledWidth() {
        return _scaledWidth;
    }

    public ScaledDistance getScaledHeight() {
        return _scaledHeight;
    }


    public void setSimulation(Simulation simulation) {
        _simulation = simulation;
    }

    public void clean() {
        removeAllAgents();
        removeAllGrids();
        removeAllInformationSurfaces();
    }

    private void removeAllInformationSurfaces() {
        for (Iterator itr = _informationSurfaces.iterator(); itr.hasNext();) {
            ISignalSurface surface = (ISignalSurface)itr.next();
            surface.tidyUp();
        }
        _informationSurfaces= new ArrayList();
        _informationSurfacesMap = new HashMap();
    }

    private void removeAllAgents() {
        _agents = new ArrayList();
    }


    public boolean isCircular() {
        return isCircular;
    }

    public void setCircular(boolean circular) {
        isCircular = circular;
    }

    public Grid getReleaseBoundaryGrid() {
        return _releaseBoundaryGrid;
    }

    public Grid getZeroBoundaryGrid() {
        return _zeroBoundaryGrid;
    }

    public String toString() {
        return "logicalWidth: " + _extentX + ", logicalHeight: " + _extentY + ", scaledWidth: " + _scaledWidth + ", scaledHeight: " + _scaledHeight + ", scale: " + _scale;
    }
    private List _agents = new ArrayList();
    private int _extentX;
    private int _extentY;
    private ScaledDistance _scale;
    private Simulation _simulation;
    private ScaledDistance _scaledWidth;
    private ScaledDistance _scaledHeight;
    private List _grids = new ArrayList();
    private Map _gridMap = new HashMap();

    private Map _informationSurfacesMap = new HashMap();
    private List _informationSurfaces = new ArrayList();

    private CartesianBounds _logicalBounds;
    private boolean isCircular = false;
    public static final String ZERO_BOUNDARY_GRID = "Zero Boundary";
    private Grid _releaseBoundaryGrid;
    private Grid _zeroBoundaryGrid;
    public static final String RELEASE_GRID = "Release Boundary";
}
