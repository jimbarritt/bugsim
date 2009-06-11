/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim;

import com.ixcode.bugsim.model.experiment.experimentX.*;
import com.ixcode.bugsim.view.experiment.experimentX.*;
import com.ixcode.framework.experiment.model.*;
import com.ixcode.framework.util.*;
import org.apache.log4j.*;

import java.util.*;

/**
 * Description : These are all the arguments that can be provided to bugsim.
 * 
 */
public class BugsimMainArgs extends ArgsBase {

    private static final String ARG_INTERACTIVE_MODE = "interactive";
    private static final String ARG_EDIT_PLAN = "editPlan";
    private static final String ARG_EXPERIMENT_FACTORY = "exp";
    private static final String ARG_PROGRESS_PANEL_FACTORY = "prog";
    private static final String ARG_LANDSCAPE_VIEW_FACTORY = "lscp";
    private static final String ARG_OUTPUT_PATH = "output";
    private static final String ARG_OUTPUT_ITERATION_DETAILS = "outputItrDetails";
    private static final String ARG_TRIAL_NAME = "trial";
    private static final String ARG_PLAN_FILE_NAME = "planFile";
    private static final String ARG_STATUS_SERVER = "statusServer";
    private static final String ARG_MULTIPLE_PROCESS = "multipleProcess";

    private static Map DEFAULTS = new HashMap();

    static {
        DEFAULTS.put(ARG_INTERACTIVE_MODE, "true");
        DEFAULTS.put(ARG_EDIT_PLAN, "false");
        DEFAULTS.put(ARG_EXPERIMENT_FACTORY, ExperimentXFactory.class.getName());
        DEFAULTS.put(ARG_PROGRESS_PANEL_FACTORY, ExperimentXProgressFactory.class.getName());
        DEFAULTS.put(ARG_LANDSCAPE_VIEW_FACTORY, ExperimentXLandscapeViewFactory.class.getName());
        DEFAULTS.put(ARG_OUTPUT_PATH, ExperimentController.getDefaultOutputPath());
        DEFAULTS.put(ARG_OUTPUT_ITERATION_DETAILS, "" + ExperimentController.getDefaultOutputIterationDetails());
        DEFAULTS.put(ARG_TRIAL_NAME, "");
        DEFAULTS.put(ARG_PLAN_FILE_NAME, "");
        DEFAULTS.put(ARG_STATUS_SERVER, "true");
        DEFAULTS.put(ARG_MULTIPLE_PROCESS, "1of1");
    }

    public BugsimMainArgs(String[] args) {
        super(args, DEFAULTS);
        if (log.isInfoEnabled()) {
            log.info("Initialising with arguments: ");
            log.info("(" + ARG_INTERACTIVE_MODE + ")  Interactive Mode : " + isInteractiveMode());
            log.info("(" + ARG_EDIT_PLAN + ")  Edit the plan before starting : " + isEditPlan());
            log.info("(" + ARG_OUTPUT_PATH + ")  Path                  : " + getOutputPath());
            log.info("(" + ARG_EXPERIMENT_FACTORY + ")  Experiment Factory       : " + getExperimentFactoryName());
            log.info("(" + ARG_PROGRESS_PANEL_FACTORY + ") Progress Panel Factory   : " + getProgressPanelFactoryName());
            log.info("(" + ARG_LANDSCAPE_VIEW_FACTORY + ") Landscape View Factory   : " + getLandscapeViewFactoryName());
            log.info("(" + ARG_OUTPUT_ITERATION_DETAILS + ") Output iteration details  : " + isOutputIterationDetails());
            log.info("(" + ARG_TRIAL_NAME + ") Trial Name  : " + getTrialName());
            log.info("(" + ARG_PLAN_FILE_NAME + ") Plan File Name  : " + getExperimentPlanFileName());
            log.info("(" + ARG_STATUS_SERVER + ") Status Server : " + getStatusServer());
            log.info("(" + ARG_MULTIPLE_PROCESS + ") Multiple Process : " + getMultipleProcessController());
        }


    }



