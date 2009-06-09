/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.model;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Mar 1, 2007 @ 5:21:04 PM by jim
 */
public interface ITimestepListener {

    void beforeTimestepExecuted(Simulation simulation);

    void timestepCompleted(Simulation simulation);

}
