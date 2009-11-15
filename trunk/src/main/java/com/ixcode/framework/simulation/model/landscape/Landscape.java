/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape;

import com.ixcode.bugsim.model.experiment.parameter.landscape.*;
import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.math.scale.*;
import com.ixcode.framework.model.*;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.*;
import com.ixcode.framework.simulation.model.*;
import com.ixcode.framework.simulation.model.agent.*;
import com.ixcode.framework.simulation.model.agent.physical.*;
import com.ixcode.framework.simulation.model.landscape.boundary.*;
import com.ixcode.framework.simulation.model.landscape.grid.*;
import com.ixcode.framework.simulation.model.landscape.information.*;

import java.beans.*;
import java.util.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class Landscape extends ModelBase implements PropertyChangeListener {

    private List<IAgent> agents = new ArrayList<IAgent>();
    private int extentX;
    private int extentY;
    private ScaledDistance scale;
    private Simulation simulation;
    private ScaledDistance scaledWidth;
    private ScaledDistance scaledHeight;
    private List grids = new ArrayList();
    private Map gridMap = new HashMap();

    private Map informationSurfacesMap = new HashMap();
    private List informationSurfaces = new ArrayList();

    private CartesianBounds logicalBounds;
    private boolean isCircular = false;
    public static final String ZERO_BOUNDARY_GRID = "Zero Boundary";
    private Grid releaseBoundaryGrid;
    private Grid zeroBoundaryGrid;
    public static final String RELEASE_GRID = "Release Boundary";


    public Landscape(Simulation simulation, ScaledDistance extent) {
        this(simulation, extent, extent);
    }

    public Landscape(Simulation simulation) {
        setScale(new ScaledDistance(1, DistanceUnitRegistry.centimetres()));
        this.simulation = simulation;
    }


    public Landscape(Simulation simulation, ScaledDistance width, ScaledDistance height) {
        setScale(new ScaledDistance(1, DistanceUnitRegistry.centimetres()));
        setScaledWidth(width);
        setScaledHeight(height);
        this.simulation = simulation;
        logicalBounds = new CartesianBounds(0, 0, getExtentX(), getExtentY());
    }

    public Landscape(ScaledDistance width, ScaledDistance height) {
        this(null, width, height);
    }


    public boolean hasGrid(String gridName) {
        return gridMap.containsKey(gridName);
    }

    public boolean hasInformationSurface(String informationSurfaceName) {
        return informationSurfacesMap.containsKey(informationSurfaceName);
    }

    public void initialise(LandscapeCategory landscapeC) {


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

        releaseBoundaryGrid = GridFactory.createGrid(null, RELEASE_GRID, releaseb, 1, 1, isReleaseCircular);
        zeroBoundaryGrid = GridFactory.createGrid(null, ZERO_BOUNDARY_GRID, zeroGridBounds, 1, 1, isReleaseCircular);


        addGrid(releaseBoundaryGrid);
        addGrid(zeroBoundaryGrid);


    }

    public static final String PROPERTY_EXTENT_X = "extentX";
    public static final String PROPERTY_EXTENT_Y = "extentY";
    public static final String PROPERTY_AGENTS = "agents";

    public CartesianBounds getLogicalBounds() {
        return logicalBounds;
    }

    public void setLogicalBounds(CartesianBounds b) {
        logicalBounds = b;
        ScaledDistance scale = getScale();
        Distance w = scale.convertLogicalToScaledDistance(b.getDoubleWidth());
        setScaledWidth(new ScaledDistance(w.getValue(), w.getUnits()));
        Distance h = scale.convertLogicalToScaledDistance(b.getDoubleHeight());
        setScaledHeight(new ScaledDistance(h.getValue(), h.getUnits()));

    }

    public List getGrids() {
        return grids;
    }

    public Grid getGrid(String name) {
        Grid foundGrid = null;
        for (Iterator itr = grids.iterator(); itr.hasNext();) {
            Grid grid = (Grid) itr.next();
            if (grid.getName().equals(name)) {
                foundGrid = grid;
                break;
            }

        }
        return foundGrid;
    }

    private void removeAllGrids() {
        gridMap = new HashMap();
        grids = new ArrayList();
    }

    public void addAllGrids(List grids) {
        this.grids.addAll(grids);
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void addGrid(Grid grid) {
        grids.add(grid);
        gridMap.put(grid.getName(), grid);
    }


    public void addSignalSurface(ISignalSurface surface) {
        informationSurfaces.add(surface);
        informationSurfacesMap.put(surface.getName(), surface);
    }

    public ISignalSurface getInformationSurface(String name) {
        return (ISignalSurface) informationSurfacesMap.get(name);
    }

    public int getExtentX() {
        return extentX;
    }

    public void setExtentX(int extentX) {
        int oldValue = this.extentX;
        this.extentX = extentX;
        logicalBounds = new CartesianBounds(0, 0, this.extentX, extentY);
        Distance d = scale.convertLogicalToScaledDistance((double) extentX);
        scaledWidth = new ScaledDistance(d.getValue(), d.getUnits());
        super.firePropertyChangeEvent(PROPERTY_EXTENT_X, new Integer(oldValue), new Integer(this.extentX));
    }

    public int getExtentY() {
        return extentY;
    }

    public void setExtentY(int extentY) {
        int oldValue = this.extentY;
        this.extentY = extentY;
        logicalBounds = new CartesianBounds(0, 0, extentX, this.extentY);
        Distance d = scale.convertLogicalToScaledDistance((double) extentY);
        scaledHeight = new ScaledDistance(d.getValue(), d.getUnits());
        super.firePropertyChangeEvent(PROPERTY_EXTENT_X, new Integer(oldValue), new Integer(this.extentY));
    }

    public List getAgents() {
        return Collections.unmodifiableList(agents);
    }


    public void registerAgent(IPhysicalAgent agent) {
        agents.add(agent);
        fireAgentsChangedEvent();
    }

    public void fireAgentsChangedEvent() {
        firePropertyChangeEvent(PROPERTY_AGENTS, agents, null);
    }


    public void unregisterAgent(IPhysicalAgent agent) {
        agents.remove(agent);
//        agent.removePropertyChangeListener(this);
        firePropertyChangeEvent(PROPERTY_AGENTS, agents, agent);

    }

    public void propertyChange(PropertyChangeEvent e) {
        if (e.getSource() instanceof IPhysicalAgent) {
//            propagatePropertyChangeEvent(e);
        }
    }

    public ScaledDistance getScale() {
        return scale;
    }


    public void setScale(ScaledDistance scale) {
        this.scale = scale;
    }

    public void setScaledWidth(ScaledDistance scaledWidth) {
        this.scaledWidth = scaledWidth;
        double logicalWidth = scale.convertScaledToLogicalDistance(new Distance(scaledWidth.getDistance(), scaledWidth.getUnits()));
        setExtentX((int) Math.round(logicalWidth));
    }

    public void setScaledHeight(ScaledDistance scaledHeight) {
        this.scaledHeight = scaledHeight;
        double logicalHeight = scale.convertScaledToLogicalDistance(new Distance(scaledHeight.getDistance(), scaledHeight.getUnits()));
        setExtentY((int) Math.round(logicalHeight));
    }

    public void setScaledSize(ScaledDistance extent) {
        setScaledHeight(extent);
        setScaledWidth(extent);

    }


    public ScaledDistance getScaledWidth() {
        return scaledWidth;
    }

    public ScaledDistance getScaledHeight() {
        return scaledHeight;
    }


    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void clean() {
        removeAllAgents();
        removeAllGrids();
        removeAllInformationSurfaces();
    }

    private void removeAllInformationSurfaces() {
        for (Iterator itr = informationSurfaces.iterator(); itr.hasNext();) {
            ISignalSurface surface = (ISignalSurface) itr.next();
            surface.tidyUp();
        }
        informationSurfaces = new ArrayList();
        informationSurfacesMap = new HashMap();
    }

    private void removeAllAgents() {
        agents = new ArrayList();
    }


    public boolean isCircular() {
        return isCircular;
    }

    public void setCircular(boolean circular) {
        isCircular = circular;
    }

    public Grid getReleaseBoundaryGrid() {
        return releaseBoundaryGrid;
    }

    public Grid getZeroBoundaryGrid() {
        return zeroBoundaryGrid;
    }

    public String toString() {
        return "logicalWidth: " + extentX + ", logicalHeight: " + extentY + ", scaledWidth: " + scaledWidth + ", scaledHeight: " + scaledHeight + ", scale: " + scale;
    }


}
