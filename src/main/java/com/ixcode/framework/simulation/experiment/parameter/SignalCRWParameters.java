/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.MixedModelSignalAndDesireRandomWalk;
import com.ixcode.framework.simulation.model.agent.physical.AgentBehaviour;
import com.ixcode.bugsim.experiment.parameter.ButterflyParameters;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class SignalCRWParameters {



    public static boolean getUseSoftMaximum(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(USE_SOFT_MAX).getBooleanValue();
    }



    public static String getMotivationName(StrategyDefinitionParameter strategyP) {
            return strategyP.findParameter(P_MOTIVATION_NAME).getStringValue();
        }

    public static StrategyDefinitionParameter createSignalCRWStrategyP(String motivationName) {
        StrategyDefinitionParameter signalCRW =  new StrategyDefinitionParameter(S_RANDOMWALK, MixedModelSignalAndDesireRandomWalk.class.getName());
        signalCRW.addParameter(new Parameter(USE_SOFT_MAX, true));
        signalCRW.addParameter(new Parameter(P_MOTIVATION_NAME, motivationName));
        return signalCRW;
    }




    private static final String USE_SOFT_MAX = "useSoftMax";
    private static final String S_RANDOMWALK = "mixedModelSignalBasedRandomWalk";
    private static final String P_MOTIVATION_NAME = "motivationName";
}
