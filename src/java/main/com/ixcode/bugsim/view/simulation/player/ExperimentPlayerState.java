/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.player;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentPlayerState extends TypeSafeEnum {

    public static final ExperimentPlayerState READY = new ExperimentPlayerState("ready");
    public static final ExperimentPlayerState PAUSED = new ExperimentPlayerState("paused");
    public static final ExperimentPlayerState RUNNING = new ExperimentPlayerState("running");
    public static final ExperimentPlayerState COMPLETE = new ExperimentPlayerState("complete");

    protected ExperimentPlayerState(String name) {
        super(name);
    }
}
