/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.population.release;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.simulation.model.agent.motile.release.ReleaseFixedLocationStrategy;
import com.ixcode.framework.simulation.model.agent.motile.release.ReleasePredefinedStrategy;
import com.ixcode.framework.simulation.model.agent.motile.release.ReleaseRandomAroundBorderStrategy;
import com.ixcode.framework.simulation.model.agent.motile.release.ReleaseRandomInZoneStrategy;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 4, 2007 @ 9:03:54 PM by jim
 * @deprecated 
 */

public class ReleaseStrategyFactory {


    public static StrategyRegistry getRegistry() {
        return ReleaseStrategyFactory.REGISTRY;
    }

    public static ReleaseStrategyBase createReleaseStrategy(StrategyDefinitionParameter releaseS, ParameterMap parameterMap, boolean forwardEvents) {

        return (ReleaseStrategyBase)getRegistry().constructStrategy(releaseS, parameterMap, forwardEvents);

    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {

        ReleaseStrategyFactory.REGISTRY.registerStrategy(FixedLocationReleaseStrategy.class, ReleaseFixedLocationStrategy.class, FixedLocationReleaseStrategy.S_FIXED_LOCATION, "Fixed Location");
        ReleaseStrategyFactory.REGISTRY.registerStrategy(PredefinedReleaseStrategy.class, ReleasePredefinedStrategy.class, PredefinedReleaseStrategy.S_PREDEFINED, "Predefined");
        ReleaseStrategyFactory.REGISTRY.registerStrategy(RandomBoundaryReleaseStrategy.class, ReleaseRandomAroundBorderStrategy.class, RandomBoundaryReleaseStrategy.S_RANDOM_BOUNDARY, "Random Boundary");        
        ReleaseStrategyFactory.REGISTRY.registerStrategy(RandomZoneReleaseStrategy.class, ReleaseRandomInZoneStrategy.class, RandomZoneReleaseStrategy.S_RANDOM_IN_ZONE, "Random Zone");

    }
}
