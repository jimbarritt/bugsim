/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.sensor.olfactory;

import com.ixcode.bugsim.agent.butterfly.SignalSensorOlfactoryStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 2:49:24 PM by jim
 */
public class OlfactorySensorStrategyFactory {


    public static StrategyRegistry getRegistry() {
        return OlfactorySensorStrategyFactory.REGISTRY;
    }

    public static OlfactorySensorStrategyBase createOlfactorySensorStrategy(StrategyDefinitionParameter moveLengthS, ParameterMap params, boolean forwardEvents) {
        return (OlfactorySensorStrategyBase)OlfactorySensorStrategyFactory.getRegistry().constructStrategy(moveLengthS, params, forwardEvents);

    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();

    static {
        OlfactorySensorStrategyFactory.REGISTRY.registerStrategy(SignalSensorOlfactoryStrategyDefinition.class,  SignalSensorOlfactoryStrategy.class,  SignalSensorOlfactoryStrategyDefinition.S_SIGNAL_SENSOR_OLFACTION, "Signal Sensor");

    }


}
