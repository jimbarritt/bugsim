package com.ixcode.framework.experiment.model;

import com.ixcode.framework.experiment.model.report.ExperimentReportController;
import com.ixcode.framework.experiment.model.report.IExperimentReporter;
import com.ixcode.framework.model.ModelBase;
import com.ixcode.framework.util.MemoryStats;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.bugsim.BugsimMain;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.Preferences;


/**
 * Main control centre of the experiment.
 *
 * @todo implement a "Run Until Condition" so that the experiment can pause the simulation - say when at the end of a single butterflies life...
 */
public class ExperimentController extends ModelBase {


    public static final String PROPERTY_STATE = "state";
    public static final String PROPERTY_MAX_TIMESTEPS = "maxStepsToExecute";
    public static final String PROPERTY_OUTPUT_RESULTS = "outputResults";
    public static final String PROPERTY_OUTPUT_ROOT_PATH = "outputRootPath";
    public static final String PROPERTY_TIMESTEP_DELAY = "timestepDelay";
    public static final String PROPERTY_EXPERIMENT_OUTPUT_DIR = "experimentOutputDir";
    public static final String PROPERTY_OUTPUT_ITERATION_DETAILS = "outputIterationDetails";


    public ExperimentController(IExperiment experiment, MultipleProcessController multipleProcessController) {
        _experiment = experiment;
        _experimentState = ExperimentState.NOT_INITIALISED;
        _reportController = new ExperimentReportController();
        _exceptionHandler = new DefaultExceptionHandler();
        _multipleProcesscontroller = multipleProcessController;

    }

    public static String getDefaultOutputPath() {
        Preferences prefs = Preferences.userNodeForPackage(ExperimentController.class);
        return prefs.get(PROPERTY_OUTPUT_ROOT_PATH, createTempPath());
    }

    public static boolean getDefaultOutputIterationDetails() {
        Preferences prefs = Preferences.userNodeForPackage(ExperimentController.class);
        return prefs.getBoolean(PROPERTY_OUTPUT_ITERATION_DETAILS, true);
    }


    public static void setDefaultOutputIterationDetails(boolean output) {
        Preferences prefs = Preferences.userNodeForPackage(ExperimentController.class);
        prefs.putBoolean(PROPERTY_OUTPUT_ITERATION_DETAILS, output);
    }

    public static void setDefaultOutputPath(String path) {
        Preferences prefs = Preferences.userNodeForPackage(ExperimentController.class);
        prefs.put(PROPERTY_OUTPUT_ROOT_PATH, path);
    }

