/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.strategy;

import com.ixcode.framework.parameter.model.IStrategyDefinitionContainer;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Feb 1, 2007 @ 4:42:56 PM by jim
 */
public interface IStrategyParameterEditor {

    /**
     * Uses IParameterContainer because it could be either a Category or a Strategy Definition which contains the
     * Parameter
     * @param parent
     * @param parameterName - the name of the parameter which contains the strategy.
     *
     */
    void bind(IStrategyDefinitionContainer strategyContainer, String parameterName);

    void unbind();


    IStrategyDefinitionContainer getStrategyContainer();

}
