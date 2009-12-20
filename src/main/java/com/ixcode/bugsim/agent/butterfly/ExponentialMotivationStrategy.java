/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.butterfly;

import com.ixcode.bugsim.experiment.parameter.ButterflyParameters;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.physical.AgentBehaviour;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Description : We did used to have a complex system for describing "motivational state" but we have removed this in favour of a simple "refactory period" of a number of timesteps...
 */
public class ExponentialMotivationStrategy implements IMotivationStrategy, IParameterisedStrategy {

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        _name = strategyP.getName();
        _keyAgentBehaviour = ButterflyParameters.getKeyAgentBehaviour(strategyP);
        _maxLevel = ButterflyParameters.getMaxBeta(strategyP);
        _gamma = ButterflyParameters.getMotivationalGamma(strategyP);
        _enabled = ButterflyParameters.getEnabled(strategyP);

    }

    public String getParameterSummary() {
        return "enabled=" + _enabled + "name" + _name + "keyBehaviour=" + _keyAgentBehaviour + "GAMMA=" + _gamma + ", MAX=" + _maxLevel;
    }

    public String getName() {
        return _name;
    }

    /**
     * Latex: \beta=\beta_{max}(1-e^{-\gamma t}
     * // It will always be 1 step behind because the oviposition will have occured in the last move
     * so we calculate t as (currentAge - ageOfBehaviour) - 1 so the next move after a behaviour is considered t=0
     * <p/>
     * Cant go much bigger than 10,000 or it will overflow unless we use BigDecimal
     *
     * @param agent
     * @return
     */
    public double calculateMotivationLevel(IMotileAgent agent) {
        if (!_enabled) {
            return _maxLevel;
        }

        int ageOfBehaviour = agent.getAgeOfLastBehaviour(_keyAgentBehaviour);

        int t = agent.getAge();
        if (ageOfBehaviour > 0) {
            t = (agent.getAge() - ageOfBehaviour) - 1;
        }


        double beta = _maxLevel * (1 - Math.exp(-(_gamma * t)));
        if (log.isDebugEnabled()) {
            log.debug("beta= " + beta + " t= " + t + " age of last behaviour: " + ageOfBehaviour + " currentAge: " + agent.getAge());
        }
        return beta;

    }

    public AgentBehaviour getKeyAgentBehaviour() {
        return _keyAgentBehaviour;
    }

    public double getMaxLevel() {
        return _maxLevel;
    }

    /**
     * Helper Function to acces the strategy proper.
     * @param agent
     * @param motivationName
     * @param doCalculation
     * @return
     */
    public static double calculateMotivationalLevelForAgent(IMotileAgent agent, String motivationName) {
        if (!(agent instanceof IMotivationalAgent)) {
            throw new IllegalStateException("Agent must implement " + IMotivationalAgent.class.getName() + " your agent is " + agent.getClass().getName());
        }
        IMotivationStrategy motivation = ((IMotivationalAgent)agent).getMotivationStrategy(motivationName);
        return  motivation.calculateMotivationLevel(agent);
    }


    private static final Logger log = Logger.getLogger(ExponentialMotivationStrategy.class);

    private String _name;
    private AgentBehaviour _keyAgentBehaviour;
    private double _gamma;
    private double _maxLevel;
    private boolean _enabled;
}
