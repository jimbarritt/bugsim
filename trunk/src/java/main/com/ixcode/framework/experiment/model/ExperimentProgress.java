package com.ixcode.framework.experiment.model;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class ExperimentProgress {

    public ExperimentProgress(long executedTimesteps, long timestepsSinceLastReset,
                              int currentIteration, long iterationTimesteps, long elapsedTime, long replicateTime,
                              long currentReplicant,  long iterationCount, long replicantCount, ExperimentState state) {
        _executedTimesteps = executedTimesteps;
        _timestepsSinceLastReset = timestepsSinceLastReset;

        _currentIteration = currentIteration;
        _currentReplicant = currentReplicant;
        _iterationTimestepsExecuted = iterationTimesteps;
        _elapsedTime = elapsedTime;
        _replicantCount = replicantCount;
        _iterationCount = iterationCount;
        _experimentState = state;
        _replicateTime = replicateTime;
    }


    public long getExecutedTimesteps() {
        return _executedTimesteps;
    }

    public long getTimestepsSinceLastReset() {
        return _timestepsSinceLastReset;
    }

    public int getCurrentIteration() {
        return _currentIteration;
    }

    public long getIterationTimestepsExecuted() {
        return _iterationTimestepsExecuted;
    }

    public long getElapsedTime() {
        return _elapsedTime;
    }

    public long getCurrentReplicate() {
        return _currentReplicant;
    }

    public String getElapsedTimeFormatted() {
         return TIME_FORMAT.format(new Long(TWELVE_H + _elapsedTime));
    }

    public String getCurrentIterationFormatted() {
        int x = (_experimentState == ExperimentState.COMPLETE) ? 0 : 1;
        double percentage = ((double)(_currentIteration-x) / (double)_iterationCount) * 100;
        return _currentIteration + " of " + _iterationCount + "  (" + DP0.format(percentage) + "%)";
    }

    public String getCurrentReplicateFormatted() {
        return _currentReplicant + " of " + _replicantCount;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("(itr=").append(getCurrentIterationFormatted());
        sb.append(", rep=").append(getCurrentReplicateFormatted());
        sb.append(", steps=").append(_iterationTimestepsExecuted);
        sb.append(", time=").append(getElapsedTimeFormatted());
//        sb.append(",executedTimesteps=").append(_executedTimesteps);
//        sb.append(", timestepsSinceLastRest=").append(_timestepsSinceLastReset);

        if (_customProperties.size() > 0 ) {
            sb.append(", ");
        }
        for (Iterator itr = _customProperties.keySet().iterator(); itr.hasNext();) {
            String name = (String)itr.next();
            sb.append(name).append("=").append(_customProperties.get(name));
            if (itr.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    public long getReplicateCount() {
        return _replicantCount;
    }

    public long getIterationCount() {
        return _iterationCount;
    }

    public void addCustomProperty(String name, boolean value) {
        addCustomProperty(name, new Boolean(value));
    }
    public void addCustomProperty(String name, long value) {
        addCustomProperty(name, new Long(value));
    }

    public void addCustomProperty(String name, double value) {
        addCustomProperty(name, new Double(value));
    }

    public void addCustomProperty(String name, Object value) {
        _customProperties.put(name, value);
    }

    public long getElapsedTimeMS() {
        return _elapsedTime;
    }

    public long getReplicateTimeMS() {
        return _replicateTime;
    }


    private long _executedTimesteps;
    private long _timestepsSinceLastReset;
    private int _currentIteration;
    private long _currentReplicant;

    private long _iterationTimestepsExecuted;
    private long _elapsedTime;
    private static Format TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static long TWELVE_H = 43200000;
    public static final String PROPERTY_ELAPSED_TIME = "elapsedTime";
    public static final String PROPERTY_CURRENT_ITERATION = "currentIteration";
    public static final String PROPERTY_CURRENT_ITERATION_TIMESTEP = "iterationTimestepsExecuted";
    public static final String PROPERTY_TOTAL_TIMESTEPS = "timestepsSinceLastReset";
    private long _replicantCount;
    private long _iterationCount;
    public static final String PROPERTY_CURRENT_REPLICANT = "currentReplicant";
    private static final DecimalFormat DP0 = new DecimalFormat("0");
    private ExperimentState _experimentState;
    private Map _customProperties = new HashMap();

    private long _replicateTime;
}
 
