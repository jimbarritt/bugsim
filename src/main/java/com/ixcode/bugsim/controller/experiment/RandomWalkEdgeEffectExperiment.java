/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import com.ixcode.bugsim.controller.experiment.properties.ExperimentProperties;
import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgent;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentFilter;
import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedEggsAdultMortalityStrategy;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.view.landscape.LandscapeFrame;
import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.bugsim.view.landscape.OpenSimulationAction;
import com.ixcode.bugsim.view.landscape.SaveCabbageExperimentResultAction;
import com.ixcode.framework.datatype.analysis.AnalysisCategory;
import com.ixcode.framework.datatype.analysis.AnalysisValue;
import com.ixcode.framework.simulation.model.agent.motile.movement.GaussianAzimuthGenerator;
import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.ScaledCartesianCoordinate;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.simulation.model.EscapingAgentCatcher;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.motile.movement.RandomWalkStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.IAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.physical.IAdultMortalityStrategy;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridFactory;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquare;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquareMap;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *  Description : ${CLASS_DESCRIPTION}
 * @deprecated 
 */
public class RandomWalkEdgeEffectExperiment extends ExperimentBase implements IExperiment {

    public RandomWalkEdgeEffectExperiment(Simulation simulation, OpenSimulationAction openSimulationAction, long numberOfButterflies) {
        super("Random Walk Experiment", simulation, openSimulationAction, Long.MAX_VALUE, 500, new RandomWalkEdgeEffectProperties(), new EdgeEffectReporter("edge-effect-experiment", GRID_NAME));
        RandomWalkEdgeEffectProperties parameters = (RandomWalkEdgeEffectProperties)super.getProperties();
        parameters.setPopulationSize(50);
        parameters.setNumberOfButterflies(1000);
        parameters.setButterflyMoveLength(10);
        parameters.setButterflyAngleOfTurnSD(20);

        parameters.setButterflyNumberOfEggs(1);
        parameters.addPropertyChangeListener(this);

    }

    public void iterationCompleted(Simulation simulation) {

    }

//    public void propertyChange(PropertyChangeEvent evt) {
//        super.propertyChange(evt);
//        if (evt.getPropertyName().equals(RandomWalkEdgeEffectProperties.PARAM_POPULATION_SIZE)) {
//            RandomWalkEdgeEffectProperties customParams = (RandomWalkEdgeEffectProperties)getProperties();
//            if (_numberOfButterfliesAlive < customParams.getPopulationSize()) {
//                long numToCreate = customParams.getPopulationSize()-_numberOfButterfliesAlive;
//                _movementStrategy = new RandomWalkStrategy(RANDOM, customParams.getButterflyAngleOfTurnSD(), customParams.getButterflyMoveLength());
//                createButterflyPopulation(getSimulation(),_releaseBoundaryGrid, _headingGenerator, _movementStrategy, _catcher, customParams.getPopulationSize(), customParams);
//            }
//        } else if (evt.getPropertyName().equals(RandomWalkEdgeEffectProperties.PARAM_NUMBER_OF_BUTTERFLIES)) {
//            RandomWalkEdgeEffectProperties customParams = (RandomWalkEdgeEffectProperties)getProperties();
//            long delta = ((Long)evt.getNewValue()).longValue() - ((Long)evt.getOldValue()).longValue();
//            _numberOfButterfliesRemaining += delta;
//            if (_numberOfButterfliesRemaining < _numberOfButterfliesAlive) {
//                killButterflies(_numberOfButterfliesAlive - _numberOfButterfliesRemaining);
//            }
//        }
//    }
//
//    /**
//     * Just going to kill off a load  sequentially in the list - could actually pick the indexes at random!?!
//     * @param bodyCount
//     */
//    private void killButterflies(long bodyCount) {
//        List butterflies = new ArrayList(getSimulation().getLiveAgents(ButterflyAgentFilter.INSTANCE));
//        Iterator itr = butterflies.iterator();
//        for (long i=0;i<bodyCount;++i) {
//             getSimulation().registerAgentDeath((IPhysicalAgent)itr.next());
//        }
//    }

