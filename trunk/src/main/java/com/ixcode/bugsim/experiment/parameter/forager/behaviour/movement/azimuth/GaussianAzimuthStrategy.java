/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.motile.movement.GaussianAzimuthGenerator;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 3:08:32 PM by jim
 */
public class GaussianAzimuthStrategy extends AzimuthStrategyBase {

    public static StrategyDefinitionParameter createDefaultStrategyS() {
         return createStrategyS(20d, 1);
    }

    public static StrategyDefinitionParameter createStrategyS(double standardDeviation, double visualNoisethreshold) {
        StrategyDefinitionParameter azimuthS = new StrategyDefinitionParameter(STRATEGY_NAME, GaussianAzimuthGenerator.class.getName());
        AzimuthStrategyBase.addParameters(azimuthS, visualNoisethreshold);
        azimuthS.addParameter(new Parameter(P_ANGLE_OF_TURN_SD, standardDeviation));

        return azimuthS;
    }
    public GaussianAzimuthStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public GaussianAzimuthStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public double getStandardDeviation() {
        return super.getParameter(P_ANGLE_OF_TURN_SD).getDoubleValue();
    }

    public void setStandardDeviation(double standardDeviation) {
        super.getParameter(P_ANGLE_OF_TURN_SD).setValue(standardDeviation);
    }

    public Parameter getAngleOfTurnP() {
        return super.getParameter(P_ANGLE_OF_TURN_SD);
    }

    public  static final String STRATEGY_NAME = "gaussian";
    public static final String P_ANGLE_OF_TURN_SD = "standardDeviation";
}
