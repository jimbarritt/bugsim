/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.model.agent.physical;

import com.ixcode.bugsim.model.agent.cabbage.ForagerEgg;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.DirectLarvalMortalityStrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.math.DoubleMath;

import java.util.Map;
import java.util.Random;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 2, 2007 @ 9:59:02 PM by jim
 */
public class DirectLarvalMortalityStrategy implements ILarvalMortalityStrategy {



    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        Simulation simulation = SimulationCategory.getSimulation(initialisationObjects);
        _random = simulation.getRandom();

        DirectLarvalMortalityStrategyDefinition strategy = new DirectLarvalMortalityStrategyDefinition(strategyP, params, false)  ;
        _mortalityRate = strategy.getMortalityRate(); 
    }

    public String getParameterSummary() {
        return null;
    }


    public boolean isLarvaeDead(ForagerEgg egg) {
        return DoubleMath.precisionLessThanEqual(_random.nextDouble(), _mortalityRate, DoubleMath.DOUBLE_PRECISION_DELTA);
    }

    private double _mortalityRate;
    private Random _random;
}