    protected void customiseView(LandscapeFrame frame) {
        LandscapeView view = frame.getLandscapeView();
        view.setGridResolution(new ScaledDistance(80, DistanceUnitRegistry.centimetres()));
        view.setGridThickness(new ScaledDistance(1, DistanceUnitRegistry.centimetres()));

    }

    public void agentDeath(Simulation simulation, IAgent agent) {
        RandomWalkEdgeEffectProperties customParams = (RandomWalkEdgeEffectProperties)getProperties();
        if (_numberOfButterfliesRemaining >0 && _numberOfButterfliesAlive <= customParams.getPopulationSize()) {
            _movementStrategy = new RandomWalkStrategy(RANDOM, customParams.getButterflyAngleOfTurnSD(), customParams.getButterflyMoveLength());
            ButterflyAgent butterfly = createButterfly(_headingGenerator, _releaseBoundaryGrid, _movementStrategy,_adultMortalityStrategy,  super.getSimulation(), _catcher, customParams);
            simulation.addAgentNextTimestep(butterfly);

        }

        _numberOfButterfliesAlive--;
//        System.out.println("AgentDEad : " + _numberOfButterfliesAlive);
    }


    protected boolean isExperimentComplete() {
        return (_numberOfButterfliesAlive <=0);
    }


    public  LandscapeFrame createSpaceAgentExperiment(Simulation simulation, OpenSimulationAction openSim) {

        LandscapeFrame frame = createLandscapeFrame(simulation, openSim);

        CartesianBounds bounds = new CartesianBounds(160, 160, 80, 80);

        return frame;
    }

    protected LandscapeFrame createLandscapeFrame(Simulation simulation, OpenSimulationAction openSim) {
        simulation.setTitle(getName());
        ScaledDistance width = new ScaledDistance(400, DistanceUnitRegistry.centimetres());
        ScaledDistance height  = new ScaledDistance(400, DistanceUnitRegistry.centimetres());

        Landscape landscape = new Landscape(simulation, width, height);
        simulation.setLandscape(landscape);

        LandscapeFrame frame = new LandscapeFrame(landscape, openSim);

        if (log.isInfoEnabled()) {
            log.info("Landscape logical size = (" + landscape.getExtentX() + ", " + landscape.getExtentY() + ")");
        }

//        String gridName = initialiseExperiment( simulation);
        // @todo tidy up this grid name business!
        frame.addActionToToolbar(new SaveCabbageExperimentResultAction(frame.getLandscapeView(),"Experimental Area" ));
        return frame;
    }

    protected  String initialiseExperiment(Simulation simulation, ExperimentProperties parameters) {
        super.setExperimentProperties(parameters);
        simulation.clean();
     
        RandomWalkEdgeEffectProperties customParams = (RandomWalkEdgeEffectProperties)parameters;

        NEXT_ID = 1;
        _numberOfButterfliesRemaining = customParams.getNumberOfButterflies();
        _numberOfButterfliesAlive = 0;

        Landscape landscape = simulation.getLandscape();
        AnalysisCategory isolationCategory = new AnalysisCategory("Isolation Level", "ISOLEVEL");
        isolationCategory.addValue("0", "Centre");
        isolationCategory.addValue("1", "Edge");
        isolationCategory.addValue("2", "Corner");






        _cabbages = createCabbages(simulation, landscape, isolationCategory);


        _headingGenerator = new GaussianAzimuthGenerator(RANDOM, 20);
        _releaseBoundaryGrid = GridFactory.createGrid(landscape, "ReleaseInitialisationParameters Boundary", 80, 80, 240, 240,1, 1);
//        GravitationalCalculator gravityMachine = new GravitationalCalculator(new ExponentialDecaySignalFunction(2, 1, .004), 0.000001);
        //        IMovementStrategy movementStrategy = new GravitationalMovementStrategy(gravityMachine, 10, RANDOM);
        _catcher = new EscapingAgentCatcher(simulation, landscape.getLogicalBounds(), landscape.isCircular(), ForagingAgentFilter.INSTANCE);
        _movementStrategy = new RandomWalkStrategy(RANDOM, customParams.getButterflyAngleOfTurnSD(), customParams.getButterflyMoveLength());
        _adultMortalityStrategy = new LimitedEggsAdultMortalityStrategy();
        landscape.addGrid(_releaseBoundaryGrid);

        createButterflies(simulation, _releaseBoundaryGrid,  _headingGenerator, _movementStrategy, _adultMortalityStrategy, _catcher, customParams.getPopulationSize(), customParams);
        return GRID_NAME;
    }



