/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.server;

import com.ixcode.framework.experiment.model.ExperimentController;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.IExperiment;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 21, 2007 @ 4:33:14 PM by jim
 */
public class BugsimSocketProtocol {
    public static final String CMD_REPORT_STATUS = "reportStatus";
    public static final String CMD_PARAMETER_SUMMARY = "parameterSummary";
    public static final String CMD_EXPERIMENT_DESCRIPTION = "description";
    public static final String CMD_EXPERIMENT_ID = "experimentId";
    public static final String CMD_EXIT = "exit";

    public BugsimSocketProtocol(ExperimentController experimentController) {
        _experimentController = experimentController;
    }

    public String processInput(String command) {
        String response = "UNKOWN COMMAND";
        if (command.equals(CMD_REPORT_STATUS)) {
            ExperimentProgress progress = ExperimentController.createExperimentProgress(_experimentController);
            response = "STATUS[" + _experimentController.getExperimentCompleteId() + " : process " + _experimentController.getMultipleProcessController() + "]  : " + _experimentController.getExperimentState() + ", " + progress;
        } else if (command.equals(CMD_PARAMETER_SUMMARY)) {
            response = "SUMMARY[" + _experimentController.getExperimentCompleteId() +" : process " + _experimentController.getMultipleProcessController() + "] : " + _experimentController.getExperiment().getParameterSummary();
        } else if (command.equals(CMD_EXPERIMENT_ID)) {
            response= _experimentController.getExperimentCompleteId();
        } else if (command.equals(CMD_EXPERIMENT_DESCRIPTION)) {
            response = _experimentController.getExperiment().getExperimentPlan().getDescription();
        }   else if (command.equals(CMD_EXIT)) {
            System.exit(0);
        }
        return response;
    }

    private ExperimentController _experimentController;
}
