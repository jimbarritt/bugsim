/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment;

import com.ixcode.framework.experiment.model.ExperimentController;
import com.ixcode.framework.swing.action.ActionBase;

import java.awt.event.ActionEvent;

/**
 *  Description : Any Action which is connected to the controller should derive from here.
 */
public abstract class ExperimentControllerActionBase extends ActionBase {

    public ExperimentControllerActionBase(String name, ExperimentController controller) {
        super(name);
        _controller = controller;
    }

    public final void actionPerformed(ActionEvent e) {
        executeControllerAction(_controller);
    }

    protected abstract void executeControllerAction(ExperimentController controller);

    private ExperimentController _controller;
}