    private void createButterflies(Simulation simulation, Grid releaseBoundaryGrid, GaussianAzimuthGenerator headingGenerator,
                                   IMovementStrategy movementStrategy, IAdultMortalityStrategy adultMortalityStrategy, EscapingAgentCatcher catcher, long populationSize,
                                   RandomWalkEdgeEffectProperties customParams) {

        for (int i=0;i<populationSize;++i) {
            if (_numberOfButterfliesRemaining >0) {
                ButterflyAgent butterfly = createButterfly(headingGenerator, releaseBoundaryGrid, movementStrategy, adultMortalityStrategy, simulation, catcher, customParams);
                simulation.addAgent((IPhysicalAgent)butterfly);

            }
        }
    }

    private ButterflyAgent createButterfly(IAzimuthGenerator azimuthGenerator, Grid releaseBoundaryGrid, IMovementStrategy movementStrategy,
                                           IAdultMortalityStrategy adultMortalityStrategy , Simulation simulation, EscapingAgentCatcher catcher, RandomWalkEdgeEffectProperties customParams) {
        double initialHeading = Geometry.generateUniformRandomAzimuthChange(simulation.getRandom());
        RectangularCoordinate coord = Geometry.generateRandomCoordOnPerimeter(RANDOM, releaseBoundaryGrid.getBounds(), releaseBoundaryGrid.isCircular());
//        ButterflyAgent butterfly = new ButterflyAgent(new Location(coord),  initialHeading,2, customParams.getButterflyNumberOfEggs(),
//                movementStrategy, mortalityStrategy,  false, ButterflyAgentBehaviour.RELEASE);
//
//        butterfly.addPropertyChangeListener(catcher);
        _numberOfButterfliesRemaining-- ;
        _numberOfButterfliesAlive++;
//        System.out.println("AgentCreated : " + _numberOfButterfliesAlive);

        return null;
    }

