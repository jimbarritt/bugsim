/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.simulation.model.agent.motile.movement.GaussianAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.VonMisesAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.WrappedCauchyAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.WrappedNormalAzimuthGenerator;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 2:49:24 PM by jim
 */
public class AzimuthStrategyFactory {


    public static StrategyRegistry getRegistry() {
        return REGISTRY;
    }

    public static AzimuthStrategyBase createAzimuthStrategy(StrategyDefinitionParameter azimuthS, ParameterMap params, boolean forwardEvents) {
        return (AzimuthStrategyBase)getRegistry().constructStrategy(azimuthS, params, forwardEvents);

    }

    public static StrategyDefinition createDefaultAzimuthStrategy(String strategyClassName, ParameterMap parameterMap) {
        return getRegistry().createDefaultStrategy(strategyClassName, parameterMap);
    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();

    static {
        REGISTRY.registerStrategy(GaussianAzimuthStrategy.class,  GaussianAzimuthGenerator.class,  GaussianAzimuthStrategy.STRATEGY_NAME, "Gaussian (Normal)");
        REGISTRY.registerStrategy(VonMisesAzimuthStrategy.class,  VonMisesAzimuthGenerator.class,  VonMisesAzimuthStrategy.STRATEGY_NAME, "Von Mises");
        REGISTRY.registerStrategy(WrappedCauchyAzimuthStrategy.class,  WrappedCauchyAzimuthGenerator.class,  WrappedCauchyAzimuthStrategy.STRATEGY_NAME, "Wrapped Cauchy");
        REGISTRY.registerStrategy(WrappedNormalAzimuthStrategy.class,  WrappedNormalAzimuthGenerator.class,  WrappedNormalAzimuthStrategy.STRATEGY_NAME, "Wrapped Normal");
    }


}
