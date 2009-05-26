/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.resource.layout.levy;

import com.ixcode.bugsim.model.agent.cabbage.layout.LevyWalkResourceLayout;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.ResourceLayoutStrategyBase;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;


/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 25, 2007 @ 11:46:53 AM by jim
 */
public class LevyWalkResourceLayoutStrategy extends ResourceLayoutStrategyBase {

    public LevyWalkResourceLayoutStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }


    public static void register(StrategyRegistry registry) {
        registry.registerStrategy(LevyWalkResourceLayoutStrategy.class, LevyWalkResourceLayout.class, S_LEVY_WALK, L_LEVY_WALK);
    }

    public static final String S_LEVY_WALK = "levyWalkLayout";
    public static final String L_LEVY_WALK = "Levy Walk";
}