    private static CabbageAgent[][] createCabbages(Simulation simulation, Landscape landscape, AnalysisCategory isolationCategory) {

        double radius = 5;
        double centreX = 200;
        double centreY = 200;


        CoordinateDistributor d = new CoordinateDistributor();
        double interPointSeperation = d.calculateSeparationForBounds(4, 80, true);
        double interEdgeSeparation = interPointSeperation - (radius*2);


        List coords = d.distributePointsBySeparation(4, 4, interPointSeperation, true);
//        debugCoords(coords);
        CartesianBounds cabbageBounds = d.calculateBoundsForSeparation(4, 4, interPointSeperation, true);
        double gridWidth = cabbageBounds.getDoubleWidth();
        double gridHeight = cabbageBounds.getDoubleHeight();

        double originX = centreX - (gridWidth / 2);
        double originY = centreY - (gridHeight / 2);

//        double originX = 160;
//        double originY = 160;

        CabbageAgent[][] cabbages = new CabbageAgent[4][4];
        int iCoord = 0;
        for (int y=3;y>=0;y--) {
            for (int x=0;x<4;++x)   {
                RectangularCoordinate coordinate = (RectangularCoordinate)coords.get(iCoord);


                cabbages[x][y] = createCabbage(nextId(), radius, coordinate.getDoubleX(), coordinate.getDoubleY(), originX, originY,simulation);
                iCoord++;
            }
        }

//         createCabbagePatchGrid(GRID_NAME, landscape, isolationCategory);
           createDynamicCabbagePatchGrid(GRID_NAME, landscape, isolationCategory, originX, originY, gridWidth, gridHeight, radius, interEdgeSeparation);

//        cabbages[0][0] = createCabbage(nextId(), 5, 10, 10, grid, simulation);
//        cabbages[1][0] = createCabbage(nextId(), 5, 30, 10, grid, simulation);
//        cabbages[2][0] = createCabbage(nextId(), 5, 50, 10, grid, simulation);
//        cabbages[3][0] = createCabbage(nextId(), 5, 70, 10, grid, simulation);
//
//        cabbages[0][1] = createCabbage(nextId(), 5, 10, 30, grid, simulation);
//        cabbages[1][1] = createCabbage(nextId(), 5, 30, 30, grid, simulation);
//        cabbages[2][1] = createCabbage(nextId(), 5, 50, 30, grid, simulation);
//        cabbages[3][1] = createCabbage(nextId(), 5, 70, 30, grid, simulation);
//
//        cabbages[0][2] = createCabbage(nextId(), 5, 10, 50, grid, simulation);
//        cabbages[1][2] = createCabbage(nextId(), 5, 30, 50, grid, simulation);
//        cabbages[2][2] = createCabbage(nextId(), 5, 50, 50, grid, simulation);
//        cabbages[3][2] = createCabbage(nextId(), 5, 70, 50, grid, simulation);
//
//        cabbages[0][3] = createCabbage(nextId(), 5, 10, 70, grid, simulation);
//        cabbages[1][3] = createCabbage(nextId(), 5, 30, 70, grid, simulation);
//        cabbages[2][3] = createCabbage(nextId(), 5, 50, 70, grid, simulation);
//        cabbages[3][3] = createCabbage(nextId(), 5, 70, 70, grid, simulation);
        return cabbages;
    }

    private static Grid createDynamicCabbagePatchGrid(String gridName, Landscape landscape, AnalysisCategory isolationCategory, double originX, double originY, double gridWidth, double gridHeight, double cabbageRadius, double interEdgeSpacing) {
        RectangularCoordinate logicalGridOrigin = new RectangularCoordinate(originX, originY);


        GridSquare[][] gridSquares = new GridSquare[3][3];

        AnalysisValue center = isolationCategory.getValue("0");
        AnalysisValue edge = isolationCategory.getValue("1");
        AnalysisValue corner = isolationCategory.getValue("2");

        double gss = (cabbageRadius * 2) + (interEdgeSpacing);

        gridSquares[0][0] = new GridSquare(0, 0,new RectangularCoordinate(0, gss * 3), gss, gss, corner, false);
        gridSquares[1][0] = new GridSquare(1, 0, new RectangularCoordinate(gss, gss * 3), gss*2, gss, edge, false);
        gridSquares[2][0] = new GridSquare(2, 0,new RectangularCoordinate(gss*3, gss *3), gss, gss, corner, true);

        gridSquares[0][1] = new GridSquare(0, 1,new RectangularCoordinate(0, gss), gss, gss*2, edge, false);
        gridSquares[1][1] = new GridSquare(1, 1,new RectangularCoordinate(gss, gss), gss*2, gss*2, center, false);
        gridSquares[2][1] = new GridSquare(2, 1,new RectangularCoordinate(gss*3, gss), gss, gss*2, edge,true);

        gridSquares[0][2] = new GridSquare(0, 2,new RectangularCoordinate(0, 0), gss, gss, corner, true);
        gridSquares[1][2] = new GridSquare(1, 2, new RectangularCoordinate(gss, 0), gss*2, gss, edge, true);
        gridSquares[2][2] = new GridSquare(2,2, new RectangularCoordinate(gss*3, 0), gss, gss, corner, true);

        GridSquareMap gridSquareMap = new GridSquareMap(logicalGridOrigin, gridWidth, gridHeight, gridSquares);


        Grid grid = new Grid(gridName, gridSquareMap);
        grid.setNonUniformGrid(true);


        landscape.addGrid(grid);

        return grid;
    }

