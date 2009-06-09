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
public class ResetExperimentAction extends ActionBase {

    public ResetExperimentAction(ExperimentPlayerFrame player) {
        super("Reset");
        _experiment = player.getExperiment();
        _player = player;

    }

    public void actionPerformed(ActionEvent e) {


        _player.reset();

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
