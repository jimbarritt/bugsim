/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager;

import com.ixcode.framework.parameter.model.Category;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.bugsim.experiment.parameter.forager.population.mortality.LimitedEggsAdultMortalityStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.population.mortality.DirectLarvalMortalityStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.InitialImmigrationStrategyDefinition;
import com.ixcode.bugsim.agent.butterfly.CompetitorAgent;
import com.ixcode.bugsim.agent.butterfly.ParasitoidAgent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 4, 2007 @ 9:54:02 PM by jim
 */
public class ParasitoidCategory extends ForagerCategory {


    public static Category createDefaultC() {
        StrategyDefinitionParameter defaultAdultMortality = LimitedEggsAdultMortalityStrategyDefinition.createStrategyS();
        StrategyDefinitionParameter defaultLarvalMortality = DirectLarvalMortalityStrategyDefinition.createDefaultStrategyS();
        StrategyDefinitionParameter defaultImmigration= InitialImmigrationStrategyDefinition.createDefaultStrategyS();
        return createParasitoidC(defaultAdultMortality, defaultLarvalMortality, defaultImmigration);
    }

    public  static Category createParasitoidC(StrategyDefinitionParameter adultMortalityS, StrategyDefinitionParameter larvalMortalityS, StrategyDefinitionParameter immigrationS) {
        String categoryName = CATEGORY_NAME;
        return createForagerC(categoryName, adultMortalityS, larvalMortalityS, immigrationS, ParasitoidAgent.class);
    }

    public ParasitoidCategory(Category category, ParameterMap params, boolean forwardEvents) {
        super(category, params, forwardEvents);
    }

    public static final String CATEGORY_NAME = "Parasitoid";
}
