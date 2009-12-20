/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.agent.motile.movement.WrappedNormalAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.WrappedCauchyAzimuthGenerator;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 2:48:53 PM by jim
 */
public class WrappedCauchyAzimuthStrategy extends AzimuthStrategyBase {

     public static StrategyDefinitionParameter createDefaultStrategyS() {
         return WrappedCauchyAzimuthStrategy.createStrategyS(4, 360, 1, 100);
    }

    public static StrategyDefinitionParameter createStrategyS(double angleOfTurnK, double resolutionN, double visualNoiseThreshold, double maxK) {
        StrategyDefinitionParameter azimuthS = new StrategyDefinitionParameter(STRATEGY_NAME, WrappedCauchyAzimuthGenerator.class.getName());
        AzimuthStrategyBase.addParameters(azimuthS, visualNoiseThreshold);
        azimuthS.addParameter(new Parameter(P_ANGLE_OF_TURN_RHO, angleOfTurnK));
        azimuthS.addParameter(new Parameter(P_MAX_RHO, maxK));
        azimuthS.addParameter(new Parameter(P_RESOLUTION_N, resolutionN));
        return azimuthS;
    }

    public WrappedCauchyAzimuthStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public WrappedCauchyAzimuthStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public double getAngleOfTurnRho() {
        return super.getParameter(P_ANGLE_OF_TURN_RHO).getDoubleValue();
    }

    public void setAngleOfTurnRho(double K) {
        super.getParameter(P_ANGLE_OF_TURN_RHO).setValue(K);
    }

    public double getResolutionN() {
        return super.getParameter(P_RESOLUTION_N).getDoubleValue();
    }

    public void setResolutionN(double N) {
         super.getParameter(P_RESOLUTION_N).setValue(N);
    }

    public double getMaxRho() {
        return super.getParameter(P_MAX_RHO).getDoubleValue();
    }

    public void setMaxRho(double maxK) {
        super.getParameter(P_MAX_RHO).setValue(maxK);
    }

    public Parameter getAngleOfTurnP() {
        return super.getParameter(WrappedCauchyAzimuthStrategy.P_ANGLE_OF_TURN_RHO);
    }


    // double N, double k, double mu
    public static final String STRATEGY_NAME = "vonMises";
    public static final String P_ANGLE_OF_TURN_RHO = "angleOfTurnRho";
    public static final String P_MAX_RHO = "maxRho";
    public static final String P_RESOLUTION_N = "resolutionN";
}
