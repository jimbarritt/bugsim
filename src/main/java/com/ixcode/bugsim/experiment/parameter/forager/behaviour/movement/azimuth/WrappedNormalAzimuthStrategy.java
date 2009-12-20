/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.agent.motile.movement.WrappedCauchyAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.WrappedNormalAzimuthGenerator;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 2:48:53 PM by jim
 */
public class WrappedNormalAzimuthStrategy extends AzimuthStrategyBase {

     public static StrategyDefinitionParameter createDefaultStrategyS() {
         return WrappedNormalAzimuthStrategy.createStrategyS(20, 360, 1, 1e-05d);
    }

    public static StrategyDefinitionParameter createStrategyS(double angleOfTurnSD, double resolutionN, double visualNoiseThreshold, double tolerance) {
        StrategyDefinitionParameter azimuthS = new StrategyDefinitionParameter(STRATEGY_NAME, WrappedNormalAzimuthGenerator.class.getName());
        AzimuthStrategyBase.addParameters(azimuthS, visualNoiseThreshold);
        azimuthS.addParameter(new Parameter(P_ANGLE_OF_TURN_SD, angleOfTurnSD));
        azimuthS.addParameter(new Parameter(P_TOLERANCE, tolerance));
        azimuthS.addParameter(new Parameter(P_RESOLUTION_N, resolutionN));
        return azimuthS;
    }

    public WrappedNormalAzimuthStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public WrappedNormalAzimuthStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public double getAngleOfTurnSD() {
        return super.getParameter(WrappedNormalAzimuthStrategy.P_ANGLE_OF_TURN_SD).getDoubleValue();
    }

    public void setAngleOfTurnSD(double sd) {
        super.getParameter(WrappedNormalAzimuthStrategy.P_ANGLE_OF_TURN_SD).setValue(sd);
    }

    public double getResolutionN() {
        return super.getParameter(WrappedNormalAzimuthStrategy.P_RESOLUTION_N).getDoubleValue();
    }

    public void setResolutionN(double N) {
         super.getParameter(WrappedNormalAzimuthStrategy.P_RESOLUTION_N).setValue(N);
    }

    public Parameter getAngleOfTurnP() {
        return super.getParameter(WrappedNormalAzimuthStrategy.P_ANGLE_OF_TURN_SD);
    }


    public void setTolerance(double tolerance) {
        super.getParameter(P_TOLERANCE).setValue(tolerance);
    }

    public double getTolerance() {
        return super.getParameter(P_TOLERANCE).getDoubleValue();
    }

    // double N, double sd, double mu, double tolerance
    public static final String STRATEGY_NAME = "wrappedNormal";

    public static final String P_RESOLUTION_N = "resolutionN";
    public static final String P_ANGLE_OF_TURN_SD = "angleOfTurnSD";
    public static final String P_TOLERANCE = "tolerance";

}
