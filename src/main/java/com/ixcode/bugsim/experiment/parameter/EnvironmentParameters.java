/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.parameter;

import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.parameter.model.Category;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class EnvironmentParameters {
    public static Category addEnvironmentParameters(ExperimentPlan plan) {
        Category environment = new Category(C_ENVIRONMENT);
        plan.getParameterTemplate().addCategory(environment);

        return environment;

    }

    public static final String C_ENVIRONMENT = "environment";
}
