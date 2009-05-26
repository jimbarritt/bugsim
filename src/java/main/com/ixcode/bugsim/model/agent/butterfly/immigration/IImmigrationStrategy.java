/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.agent.butterfly.immigration;

import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.bugsim.model.agent.butterfly.immigration.pattern.IImmigrationPattern;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Feb 26, 2007 @ 8:54:15 PM by jim
 */
public interface IImmigrationStrategy extends IParameterisedStrategy {
    boolean hasImmigration(int time);

    IImmigrationPattern getImmigration(int time);
}
