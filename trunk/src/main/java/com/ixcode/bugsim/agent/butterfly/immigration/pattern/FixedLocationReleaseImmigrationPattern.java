/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.butterfly.immigration.pattern;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.movement.UniformAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.bugsim.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.FixedLocationImmigrationStrategyDefinition;

import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 26, 2007 @ 9:09:05 PM by jim
 */
public class FixedLocationReleaseImmigrationPattern implements IImmigrationPattern {

    public void initialise(StrategyDefinitionParameter strategyS, ParameterMap params, Map initialisationObjects) {
        Simulation simulation = SimulationCategory.getSimulation(initialisationObjects);

        FixedLocationImmigrationStrategyDefinition flis = new FixedLocationImmigrationStrategyDefinition(strategyS, params, false);



        _numberToRelease = flis.getNumberToRelease();
        _eggLimit = flis.getEggLimit();

        _releaseLocation = flis.getReleaseLocation();
        _applyFixedAzimuth = flis.isApplyFixedAzimuth();
        _fixedAzimuth = flis.getFixedAzimuth();

        if (!_applyFixedAzimuth) {
            _randomHeadingGenerator = new UniformAzimuthGenerator(simulation.getRandom());
        }


        _simulation = simulation;

    }                                                                             

    public RectangularCoordinate nextReleaseLocation() {
        return _releaseLocation;
    }

    public double nextReleaseAzimuth(RectangularCoordinate releaseLocation, double moveLength) {
        double azimuth = _fixedAzimuth;
        if (!_applyFixedAzimuth) {
            azimuth = _randomHeadingGenerator.generateCourseChange(0).getNewAzimuth();
        }
        return azimuth;
    }

    public long getNumberToRelease() {
        return _numberToRelease;
    }
    public long getEggLimit() {
        return _eggLimit;
    }

    public String getParameterSummary() {
        return null;
    }



    public double nextReleaseMoveLength(IMovementStrategy movementStrategy) {
        return movementStrategy.getInitialMoveLength();
    }

    public double nextReleaseI() {
        return 0;
    }


    private long _numberToRelease;
    private long _eggLimit;
    private UniformAzimuthGenerator _randomHeadingGenerator;
    private Simulation _simulation;
    private boolean _applyFixedAzimuth;
    private double _fixedAzimuth;
    private RectangularCoordinate _releaseLocation;
}
