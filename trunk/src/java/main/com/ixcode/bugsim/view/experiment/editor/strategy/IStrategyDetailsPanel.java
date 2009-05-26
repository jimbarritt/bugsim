/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.strategy;

import com.ixcode.framework.parameter.model.StrategyDefinition;

import javax.swing.*;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Feb 6, 2007 @ 4:29:09 PM by jim
 */
public interface IStrategyDetailsPanel {
    String getStrategyClassName();

    void setStrategyDefinition(StrategyDefinition strategyDefinition);

    StrategyDefinition getStrategyDefinition();

    Class getStrategyClass();

    boolean hasStrategy();

    JComponent getDisplayComponent();
}
