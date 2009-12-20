/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager;

import com.ixcode.framework.parameter.model.Category;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.bugsim.experiment.parameter.forager.population.mortality.LimitedEggsAdultMortalityStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.population.mortality.DirectLarvalMortalityStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.InitialImmigrationStrategyDefinition;
import com.ixcode.bugsim.agent.butterfly.CompetitorAgent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 4, 2007 @ 9:54:02 PM by jim
 */
public class CompetitorCategory extends ForagerCategory {


    public static Category createDefaultC() {
        StrategyDefinitionParameter defaultAdultMortality = LimitedEggsAdultMortalityStrategyDefinition.createStrategyS();
        StrategyDefinitionParameter defaultLarvalMortality = DirectLarvalMortalityStrategyDefinition.createDefaultStrategyS();
        StrategyDefinitionParameter defaultImmigration= InitialImmigrationStrategyDefinition.createDefaultStrategyS();
        return createCompetitorC(defaultAdultMortality, defaultLarvalMortality, defaultImmigration);
    }

    public  static Category createCompetitorC(StrategyDefinitionParameter adultMortalityS, StrategyDefinitionParameter larvalMortalityS, StrategyDefinitionParameter immigrationS) {
        String categoryName = CATEGORY_NAME;
        return createForagerC(categoryName, adultMortalityS, larvalMortalityS, immigrationS, CompetitorAgent.class);
    }

    public CompetitorCategory(Category category, ParameterMap params, boolean forwardEvents) {
        super(category, params, forwardEvents);
    }

    public static final String CATEGORY_NAME = "Competitor";
}
