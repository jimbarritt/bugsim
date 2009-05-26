/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

import com.ixcode.framework.experiment.model.ExperimentPlan;

public interface IParameterManipulation {

    void configure(ParameterMap currentParameters);


    boolean isParameterStructureChanged();

    void rewireParameterReferences(ParameterMap parameterMap);

    ParameterMap getParameterMap();

    void setParent(ExperimentPlan experimentPlan);
}