    protected void mapArgsToProperties(Map argMap) {
        boolean interactive = Boolean.valueOf((String)argMap.get(ARG_INTERACTIVE_MODE)).booleanValue();
        setIsInteractiveMode(interactive);
        boolean editPlan = Boolean.valueOf((String)argMap.get(ARG_EDIT_PLAN)).booleanValue();
        setIsEditPlan(editPlan);
        setOutputPath((String)argMap.get(ARG_OUTPUT_PATH));
        setExperimentFactoryName((String)argMap.get(ARG_EXPERIMENT_FACTORY));
        setProgressPanelFactoryName((String)argMap.get(ARG_PROGRESS_PANEL_FACTORY));
        setLandscapeViewFactoryName((String)argMap.get(ARG_LANDSCAPE_VIEW_FACTORY));
        boolean outputItr = Boolean.valueOf((String)argMap.get(ARG_OUTPUT_ITERATION_DETAILS)).booleanValue();
        setIsOutputIterationDetails(outputItr);
        setTrialName((String)argMap.get(ARG_TRIAL_NAME));
        setExperimentPlanFileName((String)argMap.get(ARG_PLAN_FILE_NAME));
        boolean statusServer = Boolean.valueOf((String)argMap.get(ARG_STATUS_SERVER)).booleanValue();
        setStatusServer(statusServer);
        MultipleProcessController processController = MultipleProcessController.parse((String)argMap.get(ARG_MULTIPLE_PROCESS));
        setMultipleProcessController(processController);

    }

    private void setMultipleProcessController(MultipleProcessController processController) {
        _processController = processController;
    }

    public MultipleProcessController getMultipleProcessController() {
        return _processController;
    }

    public String getExperimentPlanFileName() {
            return _planFileName;
        }

    public void setExperimentPlanFileName(String fileName) {
        _planFileName = fileName;
    }

    public String getTrialName() {
        return _trialName;
    }

    public void setTrialName(String trialName) {
        _trialName = trialName;
    }

    public boolean isOutputIterationDetails() {
            return _isOutputIterationDetails;
        }

    public void setIsOutputIterationDetails(boolean output) {
        _isOutputIterationDetails = output;
    }

    public boolean isEditPlan() {
        return _isEditPlan;
    }

    public void setIsEditPlan(boolean edit) {
        _isEditPlan = edit;
    }

    public String getOutputPath() {
        return _outputPath;
    }


    public void setOutputPath(String outputPath) {
        _outputPath = outputPath;
    }

    public String getExperimentFactoryName() {
        return _experiment;
    }

    private void setExperimentFactoryName(String experiment) {
        _experiment = experiment;
    }


    public String getProgressPanelFactoryName() {
        return _progress;
    }

    private void setProgressPanelFactoryName(String progress) {
        _progress = progress;
    }


    public String getLandscapeViewFactoryName() {
        return _landscapeView;
    }

    private void setLandscapeViewFactoryName(String landscapeView) {
        _landscapeView = landscapeView;
    }

    public boolean isInteractiveMode() {
        return _isInteractiveMode;
    }

    private void setIsInteractiveMode(boolean interactiveMode) {
            _isInteractiveMode = interactiveMode;
        }


    public boolean getStatusServer() {
        return _statusServer;
    }

    public void setStatusServer(boolean statusServer) {
        _statusServer = statusServer;
    }

    public String toString() {
        return "Bugsim Args: experiment=" + _experiment + ", progress=" + _progress + ", landscapeView=" + _landscapeView;
    }

    private static final Logger log = Logger.getLogger(BugsimMainArgs.class);
    private String _experiment;
    private String _progress;
    private String _landscapeView;

    private boolean _isInteractiveMode;
    private String _outputPath;
    private boolean _isOutputIterationDetails;
    private String _trialName;
    private boolean _isEditPlan;
    private String _planFileName;
    private boolean _statusServer;
    private MultipleProcessController _processController;
}
