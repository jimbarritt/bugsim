/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.agent.butterfly.immigration.pattern;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.agent.motile.movement.UniformAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.pattern.RandomPointReleaseImmigrationStrategyDefinition;
import com.ixcode.bugsim.model.agent.cabbage.layout.ResourceLayoutBase;

import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 26, 2007 @ 9:09:05 PM by jim
 */
public class RandomPointReleaseImmigrationPattern implements IImmigrationPattern {

    public void initialise(StrategyDefinitionParameter strategyS, ParameterMap params, Map initialisationObjects) {
        Simulation simulation = SimulationCategory.getSimulation(initialisationObjects);

        RandomPointReleaseImmigrationStrategyDefinition flis = new RandomPointReleaseImmigrationStrategyDefinition(strategyS, params, false);



        _avoidAgents = flis.isAvoidAgents();
        _avoidingRadius = flis.getAvoidingRadius();

        _numberToRelease = flis.getNumberToRelease();
        _eggLimit = flis.getEggLimit();

        _limitToResourcePatch = flis.isLimitToResourcePatch();
        _applyFixedAzimuth = flis.isApplyFixedAzimuth();
        _fixedAzimuth = flis.getFixedAzimuth();

        if (!_applyFixedAzimuth) {
            _randomHeadingGenerator = new UniformAzimuthGenerator(simulation.getRandom());
        }


        _simulation = simulation;
        if (_limitToResourcePatch){
            _boundaryGrid = ResourceLayoutBase.getResourceLayoutGrid(_simulation.getLandscape());
        } else {
            _boundaryGrid = _simulation.getLandscape().getReleaseBoundaryGrid();
        }

    }

    public RectangularCoordinate nextReleaseLocation() {
        return _boundaryGrid.generateRandomLocation(_simulation.getRandom(), _avoidAgents, _avoidingRadius);
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
    private boolean _limitToResourcePatch;
    private Grid _boundaryGrid;
    private boolean _avoidAgents;
    private double _avoidingRadius;
}
