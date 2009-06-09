/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.player;

import com.ixcode.bugsim.controller.experiment.IExperiment;
import com.ixcode.framework.swing.action.ActionBase;

import java.awt.event.ActionEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class PauseExperimentAction extends ActionBase {

    public PauseExperimentAction(ExperimentPlayerFrame player) {
        super("Pause");
        _experiment = player.getExperiment();
        _player = player;
    }

    public void actionPerformed(ActionEvent e) {

        _player.pauseExperiment();

    }

    private IExperiment _experiment;
    private ExperimentPlayerFrame _player;
}
