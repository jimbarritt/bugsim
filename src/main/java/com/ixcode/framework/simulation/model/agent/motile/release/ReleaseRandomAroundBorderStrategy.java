/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.release;

import com.ixcode.bugsim.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.experiment.parameter.forager.population.release.RandomBoundaryReleaseStrategy;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.movement.IAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.UniformAzimuthGenerator;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridFactory;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ReleaseRandomAroundBorderStrategy implements IReleaseStrategy, IParameterisedStrategy {


    public double generateReleaseI() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @param strategyS
     * @param params
     * @param simulation
     */
    public void initialise(StrategyDefinitionParameter strategyS, ParameterMap params, Map initialisationObjects) {
        Simulation simulation = SimulationCategory.getSimulation(initialisationObjects);
        Landscape landscape = simulation.getLandscape();

        RandomBoundaryReleaseStrategy rbs = new RandomBoundaryReleaseStrategy(strategyS, params, false);

        CartesianBounds b = rbs.getBoundary().getBounds();
        boolean isCircular = rbs.getBoundary().getBoundaryShape().isCircular();


        CartesianBounds zeroGridBounds = b.calculateInnerBounds(rbs.getDistance());

        _releaseBoundaryGrid = GridFactory.createGrid(null, "ReleaseInitialisationParameters Boundary", b, 1, 1, isCircular);
        _zeroBoundaryGrid = GridFactory.createGrid(null,  ZERO_BOUNDARY_GRID, zeroGridBounds, 1, 1, isCircular);
        _randomHeadingGenerator = new UniformAzimuthGenerator(simulation.getRandom());


        landscape.addGrid(_releaseBoundaryGrid);
        landscape.addGrid(_zeroBoundaryGrid);
        _simulation = simulation;

    }

//    Parameter borderDef = ButterflyParameters.getReleaseBoundaryParameter(params);
//          StrategyDefinitionParameter borderAlgorithm = (StrategyDefinitionParameter)borderDef.getValue();
//
//          CartesianBounds b;
//          boolean isCircular;
//          CartesianBounds zeroGridBounds;
//
//          if (borderAlgorithm.getName().equals(DistancedExtentStrategy.S_DISTANCED_EXTENT)) {
//
//              Parameter boundaryDistance = borderAlgorithm.findParameter(DistancedExtentStrategy.P_DISTANCE);
//              _BFORMATTED = FORMAT2D.format(boundaryDistance.getDoubleValue());
//              _B = boundaryDistance.getDoubleValue();
//
//              DerivedParameter  borderBoundsParam = (DerivedParameter)borderAlgorithm.findParameter(BoundaryParameters.P_BOUNDARY_BOUNDS);
//              Parameter boderShapeParam = borderAlgorithm.findParameter(BoundaryParameters.P_BOUNDARY_SHAPE);
//
//
//              ISourceParameterMap sourceParams = borderBoundsParam.getSourceParameters();
//
//              Parameter sourceBoundsParam = sourceParams.getParameter(0);
//              Parameter shapeParam = sourceParams.getParameter(2);
//              Parameter sourceShapeParam = sourceParams.getParameter(3);
//
//
//              CartesianBounds sourceBounds = (CartesianBounds)sourceBoundsParam.getValue();
//              BoundaryShape shape = (BoundaryShape)shapeParam.getValue();
//              BoundaryShape sourceShape = (BoundaryShape)sourceShapeParam.getValue();
//              CartesianBounds rawBounds = (CartesianBounds)borderBoundsParam.getValue();
//              b = rawBounds.centre(landscape.getLogicalBounds());
//              isCircular = (BoundaryShape.CIRCULAR == (BoundaryShape)boderShapeParam.getValue());
//
//
//              CartesianBounds rawCpb = (CartesianBounds)RectangularDistancedExtentBoundsCalculator.INSTANCE.calculateOuterBounds(shape, sourceBounds, 0, sourceShape);
//              zeroGridBounds = rawCpb.centre(landscape.getLogicalBounds()); // Cabbage Patch bounds
//
//          }  else {
//              _BFORMATTED = "not-set";
//              _B = -1;
//              isCircular = false;
//              Parameter boundsP= borderAlgorithm.findParameter(BoundaryParameters.P_BOUNDARY_BOUNDS);
//              b = (CartesianBounds)boundsP.getValue();
//              zeroGridBounds = (CartesianBounds)boundsP.getValue();
//
//          }


    public double generateBirthMoveLength() {
        return -1;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public ReleaseRandomAroundBorderStrategy() {

    }

    public RectangularCoordinate generateInitialLocation() {
        return Geometry.generateRandomCoordOnPerimeter(_simulation.getRandom(), _releaseBoundaryGrid.getBounds(), _releaseBoundaryGrid.isCircular());
//            return _releaseBoundaryGrid.getBounds().getCentre().moveTo(90, _releaseBoundaryGrid.getBounds().getRadiusOfInnerCircle());
    }

    public double getB() {
        return _B;
    }

    protected CartesianBounds getZeroBoundaryBounds() {
        return _zeroBoundaryGrid.getBounds();
    }

    public Simulation getSimulation() {
        return _simulation;
    }

    private static int I;

    public double generateInitialAzimuth() {
        return _randomHeadingGenerator.generateCourseChange(0).getNewAzimuth();
//        return 270;
    }

    public String getParameterSummary() {
        return "";
    }

    private static final Logger log = Logger.getLogger(ReleaseRandomAroundBorderStrategy.class);

    private Grid _releaseBoundaryGrid;
    private static final DecimalFormat FORMAT2D = new DecimalFormat("0.00");

    private String _BFORMATTED;
    private Simulation _simulation;
    private IAzimuthGenerator _randomHeadingGenerator;
    public static final String ZERO_BOUNDARY_GRID = "Zero Boundary";
    private Grid _zeroBoundaryGrid;
    private double _B = -1;
}
