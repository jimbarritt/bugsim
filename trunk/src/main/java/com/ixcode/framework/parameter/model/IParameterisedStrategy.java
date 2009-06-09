package com.ixcode.framework.parameter.model;

import java.util.Map;


/**
 * @todo remove dependancy on SImulation!!
 */
public interface IParameterisedStrategy {


    void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects);

    String getParameterSummary();
}
 
