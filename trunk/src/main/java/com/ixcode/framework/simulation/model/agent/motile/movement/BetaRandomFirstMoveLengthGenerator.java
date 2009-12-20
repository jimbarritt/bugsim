/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.math.random.BetaRandom;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.bugsim.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.experiment.parameter.simulation.SimulationCategory;

import java.util.Map;

/**
 * Description : Generates a random first move length according to a beta distribution.
 */
public class BetaRandomFirstMoveLengthGenerator extends FixedMoveLengthGenerator {

     public BetaRandomFirstMoveLengthGenerator() {
        super();
    }
    public BetaRandomFirstMoveLengthGenerator(double moveLength) {
        super(moveLength);
    }

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        super.initialise(strategyP, params, initialisationObjects);

        _rfmlAlpha = ButterflyParameters.getRFMLAlpha(strategyP);
        _rfmlBeta = ButterflyParameters.getRFMLBeta(strategyP);
        _simulation = SimulationCategory.getSimulation(initialisationObjects);
    }

    public double calculateMoveLength(IMotileAgent agent) {
        double mvlen = super.calculateMoveLength(agent);

        if (agent.getAge() == 0) {
            mvlen = calculateRandomMoveLength(_simulation, mvlen, agent);
        }
        return mvlen;

    }

    /**
     * Could create subclasses for the following aswell...
     *       return IxMath.generateGaussian(simulation.getRandom(), 20, 40);
     *     return IxMath.generateUniformRandom(IxMath.accurateIn(moveLength), simulation.getRandom()).doubleValue();
     * @todo could make max movelength a seperate parameter ?
     * @param simulation
     * @param moveLength
     * @param agent
     * @return
     */
    private double calculateRandomMoveLength(Simulation simulation, double maxMoveLength, IMotileAgent agent) {
        return BetaRandom.generateBeta(simulation.getRandomNumberGenerator(), maxMoveLength, _rfmlAlpha, _rfmlBeta);
    }

    private String _RFML_MAX;
    private double _randomFirstMoveLengthMax;
    private double _rfmlAlpha;
    private double _rfmlBeta;


    private Simulation _simulation;
}