    private static void debugCoords(List coords) {
        int i=0;
                for (Iterator itr = coords.iterator(); itr.hasNext();) {
                    RectangularCoordinate coordinate = (RectangularCoordinate)itr.next();
                    System.out.println("[" + i + "] - " + coordinate);
                    i++;
                }

    }

    private static int nextId() {
        return NEXT_ID++;
    }

    private static CabbageAgent createCabbage(int id, double radius, double x, double y, double originX, double originY , Simulation simulation) {
        Location location = new Location(x + originX, y + originY);
        CabbageAgent agent = new CabbageAgent(id, location, radius);
        simulation.addAgent(agent);
        return agent;
    }

    private static Grid createCabbagePatchGrid(String gridName, Landscape landscape, AnalysisCategory isolationCategory) {
        ScaledCartesianCoordinate scaledGridLocation = new ScaledCartesianCoordinate(160, 160, DistanceUnitRegistry.centimetres());
        RectangularCoordinate logicalGridLocation = landscape.getScale().convertScaledToLogicalCoordinate(scaledGridLocation);

        GridSquare[][] gridSquares = new GridSquare[3][3];

        AnalysisValue center = isolationCategory.getValue("0");
        AnalysisValue edge = isolationCategory.getValue("1");
        AnalysisValue corner = isolationCategory.getValue("2");


        gridSquares[0][0] = new GridSquare(0, 0,new RectangularCoordinate(0, 60), 20, 20, corner, false);
        gridSquares[1][0] = new GridSquare(1, 0, new RectangularCoordinate(20, 60), 40, 20, edge, false);
        gridSquares[2][0] = new GridSquare(2, 0,new RectangularCoordinate(60, 60), 20, 20, corner, true);

        gridSquares[0][1] = new GridSquare(0, 1,new RectangularCoordinate(0, 20), 20, 40, edge, false);
        gridSquares[1][1] = new GridSquare(1, 1,new RectangularCoordinate(20, 20), 40, 40, center, false);
        gridSquares[2][1] = new GridSquare(2, 1,new RectangularCoordinate(60, 20), 20, 40, edge, true);

        gridSquares[0][2] = new GridSquare(0, 2,new RectangularCoordinate(0, 0), 20, 20, corner, true);
        gridSquares[1][2] = new GridSquare(1, 2, new RectangularCoordinate(20, 0), 40, 20, edge, true);
        gridSquares[2][2] = new GridSquare(2,2, new RectangularCoordinate(60, 0), 20, 20, corner, true);

        GridSquareMap gridSquareMap = new GridSquareMap(logicalGridLocation, 80, 80, gridSquares);


        Grid grid = new Grid(gridName, gridSquareMap);
        grid.setNonUniformGrid(true);


        landscape.addGrid(grid);

        return grid;
    }

    public CabbageAgent[][] getCabbages() {
        return _cabbages;
    }

    public long getButterfliesRemaining() {
        return _numberOfButterfliesRemaining + _numberOfButterfliesAlive;
    }

    public long getButterfliesAlive() {
        return _numberOfButterfliesAlive;
    }


    private static int NEXT_ID = 1;
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private CabbageAgent[][] _cabbages;
//    private long _numberOfButterflies;
    private Grid _releaseBoundaryGrid;
    private GaussianAzimuthGenerator _headingGenerator;
//    private long _populationSize;
    private EscapingAgentCatcher _catcher;
    private RandomWalkStrategy _movementStrategy;
    private long _numberOfButterfliesRemaining;
    private long _numberOfButterfliesAlive;


    private static final String GRID_NAME = "Experimental Area";
    private static final Logger log = Logger.getLogger(RandomWalkEdgeEffectExperiment.class);
    private IAdultMortalityStrategy _adultMortalityStrategy;
}
