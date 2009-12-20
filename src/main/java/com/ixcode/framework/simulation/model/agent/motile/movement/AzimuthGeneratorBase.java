/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.bugsim.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.AzimuthStrategyBase;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.AzimuthStrategyFactory;
import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.math.geometry.DirectionOfChange;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.VisualSignal;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.OlfactorySignal;

import java.util.Map;
import java.util.Random;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public abstract class AzimuthGeneratorBase implements IAzimuthGenerator, IParameterisedStrategy {


    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        Simulation simulation = SimulationCategory.getSimulation(initialisationObjects);
        _random = simulation.getRandom();
        AzimuthStrategyBase strategy = AzimuthStrategyFactory.createAzimuthStrategy(strategyP, params, false);
        _visualSignalNoiseThreshold = strategy.getVisualNoiseThreshold();
    }

    public String getParameterSummary() {
        return "";
    }

    protected AzimuthGeneratorBase() {
    }

    public AzimuthGeneratorBase(Random random) {
        _random = random;
    }

    public IAzimuthGenerator modifyAzimuthGenerator(VisualSignal visualSignal, double calculatedAzimuth) {
        return this;
    }

    public IAzimuthGenerator modifyAzimuthGenerator(OlfactorySignal olfactorySignal, double calculatedAzimuth) {
        return this;
    }

    protected Random getRandom() {
        return _random;
    }

    public double getVisualSignalNoiseThreshold() {
        return _visualSignalNoiseThreshold;
    }

    public double calculateSignalNoise(double signal, double psi, double maxK) {
        return (psi * maxK) - (psi * signal * maxK);
    }

    private Random _random;
    private double _visualSignalNoiseThreshold;
}
