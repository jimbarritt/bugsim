/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.mortality;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.simulation.model.agent.physical.DirectLarvalMortalityStrategy;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class DirectLarvalMortalityStrategyDefinition extends LarvalMortalityStrategyBase {

    public DirectLarvalMortalityStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public DirectLarvalMortalityStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return createStrategyS(0, 0);
    }

    public static StrategyDefinitionParameter createStrategyS(double mortalityRate, long larvalLifespan) {
        StrategyDefinitionParameter strategS = new StrategyDefinitionParameter(DirectLarvalMortalityStrategyDefinition.STRATEGY_NAME, DirectLarvalMortalityStrategy.class.getName());
        strategS.addParameter(new Parameter(P_MORTALITY_RATE, mortalityRate));
        strategS.addParameter(new Parameter(P_LARVAL_LIFESPAN, larvalLifespan));
        return strategS;
    }

    public long getLarvalLifespan() {
        return super.getParameter(P_LARVAL_LIFESPAN).getLongValue();
    }

    public void setLarvalLifespan(long lifespan) {
        super.getParameter(P_LARVAL_LIFESPAN).setValue(lifespan);
    }


    public double getMortalityRate() {
        return super.getParameter(P_MORTALITY_RATE).getDoubleValue();
    }

    public void setMortalityRate(double rate) {
        super.getParameter(P_MORTALITY_RATE).setValue(rate);
    }

    public static final String STRATEGY_NAME = "direct";

    public static final String P_MORTALITY_RATE = "mortalityRate" ;
    public static final String P_LARVAL_LIFESPAN = "larvalLifespan";




}
