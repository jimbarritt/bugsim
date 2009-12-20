/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.motile.movement.VonMisesAzimuthGenerator;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 2:48:53 PM by jim
 */
public class VonMisesAzimuthStrategy extends AzimuthStrategyBase {

     public static StrategyDefinitionParameter createDefaultStrategyS() {
         return createStrategyS(4, 360, 1, 100);
    }

    public static StrategyDefinitionParameter createStrategyS(double angleOfTurnK, double resolutionN, double visualNoiseThreshold, double maxK) {
        StrategyDefinitionParameter azimuthS = new StrategyDefinitionParameter(STRATEGY_NAME, VonMisesAzimuthGenerator.class.getName());
        AzimuthStrategyBase.addParameters(azimuthS, visualNoiseThreshold);
        azimuthS.addParameter(new Parameter(P_ANGLE_OF_TURN_K, angleOfTurnK));
        azimuthS.addParameter(new Parameter(P_MAX_K, maxK));
        azimuthS.addParameter(new Parameter(P_RESOLUTION_N, resolutionN));
        return azimuthS;
    }

    public VonMisesAzimuthStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public VonMisesAzimuthStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public double getAngleOfTurnK() {
        return super.getParameter(P_ANGLE_OF_TURN_K).getDoubleValue();
    }

    public void setAngleOfTurnK(double K) {
        super.getParameter(P_ANGLE_OF_TURN_K).setValue(K);
    }

    public double getResolutionN() {
        return super.getParameter(P_RESOLUTION_N).getDoubleValue();
    }

    public void setResolutionN(double N) {
         super.getParameter(P_RESOLUTION_N).setValue(N);
    }

    public double getMaxK() {
        return super.getParameter(P_MAX_K).getDoubleValue();
    }

    public void setMaxK(double maxK) {
        super.getParameter(P_MAX_K).setValue(maxK);
    }

    public Parameter getAngleOfTurnP() {
        return super.getParameter(P_ANGLE_OF_TURN_K);
    }

    public boolean isUseCainMethod() {
        if (!super.hasParameter(P_USE_CAIN_METHOD)) {
            super.getStrategyS().addParameter(new Parameter(P_USE_CAIN_METHOD, false));
        }
        return super.getParameter(P_USE_CAIN_METHOD).getBooleanValue();
    }

    public void setUseCainMethod(boolean useCain) {
         if (!super.hasParameter(P_USE_CAIN_METHOD)) {
            super.getStrategyS().addParameter(new Parameter(P_USE_CAIN_METHOD, useCain));
        } else {
             super.getParameter(P_USE_CAIN_METHOD).setValue(useCain);
         }

    }


    public static final String STRATEGY_NAME = "vonMises";
    public static final String P_ANGLE_OF_TURN_K = "angleOfTurnK";
    public static final String P_MAX_K = "maxK";
    public static final String P_RESOLUTION_N = "resolutionN";
    public static final String P_USE_CAIN_METHOD = "useCainMethod";
}
