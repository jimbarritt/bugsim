/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public final class ExperimentState extends TypeSafeEnum {

    public static final ExperimentState READY = new ExperimentState("Ready");
    public static final ExperimentState PAUSED = new ExperimentState("Paused");
    public static final ExperimentState RUNNING = new ExperimentState("Running");
    public static final ExperimentState COMPLETE = new ExperimentState("Complete");
    public static final ExperimentState NOT_INITIALISED = new ExperimentState("Not Initialised");
    public static final ExperimentState REPORTING = new ExperimentState("Reporting");

    private ExperimentState(String name) {
        super(name);
    }



}
