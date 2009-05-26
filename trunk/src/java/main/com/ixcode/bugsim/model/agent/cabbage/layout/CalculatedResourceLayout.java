/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.cabbage.layout;

import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgentFilter;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.calculated.CalculatedResourceLayoutType;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.calculated.CalculatedResourceLayoutStrategy;
import com.ixcode.framework.datatype.analysis.AnalysisCategory;
import com.ixcode.framework.datatype.analysis.AnalysisValue;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.CoordinateDistributor;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquare;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquareMap;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CalculatedResourceLayout extends ResourceLayoutBase {

    public void initialise(StrategyDefinitionParameter strategyS, ParameterMap params, Map initialisationObjects) {
        super.initialise(strategyS, params, initialisationObjects);


        Simulation simulation = SimulationCategory.getSimulation(initialisationObjects);
        Landscape landscape = simulation.getLandscape();


        CalculatedResourceLayoutStrategy calculatedStrategy = new CalculatedResourceLayoutStrategy(strategyS,params, false);

        _radius = calculatedStrategy.getResourceRadius();
        _interEdgeSeparation  = calculatedStrategy.getInterEdgeSeparation();
        _layoutTypeCalculated =  calculatedStrategy.getCalculatedLayoutType();
        _patchBounds = calculatedStrategy.getLayoutBoundary().getBounds();

        _centreX = _patchBounds.getDoubleCentreX();
        _centreY = _patchBounds.getDoubleCentreY();



    }


    public void createCabbages(Simulation simulation) {


        _cabbages = createCabbagePatch(_layoutTypeCalculated, _radius, _interEdgeSeparation, _centreX, _centreY, simulation, CabbageParameters.ISOLATION_CATEGORY, GRID_NAME, 0, _patchBounds);


        _S = FORMAT2D.format(_interEdgeSeparation);
        _P = _layoutTypeCalculated.getName();
        _R = FORMAT2D.format(_radius);

        super.createCabbageInformationSurfaces(simulation.getLandscape());

    }

    public String getParameterSummary() {
        return super.getParameterSummary() + "S=" + _S + ", R=" + _R  + ", P=(edge-effect: " + _P + ")";
    }

    public String getResourceLayoutGridName() {
        return GRID_NAME;
    }

    private CabbageAgent[][] createCabbagePatch(CalculatedResourceLayoutType layoutTypeCalculated, double radius, double interEdgeSeperation, double centreX, double centreY, Simulation simulation, AnalysisCategory isolationCategory, String gridName, long nextId, CartesianBounds patchBounds) {
        Landscape landscape = simulation.getLandscape();
        final int count = 4; // not ready to make this a parameter yet as the Grid relies on being 4 x 4 @todo make grid more dynamic!!

        double interPointSeperation = interEdgeSeperation + (radius * 2);

        CoordinateDistributor d = new CoordinateDistributor();
        List coords = d.distributePointsBySeparation(count, count, interPointSeperation, true);
        if (log.isDebugEnabled()) {
            debugCoords(coords);
        }

        CartesianBounds innerBounds = d.calculateBoundsForSeparation(count, count, interPointSeperation, true);

        double gridWidth = innerBounds.getDoubleWidth();
        double gridHeight = innerBounds.getDoubleHeight();

        double originX = centreX - (gridWidth / 2);
        double originY = centreY - (gridHeight / 2);

        CabbageAgent[][] cabbages = new CabbageAgent[count][count];
        int iCoord = 0;
        for (int y = count - 1; y >= 0; y--) {
            for (int x = 0; x < count; ++x) {
                RectangularCoordinate coordinate = (RectangularCoordinate)coords.get(iCoord);

                cabbages[x][y] = createCabbage(nextId++, radius, coordinate.getDoubleX(), coordinate.getDoubleY(), originX, originY, simulation);
                iCoord++;
            }
        }

        createDynamicCabbagePatchGrid(gridName, landscape, isolationCategory, originX, originY, gridWidth, gridHeight, radius, interEdgeSeperation, layoutTypeCalculated);
        return cabbages;
    }

    private Grid createDynamicCabbagePatchGrid(String gridName, Landscape landscape, AnalysisCategory isolationCategory, double originX, double originY, double gridWidth, double gridHeight, double cabbageRadius, double interEdgeSpacing, CalculatedResourceLayoutType layoutTypeCalculated) {
        RectangularCoordinate logicalGridOrigin = new RectangularCoordinate(originX, originY);


        GridSquare[][] gridSquares = new GridSquare[3][3];

        AnalysisValue center = isolationCategory.getValue(CabbageParameters.ISOLATION_VALUE_CODE_CENTRE);
        AnalysisValue edge = isolationCategory.getValue(CabbageParameters.ISOLATION_VALUE_CODE_EDGE);
        AnalysisValue corner = isolationCategory.getValue(CabbageParameters.ISOLATION_VALUE_CODE_CORNER);

        double gss = (cabbageRadius * 2) + (interEdgeSpacing);

        gridSquares[0][0] = new GridSquare(0, 0, new RectangularCoordinate(0, gss * 3), gss, gss, corner, false);
        gridSquares[1][0] = new GridSquare(1, 0, new RectangularCoordinate(gss, gss * 3), gss * 2, gss, edge, false);
        gridSquares[2][0] = new GridSquare(2, 0, new RectangularCoordinate(gss * 3, gss * 3), gss, gss, corner, true);

        gridSquares[0][1] = new GridSquare(0, 1, new RectangularCoordinate(0, gss), gss, gss * 2, edge, false);
        gridSquares[1][1] = new GridSquare(1, 1, new RectangularCoordinate(gss, gss), gss * 2, gss * 2, center, false);
        gridSquares[2][1] = new GridSquare(2, 1, new RectangularCoordinate(gss * 3, gss), gss, gss * 2, edge, true);

        gridSquares[0][2] = new GridSquare(0, 2, new RectangularCoordinate(0, 0), gss, gss, corner, true);
        gridSquares[1][2] = new GridSquare(1, 2, new RectangularCoordinate(gss, 0), gss * 2, gss, edge, true);
        gridSquares[2][2] = new GridSquare(2, 2, new RectangularCoordinate(gss * 3, 0), gss, gss, corner, true);

        GridSquareMap gridSquareMap = new GridSquareMap(logicalGridOrigin, gridWidth, gridHeight, gridSquares);


        Grid grid = new Grid(gridName, gridSquareMap);
        grid.setNonUniformGrid(true);


        landscape.addGrid(grid);


        if (layoutTypeCalculated == CalculatedResourceLayoutType.CORNER_CENTRE) {
            // @todo make this a little beter!!
            removeEdgeCabbages(gridSquares, landscape);
        }


        return grid;
    }

    private void removeEdgeCabbages(GridSquare[][] gridSquares, Landscape landscape) {
        List agentsToRemove = new ArrayList();
        agentsToRemove.addAll(gridSquares[1][0].getAgents(CabbageAgentFilter.INSTANCE, landscape.getSimulation()));
        agentsToRemove.addAll(gridSquares[0][1].getAgents(CabbageAgentFilter.INSTANCE, landscape.getSimulation()));
        agentsToRemove.addAll(gridSquares[2][1].getAgents(CabbageAgentFilter.INSTANCE, landscape.getSimulation()));
        agentsToRemove.addAll(gridSquares[1][2].getAgents(CabbageAgentFilter.INSTANCE, landscape.getSimulation()));


        for (Iterator itr = agentsToRemove.iterator(); itr.hasNext();) {
            CabbageAgent cabbageAgent = (CabbageAgent)itr.next();

            landscape.getSimulation().removeLiveAgent(cabbageAgent);

        }
    }

    private CabbageAgent createCabbage(long id, double radius, double x, double y, double originX, double originY, Simulation simulation) {
        Location location = new Location(x + originX, y + originY);
        CabbageAgent agent = new CabbageAgent(id, location, radius);
        simulation.addAgent(agent);
        return agent;
    }

    private static void debugCoords(List coords) {
        int i = 0;
        for (Iterator itr = coords.iterator(); itr.hasNext();) {
            RectangularCoordinate coordinate = (RectangularCoordinate)itr.next();
            System.out.println("[" + i + "] - " + coordinate);
            i++;
        }

    }

    private static final String GRID_NAME = "Experimental Area";
    private static final DecimalFormat FORMAT2D = new DecimalFormat("0.00");
    private String _P;
    private String _R;
    private String _S;
    private CabbageAgent[][] _cabbages;
    private CartesianBounds _patchBounds;
    private double _radius;
    private double _centreX;
    private double _centreY;

    private double _interEdgeSeparation;
    private CalculatedResourceLayoutType _layoutTypeCalculated;


    private static final Logger log = Logger.getLogger(CalculatedResourceLayout.class);

}
