/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.butterfly;

import com.ixcode.bugsim.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.agent.cabbage.CabbageAgentFilter;
import com.ixcode.bugsim.agent.cabbage.layout.PredefinedResourceLayout;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.foraging.EggLayingForagingStrategyDefinition;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.resource.IResourceAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquare;
import com.ixcode.framework.datatype.analysis.AnalysisValue;
import com.ixcode.framework.math.geometry.RectangularCoordinate;

import java.util.*;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class EggLayingForagingStrategy implements IForagingStrategy, IParameterisedStrategy {

    public void initialise(StrategyDefinitionParameter strategyS, ParameterMap params, Map initialisationObjects) {
        EggLayingForagingStrategyDefinition eggS = new EggLayingForagingStrategyDefinition(strategyS, params, false);
        _initialNumberOfEggs = eggS.getNumberOfEggs();
        _landOnCabbage = eggS.isLandOnResource();
        _layOnCurrentCabbage = eggS.isLayOnCurrentResource();
        _optimiseSearch = eggS.isOptimiseSearch();
    }

    /**
     * Do some foraging - dont lay eggs if we started within the radius of the cabbage. We can turn this on or off.
     * If you turn it on, you can also control the behaviour by changing the refactory period.
     * If you have a very short step length just set the refactory period to be more steps than it will take to leave the cabbage.
     *
     * @param next
     * @param simulation
     * @return
     */
    public Location forage(ButterflyAgent butterfly, Location next, Simulation simulation) {

        Grid searchGrid = simulation.getLandscape().getGrid(PredefinedResourceLayout.SEARCH_GRID_NAME);
        if (_optimiseSearch && searchGrid != null) {            
            _resources = getPotentialResources(searchGrid, butterfly.getLocation(), next);
        } else {
            _resources = simulation.getLiveAgents(CabbageAgentFilter.INSTANCE);
        }

        CabbageAgent lastResource = _layOnCurrentCabbage ? null : butterfly.getCurrentCabbage();
        butterfly.setCurrentCabbage(null);

        ResourceIntersection closestResourceIx = findClosestIntersectedResource(butterfly.getLocation(), next, _resources, lastResource);

        Location landingSite = next;
        if (closestResourceIx != null) {
            IResourceAgent closestResource = closestResourceIx.getResource();
            butterfly.layEgg(closestResource, simulation);
            if (_landOnCabbage){
                landingSite = closestResource.getLocation();
            }
        }
        return landingSite;

    }

    private void testCornerCentre(Simulation simulation, CabbageAgent cabbage, ButterflyAgent butterflyAgent, Location next, List intersections) {
        Grid grid = simulation.getLandscape().getGrid("Experimental Area");
        List analysisValues = getAnalysisValues(grid, cabbage);

        AnalysisValue v = (AnalysisValue)analysisValues.get(0);

        if (v.getDescription().equals("Centre")) {
            if (log.isInfoEnabled()) {
                log.info("Hit A Centre Cabbage!!! : Location: " + butterflyAgent.getLocation() + ", Azimuth: " + butterflyAgent.getLocation().getCoordinate().calculateAzimuthTo(next.getCoordinate()) + " : " + next);
                CabbageAgent cabbage2 = getClosestCabbage(intersections, butterflyAgent.getLocation());
                for (Iterator itr = intersections.iterator(); itr.hasNext();) {
                    CabbageAgent cabbageAgent = (CabbageAgent)itr.next();
                    if (log.isInfoEnabled()) {
                        log.info("Cababge: " + cabbageAgent.getLocation());
                    }
                }

            }
        }
    }

    private List getAnalysisValues(Grid grid, CabbageAgent cabbage) {
        List gridSqaures = grid.getContainingGridSquares(cabbage.getLocation().getCoordinate());
        List analysisValues;
        if (gridSqaures.size() > 0) {
            GridSquare gridSquare = (GridSquare)gridSqaures.get(0); // We happend to know theres only 1 in this case = @todo make this work for multiple ones.
            analysisValues = gridSquare.getAnalysisValues();
        } else {
            analysisValues = new ArrayList();
        }
        return analysisValues;
    }

    private Collection getPotentialResources(Grid searchGrid, Location location, Location next) {
        Set resources = new HashSet();
        List gridSquares = searchGrid.getContainingGridSquares(location.getCoordinate(), next.getCoordinate());
        for (Iterator itr = gridSquares.iterator(); itr.hasNext();) {
            GridSquare gridSquare = (GridSquare)itr.next();
            resources.addAll(gridSquare.getAgentsInSquare());
        }

        return resources;
    }

    /**
     * First check wether we are actually inside the cabbage now. This saves us calculating the intersection.
     * If not, calculate the intersection.
     * We asusme that if we were in a cabbage last time we laid an egg on it.
     * <p/>
     * Under certain circumstances we dont want to lay an egg on a plant we are already within....
     *
     * @param butterfly
     * @param previous
     * @param resources
     * @return
     */
    public static ResourceIntersection  findClosestIntersectedResource(Location previous, Location next, Collection resources, IResourceAgent currentResource) {

        List intersections = new ArrayList();
        for (Iterator itr = resources.iterator(); itr.hasNext();) {
            IResourceAgent resource = (IResourceAgent)itr.next();

            boolean includeResource = (currentResource==null) || resource.getResourceId() != currentResource.getResourceId();

            if (includeResource) {
                List intersectionPoints = resource.lineIntersections(previous.getCoordinate(), next.getCoordinate());
                if (intersectionPoints.size() > 0) {
                    ResourceIntersection ix = new ResourceIntersection(intersectionPoints, resource);
                    intersections.add(ix);
                }
            }
        }

        ResourceIntersection ix = null;
        if (intersections.size() >0) {
            ix = findClosestIntersection(previous.getCoordinate(), intersections);
        }
        return ix;
    }

//            if (cabbage.containsPoint(next.getCoordinate())) {
//                intersections.add(cabbage);
//            } else if (cabbage.containsPoint(previous.getCoordinate())) {
//                if (includeCurrentCabbage){
//                    intersections.add(cabbage);
//                }
//            } else if (cabbage.intersectsLine(previous.getCoordinate(), next.getCoordinate())) {
//                intersections.add(cabbage);
//
//            }

    public static ResourceIntersection findClosestIntersection(RectangularCoordinate point, List intersections) {
        double shortestDistance = Double.MAX_VALUE;
        ResourceIntersection closestIx = null;
        if (intersections.size() == 1) {
            closestIx = (ResourceIntersection)intersections.get(0);
        } else {
            for (Iterator itr = intersections.iterator(); itr.hasNext();) {
                ResourceIntersection resourceIx = (ResourceIntersection)itr.next();

                double d = resourceIx.shortestDistanceTo(point);
                if (d < shortestDistance) {
                    closestIx = resourceIx;
                    shortestDistance = d;
                }
            }
        }
        return closestIx;
    }


    public static CabbageAgent getClosestCabbage(List cabbageIntersections, Location previous) {
        double closestDistance = Double.MAX_VALUE;
        CabbageAgent closestCabbage = null;
        if (cabbageIntersections.size() == 1) {
            closestCabbage = (CabbageAgent)cabbageIntersections.get(0);
        } else {
            for (Iterator itr = cabbageIntersections.iterator(); itr.hasNext();) {
                CabbageAgent cabbage = (CabbageAgent)itr.next();
                double d = previous.getCoordinate().calculateDistanceTo(cabbage.getLocation().getCoordinate().getDoubleX(), cabbage.getLocation().getDoubleY());
                if (d < closestDistance) {
                    closestCabbage = cabbage;
                    closestDistance = d;
                }
            }
        }
        return closestCabbage;
    }


    public EggLayingForagingStrategy() {
    }

    public long getInitialNumberOfEggs() {
        return _initialNumberOfEggs;
    }

    public void setInitialNumberOfEggs(long initialNumberOfEggs) {
        _initialNumberOfEggs = initialNumberOfEggs;
    }

    public Collection getPotentialResources() {
        return _resources;
    }

    public String getParameterSummary() {
        return "NUM_EGGS=" + _initialNumberOfEggs + ", LAND=" + _landOnCabbage;
    }

    public String toString() {
        return "EggLayingStrategy: initialNumberOfEggs=" + _initialNumberOfEggs + ", landOnCabbage=" + _landOnCabbage;
    }

    private static final Logger log = Logger.getLogger(EggLayingForagingStrategy.class);
    private long _initialNumberOfEggs;
    private boolean _landOnCabbage;
    private boolean _layOnCurrentCabbage;
    private Collection _resources = new ArrayList();
    private boolean _optimiseSearch;
}
