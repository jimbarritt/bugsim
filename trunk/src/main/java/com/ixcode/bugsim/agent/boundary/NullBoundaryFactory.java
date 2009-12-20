/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.boundary;

import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.Simulation;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class NullBoundaryFactory implements IParameterisedStrategy, IBoundaryAgentFactory {

    public void initialise(StrategyDefinitionParameter algorithmParameter, ParameterMap params, Map initialisationObjects) {

    }

    public String getParameterSummary() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IBoundaryAgent createBoundaryAgent(Simulation simulation) {
        return null;
    }

    
}
