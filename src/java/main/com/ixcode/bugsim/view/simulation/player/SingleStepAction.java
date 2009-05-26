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
public class SingleStepAction extends ActionBase {

    public SingleStepAction(ExperimentPlayerFrame player) {
        super("Step");
        _player = player;
        _experiment = player.getExperiment();
    }

    public void actionPerformed(ActionEvent e) {
        _player.updateExperiment();
        _experiment.runFor(1);
//        final SwingWorker worker = new SwingWorker() {
//            public Object construct() {
//                _experiment.run();
//                return _experiment;
//            }
//        };
//        worker.start();
    }

    private IExperiment _experiment;
    private ExperimentPlayerFrame _player;
}
