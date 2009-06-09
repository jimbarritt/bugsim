/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

import java.util.Map;

/**
 *  Description : Use when you dont want to specify a strategy
 */
public class NullStrategyDefinitionParameter extends StrategyDefinitionParameter {

    public static final StrategyDefinitionParameter INSTANCE = new NullStrategyDefinitionParameter();

    public NullStrategyDefinitionParameter() {
        super("noStrategy", NullStrategyImplementation.class.getName());
    }


    private static class NullStrategyImplementation implements IParameterisedStrategy {

        public void initialise(StrategyDefinitionParameter algorithmParameter, ParameterMap params, Map initialisationObjects) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getParameterSummary() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    }

}
