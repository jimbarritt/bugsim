/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment1a;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.model.experiment.parameter.EnvironmentParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.landscape.LandscapeParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.IExperimentFactory;
import com.ixcode.framework.parameter.model.Category;
import com.ixcode.framework.parameter.model.ParameterMapDebug;
import com.ixcode.framework.simulation.model.Simulation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class Experiment1aFactory implements IExperimentFactory {



    public IExperiment createExperiment(String trialName) {


        Simulation simulation = new Simulation();
        ExperimentPlan plan = createDefaultPlan(Experiment1aFactory.EXP_1A_NAME);

        addManipulations(Experiment1aTrials.class,  trialName, plan);


        return new Experiment1a(simulation, plan);


    }

    private void addManipulations(Class trialsClass, String trialCode, ExperimentPlan plan) {
        String methodName = METHOD_STUB +  getTrialName(trialCode);
        try {
            Method m = trialsClass.getDeclaredMethod(methodName, new Class[]{ExperimentPlan.class});
            m.invoke(trialsClass, new Object[] {plan});
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    private String getTrialName(String trialName) {
        return trialName.substring(trialName.indexOf("Tr")+2);
    }

    private static final String METHOD_STUB ="addManipulationsTrial";

    public static ExperimentPlan createDefaultPlan(String name) {
        ExperimentPlan plan = new ExperimentPlan(name);

//        SimulationCategory.addSimulationParameters(plan);
        Category environment = EnvironmentParameters.addEnvironmentParameters(plan);
        LandscapeParameters.addLandscapeParameters(environment, 400);
        CabbageParameters.addCabbageParameters(environment);
        ButterflyParameters.addButterflyAgentParameters(plan);


        if (BugsimMain.isDebug()) {
            ParameterMapDebug.debugParams(plan.getParameterTemplate());
        }
        return plan;
    }

    public static final String EXP_1A_NAME = "exp01a";
}
