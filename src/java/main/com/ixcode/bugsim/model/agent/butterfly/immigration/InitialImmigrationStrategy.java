/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.agent.butterfly.immigration;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.bugsim.model.agent.butterfly.immigration.pattern.IImmigrationPattern;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.InitialImmigrationStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;

import java.util.Map;
import java.util.HashMap;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 26, 2007 @ 8:54:42 PM by jim
 */
public class InitialImmigrationStrategy implements IImmigrationStrategy {

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        InitialImmigrationStrategyDefinition iis = new InitialImmigrationStrategyDefinition(strategyP, params, false);
        Simulation simulation = SimulationCategory.getSimulation(initialisationObjects);

        Landscape landscape = simulation.getLandscape();

        _releaseBoundaryGrid = landscape.getReleaseBoundaryGrid();
        _zeroBoundaryGrid = landscape.getZeroBoundaryGrid();

        Map initObjects = new HashMap(initialisationObjects);
        initObjects.put(I_RELEASE_BOUNDARY, _releaseBoundaryGrid);
        initObjects.put(I_ZERO_BOUNDARY, _zeroBoundaryGrid);
        _immigrationPattern = (IImmigrationPattern)iis.getImmigrationPattern().instantiateImplementedStrategy(initObjects);
    }

    public String getParameterSummary() {
        return null;
    }

    public IImmigrationPattern getImmigrationPattern() {
        return _immigrationPattern;
    }

    public boolean hasImmigration(int time) {
        return (time == 1);
    }

    public IImmigrationPattern getImmigration(int time) {
        if (time ==1) { // First generation /  Timestep
            return _immigrationPattern;
        } else {
            throw new IllegalArgumentException("No Pattern for time " + time + " use 'hasImmigration' to test for this first!");
        }
    }

    private IImmigrationPattern _immigrationPattern;
    private Grid _releaseBoundaryGrid;
    private Grid _zeroBoundaryGrid;

    public static final String I_RELEASE_BOUNDARY = "releaseBoundary";
    public  static final String I_ZERO_BOUNDARY = "zeroBoundary";
}
