/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.physical;



public interface IAdultMortalityStrategy {


    boolean isAlive(IPhysicalAgent agent);

    String getParameterSummary();
}
