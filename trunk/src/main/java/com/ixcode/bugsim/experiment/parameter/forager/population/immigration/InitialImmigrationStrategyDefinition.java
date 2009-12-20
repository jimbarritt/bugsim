/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.immigration;

import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.ImmigrationPatternStrategyBase;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.ImmigrationPatternStrategyFactory;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.RandomReleaseImmigrationPatternStrategyDefinition;
import com.ixcode.bugsim.agent.butterfly.immigration.InitialImmigrationStrategy;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.Parameter;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class InitialImmigrationStrategyDefinition extends ImmigrationStrategyBase {

    public InitialImmigrationStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        this(sparam, params, false);


    }

    public InitialImmigrationStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
         ImmigrationPatternStrategyBase patternStrategy = ImmigrationPatternStrategyFactory.createImmigrationPatternStrategy(getImmigrationPatternS(), super.getParameterMap(), super.isForwardEvents());
        super.addStrategyDefinition(P_IMMIGRATION_PATTERN, patternStrategy);
    }

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        StrategyDefinitionParameter immgrationPatternS = RandomReleaseImmigrationPatternStrategyDefinition.createDefaultStrategyS();

        return createStrategyS(immgrationPatternS);
    }

    public static StrategyDefinitionParameter createStrategyS(StrategyDefinitionParameter immigrationPatternS) {
        StrategyDefinitionParameter immigrationS = new StrategyDefinitionParameter(InitialImmigrationStrategyDefinition.STRATEGY_NAME, InitialImmigrationStrategy.class.getName());

        ImmigrationStrategyBase.addParameters(immigrationS);
        immigrationS.addParameter(new Parameter(P_IMMIGRATION_PATTERN, immigrationPatternS));

        return immigrationS;
    }

    protected StrategyDefinition createStrategyDefinition(String parameterName, StrategyDefinitionParameter strategyS) {
        StrategyDefinition strategy = null;
        if (parameterName.equals(P_IMMIGRATION_PATTERN)) {
            strategy = ImmigrationPatternStrategyFactory.createImmigrationPatternStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        } else {
            strategy = super.createStrategyDefinition(parameterName, strategyS);
        }
        return strategy;
    }

    public StrategyDefinitionParameter getImmigrationPatternS() {
        return super.getParameter(P_IMMIGRATION_PATTERN).getStrategyDefinitionValue();
    }

    public ImmigrationPatternStrategyBase getImmigrationPattern() {
        return (ImmigrationPatternStrategyBase)super.getStrategyDefinition(P_IMMIGRATION_PATTERN);
    }

    public void setImmigrationPatternS(StrategyDefinitionParameter immigrationPatternS) {
        super.replaceStrategyDefinitionParameter(P_IMMIGRATION_PATTERN, immigrationPatternS);
    }


    public static final String STRATEGY_NAME = "initial";

    public static final String P_IMMIGRATION_PATTERN = "immigrationPattern";


}
