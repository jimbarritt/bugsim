/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.player;

import com.ixcode.bugsim.controller.experiment.IExperiment;
import com.ixcode.framework.swing.SwingWorker;
import com.ixcode.framework.swing.action.ActionBase;

import java.awt.event.ActionEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class RunExperimentAction extends ActionBase {

    public RunExperimentAction(IExperiment experiment, ExperimentPlayerFrame parent) {
        super("Run");
        _experiment = experiment;
        _player = parent;
    }

    public void actionPerformed(ActionEvent e) {


//        System.out.println("Next Timestep is  " + nextTimestep);
       _player.updateExperiment();
        final SwingWorker worker = new SwingWorker() {
            public Object construct() {
                _player.runLoop();
                return _experiment;
            }
        };
        worker.start();
    }

    private IExperiment _experiment;
    private ExperimentPlayerFrame _player;
}
