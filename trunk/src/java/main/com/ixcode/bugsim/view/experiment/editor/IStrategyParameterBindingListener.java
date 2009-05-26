/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor;

import com.ixcode.framework.parameter.model.StrategyDefinition;

/**
 * Description : Notifies you when things happen to a strategy parameter
 * Created     : Jan 31, 2007 @ 6:28:07 PM by jim
 */
public interface IStrategyParameterBindingListener {

    void strategyChanged(StrategyDefinition oldValue, StrategyDefinition newValue);

}

