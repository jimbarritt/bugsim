/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager;

import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.BehaviourCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.PopulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.InitialImmigrationStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.LimitedEggsAdultMortalityStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.DirectLarvalMortalityStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.SensorCategory;
import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgent;
import com.ixcode.framework.parameter.model.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 12:30:09 PM by jim
 */
public class ForagerCategory extends CategoryDefinition {

    public static Category createDefaultC() {
        StrategyDefinitionParameter defaultAdultMortality = LimitedEggsAdultMortalityStrategyDefinition.createStrategyS();
        StrategyDefinitionParameter defaultLarvalMortality = DirectLarvalMortalityStrategyDefinition.createDefaultStrategyS();
        StrategyDefinitionParameter defaultImmigration= InitialImmigrationStrategyDefinition.createDefaultStrategyS();
        return createForagerC(defaultAdultMortality, defaultLarvalMortality, defaultImmigration);
    }

    public  static Category createForagerC(StrategyDefinitionParameter adultMortalityS, StrategyDefinitionParameter larvalMortalityS, StrategyDefinitionParameter immigrationS) {
        return createForagerC(ForagerCategory.CATEGORY_NAME, adultMortalityS, larvalMortalityS, immigrationS, ButterflyAgent.class);
    }

    protected static Category createForagerC(String categoryName, StrategyDefinitionParameter adultMortalityS, StrategyDefinitionParameter larvalMortalityS, StrategyDefinitionParameter immigrationS, Class agentClass) {
        Category foragerC = new Category(categoryName);

        foragerC.addSubCategory(BehaviourCategory.createDefaultC());

        foragerC.addSubCategory(SensorCategory.createSensorC());

        foragerC.addSubCategory(PopulationCategory.createCategoryC(adultMortalityS, larvalMortalityS, immigrationS, agentClass.getName()));

        return foragerC;
    }


    public ForagerCategory(Category category, ParameterMap params, boolean forwardEvents) {
        super(category, params, forwardEvents);
        Category behaviourC = super.getSubCategory(BehaviourCategory.CATEGORY_NAME);
        _behaviourCategory = new BehaviourCategory(behaviourC, params,  forwardEvents);

        Category sensorC = super.getSubCategory(SensorCategory.C_SENSOR);
        _sensorCategory = new SensorCategory(sensorC, params, forwardEvents);

        Category populationC = super.getSubCategory(PopulationCategory.CATEGORY_NAME);
        _populationCategory = new PopulationCategory(populationC, params, forwardEvents);

    }



    public BehaviourCategory getBehaviourCategory() {
        return _behaviourCategory;
    }

    public SensorCategory getSensorCategory() {
        return _sensorCategory;
    }

    public PopulationCategory getPopulationCategory() {
        return _populationCategory;
    }

    public static final String CATEGORY_NAME = "forager";


    private BehaviourCategory _behaviourCategory;

    private SensorCategory _sensorCategory;
    private PopulationCategory _populationCategory;
}
