/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 */
public interface IMoveLengthGenerator  {

    double calculateMoveLength(IMotileAgent agent);

    String getParameterSummary();

    double getMoveLength();

    double getInitialMoveLength();
}
