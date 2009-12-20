/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.population;

import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.AdultMortalityStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.AdultMortalityStrategyFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.LarvalMortalityStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.LarvalMortalityStrategyFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.ImmigrationStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.ImmigrationStrategyFactory;
import com.ixcode.framework.parameter.model.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:03:45 PM by jim
 */
public class PopulationCategory extends CategoryDefinition {

    public static Category createCategoryC(StrategyDefinitionParameter adultMortalityS, StrategyDefinitionParameter larvalMortalityS, StrategyDefinitionParameter immigrationS, String agentClass) {
        Category populationC = new Category(CATEGORY_NAME);

        populationC.addParameter(new Parameter(P_AGENT_CLASS, agentClass));
        populationC.addParameter(new Parameter(P_ADULT_MORTALITY, adultMortalityS));
        populationC.addParameter(new Parameter(P_LARVAL_MORTALITY, larvalMortalityS));
        populationC.addParameter(new Parameter(P_IMMIGRATION, immigrationS));

        return populationC;
    }

    public PopulationCategory(Category category, ParameterMap params, boolean forwardEvents) {
        super(category, params, forwardEvents);


        AdultMortalityStrategyBase adultMortalityStrategy = (AdultMortalityStrategyBase)createStrategyDefinition(P_ADULT_MORTALITY, getAdultMortalityS());
        super.addStrategyDefinition(P_ADULT_MORTALITY, adultMortalityStrategy);

        LarvalMortalityStrategyBase larvalMortalityStrategy = (LarvalMortalityStrategyBase)createStrategyDefinition(P_LARVAL_MORTALITY, getLarvalMortalityS());
        super.addStrategyDefinition(P_LARVAL_MORTALITY, larvalMortalityStrategy);


        ImmigrationStrategyBase immigrationStrategy = (ImmigrationStrategyBase)createStrategyDefinition(P_IMMIGRATION, getImmigrationS());
        super.addStrategyDefinition(P_IMMIGRATION, immigrationStrategy);
    }

    protected StrategyDefinition createStrategyDefinition(String parameterName, StrategyDefinitionParameter strategyS) {
        StrategyDefinition strategy = null;
        if (parameterName.equals(P_ADULT_MORTALITY)) {
            strategy = AdultMortalityStrategyFactory.createAdultMortalityStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        } else if (parameterName.equals(P_LARVAL_MORTALITY)) {
            strategy = LarvalMortalityStrategyFactory.createLarvalMortalityStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        } else if (parameterName.equals(P_IMMIGRATION)) {
            strategy = ImmigrationStrategyFactory.createImmigrationStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        } else {
            super.createStrategyDefinition(parameterName, strategyS);
        }
        return strategy;
    }


    public StrategyDefinitionParameter getAdultMortalityS() {
        return super.getParameter(P_ADULT_MORTALITY).getStrategyDefinitionValue();
    }

    public AdultMortalityStrategyBase getAdultMortality() {
        return (AdultMortalityStrategyBase)super.getStrategyDefinition(P_ADULT_MORTALITY);
    }

    public void setAdultMortalityS(StrategyDefinitionParameter mortalityS) {
        super.replaceStrategyDefinitionParameter(P_ADULT_MORTALITY, mortalityS);
    }


    public StrategyDefinitionParameter getLarvalMortalityS() {
        return super.getParameter(P_LARVAL_MORTALITY).getStrategyDefinitionValue();
    }

    public LarvalMortalityStrategyBase getLarvalMortality() {
        return (LarvalMortalityStrategyBase)super.getStrategyDefinition(P_LARVAL_MORTALITY);
    }

    public void setLarvalMortalityS(StrategyDefinitionParameter mortalityS) {
        super.replaceStrategyDefinitionParameter(P_LARVAL_MORTALITY, mortalityS);
    }


    public StrategyDefinitionParameter getImmigrationS() {
        return super.getParameter(P_IMMIGRATION).getStrategyDefinitionValue();
    }

    public ImmigrationStrategyBase getImmigrationStrategy() {
        return (ImmigrationStrategyBase)super.getStrategyDefinition(P_IMMIGRATION);
    }


    public void setImmigrationStrategyS(StrategyDefinitionParameter immigrationS) {
        super.replaceStrategyDefinitionParameter(P_IMMIGRATION, immigrationS);
    }

    public String getAgentClass() {
        return super.getParameter(P_AGENT_CLASS).getStringValue();
    }

    public void setAgentClass(String agentClass) {
        super.getParameter(P_AGENT_CLASS).setValue(agentClass);
    }


    public static final String CATEGORY_NAME = "population";

    public static final String P_AGENT_CLASS = "agentClass";

    public static final String P_IMMIGRATION = "immigration";

    public static final String P_LARVAL_MORTALITY = "larvalMortality";

    public static final String P_ADULT_MORTALITY = "adultMortality";


}
