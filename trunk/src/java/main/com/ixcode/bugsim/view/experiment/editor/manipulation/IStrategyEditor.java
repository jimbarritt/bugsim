/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

import javax.swing.*;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Mar 14, 2007 @ 9:32:33 PM by jim
 */
public interface IStrategyEditor {


    JComponent getEditingComponent();



    void setStrategyDefinitionParameter(StrategyDefinitionParameter strategyS);

    StrategyDefinitionParameter getStrategyDefinitionParameter();
}
