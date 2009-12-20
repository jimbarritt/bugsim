/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.mortality;

import com.ixcode.bugsim.agent.butterfly.mortality.LimitedAgeAdultMortalityStrategy;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class LimitedAgeAdultMortalityStrategyDefinition extends AdultMortalityStrategyBase {

    public LimitedAgeAdultMortalityStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public LimitedAgeAdultMortalityStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return createStrategyS(100);        
    }
    public static StrategyDefinitionParameter createStrategyS(long maxAge) {
        StrategyDefinitionParameter limitedAgeS = new StrategyDefinitionParameter(S_LIMITED_AGE, LimitedAgeAdultMortalityStrategy.class.getName());

        addParameters(limitedAgeS, maxAge);

        return limitedAgeS;
    }

    public static void addParameters(StrategyDefinitionParameter strategyS, long maxAge) {
        Parameter maxAgeP = new Parameter(P_MAX_AGE, maxAge);
        strategyS.addParameter(maxAgeP);
    }

    public long getMaxAge() {
        return super.getParameter(P_MAX_AGE).getLongValue();
    }

    public void setMaxAge(long maxAge) {
        super.getParameter(P_MAX_AGE).setValue(maxAge);
    }



    public static final String S_LIMITED_AGE = "limitedAge";

    public static final String P_MAX_AGE = "maxAge";

}
