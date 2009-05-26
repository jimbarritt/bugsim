/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout;

import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 26, 2007 @ 7:17:03 PM by jim
 */
public abstract class ResourceLayoutPanelBase extends StrategyDetailsPanel {

    protected ResourceLayoutPanelBase(IModelAdapter modelAdapter, Class strategyClass) {
        super(modelAdapter, strategyClass);
    }

    
}
