/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.simulation.model.agent.physical.DirectLarvalMortalityStrategy;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class CompetitiveLarvalMortalityStrategyDefinition extends LarvalMortalityStrategyBase {

    public CompetitiveLarvalMortalityStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public CompetitiveLarvalMortalityStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return CompetitiveLarvalMortalityStrategyDefinition.createStrategyS(0);
    }

    public static StrategyDefinitionParameter createStrategyS(long larvalLifespan) {
        StrategyDefinitionParameter strategS = new StrategyDefinitionParameter(CompetitiveLarvalMortalityStrategyDefinition.STRATEGY_NAME, DirectLarvalMortalityStrategy.class.getName());
        strategS.addParameter(new Parameter(CompetitiveLarvalMortalityStrategyDefinition.P_LARVAL_LIFESPAN, larvalLifespan));
        return strategS;
    }

    public long getLarvalLifespan() {
        return super.getParameter(CompetitiveLarvalMortalityStrategyDefinition.P_LARVAL_LIFESPAN).getLongValue();
    }

    public void setLarvalLifespan(long lifespan) {
        super.getParameter(CompetitiveLarvalMortalityStrategyDefinition.P_LARVAL_LIFESPAN).setValue(lifespan);
    }



    public static final String STRATEGY_NAME = "direct";


    public static final String P_LARVAL_LIFESPAN = "larvalLifespan";




}