    public void pause() {
        if (log.isDebugEnabled()) {
            log.debug("Pause");
        }
        setExperimentState(ExperimentState.PAUSED);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                collectGarbage();
            }
        });
    }

    private void setExperimentState(ExperimentState newState) {
        ExperimentState oldState = _experimentState;
        _experimentState = newState;
        super.firePropertyChangeEvent(PROPERTY_STATE, oldState, newState);
    }

    public void run() {

        run(_maxStepsToExecute);
    }

    public IExperiment getExperiment() {
        return _experiment;
    }


    private void run(final long stepsToExecute) {
        if (log.isDebugEnabled()) {
            log.debug("Run");
        }

        Runnable task = new Runnable() {
            public void run() {
                runExperiment(stepsToExecute);
            }
        };
        Thread t = new Thread(task, "Experiment-Task-Thread");
        t.start();
    }

    public long getMaxStepsToExecute() {
        return _maxStepsToExecute;
    }

    private void runExperiment(long stepsToExecute) {
        setExperimentState(ExperimentState.RUNNING);


        long startTime = System.currentTimeMillis();
        long repStartTime=startTime;
        while (_experimentState == ExperimentState.RUNNING) {
            try {
                if (_experiment.isIterationComplete()) {
                    reportResults();
                    if (getCurrentReplicate() >= getReplicantCount()) {
                        _replicateTime = 0;
                        repStartTime = System.currentTimeMillis();

                        long lastReplicate = getCurrentReplicate();
                        setCurrentReplicate(1); // need to do this so it is correct duting nextIteration()
                        boolean isExperimentComplete = nextIteration();
                        if (isExperimentComplete) { // we won't know its complete until after we exectue nextIteration ... could be tidied up...
                            setCurrentReplicate(lastReplicate); // so its correct for reporting - dont need to set it back because we wont be runnin no mo.
                            complete();
                            break; // this is because this check is at the beginning of the loop.
                        } else {

                        }
                    } else {
                        _replicateTime = 0;
                        repStartTime = System.currentTimeMillis();

                        setCurrentReplicate(getCurrentReplicate() + 1);
                        _experiment.initialiseIteration(createExperimentProgress(this), _experimentIterator.getCurrentParameters());
                        _iterationTimesteps = 0;

                    }
                }

                try {
                    _experiment.executeTimestep();
                } catch (Throwable t) {
                    _exceptionHandler.handle(t);
                }

                Thread.currentThread().sleep(_timestepDelay);

            } catch (InterruptedException e) {
                if (log.isInfoEnabled()) {
                    log.info("Thread Interrupted!");
                }
            }

            _timestepsSinceLastReset++;
            _executedTimesteps++;
            _iterationTimesteps++;
            long currentTime = System.currentTimeMillis();
            _elapsedTime += currentTime - startTime;
            _replicateTime += currentTime-repStartTime;
            startTime = currentTime;
            repStartTime = currentTime;

            fireProgressEvents();

            processAutopause(stepsToExecute);

        }


    }

    private void processAutopause(long stepsToExecute) {
        if (_isAutopauseOn && _executedTimesteps >= stepsToExecute) {

//            if (!_experiment.isIterationComplete()) {  //If this is here it doesnt pause at the end of the iteration!
            if (log.isDebugEnabled()) {
                log.debug("Autopause at " + _executedTimesteps + " out of " + stepsToExecute + " timesteps.");
            }
            pause();
//            }
            _executedTimesteps = 0;
        }
    }

    public boolean isAutopauseOn() {
        return _isAutopauseOn;
    }

    public void setAutopauseOn(boolean autopauseOn) {
        _isAutopauseOn = autopauseOn;
    }


    private void reportResults() {
        try {
            if (_outputResults) {
                ExperimentState currentState = getExperimentState();
                setExperimentState(ExperimentState.REPORTING);
                ExperimentProgress progress = createExperimentProgress(this);
                boolean pramStructureChanged = _currentReplicant > 1 ? false : _experimentIterator.isParameterStructureChanged();
                _reportController.reportResults(_experimentOutputDir.getAbsolutePath(), _experimentId, _experiment, _experimentIterator.getCurrentParameters(), progress, pramStructureChanged, _outputIterationDetails);
                setExperimentState(currentState);


            }
        } catch (IOException e) {
            _exceptionHandler.handle(e);
        }
    }

    private void complete() {
        setExperimentState(ExperimentState.COMPLETE);
        if (log.isInfoEnabled()) {
            log.info("Experiment is Complete!");
        }
        fireProgressEvents();

        BugsimMain.execCompleteScript(this);
    }


    public boolean nextIteration() {
        collectGarbage();

        boolean isExperimentComplete = false;
        if (_experimentIterator.hasNextIteration()) {
            _experimentIterator.nextIteration();
//            if (log.isInfoEnabled()) {
//                log.info("Initialising Iteration: " + _experimentIterator.getCurrentIteration() + ", replicant: " + _currentReplicant);
//            }
            _iterationTimesteps = 0;
            _experiment.initialiseIteration(createExperimentProgress(this), _experimentIterator.getCurrentParameters());

        } else {
            isExperimentComplete = true;
        }
        return isExperimentComplete;
    }

    public void fireProgressEvents() {
        for (Iterator itr = _progressListeners.iterator(); itr.hasNext();) {
            ProgressListenerMapping mapping = (ProgressListenerMapping)itr.next();
            mapping.notify(this, _timestepsSinceLastReset);
        }
    }

    public void step() {
        if (log.isDebugEnabled()) {
            log.debug("Step");
        }
        run(1);
    }

    public void reset() {
        collectGarbage();

        _experiment.initialiseExperiment(_experiment.getExperimentPlan());
        _experimentIterator = new ExperimentIterator(_experiment.getExperimentPlan(), _multipleProcesscontroller);

        setExperimentOutputDir(createExperimentOutputPath());

        openLogFile(getExperimentOutputDir());

        _executedTimesteps = 0;
        _timestepsSinceLastReset = 0;
        _elapsedTime = 0;
        _replicateTime = 0;
        _currentReplicant = 1;

        if (log.isInfoEnabled()) {
            log.info("Initialised Experiment: ITRCOUNT=" + _experimentIterator.getIterationCount() + ", REPCOUNT=" + _experiment.getNumberOfReplicates());
        }

        nextIteration();


        setExperimentState(ExperimentState.READY);
        fireProgressEvents();
    }


    private void openLogFile(File experimentDir) {
        if (_log4JAppender == null) {
            File logFile = new File(new File("logs"), experimentDir.getName() + ".log");

            if (log.isInfoEnabled()) {
                log.info("Log File output is being redirected to '" + logFile.getAbsolutePath() + "'");
            }
            Appender a = Logger.getRootLogger().getAppender(_startupLogAppenderName);
            if (a == null) {
                _exceptionHandler.handle(new IOException("No appender called '" + _startupLogAppenderName + "' found to detatch from."));
            }
            Logger.getRootLogger().removeAppender(a);


            try {
                String pattern = "%d{dd-MM-yyyy hh:mm:ss} [%c{2}] %p - %m%n";
                PatternLayout layout = new PatternLayout(pattern);

                _log4JAppender = new RollingFileAppender(layout, logFile.getAbsolutePath(), false);
                Logger.getRootLogger().addAppender(_log4JAppender);

                if (log.isInfoEnabled()) {
                    log.info("Log output redirected from startup log.");
                }
            } catch (IOException e) {
                _exceptionHandler.handle(e);
            }
        }
    }

    public void setStartupLogAppenderName(String startupLogAppenderName) {
        _startupLogAppenderName = startupLogAppenderName;
    }

