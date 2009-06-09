/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.simulation.experiment.ExperimentTemplateRegistry;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class FakePlanTemplate {


    public static final String TEMPLATE_NAME = "expX-trX";
    public static final String TEMPLATE_DESCRIPTION = "Test Templat for JUnit";
    public static final String TEMPLATE_LONG_DESCRIPTION = "Used for unit testing only";
    public static final String TEMPLATE_FACTORY_METHOD = "createDefaultPlan";
    public static final String TEMPLATE_CONFIGURATION_METHOD = "configurePlan";

    public static void registerTemplate() {
        ExperimentTemplateRegistry REGISTRY = ExperimentTemplateRegistry.getInstance();
        REGISTRY.registerTemplate(TEMPLATE_NAME, TEMPLATE_DESCRIPTION, TEMPLATE_LONG_DESCRIPTION, FakePlanTemplate.class, TEMPLATE_FACTORY_METHOD, FakePlanTemplate.class,  TEMPLATE_CONFIGURATION_METHOD);
    }

    public static ExperimentPlan createDefaultPlan() {
        return new ExperimentPlan("Test Plan");
    }

    public static void configurPlan(ExperimentPlan plan) {

    }

}
