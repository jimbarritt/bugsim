/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.model.agent.physical;

import com.ixcode.bugsim.agent.cabbage.ForagerEgg;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Mar 2, 2007 @ 8:56:49 PM by jim
 */
public interface ILarvalMortalityStrategy extends IParameterisedStrategy {
    boolean isLarvaeDead(ForagerEgg egg);
}
