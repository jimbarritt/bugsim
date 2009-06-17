/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experimentX;

import com.ixcode.bugsim.model.experiment.parameter.*;
import com.ixcode.framework.experiment.model.*;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.model.*;

import java.lang.reflect.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentXFactory implements ILoadedExperimentFactory {

    public ExperimentXFactory() {
    }

    public IExperiment createExperiment(ExperimentPlan plan) {
        Simulation simulation = new Simulation();
        return new ExperimentX(simulation, plan);
    }

    public IExperiment createExperiment(String trialName) {
        Simulation simulation = new Simulation();
        ExperimentPlan plan = createDefaultPlan();

        addManipulations(ExperimentXTrials.class,  trialName, plan);

        return new ExperimentX(simulation, plan);
    }

    public static void addManipulations(Class trialsClass, String trialCode, ExperimentPlan plan) {
        String methodName = ExperimentXFactory.METHOD_STUB +  getTrialName(trialCode);
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

    private static String getTrialName(String trialName) {
        return trialName.substring(trialName.indexOf("Tr")+2);
    }

    private static final String METHOD_STUB ="addManipulationsTrial";

    public static final String METHOD_DEFAULT_FACTORY = "createDefaultPlan";

    public static ExperimentPlan createDefaultPlan() {
        ExperimentPlan plan = new ExperimentPlan(ExperimentXFactory.EXP_X_NAME);

        ParameterMap params = plan.getParameterTemplate();

        BugsimParameterMap.addDefaultParameters(params);

        return plan;
    }



    public static final String EXP_X_NAME = "expX";

}
