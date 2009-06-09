/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.simulation.timescale;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.simulation.model.timescale.DiscreteGenerationsTimescale;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class DiscreteTimescaleStrategyDefinition extends ContinuousTimescaleStrategyDefinition {

    public DiscreteTimescaleStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

  public DiscreteTimescaleStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return createStrategyS(30, 5);
    }
    public static StrategyDefinitionParameter createStrategyS(long timestepLimit, long generationLmit) {
        StrategyDefinitionParameter discreteS = new StrategyDefinitionParameter(STRATEGY_NAME, DiscreteGenerationsTimescale.class.getName());

        DiscreteTimescaleStrategyDefinition.addParameters(discreteS, timestepLimit);

        discreteS.addParameter(new Parameter(P_GENERATION_LIMIT, generationLmit));

        return discreteS;
    }

    private static void addParameters(StrategyDefinitionParameter strategyS, long timestepLimit) {
        strategyS.addParameter(new Parameter(DiscreteTimescaleStrategyDefinition.P_TIMESTEP_LIMIT, timestepLimit));
    }

    public long getGenerationLimit() {
        return super.getParameter(P_GENERATION_LIMIT).getLongValue();
    }

    public void setGenerationLimit(long generationLimit) {
        super.getParameter(P_GENERATION_LIMIT).setValue(generationLimit);
    }


    public static final String STRATEGY_NAME = "discreteGenerations";

    public static final String P_GENERATION_LIMIT = "generationLimit";









}
