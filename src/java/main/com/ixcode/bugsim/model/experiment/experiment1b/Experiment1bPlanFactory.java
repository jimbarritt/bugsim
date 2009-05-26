/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment1b;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.model.experiment.parameter.EnvironmentParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.InitialImmigrationStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.DirectLarvalMortalityStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.landscape.LandscapeParameters;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.parameter.model.Category;
import com.ixcode.framework.parameter.model.ParameterMapDebug;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

/**
 * Description : ${CLASS_DESCRIPTION}
 *
 * @todo implement subclasses of the Plan and parameters with specific getters and setters
 */
public class Experiment1bPlanFactory {



    private Experiment1bPlanFactory() {
    }


    public static ExperimentPlan createExperiment1bPlanTrialA() {
         ExperimentPlan plan = Experiment1bPlanFactory.createDefaultPlan("exp01b");

         Experiment1bParameters.addManipulationsTrialA(plan);

         return plan;
     }


    public static ExperimentPlan createExperiment1bPlanDebug() {
        ExperimentPlan plan = Experiment1bPlanFactory.createDefaultPlan("exp01b");


        Experiment1bParameters.addManipulationsTrialDebug(plan);

        return plan;
    }


    public static ExperimentPlan createExperiment1bPlanTrialB() {
            ExperimentPlan plan = Experiment1bPlanFactory.createDefaultPlan("exp01b");


            Experiment1bParameters.addManipulationsTrialB(plan);

            return plan;
        }




    private static ExperimentPlan createDefaultPlan(String name) {
        ExperimentPlan plan = new ExperimentPlan(name);

//        SimulationCategory.addSimulationParameters(plan);
        Category environment = EnvironmentParameters.addEnvironmentParameters(plan);
        LandscapeParameters.addLandscapeParameters(environment, 100000);



        StrategyDefinitionParameter limitedAge = ButterflyParameters.createLimitedAgeMortality(20);
        StrategyDefinitionParameter directLarval = DirectLarvalMortalityStrategyDefinition.createDefaultStrategyS();
        StrategyDefinitionParameter initialImmigrationS = InitialImmigrationStrategyDefinition.createDefaultStrategyS();
        ButterflyParameters.addButterflyAgentParameters(plan, limitedAge,directLarval,  initialImmigrationS);

        SimulationCategory.setArchiveRemovedAgents(plan.getParameterTemplate(), true);

        if (BugsimMain.isDebug()) {
            ParameterMapDebug.debugParams(plan.getParameterTemplate());
        }
        return plan;
    }


}
