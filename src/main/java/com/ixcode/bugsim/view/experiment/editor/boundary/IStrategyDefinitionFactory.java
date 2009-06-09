/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.boundary;

import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.framework.parameter.model.StrategyDefinition;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Jan 31, 2007 @ 6:37:37 PM by jim
 */
public interface IStrategyDefinitionFactory {

    

    StrategyDefinition createDefaultStrategyDefinition(String className, IParameterMapLookup parameterMapLookup);
}
