/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.sensor.visual;

import com.ixcode.bugsim.model.agent.butterfly.FieldOfViewVisualStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 2:49:24 PM by jim
 */
public class VisualSensorStrategyFactory {


    public static StrategyRegistry getRegistry() {
        return VisualSensorStrategyFactory.REGISTRY;
    }

    public static VisualSensorStrategyBase createVisualSensorStrategy(StrategyDefinitionParameter moveLengthS, ParameterMap params, boolean forwardEvents) {
        return (VisualSensorStrategyBase)VisualSensorStrategyFactory.getRegistry().constructStrategy(moveLengthS, params, forwardEvents);

    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();

    static {
        REGISTRY.registerStrategy(FieldOfViewVisualStrategyDefinition.class,  FieldOfViewVisualStrategy.class,  FieldOfViewVisualStrategyDefinition.S_FIELD_OF_VIEW_VISION, "Field Of View");

    }


}
