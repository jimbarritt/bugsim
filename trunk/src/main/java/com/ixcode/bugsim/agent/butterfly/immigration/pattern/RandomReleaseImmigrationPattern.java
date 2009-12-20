/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.butterfly.immigration.pattern;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.EscapingAgentCatcher;
import com.ixcode.framework.simulation.model.agent.motile.movement.UniformAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.IAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.bugsim.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.RandomReleaseImmigrationPatternStrategyDefinition;
import com.ixcode.bugsim.agent.butterfly.immigration.InitialImmigrationStrategy;

import java.util.Map;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 26, 2007 @ 9:09:05 PM by jim
 */
public class RandomReleaseImmigrationPattern implements IImmigrationPattern {

    public RandomReleaseImmigrationPattern() {

    }

    public long getEggLimit() {
        return _eggLimit;
    }

    public long getNumberToRelease() {
        return _numberToRelease;
    }

    /**
     * @param strategyS
     * @param params
     * @param
     */
    public void initialise(StrategyDefinitionParameter strategyS, ParameterMap params, Map initialisationObjects) {
        Simulation simulation = SimulationCategory.getSimulation(initialisationObjects);

        RandomReleaseImmigrationPatternStrategyDefinition rris = new RandomReleaseImmigrationPatternStrategyDefinition(strategyS, params, false);

        _releaseInZone = rris.isReleaseInZone();
        _numberToRelease = rris.getNumberToRelease();
        _eggLimit = rris.getEggLimit();

        _randomHeadingGenerator = new UniformAzimuthGenerator(simulation.getRandom());

        _releaseBoundaryGrid = (Grid)initialisationObjects.get(InitialImmigrationStrategy.I_RELEASE_BOUNDARY);
        _zeroBoundaryGrid = (Grid)initialisationObjects.get(InitialImmigrationStrategy.I_ZERO_BOUNDARY);

        _simulation = simulation;

        _optimiseReleaseAzimuth = rris.isOptimiseReleaseAzimuth();
//        if (_optimiseReleaseAzimuth && _releaseInZone) {
//            throw new IllegalStateException("Currnetly cannot use RandomRelease In zone and Optimised Release Azimuth - bug in optimise release azimuth") ;
//        }

    }


    public double nextReleaseMoveLength(IMovementStrategy movementStrategy) {
        return movementStrategy.getInitialMoveLength();
    }

    public double nextReleaseI() {
        return 0;
    }

    public RectangularCoordinate nextReleaseLocation() {
        RectangularCoordinate location = null;
        if (_releaseInZone) {
            location = generateInZone();
        } else {
            location = Geometry.generateRandomCoordOnPerimeter(_simulation.getRandom(), _releaseBoundaryGrid.getBounds(), _releaseBoundaryGrid.isCircular());
        }
        return location;
    }


    public double nextReleaseAzimuth(RectangularCoordinate releaseLocation, double moveLength) {
        double azimuth = 0;
        if (_optimiseReleaseAzimuth && _simulation.hasEscapingAgentCatcher()) {
            azimuth = generateOptimizedAzimuth(releaseLocation, moveLength);
        } else {
            azimuth = _randomHeadingGenerator.generateCourseChange(0).getNewAzimuth();
        }
        return azimuth;
    }

    /**
     *
     * @param releaseLocation
     * @param moveLength
     * @return
     */
    private double generateOptimizedAzimuth(RectangularCoordinate releaseLocation, double moveLength) {
        EscapingAgentCatcher catcher = _simulation.getEscapingAgentCatcher();
        double azimuth = 0;
        boolean found = false;
        while (!found) {
            azimuth = _randomHeadingGenerator.generateCourseChange(0).getNewAzimuth();
            AzimuthCoordinate azNew = new AzimuthCoordinate(azimuth, moveLength);
            RectangularCoordinate projectedLocation = releaseLocation.moveTo(azNew.asPolar());
            if (!catcher.isPointOutsideBounds(projectedLocation)) {
                found = true;
            } else {
                //@todo There is a sublte bug here somehwere when the patch is very small relative to the step size and it never finds an azimuth that enters the patch.
                if (catcher.pathEntersBounds(releaseLocation, projectedLocation)) {
                    found = true;
                }
            }

        }

        return azimuth;
    }

    private RectangularCoordinate generateInZone() {
        RectangularCoordinate startCoord = null;
        while (startCoord == null) {

            RectangularCoordinate randomCoord = Geometry.generateUniformRandomCoordinate(_simulation.getRandom(), _releaseBoundaryGrid.getBounds());
            if (_releaseBoundaryGrid.containsPoint(randomCoord, true) && !_zeroBoundaryGrid.containsPoint(randomCoord, false) ) {
                startCoord = randomCoord;
            }
        }
        return startCoord;

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


    public String getParameterSummary() {
        return "";
    }

    private static final Logger log = Logger.getLogger(RandomReleaseImmigrationPattern.class);

    private Simulation _simulation;

    private static final DecimalFormat FORMAT2D = new DecimalFormat("0.00");

    private String _BFORMATTED;
    private IAzimuthGenerator _randomHeadingGenerator;
    private Grid _releaseBoundaryGrid;
    private Grid _zeroBoundaryGrid;


    private boolean _releaseInZone;
    private double _B = -1;
    private long _numberToRelease;
    private long _eggLimit;
    private boolean _optimiseReleaseAzimuth;
}
