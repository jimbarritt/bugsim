/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.butterfly.immigration;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.bugsim.agent.butterfly.immigration.pattern.IImmigrationPattern;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 26, 2007 @ 8:55:03 PM by jim
 */
public class ScheduledImmigrationStrategy implements IImmigrationStrategy {

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {

    }

    public String getParameterSummary() {
        return null;
    }

    public boolean hasImmigration(int time) {
        return false;
    }

    public IImmigrationPattern getImmigration(int time) {
        return null;
    }

}