//    private void closeLogFile() {
//        if (_log4JAppender != null) {
//            Logger.getRootLogger().removeAppender(_log4JAppender);
//            _log4JAppender = null;
//        }
//
//    }

    private void collectGarbage() {
        Runtime r = Runtime.getRuntime();
        if (log.isTraceEnabled()) {
            log.trace("Collectiong garbage... " + _memStat.getSummary());
        }
        r.gc();
        if (log.isTraceEnabled()) {
            log.trace("Garbage collected  ... " + _memStat.getSummary());
        }
    }

    public String getExperimentCompleteId() {

        return _experiment.getOutputDirectoryName() + "-" + _experiment.getExperimentPlan().getName() + "-" + _experiment.getExperimentPlan().getTrialName() + "-" + _experimentId;
    }

    private File createExperimentOutputPath() {
        int id = 1;
        String idFormatted = FORMAT.format(id);

        File candidate = new File(createExperimentPathName(_outputRootPath, _experiment.getOutputDirectoryName(), _experiment.getExperimentPlan().getName(), _experiment.getExperimentPlan().getTrialName(), idFormatted));

        int tries = 0;
        while (directoryIsFull(candidate) && tries++ < _maxExperimentDirs) {
            idFormatted = FORMAT.format(++id);
            candidate = new File(createExperimentPathName(_outputRootPath, _experiment.getOutputDirectoryName(), _experiment.getExperimentPlan().getName(), _experiment.getExperimentPlan().getTrialName(), idFormatted));
        }
        if (tries >= _maxExperimentDirs) {
            _exceptionHandler.handle(new IOException("Exceeded 20 directories for this experiment (" + candidate + "), please delete some!"));
        }
        _experimentId = idFormatted;


        return candidate;

    }

    /**
     * Checks to see if this directory exists and has none of our iterations in it...
     *
     * @param candidate
     * @return
     */
    private boolean directoryIsFull(File candidate) {
        boolean full = false;

        if (candidate.exists()) {
            File[] subdirs = candidate.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.isDirectory() && pathname.getName().startsWith("Iteration-");
                }
            });

            if (subdirs.length > 0) {
                for (int i = 0; i < subdirs.length; ++i) {
                    String name = subdirs[i].getName();
                    int iteration = Integer.parseInt(name.substring(name.lastIndexOf("-")+1));
                    if (_experimentIterator.containsIterationIndex(iteration - 1)) {
                        full = true;
                        break;
                    }
                }
            }
        }


        return full;
    }

    /**
     * @param rootPath
     * @param id
     * @return
     * @todo refactor name creation to a seperate class.
     */
    private String createExperimentPathName(String rootPath, String directoryName, String planName, String trialName, String id) {
        String t = (trialName.length() == 0) ? "" : "-" + trialName;
        return rootPath + File.separator + directoryName + "-" + planName + t + "-" + id + File.separator;
    }


    public int getMaxExperimentDirs() {
        return _maxExperimentDirs;
    }

    public void setMaxExperimentDirs(int maxExperimentDirs) {
        this._maxExperimentDirs = maxExperimentDirs;
    }


    public ExperimentState getExperimentState() {
        return _experimentState;
    }


    public void addProgressListener(IExperimentProgressListener listener, int frequency) {
        _progressListeners.add(new ProgressListenerMapping(listener, frequency));
        ExperimentProgress progress = createExperimentProgress(this);
        listener.progressNotification(progress);
    }

    private static class ProgressListenerMapping {

        public ProgressListenerMapping(IExperimentProgressListener listener, int frequency) {
            _listener = listener;
            _frequency = frequency;
        }

        public void notify(ExperimentController experimentController, long timestepsSinceLastReset) {
            if ((timestepsSinceLastReset % _frequency) == 0) {
                ExperimentProgress progress = ExperimentController.createExperimentProgress(experimentController);
                _listener.progressNotification(progress);
            }
        }

        private IExperimentProgressListener _listener;
        private int _frequency;
    }

    public static ExperimentProgress createExperimentProgress(ExperimentController c) {
        ExperimentProgress progress = new ExperimentProgress(c.getExecutedTimesteps(),
                c.getTimestepsSinceLastReset(), c.getCurrentIteration(),
                c.getIterationTimesteps(), c.getElapsedTime(),c.getReplicateTime(),
                c.getCurrentReplicate(), c.getIterationCount(), c.getReplicantCount(), c.getExperimentState());
        c.getExperiment().reportProgress(progress);
        return progress;

    }


    private long getReplicantCount() {
        return _experiment.getNumberOfReplicates();
    }

    private long getIterationCount() {
        return _experimentIterator.getIterationCount();
    }

    private long getElapsedTime() {
        return _elapsedTime;
    }

    private long getReplicateTime() {
            return _replicateTime;
        }



    private long getIterationTimesteps() {
        return _iterationTimesteps;
    }

    private int getCurrentIteration() {
        return _experimentIterator.getCurrentIteration();
    }

    private long getExecutedTimesteps() {
        return _executedTimesteps;
    }

    public long getTimestepsSinceLastReset() {
        return _timestepsSinceLastReset;
    }


    public boolean isOutputResults() {
        return _outputResults;
    }

    public void setOutputResults(boolean outputResults) {
        boolean oldValue = _outputResults;
        _outputResults = outputResults;
        super.firePropertyChangeEvent(PROPERTY_OUTPUT_RESULTS, new Boolean(oldValue), new Boolean(_outputResults));
    }

    public String getOutputRootPath() {
        return _outputRootPath;
    }

    public void setOutputRootPath(String outputRootPath) {
        if (outputRootPath != null) {
            String oldVal = _outputRootPath;
            _outputRootPath = outputRootPath;
            setDefaultOutputPath(_outputRootPath);
            super.firePropertyChangeEvent(PROPERTY_OUTPUT_ROOT_PATH, oldVal, _outputRootPath);
        }
    }

    private static String createTempPath() {
        String path = new File(".").getAbsolutePath();
        try {
            path = File.createTempFile("blah", "blah").getParentFile().getAbsolutePath();
        } catch (IOException e) {
            log.error("Trying to create temp path", e);
        }
        return path;
    }

    public void setExceptionHandler(IExceptionHandler exceptionHandler) {
        _exceptionHandler = exceptionHandler;
    }

    public long getTimestepDelay() {
        return _timestepDelay;
    }

    public void setTimestepDelay(long timestepDelay) {
        _timestepDelay = timestepDelay;
    }

    public void setMaxStepsToExecute(long maxStepsToExecute) {
        _maxStepsToExecute = maxStepsToExecute;
    }

    public long getCurrentReplicate() {
        return _currentReplicant;
    }

    private void setCurrentReplicate(long currentReplicant) {
        _currentReplicant = currentReplicant;
    }

    public File getExperimentOutputDir() {
        return _experimentOutputDir;
    }

    public ParameterMap getCurrentParameterMap() {
        if (_experimentIterator != null) {
            return _experimentIterator.getCurrentParameters();
        } else {
            return null;
        }
    }

    private void setExperimentOutputDir(File experimentOutputDir) {
        File oldVal = _experimentOutputDir;
        _experimentOutputDir = experimentOutputDir;
        super.firePropertyChangeEvent(PROPERTY_EXPERIMENT_OUTPUT_DIR, oldVal, _experimentOutputDir);
    }


    public boolean isOutputIterationDetails() {
        return _outputIterationDetails;
    }

    public String getExperimentId() {
        return _experimentId;
    }

    public void setOutputIterationDetails(boolean outputIterationDetails) {
        Boolean oldVal = new Boolean(_outputIterationDetails);
        _outputIterationDetails = outputIterationDetails;
        ExperimentController.setDefaultOutputIterationDetails(outputIterationDetails);
        super.firePropertyChangeEvent(PROPERTY_OUTPUT_ITERATION_DETAILS, oldVal, new Boolean(_outputIterationDetails));
    }

    public MultipleProcessController getMultipleProcessController() {
        return _multipleProcesscontroller;
    }

    private static final Logger log = Logger.getLogger(ExperimentController.class);

    private IExperiment _experiment;


    private ExperimentIterator _experimentIterator;

    private ExperimentState _experimentState;

    private long _maxStepsToExecute = 1000000;

    private long _executedTimesteps;
    private long _timestepsSinceLastReset;

    private List _progressListeners = new ArrayList();


    private long _timestepDelay = 1;
    private long _iterationTimesteps;

    private boolean _outputResults = true;
    private boolean _outputIterationDetails = true;
    private String _outputRootPath = ".";
    private File _experimentOutputDir;

    private IExperimentReporter _reportController;
    private IExceptionHandler _exceptionHandler;
    private int _elapsedTime;
    private long _replicateTime;

    private String _experimentId;

    private int _maxExperimentDirs = 40;
    private NumberFormat FORMAT = new DecimalFormat("000");

    private long _currentReplicant;
    private MemoryStats _memStat = new MemoryStats(Runtime.getRuntime());

    private boolean _isAutopauseOn = true;
    private RollingFileAppender _log4JAppender;
    private String _startupLogAppenderName;
    private MultipleProcessController _multipleProcesscontroller;

}
 
